package com.peer.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.sun.xml.internal.org.jvnet.staxex.NamespaceContextEx.Binding;

@Controller
public class Login {	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		binder.setDisallowedFields(new String[] {""});
	}
	
	@RequestMapping(value="/login",method= RequestMethod.GET)
	public ModelAndView LoginPage() throws Exception {
		// TODO Auto-generated method stub 		
		ModelAndView mv = new ModelAndView("loginPage");	
		return mv;
	}
	
	@RequestMapping(value="/WelcomePage",method= RequestMethod.POST) 
	public ModelAndView WelcomePage(@Valid @ModelAttribute("student") BeanClass student, BindingResult result,HttpServletRequest request ) throws Exception {
		
		if(result.hasErrors()){
			ModelAndView mv = new ModelAndView("loginPage");
			return mv;
		}
		if(student!=null){
		request.getSession().setAttribute("student",student);
		}
		System.out.println(student.getUsername());
		ModelAndView mv = new ModelAndView("WelcomePage");
		mv.addObject("WelcomeMsg", student.getUsername());		
		return mv;
	}

	
	@ModelAttribute
	public void addCommonObj(Model mv){
		mv.addAttribute("headermsg", "PEER REVIEW");
	}

}
