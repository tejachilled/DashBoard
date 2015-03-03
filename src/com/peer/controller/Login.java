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
	private String admin;
	@Value("${student}")
	private String stud;
	@Value("${ta}")
	private String ta;
	@Value("${teacher}")
	private String teacher;

	@RequestMapping(value="/",method= RequestMethod.GET)
	public ModelAndView LoginPage() throws Exception {
		ModelAndView mv = new ModelAndView("loginPage");
		return mv;
	}
	@RequestMapping(value="/register",method= RequestMethod.GET)
	public ModelAndView Register() throws Exception {
		ModelAndView mv = new ModelAndView("register");
		mv.addObject("headermsg", "Registration Page");
		return mv;
	}

	@RequestMapping(value="/saveUserInfo",method= RequestMethod.POST)
	public ModelAndView SaveUserInfo(@ModelAttribute("userInfo") UserInfoBean userInfo) throws Exception {
		ModelAndView mv = new ModelAndView("loginPage");
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
		return mv;
	}
	@RequestMapping(value="/validate",method= RequestMethod.POST)
	public ModelAndView Validate(@Valid @ModelAttribute("student") BeanClass student, BindingResult result,HttpServletRequest request ) throws Exception {
		// TODO Auto-generated method stub
		ModelAndView mv = new ModelAndView("loginPage");
		if(result.hasErrors()){
			return mv;
		}
		if(student.getRole()==null){
			student = Database.validate(student);
			request.getSession().setAttribute("student",student);
		}
		System.out.println("Validate user role: "+student.getRole());

		if(student!= null ){
			String role = student.getRole();
			if(role.equals(admin) || role.equals(stud)){
				mv = new ModelAndView("WelcomePage");				
			}else if(role.equals(teacher) || role.equals(ta)){
				mv = new ModelAndView("TeacherWelcomePage");
			}
		} else
			mv.addObject("ErrorMsg", "Username/Password entered was incorrect!");

		return mv;
	}

	@RequestMapping(value="/WelcomePage",method= RequestMethod.POST) 
	public ModelAndView WelcomePage(HttpServletRequest request ) throws Exception {
		ModelAndView mv = new ModelAndView("WelcomePage");		
		return mv;
	}

	@RequestMapping(value="/TeacherWelcomePage",method= RequestMethod.POST) 
	public ModelAndView TeacherWelcomePage(HttpServletRequest request ) throws Exception {
		ModelAndView mv = new ModelAndView("TeacherWelcomePage");		
		return mv;
	}


	@ModelAttribute
	public void addCommonObj(Model mv){
		mv.addAttribute("headermsg", "PEER REVIEW");
	}

}
