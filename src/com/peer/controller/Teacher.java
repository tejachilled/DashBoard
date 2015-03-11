package com.peer.controller;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Teacher {	

	@RequestMapping(value="/assign",method= RequestMethod.POST)
	public ModelAndView Assign(HttpServletRequest request ) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Assign method");
		
		ModelAndView mv = new ModelAndView("TeacherAssign");

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
		BeanTeacher teacher = null;
		if(request.getSession().getAttribute("teacher")!=null){
			teacher = (BeanTeacher) request.getSession().getAttribute("teacher");
		}
		teacher = Database.GetGroups(teacher);
		teacher = Database.GetAllStudents(teacher);
		ModelAndView mv = new ModelAndView("modifyGroup");		
		return mv;
	}
	@RequestMapping(value="/changeGroup",method= RequestMethod.POST)
	public ModelAndView ChangeGroup(HttpServletRequest request ) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("ChangeGroup method");
		BeanTeacher teacher = null;
		if(request.getSession().getAttribute("teacher")!=null){
			teacher = (BeanTeacher) request.getSession().getAttribute("teacher");
		}
		
		ModelAndView mv = new ModelAndView("modifyGroup");		
		return mv;
	}
	
	@RequestMapping(value="/evaluateStudents",method= RequestMethod.POST)
	public ModelAndView Evaluate(HttpServletRequest request ) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Evaluate method");
		ModelAndView mv = new ModelAndView("viewStudents");	
		BeanTeacher teacher = null;
		if(request.getSession().getAttribute("teacher")!=null){
			teacher = (BeanTeacher) request.getSession().getAttribute("teacher");
		}
		String fGroup = (String) request.getParameter("finalGroup");
		System.out.println("selected group is: "+fGroup);	
		if(fGroup!=null){
			teacher.setGroupid(fGroup);
			teacher = Database.GetStudentsInfo(teacher);
			if(teacher.getStudentList()!=null)
				System.out.println("Tot number of students: "+teacher.getStudentList().size());			
		}
		return mv;
	}

	private boolean checkWeights(BeanTeacher teacher) {
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
	@RequestMapping(value="/submitReview",method= RequestMethod.POST)
	public ModelAndView submitReview(@Valid @ModelAttribute("teacher") BeanTeacher teacher1,HttpServletRequest request ) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("view students");
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
			teacher = Database.InsertReviewPannel(teacher);
			mv.addObject("customMsg", "Successfully created an evaluation criteria");
		}else{
			mv.addObject("customMsg", "Please enter correct numeric inputs for weights in review panel");
		}

		return mv;
	}

	@RequestMapping(value="/reviewWork/{stud}",method= RequestMethod.GET)
	protected ModelAndView review(@PathVariable("stud") String uname,HttpServletRequest request) throws Exception{
		System.out.println("stud name: "+uname);
		BeanClass student = null;
		ModelAndView mv = new ModelAndView("evaluate");
		BeanTeacher teacher = (BeanTeacher) request.getSession().getAttribute("teacher");
		HashMap<String, BeanClass> map = teacher.getStudentList();
		if(map.size()>0){
			student = map.get(uname);
			int i =2;
			if(student.getMarks()!=null){
				for(BeanMarks marks : student.getMarks()){
					if(!marks.isTeacher_evaluation()){
						mv.addObject("marks"+i,marks); i++;
					}else{
						mv.addObject("marks1",marks); 
					}
				}
			}
		}
		String mode = Database.getValues("teacher");
		request.getSession().setAttribute("mode",mode );
		mv.addObject("mode",mode);
		mv.addObject("student", student);
		mv.addObject("reviewheader", "Evaluate");
		mv.addObject("headermsg", "View Assignment");
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


}





