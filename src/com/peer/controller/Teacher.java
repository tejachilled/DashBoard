package com.peer.controller;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Teacher {	
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
	private static final String DATA_DIRECTORY = "data";
	private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
	private static final int MAX_REQUEST_SIZE = 1024 * 1024;

	@InitBinder
	public void initBinder(WebDataBinder binder) {

	}

	@RequestMapping(value="/assign",method= RequestMethod.POST)
	public ModelAndView Assign(HttpServletRequest request ) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Assign method");
		ModelAndView mv = new ModelAndView("criteriaAssign");
		return mv;
	}
	@RequestMapping(value="/createCriteria",method= RequestMethod.POST)
	public ModelAndView createCriteria(HttpServletRequest request ) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("createCriteria method");
		BeanClass student = new BeanClass();
		student = Database.TeacherInfo(student);
		BeanTeacher teacher = null;
		if(request.getSession().getAttribute("teacher")!=null){
			teacher = (BeanTeacher) request.getSession().getAttribute("teacher");
		}
		teacher.setGroups(student.getGroups());
		ModelAndView mv = new ModelAndView("TeacherAssign");
		return mv;
	}

	@RequestMapping(value="/submitReview",method= RequestMethod.POST)
	public ModelAndView submitReview(@Valid @ModelAttribute("teacher") BeanTeacher teacher1,HttpServletRequest request ) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("submitReview method");
		ModelAndView mv = new ModelAndView("TeacherAssign");
		BeanTeacher teacher = (BeanTeacher) request.getSession().getAttribute("teacher");
		System.out.println("submit review teacher groups list : "+teacher.getGroups());
		teacher1.setGroups(teacher.getGroups());
		System.out.println("submit review teacher groups list : "+teacher1.getGroups());
		if(checkWeights(teacher1)){
			mv = new ModelAndView("TeacherFirstPage");
			List<OperationParameters> param = teacher1.getOperationParameterses();
			System.out.println("operations : "+param.get(0).getCriteria() +param.get(0).getWeight());
			System.out.println("group Id: "+ teacher1.getGroupid() + " ass id: "+teacher1.getAssignment_id());
			teacher = Database.InsertReviewPannel(teacher1);
			mv.addObject("customMsg", "Successfully created an evaluation criteria");
		}else{
			mv.addObject("customMsg", "Please enter correct numeric inputs for weights in review panel");
		}

		return mv;
	}

	@RequestMapping(value="/modifyCriteria",method= RequestMethod.POST)
	public ModelAndView modifyCriteria(HttpServletRequest request ) throws Exception {
		System.out.println("modifyCriteria method");
		BeanTeacher teacher = null;
		if(request.getSession().getAttribute("teacher")!=null){
			teacher = (BeanTeacher) request.getSession().getAttribute("teacher");
		}
		teacher = Database.GetGroups(teacher);		
		ModelAndView mv = new ModelAndView("modifyCriteria");	
		return mv;
	}

	@RequestMapping(value="/changeCriteria",method= RequestMethod.POST)
	public ModelAndView changeCriteria(HttpServletRequest request ) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("ChangeCriteria method");
		String group = (String) request.getParameter("group_id");
		System.out.println("selected group is : "+group);
		BeanTeacher teacher = null;
		if(request.getSession().getAttribute("teacher")!=null){
			teacher = (BeanTeacher) request.getSession().getAttribute("teacher");
		}		
		teacher.setGroupid(group);
		teacher = Database.getAssigIds(teacher);
		ModelAndView mv = new ModelAndView("changeCriteria");
		mv.addObject("groupid", group);
		return mv;
	}
	@RequestMapping(value="/submitaid",method= RequestMethod.POST)
	public ModelAndView Submitaid(HttpServletRequest request ) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("ChangeCriteria method");
		String group = (String) request.getParameter("group_id");
		String assignid = (String) request.getParameter("assignid");
		System.out.println("selected group is : "+group);
		System.out.println("selected assignid is : "+assignid);
		BeanTeacher teacher = null;
		if(request.getSession().getAttribute("teacher")!=null){
			teacher = (BeanTeacher) request.getSession().getAttribute("teacher");
		}	
		if(assignid != null)
			teacher.setAssignment_id(Integer.parseInt(assignid));
		teacher = Database.getReviewCriteria(teacher);
		ModelAndView mv = new ModelAndView("changeCriteria");
		mv.addObject("groupid", group);
		mv.addObject("display","display");
		return mv;
	}
	@RequestMapping(value="/modifiedCriteria",method= RequestMethod.POST)
	public ModelAndView modifiedCriteria(@Valid @ModelAttribute("teacher") BeanTeacher teacher1,HttpServletRequest request ) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("modifiedCriteria method"); 
		ModelAndView mv = new ModelAndView("changeCriteria");
		BeanTeacher teacher = (BeanTeacher) request.getSession().getAttribute("teacher");
		System.out.println("teacher group id : "+teacher.getGroupid());
		System.out.println("teacher ass id : "+teacher.getAssignment_id());
		System.out.println("teacher random number : "+ teacher.getRandomNumber());
		System.out.println("teacher submission count : "+ teacher.getSubmissionCount());
		teacher1.setGroupid(teacher.getGroupid());
		teacher1.setAssignment_id(teacher.getAssignment_id());
		System.out.println("teacher1 ops size : "+teacher1.getOperationParameterses().size());
		if(checkWeights(teacher1)){
			mv = new ModelAndView("TeacherFirstPage");
			List<OperationParameters> param = teacher1.getOperationParameterses();
			System.out.println("operations : "+param.get(0).getCriteria() +param.get(0).getWeight());
			System.out.println("group Id: "+ teacher1.getGroupid() + " ass id: "+teacher1.getAssignment_id());
			teacher = Database.InsertReviewPannel(teacher1);
			mv.addObject("customMsg", "Successfully updated an evaluation criteria");
		}else{
			mv.addObject("customMsg", "Please enter correct numeric inputs for weights in review panel");
		}
		mv.addObject("teacher",teacher);
		return mv;
	}

	@RequestMapping(value="/groups",method= RequestMethod.POST)
	public ModelAndView Groups(HttpServletRequest request ) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Groups method");
		ModelAndView mv = new ModelAndView("groupsAssign");		
		return mv;
	}
	@RequestMapping(value="/createGroup",method= RequestMethod.POST)
	public ModelAndView CreateGroup(HttpServletRequest request ) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("CreateGroup method");
		BeanTeacher teacher = null;
		if(request.getSession().getAttribute("teacher")!=null){
			teacher = (BeanTeacher) request.getSession().getAttribute("teacher");
		}
		teacher = Database.GetAllStudents(teacher);
		ModelAndView mv = new ModelAndView("createGroup");		
		return mv;
	}
	@RequestMapping(value="/createmulgrpstuds",method= RequestMethod.POST)
	public ModelAndView Createmulgrpstuds(HttpServletRequest request) throws Exception {
		System.out.println("Create group and link students to tht group");
		BeanTeacher teacher = null;
		if(request.getSession().getAttribute("teacher")!=null){
			teacher = (BeanTeacher) request.getSession().getAttribute("teacher");
		}
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart){
			ModelAndView mv = new ModelAndView("register");	
			mv.addObject("headermsg", "Assignment");
			return mv;
		}
		ModelAndView mv = new ModelAndView("TeacherFirstPage");	
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// Sets the size threshold beyond which files are written directly to
		// disk
		factory.setSizeThreshold(MAX_MEMORY_SIZE);
		// Sets the directory used to temporarily store files that are larger
		// than the configured size threshold. We use temporary directory for
		// java
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		// constructs the folder where uploaded file will be stored
		String uploadFolder = Database.getValues("createStudents");
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// Set overall request size constraint
		upload.setSizeMax(MAX_REQUEST_SIZE);        
		try {
			// Parse the request
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (item.isFormField()) {
					System.out.println(" Form field");
					//String fileName = new File(item.getName()).getName();
					String itemname = item.getName();
					String link = item.getFieldName();
					String lvalue = item.getString();
					System.out.println("itemname: "+itemname);
					if(lvalue!=null){
						System.out.println("link : "+link);
						System.out.println("lvalue : "+lvalue);
					}
				}else{
					System.out.println("Not a form field");
					String itemname = item.getName();
					if((itemname==null || itemname.equals(""))){
						continue;
					}else{
						System.out.println("itemname: "+itemname);
						String filename = FilenameUtils.getName(itemname);
						String filePath = uploadFolder + "/"+"Response"+"_"+filename;
						System.out.println("filename: "+filePath);
						File uploadedFile = new File(filePath);
						// saves the file to upload directory
						item.write(uploadedFile);
						FileInputStream  fis = new FileInputStream(uploadedFile);;
						//Create Workbook instance holding reference to .xlsx file
						XSSFWorkbook workbook = new XSSFWorkbook(fis);

						//Get first/desired sheet from the workbook
						XSSFSheet sheet = workbook.getSheetAt(0);

						//Iterate through each rows one by one
						Iterator<Row> rowIterator = sheet.iterator();
						while (rowIterator.hasNext()){
							Row row = rowIterator.next();
							//For each row, iterate through all the columns
							Iterator<Cell> cellIterator = row.cellIterator();
							String user_id = "",group_id="";
							while (cellIterator.hasNext()){
								Cell cell = cellIterator.next();								
								//Check the cell type and format accordingly								
								if(cell.getColumnIndex() == 0){
									user_id = checkVal(cell);
								} else if(cell.getColumnIndex() == 1){
									group_id = checkVal(cell);
								} 									
							}		
							System.out.println("user_id: "+user_id);
							System.out.println("group_id: "+group_id);
							String customMsg = Database.SaveNewMultipleGroups(user_id,group_id);
							mv.addObject("customMsgGrp", "Successfully processed!");
							
							row.createCell(2).setCellValue(customMsg);
						
						}
						FileOutputStream fos = new FileOutputStream(uploadedFile);
						workbook.write(fos); fos.close();
						System.out.println(uploadedFile + " is successfully written");
					}
				}
			}
			teacher = Database.GetAllStudents(teacher);
			return mv;
		} catch (FileUploadException ex) {
			throw new ServletException(ex);
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
		
	}
	@RequestMapping(value="/saveNewGroup",method= RequestMethod.POST)
	public ModelAndView SaveNewGroup(HttpServletRequest request ) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("SaveNewGroup method");
		ModelAndView mv = new ModelAndView("groupsAssign");	
		BeanTeacher teacher = null;
		if(request.getSession().getAttribute("teacher")!=null){
			teacher = (BeanTeacher) request.getSession().getAttribute("teacher");
		}		
		String[] students = (String[]) request.getParameterValues("checkboxValues");
		String group = (String) request.getParameter("groupid");
		System.out.println("students size: "+students.length);
		System.out.println("student group: "+group);
		ArrayList<String> groups = teacher.getGroups();

		if(groups.contains(group)){
			mv = new ModelAndView("createGroup");	
			mv.addObject("customMsg", "Group name exists already, Please select a unique group name");
		}else{
			teacher.setGroupid(group);
			teacher = Database.SaveNewGroup(teacher,students);
			mv.addObject("customMsg", "Group created successfully!");			
		}		 
		return mv;
	}
	@RequestMapping(value="/modifyGroup",method= RequestMethod.POST)
	public ModelAndView ModifyGroup(HttpServletRequest request ) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("ModifyGroup method"); 
		String group = (String) request.getParameter("group_id");
		String update = (String) request.getParameter("update");
		System.out.println("ChangeGroup method group is : "+group);
		System.out.println("ChangeGroup method update is : "+update);
		BeanTeacher teacher = null;
		if(request.getSession().getAttribute("teacher")!=null){
			teacher = (BeanTeacher) request.getSession().getAttribute("teacher");
		}
		if(group != null){
			String[] students = (String[]) request.getParameterValues("checkedValues");
			System.out.println("ModifyGroup students size: "+students.length);	
			teacher = Database.saveStudents(teacher,students,group);
		}
		teacher = Database.GetGroups(teacher);		
		ModelAndView mv = new ModelAndView("modifyGroup");	
		
		return mv;
	}
	@RequestMapping(value="/changeGroup",method= RequestMethod.POST)
	public ModelAndView ChangeGroup(HttpServletRequest request ) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("ChangeGroup method");
		String group = (String) request.getParameter("group_id");
		System.out.println("selected group is : "+group);
		BeanTeacher teacher = null;
		if(request.getSession().getAttribute("teacher")!=null){
			teacher = (BeanTeacher) request.getSession().getAttribute("teacher");
		}		
		teacher = Database.GetAllStudents(teacher);
		ModelAndView mv = new ModelAndView("changeStudents");
		mv.addObject("groupid", group);
		return mv;
	}
	@RequestMapping(value="/evaluateStudents1",method= RequestMethod.POST)
	public ModelAndView Evaluate1(HttpServletRequest request ) throws Exception {
		System.out.println("evaluateStudents1 method");
		ModelAndView mv = new ModelAndView("selectGroup");			
		BeanTeacher teacher = null;
		if(request.getSession().getAttribute("teacher")!=null){
			teacher = (BeanTeacher) request.getSession().getAttribute("teacher");
		}
		return mv;
	}

	@RequestMapping(value="/evaluateStudents2",method= RequestMethod.POST)
	public ModelAndView Evaluate2(HttpServletRequest request ) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("evaluateStudents2 method");
		ModelAndView mv = new ModelAndView("viewStudents");	
		String group = (String) request.getParameter("group_id");
		System.out.println("selected group is : "+group);
		BeanTeacher teacher = null;
		if(request.getSession().getAttribute("teacher")!=null){
			teacher = (BeanTeacher) request.getSession().getAttribute("teacher");
		}		
		if(group!=null) teacher.setGroupid(group);

		System.out.println("Group id is "+teacher.getGroupid());
		teacher = Database.getAssigIds(teacher);
		String fAid = (String) request.getParameter("finalaid");	
		System.out.println("selected aid is: "+fAid);		

		if(fAid!=null ){
			if(fAid.length()>0){
				teacher.setAssignment_id(Integer.parseInt(fAid));
				mv.addObject("fAid",fAid);
				System.out.println("group: "+teacher.getGroupid()+ " assignment id : "+teacher.getAssignment_id());
				
				teacher = Database.GetStudentsInfo(teacher);				
			}

		}
		return mv;
	}	
	public static boolean checkWeights(BeanTeacher teacher) {
		// TODO Auto-generated method stub
		for(OperationParameters ops : teacher.getOperationParameterses()){
			try { 
				Integer.parseInt(ops.getWeight()); 
			} catch(NumberFormatException e) { 
				return false; 
			}			   
		}
		return true;
	}

	public static boolean checkWeights(BeanTeacher teacher, BeanClass student) {
		// TODO Auto-generated method stub
		if(student != null ){
			System.out.println("to display params: "+student.getToDisplay().get(0).getWeight());

		}
		for(OperationParameters ops : teacher.getOperationParameterses()){
			try { 
				Integer.parseInt(ops.getWeight()); 
			} catch(NumberFormatException e) { 
				return false; 
			}			   
		}
		for(int i=0;i<teacher.getOperationParameterses().size();i++){
			int student_mark = Integer.parseInt(teacher.getOperationParameterses().get(i).getWeight());
			int tot_mark = Integer.parseInt(student.getToDisplay().get(i).getWeight());
			if( tot_mark- student_mark <0){
				return false;
			}
			if(teacher.getOperationParameterses().get(i).getCriteria().length() <2){
				System.out.println("ravi came");
				return false;
			}
		}
		return true;
	}

	@RequestMapping(value="/reviewWork/{stud}",method= RequestMethod.GET)
	protected ModelAndView review(@PathVariable("stud") String uname,HttpServletRequest request) throws Exception{
		System.out.println("review method");
		System.out.println("stud name: "+uname);
		BeanClass student = null;
		ModelAndView mv = new ModelAndView("metaReview");
		BeanTeacher teacher = (BeanTeacher) request.getSession().getAttribute("teacher");
		HashMap<String, BeanClass> map = teacher.getStudentList();
		if(map.size()>0){
			student = map.get(uname);			
		}
		System.out.println("group: "+teacher.getGroupid()+ " assignment id : "+teacher.getAssignment_id());
		if(student.getToDisplay()==null){
			BeanClass temp = new BeanClass();
			temp.setGroup_id(teacher.getGroupid());
			temp.setAssignment_id(""+teacher.getAssignment_id());
			temp = Database.getReviewCriteria(temp);
			student.setToDisplay(temp.getReviewCriteria());
		}
		student = Database.GetMarks(student);
		if(student.getTeacherMarks()!=null){
			System.out.println(student.getTeacherMarks().get(0).getCriteria());
		}
		String mode = Database.getValues("teacher");
		request.getSession().setAttribute("mode",mode );
		mv.addObject("mode",mode);
		request.getSession().setAttribute("student",student );
		mv.addObject("student", student);
		mv.addObject("reviewheader", "Evaluate");
		mv.addObject("headermsg", "Review Assignment");
		return mv;
	}

	@RequestMapping(value="/saveUserInfo",method= RequestMethod.POST)
	public ModelAndView SaveUserInfo(@ModelAttribute("userInfo") UserInfoBean userInfo) throws Exception {
		ModelAndView mv = new ModelAndView("register");
		System.out.println("SaveUserInfo group: "+userInfo.getGroup());
		String customMsg = Database.RegisterUser(userInfo);
		System.out.println("save user info custom msg : "+customMsg);
		if(customMsg.equalsIgnoreCase("email")){
			mv = new ModelAndView("register");
			mv.addObject("headermsg", "Registration Page");
			mv.addObject("customMsg", "Email id was already registered!");
		}else if(customMsg.equalsIgnoreCase("userid")){
			mv = new ModelAndView("register");
			mv.addObject("headermsg", "Registration Page");
			mv.addObject("customMsg", "User Id already exists, please choose a different user id!");
		}else{
			mv.addObject("customMsg", "Registered Successfully!");
		}
		mv.addObject("headermsg", "Registration Page");
		return mv;
	}
	@RequestMapping(value="/register",method= RequestMethod.POST)
	public ModelAndView Register() throws Exception {
		ModelAndView mv = new ModelAndView("register");
		mv.addObject("headermsg", "Registration Page");
		return mv;
	}
	@RequestMapping(value="/teacherfirst",method= RequestMethod.GET)
	public ModelAndView FirstPage() throws Exception {
		ModelAndView mv = new ModelAndView("TeacherFirstPage");
		mv.addObject("headermsg", "PEER REVIEW");
		return mv;
	}
	
	

	@RequestMapping(value="/createStudents",method= RequestMethod.POST)
	protected ModelAndView createStudents(HttpServletRequest request) throws ServletException, IOException {
		System.out.println("createStudents method");
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart){
			ModelAndView mv = new ModelAndView("register");	
			mv.addObject("headermsg", "Assignment");
			return mv;
		}
		ModelAndView mv = new ModelAndView("TeacherFirstPage");	
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
		String uploadFolder = Database.getValues("createStudents");
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// Set overall request size constraint
		upload.setSizeMax(MAX_REQUEST_SIZE);        
		try {
			// Parse the request
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (item.isFormField()) {
					System.out.println(" Form field");
					String fileName = new File(item.getName()).getName();
					String link = item.getFieldName();
					String lvalue = item.getString();
					if(lvalue!=null){
						System.out.println("link : "+link);
						System.out.println("lvalue : "+lvalue);
					}
				}else{
					System.out.println("Not a form field");
					String itemname = item.getName();
					if((itemname==null || itemname.equals(""))){
						continue;
					}else{
						System.out.println("itemname: "+itemname);
						String filename = FilenameUtils.getName(itemname);
						String filePath = uploadFolder + "/"+filename;
						System.out.println("filename: "+filePath);
						File uploadedFile = new File(filePath);
						// saves the file to upload directory
						item.write(uploadedFile);
						FileInputStream  fis = new FileInputStream(uploadedFile);;
						//Create Workbook instance holding reference to .xlsx file
						XSSFWorkbook workbook = new XSSFWorkbook(fis);

						//Get first/desired sheet from the workbook
						XSSFSheet sheet = workbook.getSheetAt(0);

						//Iterate through each rows one by one
						Iterator<Row> rowIterator = sheet.iterator();
						UserInfoBean userInfo = null;
						while (rowIterator.hasNext()){
							Row row = rowIterator.next();
							userInfo = new UserInfoBean();
							//For each row, iterate through all the columns
							Iterator<Cell> cellIterator = row.cellIterator();
							
							while (cellIterator.hasNext()){
								Cell cell = cellIterator.next();
								String result = "";
								//Check the cell type and format accordingly
								
								if(cell.getColumnIndex() == 0){
									result = checkVal(cell);
									userInfo.setUser_id(result);
								} else if(cell.getColumnIndex() == 1){
									result = checkVal(cell);
									userInfo.setPassword(result);
								} else if(cell.getColumnIndex() == 2){
									result = checkVal(cell);
									userInfo.setUname(result);
								} else if(cell.getColumnIndex() == 3){
									result = checkVal(cell);
									userInfo.setEmailId(result);
								} else if(cell.getColumnIndex() == 4){
									result = checkVal(cell);
									userInfo.setGroup(result);
								} 	
								System.out.println("value: "+result);
							}
							String customMsg = Database.RegisterUser(userInfo);
							if(customMsg.equalsIgnoreCase("email")){
								customMsg = "Email id was already registered!";
							}else if(customMsg.equalsIgnoreCase("userid")){
								customMsg = "User Id already exists, please choose a different user id!";
							}else{
								mv.addObject("customMsg", "Registered Successfully!");
							}
							row.createCell(5).setCellValue(customMsg);
						}
						FileOutputStream fos = new FileOutputStream(uploadedFile);
						workbook.write(fos); fos.close();
						System.out.println(uploadedFile + " is successfully written");
					}
				}
			}
			return mv;

		} catch (FileUploadException ex) {
			throw new ServletException(ex);
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	private String checkVal(Cell cell) {
		switch (cell.getCellType()){
		case Cell.CELL_TYPE_NUMERIC:
			return "" +cell.getNumericCellValue() ;
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		}


		return null;

	}


}





