package com.peer.controller;

import javax.servlet.http.HttpServletRequest;
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
	public static String CONSTANT_FILE_PATH = "C:/Users/tejj/Desktop/PeerTool/PeerTool/WebContent/WEB-INF/constants.properties";
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
	public ModelAndView Validate(@Valid @ModelAttribute("student") BeanClass student, BindingResult result,HttpServletRequest request ) throws Exception {
		// TODO Auto-generated method stub
		ModelAndView mv = new ModelAndView("loginPage");
		if(result.hasErrors()){
			return mv;
		}
		if(student.getRole()==null){
			student = Database.validate(student);
			
			//System.out.println("Validate user role: "+student.getRole());
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
