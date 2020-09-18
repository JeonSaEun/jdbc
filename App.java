package com.zerock.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import lombok.extern.log4j.Log4j;


@Log4j
public class App {
	
	// 1. JDBC Driver를 사용하려면, 우선 연결하고자 하는 데이터베이스에 대한
	// 2가지 정보를 확보 : jdbcURL, jdbcDriver 우선 알아야 합니다.
	static String jdbcURL = "jdbc:oracle:thin:@PDB";
	
    public static void main( String[] args ) 
    	throws SQLException {
    	
    	Connection conn_1 = null;
    	Connection conn_2 = null;
    	
    	try(Connection conn1 = 
        		DriverManager.getConnection(
        				jdbcURL, "HR", "oracle"); ) {
    		
    		Connection conn2 = conn1;
			
    		log.info(conn1);
    		log.info(conn2);
    		
    		conn1.setAutoCommit(false);		// TX 시작
    		
    		conn_1 = conn1;
    		conn_2 = conn2;
    		
    		String sql_1 = "UPDATE employees SET salary = 0.0 WHERE salary > ?";
    		PreparedStatement pstmt1 = conn1.prepareStatement(sql_1);
    		pstmt1.setDouble(1, 4000);
    		
    		pstmt1.executeUpdate();	// when DML executing
    		//---------------------------------//
    		String sql_2 = "UPDATE employees SET salary = 0.0 WHERE COMMISSION_PCT IS NOT NULL";
    		
    		PreparedStatement pstmt2 = conn2.prepareStatement(sql_2);
    		
    		pstmt2.executeUpdate(); // when DML executing
    		
    		
    		conn1.commit(); 		// TX 종료 (ALL)
    		conn2.commit(); 		// TX 종료 (ALL)
    	} catch(SQLException e) {
    		conn_1.rollback(); 	// TX 종료 (Nothing)
    		conn_2.rollback(); 	// TX 종료 (Nothing)
    	}// try-with-resources
    	
    } // main
    
} // end class
