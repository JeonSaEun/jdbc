package com.zerock.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import lombok.extern.log4j.Log4j;

@Log4j
public class App2 {

	static String jdbcURL = "jdbc:oracle:thin:@PDB";
	static String user = "HR"; 
	static String pw = "oracle";
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
		
		try(Connection conn = 
				DriverManager.getConnection(jdbcURL, user, pw);) {
				
			Objects.requireNonNull(conn);
			
			log.info(conn);
		
			//--------------------------//
			String sql = "SELECT * FROM Employees WHERE salary > ?";
			
			try(PreparedStatement stmt = conn.prepareStatement(sql);) {
				
				stmt.setDouble(1, 3000.0);
			
				ResultSet rs = stmt.executeQuery();
					
					//--------------------------//
					while(rs.next()) {
						int empId = rs.getInt("EMPLOYEE_ID");
			    		String firstName = rs.getString("FIRST_NAME");
			    		String lastName = rs.getString("LAST_NAME");
			    		String email = rs.getString("EMAIL");
			    		double salary = rs.getDouble("SALARY");
			    		
			    		String format = "%d, %s, %s, %s, %f";
			    		log.info(String.format(format,
			    				empId,firstName,lastName,email,salary));
							} // while
			
			} // inner-1 try-with-resources
			
		} catch(SQLException e) {
			e.printStackTrace();
		} // try-with-resources
	} // main

} // end class