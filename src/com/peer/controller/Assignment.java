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
import javax.validation.Valid;

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
	private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 1024;
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 1024;

	@RequestMapping(value="/submitwork",method= RequestMethod.POST)
	public ModelAndView SubmitAssignment(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub 		
		String aid = (String) request.getParameter("aid"); 
		System.out.println("SubmitAssignment assignmentid : "+aid);
		BeanClass student = (BeanClass) request.getSession().getAttribute("student");
		if(student==null) student = (BeanClass) request.getSession().getAttribute("reviewer");
		student.setAssignment_id(aid);
		HashMap<String, Integer> map = (HashMap<String, Integer>) request.getSession().getAttribute("map");
		System.out.println("map size: "+map.size());
		student.setCount(map.get(aid));
		student = Database.checkCount(student);		
		ModelAndView mv = new ModelAndView("upload");	
		mv.addObject("headermsg", "Assignment");
		return mv;
	}


	@RequestMapping(value="/studentPage",method= RequestMethod.POST)
	protected ModelAndView studentPage(HttpServletRequest request) throws Exception{
		BeanClass student = (BeanClass) request.getSession().getAttribute("student");
		System.out.println("studentPage method");
		if(student==null) student = (BeanClass) request.getSession().getAttribute("reviewer");
		System.out.println("EvaluateAssignment student.getUsername() : "+student.getUsername());
		ModelAndView mv = new ModelAndView("displayStudent");	
		HashMap<String, Integer> map = Database.getAssignmentIds(student);
		System.out.println("list size: "+map.size());
		mv.addObject("reviewheader", "Review");
		mv.addObject("list",map.keySet());
		mv.addObject("listSize",map.size());
		mv.addObject("headermsg", "Upload/View Assignment");
		request.getSession().setAttribute("map",map);
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
		String uploadFolder = Database.getValues("uploadFolder"); 
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// Set overall request size constraint
		upload.setSizeMax(MAX_REQUEST_SIZE);        
		BeanClass student = (BeanClass) request.getSession().getAttribute("student");
		System.out.println("student aid : "+student.getAssignment_id());
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
			System.out.println(" group id : "+student.getGroup_id());
			student = Database.UserDataUploadtoDB(student);
			System.out.println("upload: submission date "+student.getSubmission_date());
			ModelAndView mv = viewAssignment(request);
			return mv;

		} catch (FileUploadException ex) {
			ModelAndView mv = new ModelAndView("WelcomePage");
			mv.addObject("uploadmsg","Upload failed! Please contact admin");
			return mv;
		} catch (Exception ex) {
			ModelAndView mv = new ModelAndView("WelcomePage");
			mv.addObject("uploadmsg","Upload failed! Please contact admin");
			return mv;
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
		System.out.println(" viewAssignment method");
		String aid = (String) request.getParameter("aid"); 
		System.out.println(" assignmentid : "+aid);
		BeanClass student = (BeanClass) request.getSession().getAttribute("student");
		if(student==null) student = (BeanClass) request.getSession().getAttribute("reviewer");
		student.setAssignment_id(aid);
		ModelAndView mv = new ModelAndView("viewAssignment");	
		if(student.getImagefile()==null){
			student = Database.RetrieveInfo(student);
			//System.out.println("Review criteria to display criteria : "+student.getToDisplay().get(0).getCriteria());
			//System.out.println(" marks: "+student.getReviewCriteria().get(0).getCriteria());
		}
		mv.addObject("reviewheader", "Review");
		mv.addObject("headermsg", "View Assignment");
		return mv;
	}
	@RequestMapping(value="/eassignment",method= RequestMethod.POST)
	protected ModelAndView EvaluateAss(HttpServletRequest request) throws Exception{
		BeanClass student = (BeanClass) request.getSession().getAttribute("student");
		System.out.println("EvaluateAss: "+student);
		if(student==null) student = (BeanClass) request.getSession().getAttribute("reviewer");
		System.out.println("EvaluateAssignment student.getUsername() : "+student.getUsername());

		ModelAndView mv = new ModelAndView("selectAssign");
		HashMap<String, Integer> map = Database.getAssignmentIds(student);
		System.out.println("list size: "+map.size());
		mv.addObject("list",map.keySet());
		mv.addObject("listSize",map.size());
		mv.addObject("headermsg", "Evaluate Assignment");
		return mv;
	}	

	@RequestMapping(value="/evalassignment",method= RequestMethod.POST)
	protected ModelAndView EvaluateAssignment(HttpServletRequest request) throws Exception{
		String aid = (String) request.getParameter("assid"); 
		System.out.println("EvaluateAssignment assignmentid : "+aid);
		BeanClass student = (BeanClass) request.getSession().getAttribute("student");
		if(student==null) student = (BeanClass) request.getSession().getAttribute("reviewer");
		System.out.println("EvaluateAssignment student.getUsername() : "+student.getUsername());
		if(aid!=null ){
			student.setAssignment_id(aid);
		}
		student = Database.GetPeerInfo(student);
		HashMap<String, BeanClass> peerObj = student.getPeerList();
		if(peerObj!=null)
		student.setCount(peerObj.size());
		request.getSession().setAttribute("student", student);
		//System.out.println("EvaluateAssignment peer size: "+peerObj.size());
		ModelAndView mv = new ModelAndView("selectPeer");
		mv.addObject("headermsg", "Peer Evaluation");
		mv.addObject("mode","student");
		mv.addObject("peerObj", peerObj);
		return mv;
	}

	@RequestMapping(value="/peerWork/{stud}",method= RequestMethod.GET)
	protected ModelAndView review(@PathVariable("stud") String uname,HttpServletRequest request) throws Exception{
		System.out.println("review method");
		System.out.println("peer name: "+uname);
		ModelAndView mv = new ModelAndView("evaluate");
		BeanClass reviewer = (BeanClass) request.getSession().getAttribute("student");
		if(reviewer==null) reviewer = (BeanClass) request.getSession().getAttribute("reviewer");
		reviewer.setToDisplay(reviewer.getReviewCriteria());
		BeanClass peer = null;
		HashMap<String, BeanClass> peerObj = reviewer.getPeerList();
		System.out.println("peers size: "+peerObj.size());	
		System.out.println("operationParameterses size : "+reviewer.getReviewCriteria().size());
		if(peerObj.size()>0){
			peer = peerObj.get(uname);
			
			System.out.println("Teacher marks of peer : "+peer.getTeacherTotMarks());
			request.getSession().setAttribute("peer", peer);
			if(peer.getReviewCriteria()!=null){
				System.out.println("size of marks list: "+peer.getReviewCriteria().size());
			}
		}
		request.getSession().setAttribute("reviewer", reviewer);
		//request.getSession().removeAttribute("student");		
		System.out.println("review method peer id: "+peer.getUsername());
		request.getSession().setAttribute("mode","student" );
		mv.addObject("mode","student");
		mv.addObject("peer", peer);
		mv.addObject("reviewer",reviewer);
		mv.addObject("headermsg", "Evaluate Peer Assignment");
		return mv;
	}

	@RequestMapping(value="/saveMarks",method= RequestMethod.POST)
	protected ModelAndView saveMarks(@Valid @ModelAttribute("teacher") BeanTeacher teacher1, HttpServletRequest request) throws Exception{
		String mode = (String) request.getSession().getAttribute("mode");
		System.out.println("saveMarks method mode: "+mode);
		ModelAndView mv = null;
		String peerId = request.getParameter("peerId");	
		System.out.println("peer id : "+peerId);
		List<OperationParameters> marks = teacher1.getOperationParameterses();
		System.out.println("operations : "+marks.get(0).getCriteria() +marks.get(0).getWeight());
		BeanClass student = (BeanClass) request.getSession().getAttribute("student");
		if(student==null) student = (BeanClass) request.getSession().getAttribute("reviewer");
		if(Teacher.checkWeights(teacher1,student)){
			if(mode.equals("teacher")){
				BeanTeacher teacher = (BeanTeacher) request.getSession().getAttribute("teacher");
				Database.UploadMarks(teacher.getUsername(),peerId,marks,mode,teacher.getGroupid(),""+teacher.getAssignment_id());
				teacher = Database.GetStudentsInfo(teacher);
				//teacher = Database.GetStudentsInfo(teacher);
				//System.out.println("student 1 teacher evaluation : "+teacher.getStudentList().get("1").getMarks().get(0).isTeacher_evaluation());
				request.getSession().setAttribute("teacher", teacher);
				System.out.println("teacher assign ids are: "+teacher.getAssignids());
				mv = new ModelAndView("viewStudents");
				mv.addObject("fAid",teacher.getAssignment_id());	
				mv.addObject("teacher",teacher);
			}else{			
				BeanClass reviewer =  (BeanClass) request.getSession().getAttribute("reviewer");
				System.out.println("save marks: "+reviewer.getUsername());
				request.getSession().setAttribute("student", reviewer);
				//request.getSession().removeAttribute("reviewer");
				BeanClass peer = reviewer.getPeerList().get(peerId);			
				System.out.println("peer id from session: "+peer.getUsername());
				Database.UploadMarks(reviewer.getUsername(),peerId,marks,mode,reviewer.getGroup_id(),reviewer.getAssignment_id());
				mv = new ModelAndView("selectPeer");
				mv = EvaluateAssignment(request);
			}
		}else{
			mv = new ModelAndView("evaluate");
			BeanClass reviewer = (BeanClass) request.getSession().getAttribute("student");
			if(reviewer==null) reviewer = (BeanClass) request.getSession().getAttribute("reviewer");
			BeanClass peer = (BeanClass) request.getSession().getAttribute("peer");
			mv.addObject("mode","student");
			mv.addObject("student", peer);
			mv.addObject("reviewer",reviewer);
			mv.addObject("headermsg", "Evaluate Peer Assignment");
			if(mode.equals("teacher")){
				System.out.println("!!!!!!!");
				mv = new ModelAndView("metaReview");
				BeanTeacher teacher = (BeanTeacher) request.getSession().getAttribute("teacher");
				mv.addObject("teacher",teacher);
				mv.addObject("student",student);
				mv.addObject("reviewheader", "Evaluate");
				mv.addObject("headermsg", "Review Assignment");
			}
			
			mv.addObject("customMsg", "Please enter correct inputs in review panel");
		}
		return mv;
	}


}
