package com.peer.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Assignment {
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
	private static final String DATA_DIRECTORY = "data";
	private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
	private static final int MAX_REQUEST_SIZE = 1024 * 1024;

	@RequestMapping(value="/submitwork",method= RequestMethod.GET)
	public ModelAndView SubmitAssignment() throws Exception {
		// TODO Auto-generated method stub 		
		ModelAndView mv = new ModelAndView("upload");	
		mv.addObject("headermsg", "Assignment");
		return mv;
	}

	@RequestMapping(value="/upload",method= RequestMethod.POST)
	protected ModelAndView upload(HttpServletRequest request) throws ServletException, IOException {
		//			file:///C:/Users/tejj/Desktop/link.html
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			ModelAndView mv = new ModelAndView("upload");	
			mv.addObject("headermsg", "Assignment");
			return mv;
		}
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// Sets the size threshold beyond which files are written directly to
		// disk.
		factory.setSizeThreshold(MAX_MEMORY_SIZE);
		// Sets the directory used to temporarily store files that are larger
		// than the configured size threshold. We use temporary directory for
		// java
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		// constructs the folder where uploaded file will be stored
		String uploadFolder = "C:/Users/tejj/Downloads/Ravi";
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// Set overall request size constraint
		upload.setSizeMax(MAX_REQUEST_SIZE);        
		BeanClass student = (BeanClass) request.getSession().getAttribute("student");
		try {
			// Parse the request
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField()) {
					String fileName = new File(item.getName()).getName();
					String filePath = uploadFolder + "/" + student.getUsername()+"_"+fileName;
					System.out.println(filePath.toString());
					if(filePath.contains(".zip") ){
						student.setZipFile(filePath);
					}
					File uploadedFile = new File(filePath);
					// saves the file to upload directory
					item.write(uploadedFile);
				}else{
					String link = item.getFieldName();
					String lvalue = item.getString();
					if(lvalue!=null){
						student.setLink(lvalue);
					}
				}
			}		 
			student = HTMLParser(student);
			student = Words_Chars(student);

			student = Database.UserDataUploadtoDB(student);
			System.out.println("upload: submission date "+student.getSubmission_date());
			ModelAndView mv = viewAssignment(request);
			return mv;

		} catch (FileUploadException ex) {
			throw new ServletException(ex);
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	private BeanClass Words_Chars(BeanClass student) {
		// TODO Auto-generated method stub
		char[] chars = student.getContent().toCharArray();
		student.setCharCount(chars.length);
		String[] wordCount = student.getContent().split(" ");
		student.setWordCount(wordCount.length);
		System.out.println("char count: "+chars.length+ " : word count : "+wordCount.length);
		return student;
	}

	private BeanClass HTMLParser(BeanClass student) throws IOException {
		// TODO Auto-generated method stub
		//Images part
		int images = 0;
		Document document = Jsoup.connect(student.getLink()).get();

		Elements media = document.select("[src]");
		StringBuilder img = new StringBuilder();
		ArrayList<String> imageFiles = new ArrayList<String>();
		for (Element src : media) {
			if (src.tagName().equals("img")){	
				images++;
				img.append(src.attr("abs:src"));img.append(",");
				imageFiles.add(src.attr("abs:src"));
			}
			else
				System.out.println("else img: "+src.attr("abs:src"));
		}
		student.setImages(imageFiles);
		System.out.println("First image: "+student.getImages().get(0));
		student.setImagefile(img.toString());
		student.setImagesNumber(images);		
		student.setFullContext(document.toString());
		student = Database.GetHTMLBody(student);
		//System.out.println(">>>>>Fulltext<<<<\n "+document.toString());
		return student;
	}

	@RequestMapping(value="/vassignment",method= RequestMethod.POST)
	protected ModelAndView viewAssignment(HttpServletRequest request) throws Exception{
		BeanClass student = (BeanClass) request.getSession().getAttribute("student");
		ModelAndView mv = new ModelAndView("viewAssignment");	

		if(student.getImagefile()==null){
			student = Database.RetrieveInfo(student);			
		}
		int i=2;
		if(student.getMarks()!=null){
			for(BeanMarks marks : student.getMarks()){
				if(!marks.isTeacher_evaluation()){
					mv.addObject("marks"+i,marks); i++;
				}else{
					mv.addObject("marks1",marks); 
				}
			}
		}

		System.out.println(">>>>>text<<<<\n "+student.getContent());
		mv.addObject("reviewheader", "Review");
		mv.addObject("headermsg", "View Assignment");
		return mv;
	}

	@RequestMapping(value="/eassignment",method= RequestMethod.POST)
	protected ModelAndView EvaluateAssignment(HttpServletRequest request) throws Exception{

		BeanClass student = (BeanClass) request.getSession().getAttribute("student");
		System.out.println("EvaluateAssignment: "+student);
		if(student==null) student = (BeanClass) request.getSession().getAttribute("reviewer");
		System.out.println("EvaluateAssignment student.getUsername() : "+student.getUsername());
		HashMap<String, BeanClass> peerObj = Database.GetPeerInfo(student);
		student.setPeerList(peerObj);
		request.getSession().setAttribute("student", student);
		System.out.println("EvaluateAssignment peer size: "+peerObj.size());
		ModelAndView mv = new ModelAndView("selectPeer");
		mv.addObject("headermsg", "Peer Evaluation");
		mv.addObject("mode","student");
		mv.addObject("peerObj", peerObj);
		return mv;
	}

	@RequestMapping(value="/peerWork/{stud}",method= RequestMethod.GET)
	protected ModelAndView review(@PathVariable("stud") String uname,HttpServletRequest request) throws Exception{
		System.out.println("peer name: "+uname);
		ModelAndView mv = new ModelAndView("evaluate");
		BeanClass reviewer = (BeanClass) request.getSession().getAttribute("student");
		if(reviewer==null) reviewer = (BeanClass) request.getSession().getAttribute("reviewer");
		BeanClass peer = null;
		HashMap<String, BeanClass> peerObj = reviewer.getPeerList();
		System.out.println("peers size: "+peerObj.size());
		if(peerObj.size()>0){
			peer = peerObj.get(uname);
		}
		request.getSession().setAttribute("reviewer", reviewer);
		request.getSession().removeAttribute("student");		
		System.out.println("review method peer id: "+peer.getUsername());
		request.getSession().setAttribute("mode","student" );
		mv.addObject("mode","student");
		mv.addObject("student", peer);
		mv.addObject("headermsg", "Evaluate Peer Assignment");
		return mv;
	}

	public static String getValues(String inp) throws IOException{
		Properties prop = new Properties();
		InputStream input = new FileInputStream("C:/Users/tejj/Desktop/PeerTool/PeerTool/WebContent/WEB-INF/constants.properties");
		if(input==null) System.out.println("nullllllllll values in review class");
		// load a properties file
		prop.load(input);
		if(prop.get(inp)!=null) return (String) prop.get(inp);
		return null;
	}

	@RequestMapping(value="/saveMarks",method= RequestMethod.POST)
	protected ModelAndView saveMarks(@ModelAttribute("marks") BeanMarks marks, HttpServletRequest request) throws Exception{
		String mode = (String) request.getSession().getAttribute("mode");
		System.out.println("saveMarks method mode: "+mode);
		ModelAndView mv = null;
		String peerId = request.getParameter("peerId");		
		System.out.println("design Marks: "+marks.getDesign());
		System.out.println("peer Id: "+peerId);
		if(mode.equals("teacher")){
			BeanTeacher teacher = (BeanTeacher) request.getSession().getAttribute("teacher");
			Database.UploadMarks(teacher.getUsername(),peerId,marks,mode);
			teacher = Database.GetStudentsInfo(teacher);
			System.out.println("student 1 teacher evaluation : "+teacher.getStudentList().get("1").getMarks().get(0).isTeacher_evaluation());
			request.getSession().setAttribute("teacher", teacher);
			mv = new ModelAndView("viewStudents");
		}else{			
			BeanClass reviewer =  (BeanClass) request.getSession().getAttribute("reviewer");
			System.out.println("save marks: "+reviewer.getUsername());
			request.getSession().setAttribute("student", reviewer);
			//request.getSession().removeAttribute("reviewer");
			BeanClass peer = reviewer.getPeerList().get(peerId);			
			System.out.println("peer id from session: "+peer.getUsername());
			Database.UploadMarks(reviewer.getUsername(),peerId,marks,mode);
			mv = new ModelAndView("selectPeer");
			mv = EvaluateAssignment(request);
		}
		return mv;
	}


}
