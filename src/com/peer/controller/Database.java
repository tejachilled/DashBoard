package com.peer.controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
	Connection conn = null;
	String url = "jdbc:mysql://localhost:3306/";
	String dbName = "test";
	String driver = "com.mysql.jdbc.Driver";
	String userName = "ravi";
	String password = "teja";
	Database(){
		try {
			  Class.forName(driver).newInstance();
			  conn = DriverManager.getConnection(url+dbName,userName,password);
			  System.out.println("Connected to the database");
			  conn.close();
			  System.out.println("Disconnected from database");
			} catch (Exception e) {
			    System.out.println("NO CONNECTION =(");
			}
			System.out.println("-------- MySQL JDBC Connection Testing ------------");
			 
	}
}
