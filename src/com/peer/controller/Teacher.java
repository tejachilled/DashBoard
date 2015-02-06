package com.peer.controller;
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
		BeanClass student = (BeanClass) request.getSession().getAttribute("student");
		System.out.println("viewStudents group: "+teacher.getGroupid() +" \nrandom number: "+teacher.getRandomNumber()+ " \na_id: "+teacher.getAssignment_id());
		ModelAndView mv = new ModelAndView("viewStudents");
		if(teacher.getStudentsList() ==null){
			teacher = Database.GetStudentsInfo(teacher);
			teacher.setUsername(student.getUsername());
			request.getSession().setAttribute("teacher",teacher);
			System.out.println(teacher.getStudentsList().get(0).getUsername());
		}
		return mv;
	}
	
	@RequestMapping(value="/reviewWork/{stud}",method= RequestMethod.GET)
	protected ModelAndView viewAssignment(@PathVariable("stud") String uname,HttpServletRequest request) throws Exception{
		System.out.println("hamayaaa stude: "+uname);
		//BeanClass student = (BeanClass) request.getSession().getAttribute("student");
		ModelAndView mv = new ModelAndView("viewStudents");	
		
//		if(student.getImagefile()==null){
//			student = Database.RetrieveInfo(student);
//		}
//		System.out.println(">>>>>text<<<<\n "+student.getContent());
		mv.addObject("reviewheader", "Review");
		mv.addObject("headermsg", "View Assignment");
		return mv;
	}

}





