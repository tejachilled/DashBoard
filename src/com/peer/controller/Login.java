package com.peer.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
	@Value("${admin}")
	private String role;
	@RequestMapping(value="/login",method= RequestMethod.GET)
	public ModelAndView LoginPage() throws Exception {
		ModelAndView mv = new ModelAndView("loginPage");
		System.out.println(">>>>>>role:  "+role);
		return mv;
	}
	@RequestMapping(value="/validate",method= RequestMethod.POST)
	public ModelAndView Validate(@Valid @ModelAttribute("student") BeanClass student, BindingResult result,HttpServletRequest request ) throws Exception {
		// TODO Auto-generated method stub
		ModelAndView mv = new ModelAndView("loginPage");
		if(result.hasErrors()){
			return mv;
		}
		student = Database.validate(student);
		System.out.println("Validate user role: "+student.getRole());
		String role = student.getRole();
		if(role!= null ){
			if(role.equals("admin")){
			mv = new ModelAndView("WelcomePage");
				request.getSession().setAttribute("student",student);
			}
		}else{
			mv.addObject("ErrorMsg", "Username/Password entered was incorrect!");
		}
		
		return mv;
	}

	@RequestMapping(value="/WelcomePage",method= RequestMethod.POST) 
	public ModelAndView WelcomePage(HttpServletRequest request ) throws Exception {
		BeanClass student = (BeanClass) request.getSession().getAttribute("student");
		ModelAndView mv = new ModelAndView("WelcomePage");
		mv.addObject("WelcomeMsg", student.getUsername());		
		return mv;
	}


	@ModelAttribute
	public void addCommonObj(Model mv){
		mv.addAttribute("headermsg", "PEER REVIEW");
	}

}
