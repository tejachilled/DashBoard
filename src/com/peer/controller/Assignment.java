package com.peer.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
			//ArrayList  
			ArrayList<String> demo = new ArrayList<String>();
			demo.add("Worst!");demo.add("best!");demo.add("Worst!");demo.add("best!");demo.add("Worst!");demo.add("best!");demo.add("Worst!");demo.add("best!");
			student.setReview(demo);
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
		System.out.println(">>>>>text<<<<\n "+student.getContent());
		mv.addObject("reviewheader", "Review");
		mv.addObject("headermsg", "View Assignment");
		return mv;
	}

	@RequestMapping(value="/eassignment",method= RequestMethod.POST)
	protected ModelAndView EvaluateAssignment(HttpServletRequest request) throws Exception{
		BeanClass student = (BeanClass) request.getSession().getAttribute("student");
		ArrayList<BeanClass> peer = Database.GetPeerInfo(student);
		System.out.println("EvaluateAssignment username: "+peer.get(0).getUsername());
		ModelAndView mv = new ModelAndView("evaluate");			
		mv.addObject("headermsg", "Peer Evaluation");
		mv.addObject("peer", peer);
		return mv;
	}

	/*	
	private String GetEncodedString(String imagefile) {
		File file = new File(imagefile);
		String encodedString = "";
		try{
			FileInputStream fis=new FileInputStream(file);
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			int b;
			byte[] buffer = new byte[1024];
			while((b=fis.read(buffer))!=-1){
				bos.write(buffer,0,b);
			}
			byte[] fileBytes=bos.toByteArray();
			fis.close();
			bos.close();


			byte[] encoded=Base64.encodeBase64(fileBytes);

			encodedString = new String(encoded);
		}catch(Exception e){ e.printStackTrace();}
		return encodedString;
	}


	private static void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	 */
}
