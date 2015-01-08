package com.peer.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.tomcat.util.codec.binary.Base64;
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
	protected ModelAndView upload(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

		// Check that we have a file upload request
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
					student.setImagefile("C:/Users/tejj/Downloads/Ravi/q_Assign1.png");
					if(filePath.contains(".png")||filePath.contains(".jpg")){
						student.setImagefile(filePath);
						
					}
					if(filePath.contains(".txt") ||filePath.contains(".doc")){
						student.setContentFile(filePath);
					}
					
					File uploadedFile = new File(filePath);
					// saves the file to upload directory
					item.write(uploadedFile);
				}
			}
			//ArrayList
			ArrayList<String> demo = new ArrayList<String>();
			demo.add("Worst!");demo.add("best!");demo.add("Worst!");demo.add("best!");demo.add("Worst!");demo.add("best!");demo.add("Worst!");demo.add("best!");
			student.setReview(demo);
			
			ModelAndView mv = viewAssignment(request,response);
			//            ModelAndView mv = new ModelAndView("WelcomePage");	
			//            mv.addObject("headermsg", "PEER REVIEW");
			//            mv.addObject("uploadmsg", "File uploaded successfully!");
			//            mv.addObject("student", student);
			return mv;

		} catch (FileUploadException ex) {
			throw new ServletException(ex);
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	@RequestMapping(value="/vassignment",method= RequestMethod.POST)
	protected ModelAndView viewAssignment(HttpServletRequest request,HttpServletResponse response) throws Exception{

		BeanClass student = (BeanClass) request.getSession().getAttribute("student");
		String everything ="";
		ModelAndView mv = new ModelAndView("viewAssignment");	
		//System.out.println("view assignment: student.getImagefile() "+student.getImagefile());
		if(student.getImagefile()!=null){
			String encodedString = GetEncodedString(student.getImagefile());
			mv.addObject("ImageFile", encodedString);
		}
		System.out.println("view assignment: student.getContentFile() "+student.getContentFile());
		if(student.getContentFile()!=null){
			/**Method call to read the document (demonstrate some useage of POI)**/
			//everything = readMyDocument(student.getContentFile());
			
			
			File f=new File(student.getContentFile()); 
			String filePath=f.getPath();
			
			BufferedReader br = new BufferedReader(new FileReader(filePath));
		    try {
		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();
		        while (line != null) {
		            sb.append(line);
		            sb.append(System.lineSeparator());
		            line = br.readLine();
		        }
		        everything  = sb.toString();
		        
		    } finally {
		        br.close();
		    }
			
			
		}
		mv.addObject("reviewheader", "Review");
        mv.addObject("ContentFile", everything);
		mv.addObject("headermsg", "View Assignment");
		
		return mv;

	}
	private String readMyDocument(String fileName) {
		POIFSFileSystem fs = null;
		StringBuilder sb = new StringBuilder();
        try {
            fs = new POIFSFileSystem(new FileInputStream(fileName));
            
            HWPFDocument doc = new HWPFDocument(fs);
            WordExtractor we = new WordExtractor(doc);

            /**Get the total number of paragraphs**/
            String[] paragraphs = we.getParagraphText();
            System.out.println("Total Paragraphs: "+paragraphs.length);

            for (int i = 0; i < paragraphs.length; i++) {
            	System.out.println(paragraphs[i].toString());
                sb.append(paragraphs[i].toString());
                sb.append(System.lineSeparator());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
		return sb.toString();
	}

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
}
