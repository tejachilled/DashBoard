package com.peer.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class Database {
	private static Connection conn = null;
	private  static String url = null;
	private  static String dbName = null;
	private static String driver = null;
	private static String userName = null;
	private static String password = null;
	public static String student = null;
	public static String ta = null;
	public static String teacher = null;

	Database(){	}
	public static BeanClass validate(BeanClass student) throws Exception {
		
			if(conn==null || conn.isClosed())GetConnection();
		
		String sqlQuery = "SELECT PASSWRD,ROLE,group_id,email,uname FROM USER_TABLE WHERE USER_ID = ?";
		PreparedStatement ps = conn.prepareStatement(sqlQuery);
		ps.setString(1, student.getUsername());
		ResultSet rs = ps.executeQuery();
		String pwd =""; String role = "";
		String group_id = "";
		while(rs.next()){
			pwd = rs.getString(1);	
			role = rs.getString(2);
			group_id = rs.getString(3);
			student.setFullName(rs.getString("uname"));			
		}
		System.out.println(student.getUsername()+" :login info: "+student.getPassword());
		System.out.println("DB pwd: "+pwd + " role: "+role);
		if(pwd.equals(student.getPassword())){
			student.setRole(role); student.setGroup_id(group_id);
			if(role.equalsIgnoreCase(teacher)||role.equalsIgnoreCase(ta))
				student = TeacherInfo(student);
		}else{
			student = null;
		}
		ps.close();
		conn.close();
		
		System.out.println("Disconnected from database");

		return student;
	}

	public static String RegisterUser(UserInfoBean userInfo) throws SQLException {
		// TODO Auto-generated method stub
		if(conn==null || conn.isClosed())GetConnection();
		String sqlQuery = "INSERT INTO user_table(user_id,uname,passwrd,email,group_id) values(?,?,?,?,?)";
		int count =0;
		if(CheckEmail(userInfo)){
			return "email";
		}else if(CheckUId(userInfo)){
			return "userid";
		} else{
			try {
				PreparedStatement ps = conn.prepareStatement(sqlQuery);
				ps.setString(1, userInfo.getUser_id());
				ps.setString(2, userInfo.getUname());
				ps.setString(3, userInfo.getPassword());
				ps.setString(4, userInfo.getEmailId());
				ps.setString(5, userInfo.getGroup());
				count = ps.executeUpdate();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				conn.close();
				System.out.println("New user registration successful!");
			}
		}
		return "success";
	}
	private static boolean CheckUId(UserInfoBean userInfo) throws SQLException {
		// TODO Auto-generated method stub
		if(conn==null || conn.isClosed())GetConnection();
		int count = 0;
		String sqlQuery = "select count(*) from user_table where user_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sqlQuery);
			ps.setString(1, userInfo.getUser_id());
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("user id count: "+count);
		if(count ==0) return false;
		return true;
	}
	private static boolean CheckEmail(UserInfoBean userInfo) throws SQLException {
		// TODO Auto-generated method stub
		if(conn==null || conn.isClosed())GetConnection();
		int count = 0;
		String sqlQuery = "select count(*) cou from user_table where email = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sqlQuery);
			ps.setString(1, userInfo.getEmailId());
			System.out.println(ps.toString());
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt("cou");				
			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("email id count: "+count);
		if(count ==0) return false;
		return true;
	}

	public static BeanClass TeacherInfo(BeanClass student) throws SQLException {
		// TODO Auto-generated method stub
		if(conn==null || conn.isClosed())GetConnection();
		ArrayList<String> al = new ArrayList<String>();
		String sqlQuery = "select  distinct group_id  from user_table";
		try {
			Statement ps = conn.createStatement();
			ResultSet rs = ps.executeQuery(sqlQuery);
			while(rs.next()){
				String key = rs.getString("group_id");
				System.out.println("key: "+key);
				if(key != null)
					al.add(key);
			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{		
			conn.close();
		}
		if(al.size()>0){
			student.setGroups(al);
		}

		return student;
	}
	public static BeanTeacher GetGroups(BeanTeacher teacher) throws SQLException {
		// TODO Auto-generated method stub
		if(conn==null || conn.isClosed())GetConnection();
		ArrayList<String> al = new ArrayList<String>();
		String sqlQuery = "select  distinct group_id  from user_table";
		try {
			Statement ps = conn.createStatement();
			ResultSet rs = ps.executeQuery(sqlQuery);
			while(rs.next()){
				String key = rs.getString("group_id");
				System.out.println("key: "+key);
				if(key !=null)
					al.add(key);
			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{		
			conn.close();
		}
		if(al.size()>0){
			teacher.setGroups(al);
		}

		return teacher;
	}
	public static BeanClass UserDataUploadtoDB(BeanClass student) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println("UserDataUploadtoDB method");
		if(conn==null || conn.isClosed())GetConnection();
		int count = getCount(student);

		student.setCount(count);
		java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		System.out.println("todays date is : "+date);
		System.out.println("No of Submissions: "+count);
		String sqlQuery = "";
		PreparedStatement ps = null;
		if(IsExist(student)){
			if(count>0){
				sqlQuery = "update user_assignment set IMAGEFILE=?,CONTENT=?,LINK=?,SUBMISSIONCOUNT=?,SUBMISSIONDATE=?,NOOFIMAGES=?,WORDCOUNT=?,CHARCOUNT=? WHERE USER_ID = ? and assignment_id = ? and group_id = ?";
				ps = conn.prepareStatement(sqlQuery);
				ps.setString(1, student.getImagefile());
				ps.setString(2, student.getFullContext());
				ps.setString(3, student.getLink());
				ps.setInt(4, count-1);
				ps.setTimestamp(5, date);
				ps.setInt(6, student.getImagesNumber());
				ps.setInt(7, student.getWordCount());
				ps.setInt(8, student.getCharCount());
				ps.setString(9, student.getUsername());
				ps.setInt(10, Integer.parseInt(student.getAssignment_id()));
				ps.setString(11, student.getGroup_id());	
				System.out.println("prepared statement: "+ps.toString());
				ps.executeUpdate();
				student.setSubmission_date(""+date);
				System.out.println("updated a record into DB");
			}
		}else{	
			int maxCount =getMaxCount(student);
			sqlQuery = "INSERT INTO USER_ASSIGNMENT(USER_ID,IMAGEFILE,CONTENT,LINK,SUBMISSIONCOUNT,SUBMISSIONDATE,NOOFIMAGES,WORDCOUNT,CHARCOUNT,group_id,assignment_id) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sqlQuery);
			ps.setString(1, student.getUsername());
			ps.setString(2, student.getImagefile());
			ps.setString(3, student.getFullContext());
			ps.setString(4, student.getLink());
			ps.setInt(5, maxCount - 1);
			ps.setTimestamp(6, date);
			ps.setInt(7, student.getImagesNumber());
			ps.setInt(8, student.getWordCount());
			ps.setInt(9, student.getCharCount());
			ps.setString(10, student.getGroup_id());
			ps.setInt(11, Integer.parseInt(student.getAssignment_id()));
			System.out.println("prepared statement: "+ps.toString());
			ps.executeUpdate();
			student.setSubmission_date(""+date);
			System.out.println("Inserted a record into DB");
		}

		ps.close();
		conn.close();
		System.out.println("Disconnected from database");
		return student;
	}



	public static int getCount(BeanClass student) throws SQLException{
		String sqlQuery = "SELECT SUBMISSIONCOUNT FROM USER_ASSIGNMENT WHERE USER_ID = ? and assignment_id = ? and group_id = ?";
		int count = 0;
		PreparedStatement ps = conn.prepareStatement(sqlQuery);
		ps.setString(1, student.getUsername());
		ps.setInt(2, Integer.parseInt(student.getAssignment_id()));
		ps.setString(3,student.getGroup_id());
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			count = rs.getInt("SUBMISSIONCOUNT");
		}

		return count;
	}
	private static int getMaxCount(BeanClass student) throws SQLException {
		// TODO Auto-generated method stub
		String sqlQuery = "SELECT submissioncount FROM group_assign_review WHERE  assignment_id = ? and group_id = ?";
		int count = 0;
		PreparedStatement ps = conn.prepareStatement(sqlQuery);
		ps.setInt(1, Integer.parseInt(student.getAssignment_id()));
		ps.setString(2,student.getGroup_id());
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			count = rs.getInt("submissioncount");
		}

		return count;
	}
	public static BeanClass RetrieveInfo(BeanClass student) throws SQLException {
		System.out.println("RetrieveInfo method");
		if(conn==null || conn.isClosed()) GetConnection();
		String sqlQuery = "SELECT * FROM user_assignment WHERE USER_ID = ? and  assignment_id = ? and group_id = ?";
		PreparedStatement ps = conn.prepareStatement(sqlQuery);
		ps.setString(1, student.getUsername());
		ps.setInt(2, Integer.parseInt(student.getAssignment_id()));
		ps.setString(3, student.getGroup_id());
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			student.setFullContext(rs.getString("CONTENT"));
			student.setCount(rs.getInt("SUBMISSIONCOUNT"));
			student.setImagefile(rs.getString("IMAGEFILE"));
			student.setLink(rs.getString("LINK"));
			student.setAssignment_name(rs.getString("assignment_name"));
			student.setGroup_id(rs.getString("group_id"));
			student.setSubmission_date(""+rs.getTimestamp("submissionDate"));
			student.setImagesNumber(rs.getInt("NOOFIMAGES"));
			student.setWordCount(rs.getInt("WORDCOUNT"));
			student.setCharCount(rs.getInt("CHARCOUNT"));
		}
		if(student.getImagefile()!=null){
			BeanClass temp = new BeanClass();
			temp.setAssignment_id(student.getAssignment_id());
			temp.setGroup_id(student.getGroup_id());
			temp	=	getReviewCriteria(temp);
			student.setToDisplay(temp.getReviewCriteria());
			student = GetImageList(student);
			student = GetHTMLBody(student);
			student = GetMarks(student);
			
		}
		ps.close();
		conn.close();
		return student;
	}

	public static BeanTeacher SaveNewGroup(BeanTeacher teacher, String[] students) throws SQLException {
		try {
			if(conn==null || conn.isClosed()) GetConnection();
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("update user_table set group_id = ? ");
			sqlQuery.append(" where user_id in ( ");
			for(int i =0;i<students.length-1;i++){
				sqlQuery.append("?");sqlQuery.append(",");
			}
			sqlQuery.append("?");
			sqlQuery.append(" )");
			System.out.println("GetStudentsInfo statement: "+sqlQuery.toString());
			PreparedStatement ps = conn.prepareStatement(sqlQuery.toString());
			ps.setString(1, teacher.getGroupid());
			for(int i =0,j=2;i<students.length;i++,j++){
				ps.setString(j, students[i]);
			}
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			conn.close();
		}

		return teacher;
	}
	public static String SaveNewMultipleGroups(String user_id, String group_id) throws SQLException {
		try {
			if(conn==null || conn.isClosed()) GetConnection();
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("update user_table set group_id = ? ");
			sqlQuery.append(" where user_id = ? ");

			PreparedStatement ps = conn.prepareStatement(sqlQuery.toString());
			ps.setString(1, group_id);
			ps.setString(2, user_id);
			System.out.println("SaveNewMultipleGroups statement: "+ps.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "failed";
		}finally{
			conn.close();
		}

		return "successfully created";
	}
	public static BeanTeacher GetAllStudents(BeanTeacher teacher) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<BeanClass> allStudents = new ArrayList<BeanClass>();
		try {
			if(conn==null || conn.isClosed()) GetConnection();
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("select * from user_table where role = ? ");
			sqlQuery.append(" group by user_id");
			BeanClass student;
			System.out.println("GetStudentsInfo statement: "+sqlQuery.toString());
			PreparedStatement ps = conn.prepareStatement(sqlQuery.toString());
			ps.setString(1, "student");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				student = new BeanClass();
				student.setUsername(rs.getString("USER_ID"));
				student.setFullName(rs.getString("UNAME"));
				student.setGroup_id(rs.getString("GROUP_ID"));
				allStudents.add(student);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(allStudents.size()>0){
				teacher.setAllStudents(allStudents);
			}
			conn.close();
		}

		return teacher;
	}
	public static ArrayList<String> GetGroupStudents(BeanTeacher teacher,String group) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<String> gStudents = new ArrayList<String>();
		try {
			if(conn==null || conn.isClosed()) GetConnection();
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("select * from user_table where role = ? and group_id = ? ");
			sqlQuery.append(" group by user_id");
			System.out.println("GetStudentsInfo statement: "+sqlQuery.toString());
			PreparedStatement ps = conn.prepareStatement(sqlQuery.toString());
			ps.setString(1, "student");
			ps.setString(2, group);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				gStudents.add(rs.getString("USER_ID"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return gStudents;
	}


	public static BeanTeacher saveStudents(BeanTeacher teacher,
			String[] students, String group) throws SQLException {
		// TODO Auto-generated method stub
		try {
			if(conn==null || conn.isClosed()) GetConnection();
			StringBuffer updateg = new StringBuffer();			
			updateg.append("update user_table set group_id = ? ");
			updateg.append(" where user_id in ( ");			
			ArrayList<String> list = GetGroupStudents(teacher,group);
			ArrayList<String> removestudentsgroup = new ArrayList<String>();

			for(int i =0;i<students.length;i++){				
				updateg.append("?");updateg.append(",");

			}
			//System.out.println("last char in update string is : "+updateg.charAt(updateg.length()-1));
			updateg.deleteCharAt(updateg.length()-1);
			updateg.append(" )");
			System.out.println("GetStudentsInfo statement: "+updateg.toString());
			PreparedStatement ps = conn.prepareStatement(updateg.toString());
			ps.setString(1, group);
			System.out.println("GetGroupStudents :   "+list);
			for(int i =0,j=2;i<students.length;i++){
				System.out.println("students : "+students[i]);				
				ps.setString(j, students[i]);  j++;
				if(list.contains(students[i]))
					list.remove(students[i]);
			}
			removestudentsgroup.addAll(list);
			System.out.println("PreparedStatement : "+ps.toString());
			ps.executeUpdate();
			System.out.println("updated groups for students ");

			// update group to null for students who are unchecked
			if(removestudentsgroup.size()>=1){
				StringBuffer removeg = new StringBuffer();
				removeg.append("update user_table set group_id = ? ");
				removeg.append(" where user_id in ( ");
				for(int i =0;i<removestudentsgroup.size()-1;i++){
					removeg.append("?");removeg.append(",");
				}
				removeg.append("?");
				removeg.append(" )");
				System.out.println("GetStudentsInfo statement: "+removeg.toString());
				PreparedStatement ps2 = conn.prepareStatement(removeg.toString());
				System.out.println("GetGroupStudents to remove group:   "+removestudentsgroup);
				ps2.setNull(1, java.sql.Types.VARCHAR);
				for(int i =0,j=2;i<removestudentsgroup.size();i++,j++){
					ps2.setString(j, removestudentsgroup.get(i));
				}
				System.out.println("PreparedStatement 2 : "+ps2.toString());
				ps2.executeUpdate();
				System.out.println("deleted groups for students ");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			conn.close();
		}

		return teacher;
	}

	public static BeanTeacher GetStudentsInfo(BeanTeacher teacher) throws SQLException {
		// TODO Auto-generated method stub
		HashMap<String, BeanClass> map = new HashMap<String, BeanClass>();
		try {
			if(conn==null || conn.isClosed()) GetConnection();
			StringBuffer sqlQuery = new StringBuffer();
			sqlQuery.append("select user_id,assignment_id,group_id,assignment_name,imagefile,NoofImages,content,link, max(submissioncount),submissionDate,wordcount,charcount from user_assignment");
			sqlQuery.append(" where group_id= ? and assignment_id = ?");
			sqlQuery.append(" group by user_id ");
			BeanClass student;		
			System.out.println("GetStudentsInfo statement: "+sqlQuery.toString());
			System.out.println("group id: "+teacher.getGroupid());
			PreparedStatement ps = conn.prepareStatement(sqlQuery.toString());
			ps.setString(1, teacher.getGroupid());
			ps.setInt(2, teacher.getAssignment_id());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				student = new BeanClass();
				student.setUsername(rs.getString("USER_ID"));
				student.setFullContext(rs.getString("CONTENT"));
				student.setImagefile(rs.getString("IMAGEFILE"));
				student.setLink(rs.getString("LINK"));
				student.setAssignment_id(rs.getString("assignment_id"));
				student.setAssignment_name(rs.getString("assignment_name"));
				student.setGroup_id(rs.getString("group_id"));
				student.setSubmission_date(""+rs.getTimestamp("submissionDate"));
				student.setImagesNumber(rs.getInt("NOOFIMAGES"));
				student.setWordCount(rs.getInt("WORDCOUNT"));
				student.setCharCount(rs.getInt("CHARCOUNT"));	
				student  = GetImageList(student);
				student = GetHTMLBody(student);
				if(student.getToDisplay()==null){
					BeanClass temp = new BeanClass();
					temp.setGroup_id(student.getGroup_id());
					temp.setAssignment_id(""+student.getAssignment_id());
					temp = Database.getReviewCriteria(temp);
					student.setToDisplay(temp.getReviewCriteria());
				}
				student = GetMarks(student);
				//System.out.println("student marks size: "+student.getReviewCriteria().size());
				map.put(student.getUsername(), student);
			}
			student = new BeanClass();
			student.setAssignment_id(""+teacher.getAssignment_id());
			student.setGroup_id(teacher.getGroupid());
			teacher.setRandomNumber( getRandom(student) );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{

			teacher.setStudentList(map);

			conn.close();
		}
		return teacher;
	}
	public static BeanClass GetMarks(BeanClass student, String reviewer_id) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println("Get Marks method");
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		ArrayList<OperationParameters> list = new ArrayList<OperationParameters>();
		ArrayList<Integer> studentTotMarks = new ArrayList<Integer>();
		ArrayList<OperationParameters> teacherMarks = new ArrayList<OperationParameters>();
		OperationParameters marks = null;
		try {
			if(conn==null || conn.isClosed()) GetConnection();
			sb.append("select marks,teacher_evaluation from peer_table");
			sb.append(" where user_id = ? and assignment_id = ? and group_id = ? and reviewer_id = ?");
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, student.getUsername());
			ps.setInt(2, Integer.parseInt(student.getAssignment_id()));
			ps.setString(3, student.getGroup_id());
			ps.setString(4, reviewer_id);
			System.out.println("prepared statement : "+ps.toString());
			ResultSet rs = ps.executeQuery();			
			while(rs.next()){	
				int totMarks =0;
				String temp = rs.getString("marks");
				boolean flag = rs.getBoolean("teacher_evaluation");
				System.out.println("flag :"+flag);
				String[] split = temp.split("_"); 
				for(String s: split){
					marks = new OperationParameters();
					String[] grades = s.split(",");
					marks.setWeight(grades[0]);
					marks.setCriteria(grades[1]);
					System.out.println("marks[0] is: "+grades[0]);
					System.out.println("marks[1] is: "+grades[1]);
					marks.setTeacher_evaluation(flag);
					totMarks += Integer.parseInt(grades[0]);
					if(flag == true){
						teacherMarks.add(marks);
					}else
						list.add(marks);
				}
				//For adding total marks 
				if(flag == true){
					student.setTeacherTotMarks(totMarks);
					System.out.println("teacher tot marks: "+totMarks);
				}else{
					studentTotMarks.add(totMarks);
				}

			}
			//for rest of the peers
			sb = new StringBuffer();
			sb.append("select marks,teacher_evaluation from peer_table");
			sb.append(" where user_id = ? and assignment_id = ? and group_id = ? and reviewer_id != ?");
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, student.getUsername());
			ps.setInt(2, Integer.parseInt(student.getAssignment_id()));
			ps.setString(3, student.getGroup_id());
			ps.setString(4, reviewer_id);
			System.out.println("prepared statement : "+ps.toString());
			rs = ps.executeQuery();			
			while(rs.next()){				
				int totMarks =0;
				String temp = rs.getString("marks");
				boolean flag = rs.getBoolean("teacher_evaluation");
				System.out.println("flag :"+flag);
				String[] split = temp.split("_"); 
				for(String s: split){
					marks = new OperationParameters();
					String[] grades = s.split(",");
					marks.setWeight(grades[0]);
					marks.setCriteria(grades[1]);
					System.out.println("marks[0] is: "+grades[0]);
					System.out.println("marks[1] is: "+grades[1]);
					marks.setTeacher_evaluation(flag);
					totMarks += Integer.parseInt(grades[0]);
					if(flag == true){
						teacherMarks.add(marks);
					}else
						list.add(marks);
				}
				//For adding total marks 
				if(flag == true){
					student.setTeacherTotMarks(totMarks);
					System.out.println("teacher tot marks: "+totMarks);
				}else{
					studentTotMarks.add(totMarks);
				}
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(list.size()>0){
				student.setReviewCriteria(list);
				student.setStudentTotMarks(studentTotMarks);
			}
			if(teacherMarks.size()>0) student.setTeacherMarks(teacherMarks);
			ps.close();
		}
		return student;
	}

	public static BeanClass GetMarks(BeanClass student) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println("Get Marks method");
		PreparedStatement ps = null;
		int count = 0,avg =0;
		StringBuffer sb = new StringBuffer();
		ArrayList<OperationParameters> list = new ArrayList<OperationParameters>();
		ArrayList<Integer> studentTotMarks = new ArrayList<Integer>();
		ArrayList<OperationParameters> teacherMarks = new ArrayList<OperationParameters>();
		OperationParameters marks = null;
		try {
			if(conn==null || conn.isClosed()) GetConnection();
			sb.append("select marks,teacher_evaluation from peer_table");
			sb.append(" where user_id = ? and assignment_id = ? and group_id = ?");
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, student.getUsername());
			ps.setInt(2, Integer.parseInt(student.getAssignment_id()));
			ps.setString(3, student.getGroup_id());
			System.out.println("marks and teacher eval : "+ps.toString());
			ResultSet rs = ps.executeQuery();	
			while(rs.next()){	
				int totMarks = 0;
				String temp = rs.getString("marks");
				boolean flag = rs.getBoolean("teacher_evaluation");
				String[] split = temp.split("_"); 
				for(String s: split){
					marks = new OperationParameters();
					String[] grades = s.split(",");
					marks.setWeight(grades[0]);
					marks.setCriteria(grades[1]);
					System.out.println("marks[0] is: "+grades[0]);
					System.out.println("marks[1] is: "+grades[1]);
					marks.setTeacher_evaluation(flag);
					totMarks += Integer.parseInt(grades[0]);
					avg += Integer.parseInt(grades[0]);
					if(flag == true){
						teacherMarks.add(marks);
					}else{
						list.add(marks);
					}					
				}
				count++;
				//For adding total marks 
				if(flag == true){
					student.setTeacherTotMarks(totMarks);
					System.out.println("teacher tot marks: "+totMarks);
				}else{
					studentTotMarks.add(totMarks);
				}
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			int total_overall = 0;
			if(list.size()>0){
				student.setReviewCriteria(list);
				student.setStudentTotMarks(studentTotMarks);
				System.out.println("Review criteria "+student.getReviewCriteria().get(0).getWeight());
				System.out.println("student marks set !");
			}
			if(teacherMarks.size()>0) student.setTeacherMarks(teacherMarks);
			
			for(OperationParameters par: student.getToDisplay()){
				total_overall += Integer.parseInt(par.getWeight());
				
			}			
			if(count > 0 && avg >0){				
				System.out.println("avg marks for : "+student.getUsername()+" "+avg/count);
				System.out.println("total overall marks: "+total_overall);
				student.setAverage(avg/count);
				student.setTotMarks(total_overall);
			}
			ps.close();
		}
		return student;
	}
	public static BeanClass GetTeacherEvaluation(BeanClass student) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println(" GetTeacherEvaluation method");
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		ArrayList<OperationParameters> teacherMarks = new ArrayList<OperationParameters>();
		OperationParameters marks = null;
		try {
			if(conn==null || conn.isClosed()) GetConnection();
			sb.append("select marks,teacher_evaluation from peer_table");
			sb.append(" where user_id = ? and assignment_id = ? and group_id = ?");
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, student.getUsername());
			ps.setInt(2, Integer.parseInt(student.getAssignment_id()));
			ps.setString(3, student.getGroup_id());
			System.out.println("marks and teacher eval : "+ps.toString());
			ResultSet rs = ps.executeQuery();	

			while(rs.next()){	
				int totMarks = 0;
				String temp = rs.getString("marks");
				boolean flag = rs.getBoolean("teacher_evaluation");
				String[] split = temp.split("_"); 
				if(flag == true){
					for(String s: split){
						marks = new OperationParameters();
						String[] grades = s.split(",");
						marks.setWeight(grades[0]);
						marks.setCriteria(grades[1]);
						System.out.println("marks[0] is: "+grades[0]);
						System.out.println("marks[1] is: "+grades[1]);
						marks.setTeacher_evaluation(flag);
						totMarks += Integer.parseInt(grades[0]);
						teacherMarks.add(marks);
						student.setTeacherTotMarks(totMarks);
						System.out.println("teacher tot marks: "+totMarks);
					}					
				}
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(teacherMarks.size()>0) student.setTeacherMarks(teacherMarks);
			ps.close();
		}
		return student;
	}

	public static void UploadMarks(String reviewer, String userId,
			List<OperationParameters> marks, String mode, String group_id,
			String assignment_id) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		StringBuffer grade = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		try {
			if(conn==null || conn.isClosed()) GetConnection();
			if(CheckPeerTable(userId,reviewer, group_id, assignment_id)){
				sb.append("update peer_table  set marks=? where user_id = ? and reviewer_id = ? "
						+ " and assignment_id=? and group_id=?");
				ps = conn.prepareStatement(sb.toString());

				for(OperationParameters ops : marks){
					grade.append(ops.getWeight());grade.append(",");grade.append(ops.getCriteria());
					grade.append("_");
				}
				grade.deleteCharAt(grade.length()-1);
				ps.setString(1, grade.toString());
				ps.setString(2, userId);
				ps.setString(3, reviewer);
				ps.setInt(4, Integer.parseInt(assignment_id));
				ps.setString(5, group_id);
				ps.executeUpdate();
				System.out.println("Marks updated to DB");
			}else{
				int count = getPeersCount(userId,assignment_id,group_id);
				sb.append("insert into peer_table(user_id,reviewer_id,marks,assignment_id,group_id,teacher_evaluation,count )");
				sb.append("values(?, ?, ?,?,?,?,?)");
				ps = conn.prepareStatement(sb.toString());			
				ps.setString(1, userId);			
				ps.setString(2, reviewer);

				for(OperationParameters ops : marks){
					grade.append(ops.getWeight());grade.append(",");grade.append(ops.getCriteria());
					grade.append("_");
				}

				grade.deleteCharAt(grade.length()-1);

				ps.setString(3, grade.toString());
				ps.setInt(4, Integer.parseInt(assignment_id));
				ps.setString(5, group_id);
				if(mode.equalsIgnoreCase("student"))
					ps.setBoolean(6, false);
				else
					ps.setBoolean(6, true);
				ps.setInt(7, count+1);
				ps.execute();
				System.out.println("Marks Inserted to DB");
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			ps.close();
			conn.close();
		}
	}

	private static boolean IsExist(BeanClass student) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		try {
			if(conn==null || conn.isClosed()) GetConnection();
			sb.append("select * from user_assignment where user_id = ? and assignment_id = ? and group_id = ?");
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, student.getUsername());			
			ps.setInt(2, Integer.parseInt(student.getAssignment_id()));
			ps.setString(3, student.getGroup_id());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return true;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
	public static BeanClass checkCount(BeanClass student) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		try {
			if(conn==null || conn.isClosed()) GetConnection();
			sb.append("select SUBMISSIONCOUNT from user_assignment where user_id = ? and group_id = ? and assignment_id=?");
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, student.getUsername());			
			ps.setString(2, student.getGroup_id());
			ps.setInt(3, Integer.parseInt(student.getAssignment_id()));
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				student.setCount(rs.getInt("SUBMISSIONCOUNT"));
			}else{

			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			ps.close();
		}
		return student;
	}

	private static boolean CheckPeerTable(String userId, String reviewerId,String group_id,
			String assignment_id) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		try {
			if(conn==null || conn.isClosed()) GetConnection();
			sb.append("select * from peer_table where user_id = ? and reviewer_id = ? and assignment_id=? and group_id=?");
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, userId);			
			ps.setString(2, reviewerId);
			ps.setInt(3, Integer.parseInt(assignment_id));
			ps.setString(4, group_id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return true;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			ps.close();
		}

		return false;
	}
	private static int getPeersCount(String user_id,String assignment_id,String group_id) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null; int count =0;
		StringBuffer sb = new StringBuffer();
		sb.append("select max(count) from peer_table  where user_id= ? and assignment_id=? and group_id=?");
		ps = conn.prepareStatement(sb.toString());
		ps.setString(1, user_id);
		ps.setInt(2, Integer.parseInt(assignment_id));
		ps.setString(3, group_id);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			count = rs.getInt(1);
		}
		return count;
	}

	public static BeanTeacher InsertReviewPannel(BeanTeacher teacher) {
		// TODO Auto-generated method stub
		String tableName = teacher.getGroupid()+"_"+teacher.getAssignment_id();

		if(updateCheck_ReviewPannel(teacher)){
			System.out.println("update review");
			String sqlQuery = "update group_assign_review  set random_number=?,submissioncount=?,review_pannel=? where group_id = ? and assignment_id = ?";
			PreparedStatement ps = null;
			try {
				if(conn==null || conn.isClosed()) GetConnection();
				ps = conn.prepareStatement(sqlQuery);
				ps.setInt(1, teacher.getRandomNumber());
				ps.setInt(2, teacher.getSubmissionCount());
				StringBuffer sb = getReviewContent(teacher);
				ps.setString(3, sb.toString());
				ps.setString(4, teacher.getGroupid());
				ps.setInt(5, teacher.getAssignment_id());
				System.out.println("String is : "+ps.toString());
				ps.executeUpdate();
				System.out.println("successfully updated review criteria");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("Insert review");
			String sqlQuery = "INSERT INTO group_assign_review(group_id,assignment_id,random_number,submissioncount,review_pannel) values(?,?,?,?,?)";
			PreparedStatement ps = null;
			try {
				if(conn==null || conn.isClosed()) GetConnection();
				ps = conn.prepareStatement(sqlQuery);
				ps.setString(1, teacher.getGroupid());
				ps.setInt(2, teacher.getAssignment_id());
				ps.setInt(3, teacher.getRandomNumber());
				ps.setInt(4, teacher.getSubmissionCount());
				StringBuffer sb = getReviewContent(teacher);
				ps.setString(5, sb.toString());
				System.out.println("String is : "+ps.toString());
				ps.executeUpdate();
				System.out.println("successfully inserted review criteria");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return teacher;
	}


	private static StringBuffer getReviewContent(BeanTeacher teacher) {
		// TODO Auto-generated method stub
		System.out.println("getReviewContent method");
		StringBuffer sb = new StringBuffer();
		for(OperationParameters ops : teacher.getOperationParameterses()){
			sb.append(ops.getCriteria());sb.append(",");sb.append(ops.getWeight());
			sb.append(" ");
		}
		sb.deleteCharAt(sb.length()-1);
		System.out.println("review criteria is : "+sb.toString());
		return sb;
	}
	private static boolean updateCheck_ReviewPannel(BeanTeacher teacher) {
		// TODO Auto-generated method stub
		String sqlQuery = "select * from group_assign_review where group_id = ? and assignment_id = ?";
		int count =0;
		PreparedStatement ps = null;
		try {
			if(conn==null || conn.isClosed()) GetConnection();
			ps = conn.prepareStatement(sqlQuery);
			ps.setString(1, teacher.getGroupid());
			ps.setInt(2, teacher.getAssignment_id());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
	public static BeanClass GetPeerInfo(BeanClass student) throws SQLException {
		HashMap<String, BeanClass> map = new HashMap<String, BeanClass>();
		PreparedStatement ps = null;
		int count =1;
		int randomNumber = getRandom(student);
		System.out.println("random number : "+randomNumber);
		try {
			if(conn==null || conn.isClosed()) GetConnection();
			student = getSubmissionDate(student);
			StringBuffer sqlQuery = new StringBuffer();			
			sqlQuery.append("select user_id,assignment_id,group_id,assignment_name,imagefile,NoofImages,content,link,");
			sqlQuery.append("submissioncount,submissionDate,wordcount,charcount from user_assignment where  ");
			sqlQuery.append("user_id != ? and assignment_id =? and group_id = ? and submissionDate > ?");
			sqlQuery.append("order by submissionDate asc limit ?");
			ps = conn.prepareStatement(sqlQuery.toString());
			System.out.println("Get peer info submission date: "+student.getSubmission_date());
			ps.setString(1, student.getUsername());
			ps.setString(2, student.getAssignment_id());
			ps.setString(3, student.getGroup_id());
			ps.setString(4, student.getSubmission_date());
			ps.setInt(5, randomNumber);
			System.out.println("prepared statement: "+ps.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next() && count <=randomNumber){
				BeanClass peer = new BeanClass();
				peer.setUsername(rs.getString("USER_ID"));
				peer.setFullContext(rs.getString("CONTENT"));
				peer.setImagefile(rs.getString("IMAGEFILE"));
				peer.setLink(rs.getString("LINK"));
				peer.setAssignment_id(rs.getString("assignment_id"));
				peer.setAssignment_name(rs.getString("assignment_name"));
				peer.setGroup_id(rs.getString("group_id"));
				peer.setSubmission_date(""+rs.getTimestamp("submissionDate"));
				peer.setImagesNumber(rs.getInt("NOOFIMAGES"));
				peer.setWordCount(rs.getInt("WORDCOUNT"));
				peer.setCharCount(rs.getInt("CHARCOUNT"));	
				peer  = GetImageList(peer);
				peer = GetHTMLBody(peer);
				peer = GetMarks(peer,student.getUsername());
				peer = GetTeacherEvaluation(peer);
				//System.out.println(peer.getUsername());
				map.put(peer.getUsername(), peer);
				count++;
			}
			if(count <=randomNumber){
				sqlQuery = new StringBuffer();			
				sqlQuery.append("select user_id,assignment_id,group_id,assignment_name,imagefile,NoofImages,content,link,");
				sqlQuery.append("submissioncount,submissionDate,wordcount,charcount from user_assignment where");
				sqlQuery.append(" assignment_id =? and group_id = ? and user_id != ?");
				sqlQuery.append("order by submissionDate ");
				ps = conn.prepareStatement(sqlQuery.toString());
				ps.setInt(1, Integer.parseInt(student.getAssignment_id()));
				ps.setString(2, student.getGroup_id());
				ps.setString(3, student.getUsername());
				System.out.println("prepared statement: "+ps.toString());
				rs = ps.executeQuery();
				while(rs.next() && count <=randomNumber){
					BeanClass peer = new BeanClass();
					peer.setUsername(rs.getString("USER_ID"));
					peer.setFullContext(rs.getString("CONTENT"));
					peer.setImagefile(rs.getString("IMAGEFILE"));
					peer.setLink(rs.getString("LINK"));
					peer.setAssignment_id(rs.getString("assignment_id"));
					peer.setAssignment_name(rs.getString("assignment_name"));
					peer.setGroup_id(rs.getString("group_id"));
					peer.setSubmission_date(""+rs.getTimestamp("submissionDate"));
					peer.setImagesNumber(rs.getInt("NOOFIMAGES"));
					peer.setWordCount(rs.getInt("WORDCOUNT"));
					peer.setCharCount(rs.getInt("CHARCOUNT"));	
					peer  = GetImageList(peer);
					peer = GetHTMLBody(peer);
					peer = GetMarks(peer,student.getUsername());
					map.put(peer.getUsername(), peer);
					count++;
				}

			}
			//get Review Criteria
			student = getReviewCriteria(student);	
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (NullPointerException exp) {
			System.out.println("coming");
		}
		finally{
			if(map.size()>0){
				student.setPeerList(map);
			}
			ps.close();
			conn.close();
		}
		return student;
	}

	public static BeanClass getReviewCriteria(BeanClass student) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println("getReviewCriteria");
		if(conn==null || conn.isClosed()) GetConnection();
		StringBuffer sqlQuery = new StringBuffer();	
		String review = "";	
		PreparedStatement ps = null;
		// select random_number from group_assign_review  where group_id = ? and assignment_id =?
		sqlQuery.append("select review_pannel,random_number from group_assign_review where group_id = ? and assignment_id = ?");
		ps = conn.prepareStatement(sqlQuery.toString());
		ps.setString(1, student.getGroup_id());
		ps.setInt(2, Integer.parseInt(student.getAssignment_id()));
		System.out.println("review pannel:  "+ps.toString());
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			review = rs.getString("review_pannel");
			student.setRandomNumber(rs.getInt("random_number"));
		}
		System.out.println("review criteria is: "+review);
		String[] criteria = review.split(" ");
		OperationParameters ops = null;
		ArrayList<OperationParameters> operationParameterses = new ArrayList<OperationParameters>();
		System.out.println("criteria[0] is : "+criteria[0]);
		for(String s :criteria){
			System.out.println("s is: "+s);
			ops = new OperationParameters();
			String[] split = s.split(",");
			System.out.println("split[0] is: "+split[0]);
			System.out.println("split[1] is: "+split[1]);
			ops.setCriteria(split[0]);
			ops.setWeight(split[1]);
			operationParameterses.add(ops);
		}
		student.setReviewCriteria(operationParameterses);
		return student;
	}
	public static BeanTeacher getReviewCriteria(BeanTeacher teacher) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("getReviewCriteria");
		if(conn==null || conn.isClosed()) GetConnection();
		StringBuffer sqlQuery = new StringBuffer();	
		String review = "";	
		PreparedStatement ps = null;
		try{
			
		sqlQuery.append("select review_pannel,random_number,submissioncount from group_assign_review where group_id = ? and assignment_id = ?");
		ps = conn.prepareStatement(sqlQuery.toString());
		ps.setString(1, teacher.getGroupid());
		ps.setInt(2, teacher.getAssignment_id());
		System.out.println("review pannel:  "+ps.toString());
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			review = rs.getString("review_pannel");
			teacher.setRandomNumber(rs.getInt("random_number"));
			teacher.setSubmissionCount(rs.getInt("submissioncount"));
		}
		System.out.println("review criteria is: "+review);
		System.out.println("Submission count : "+teacher.getSubmissionCount());
		String[] criteria = review.split(" ");
		OperationParameters ops = null;
		ArrayList<OperationParameters> operationParameterses = new ArrayList<OperationParameters>();
		System.out.println("criteria[0] is : "+criteria[0]);
		for(String s :criteria){
			System.out.println("s is: "+s);
			ops = new OperationParameters();
			String[] split = s.split(",");
			System.out.println("split[0] is: "+split[0]);
			System.out.println("split[1] is: "+split[1]);
			ops.setCriteria(split[0]);
			ops.setWeight(split[1]);
			operationParameterses.add(ops);
		}
		teacher.setOperationParameterses(operationParameterses);
		}
		catch(ArrayIndexOutOfBoundsException exception) {
			Login.Logout_Excpetion();
			
		}
		return teacher;
	}
	private static int getRandom(BeanClass student) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		int count =0;
		try {
			if(conn==null || conn.isClosed()) GetConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select random_number from group_assign_review  where group_id = ? and assignment_id =?");
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, student.getGroup_id());
			ps.setInt(2, Integer.parseInt(student.getAssignment_id()));
			System.out.println("prepareStatement: "+ps.toString());
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt("random_number");
			}
		}finally{
			ps.close();
		}		
		return count;
	}
	public static BeanTeacher getAssigIds(BeanTeacher teacher) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ArrayList<String> assignids = new ArrayList<String>();
		try {
			if(conn==null || conn.isClosed()) GetConnection();
			System.out.println("getAssignmentIds method");
			StringBuffer sb = new StringBuffer();
			sb.append("select distinct assignment_id  from group_assign_review where group_id = ?");
			ps = conn.prepareStatement(sb.toString());
			System.out.println("getAssigIds ps : "+ps.toString());
			ps.setString(1, teacher.getGroupid());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				assignids.add(rs.getString("assignment_id"));
			}
		}finally{
			if(assignids.size()>0) teacher.setAssignids(assignids);
			ps.close(); conn.close();
		}		
		return teacher;
	}
	public static HashMap<String, Integer> getAssignmentIds(BeanClass student) throws SQLException {
		// TODO Auto-generated method stub

		HashMap<String, Integer> map = new HashMap<String, Integer>();
		PreparedStatement ps = null;
		try {
			if(conn==null || conn.isClosed()) GetConnection();
			System.out.println("getAssignmentIds method");
			StringBuffer sb = new StringBuffer();
			sb.append("select distinct a.assignment_id, a.SUBMISSIONCOUNT   from group_assign_review a join user_table b where a.group_id = b.group_id and b.user_id =?");
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, student.getUsername());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				map.put(rs.getString("assignment_id"),rs.getInt("SUBMISSIONCOUNT"));
			}
		}finally{
			ps.close(); conn.close();
		}		
		return map;
	}


	private static BeanClass getSubmissionDate(BeanClass student) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select submissionDate from user_assignment  where user_id = ? and group_id = ? and assignment_id = ?");
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, student.getUsername());
			ps.setString(2, student.getGroup_id());
			ps.setInt(3, Integer.parseInt(student.getAssignment_id()));
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				System.out.println(" sub date: "+rs.getTimestamp(1));
				student.setSubmission_date("" +rs.getTimestamp(1));
			}
		}finally{
			ps.close();
		}
		return student;
	}
	private static BeanClass GetImageList(BeanClass student){
		String[] temp = student.getImagefile().split(",");
		ArrayList<String> imglist = new ArrayList<String>();
		for(int i=0;i<temp.length;i++){
			imglist.add(temp[i]);
		}
		student.setImages(imglist);

		return student;
	}
	public static BeanClass GetHTMLBody(BeanClass student) {
		StringBuilder sb = new StringBuilder();
		Document document = Jsoup.parse(student.getFullContext());
		Elements elements = document.body().select("*");
		for (Element element : elements) {
			sb.append( element.ownText());
		}
		student.setContent(sb.toString());
		return student;
	}
	private static void GetDBdetails() {
		// TODO Auto-generated method stub
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(Login.CONSTANT_FILE_PATH);
			if(input==null) System.out.println("nullllllllll values");
			// load a properties file
			prop.load(input);

			// get the property value and print it out
			url = getValues("url");
			dbName = getValues("dbName");
			driver = getValues("driver");;
			userName= getValues("userName");
			password =getValues("password");
			student = getValues("student");
			ta= getValues("ta");
			teacher = getValues("teacher");

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void GetConnection(){
		System.out.println("-------- MySQL JDBC Connection Testing ------------");
		try {
			GetDBdetails();
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url+dbName,userName,password);
			System.out.println("Connected to the database");

		} catch (Exception e) {
			System.out.println("NO CONNECTION =(");
		}

	}
	public static String getValues(String inp) throws IOException{
		Properties prop = new Properties();
		InputStream input = new FileInputStream(Login.CONSTANT_FILE_PATH);
		if(input==null) System.out.println("nullllllllll values in getValues method");
		// load a properties file
		prop.load(input);
		if(prop.get(inp)!=null){
			String value = ""+ prop.get(inp);
			return value ;
		}
		return null;
	}





}
