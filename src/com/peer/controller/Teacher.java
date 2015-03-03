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

	@RequestMapping(value="/viewStudents",method= RequestMethod.POST)
	public ModelAndView viewStudents(@Valid @ModelAttribute("teacher") BeanTeacher teacher,HttpServletRequest request ) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("view students");
		BeanClass student = null;
		if(request.getSession().getAttribute("student")!=null){
			student = (BeanClass) request.getSession().getAttribute("student");
		}
		//request.getSession().removeAttribute("student");
		System.out.println("viewStudents group: "+teacher.getGroupid() +" \nrandom number: "+teacher.getRandomNumber()+ " \na_id: "+teacher.getAssignment_id());
		ModelAndView mv = new ModelAndView("viewStudents");
		if(teacher.getStudentList() ==null){
			teacher = Database.GetStudentsInfo(teacher);
			teacher.setUsername(student.getUsername());
			request.getSession().setAttribute("teacher",teacher);
			if(teacher.getStudentList()!=null)
			System.out.println("Tot number of students: "+teacher.getStudentList().size());
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
		Properties prop = new Properties();
		InputStream input = new FileInputStream("C:/Users/tejj/Desktop/PeerTool/PeerTool/WebContent/WEB-INF/constants.properties");
		if(input==null) System.out.println("null values in review class");
		// load a properties file
		prop.load(input);
		request.getSession().setAttribute("mode",prop.getProperty("teacher") );
		mv.addObject("mode",prop.getProperty("teacher"));
		mv.addObject("student", student);
		mv.addObject("reviewheader", "Evaluate");
		mv.addObject("headermsg", "View Assignment");
		return mv;
	}

}





