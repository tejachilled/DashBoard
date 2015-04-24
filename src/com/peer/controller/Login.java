package com.peer.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Login {	

	@InitBinder
	public void initBinder(WebDataBinder binder){
		binder.setDisallowedFields(new String[] {""});
	}
	//Give the complete path of constants file here
	public static String CONSTANT_FILE_PATH = "C:\\Users\\lyihan\\Documents\\GitHub\\PeerTool\\WebContent\\WEB-INF\\constants.properties";
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

	@RequestMapping(value="/validate",method= RequestMethod.POST)
	public ModelAndView Validate(@Valid @ModelAttribute("student") BeanClass student, BindingResult result,HttpServletRequest request )  {
		// TODO Auto-generated method stub
		ModelAndView mv = new ModelAndView("loginPage");
		BeanClass temp1  = (BeanClass)request.getSession().getAttribute("student");
		BeanTeacher temp2  = (BeanTeacher)request.getSession().getAttribute("teacher");
//		if(temp1 !=null ){
//			mv.addObject("ErrorMsg","You cannot login twice, Please logout from previous session!");
//		}else if( temp2 !=null){
//			mv.addObject("ErrorMsg","You cannot login twice, Please logout from previous session!");
//		}else
//		{
			if(result.hasErrors()){
				return mv;
			}
			if(student.getRole()==null){
				try {
					student = Database.validate(student);
				} catch (Exception e) {
					e.printStackTrace();
					mv.addObject("ErrorMsg","Please check DB connection!");
					return mv;
				}
			}

			if(student!= null ){

				String role = student.getRole();
				if(role.equals(admin) || role.equals(stud)){
					request.getSession().setAttribute("student",student);
					mv = new ModelAndView("WelcomePage");				
				}else if(role.equals(teacher) || role.equals(ta)){
					BeanTeacher teacher = new BeanTeacher();
					teacher.setUsername(student.getUsername());
					teacher.setGroups(student.getGroups());
					request.getSession().setAttribute("teacher",teacher);
					mv = new ModelAndView("TeacherFirstPage");
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
	@RequestMapping(value="/Welcome",method= RequestMethod.GET) 
	public ModelAndView Welcome(HttpServletRequest request ) throws Exception {
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

	@RequestMapping(value="/logout",method= RequestMethod.GET) 
	public ModelAndView Logout(HttpServletRequest request ) throws Exception {
		try
		{
			HttpSession session=request.getSession(false);
			if(session!=null)
			{
				Enumeration attributeNames=session.getAttributeNames();
				while(attributeNames.hasMoreElements())
				{
					String sAttribute=attributeNames.nextElement().toString();
					System.out.println("session Attributes: "+sAttribute);
						session.removeAttribute(sAttribute);
					
				}
			}
		}catch(Exception e) { }
		ModelAndView mv = new ModelAndView("loginPage");
		mv.addObject("customMsg","Successfully logged out!");
		return mv;
	}
	public static ModelAndView Logout_Excpetion( ) throws Exception {
		
		ModelAndView mv = new ModelAndView("loginPage");
		mv.addObject("customMsg","Please contact admin!");
		return mv;
	}


}
