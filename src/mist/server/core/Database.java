package mist.server.core;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class Database {
	
	static final String DB_USERNAME = "root";
	static final String DB_PASSWORD = "";
	static final String DB_SERVER = "localhost";
	
	MysqlDataSource dataSource;
	Connection con;
	
	public Database() {
		dataSource = new MysqlDataSource();
		dataSource.setUser(DB_USERNAME);
		dataSource.setPassword(DB_PASSWORD);
		dataSource.setServerName(DB_SERVER);
		
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public boolean checkAccountExists(String email, String password){
		//"SELECT * FROM accounts WHERE email='" + email + "' AND password='" + password + "'"
		String q = "SELECT * FROM accounts WHERE email='" + email + "' AND password='" + password + "'";
		try {
			Statement stmt = con.createStatement();
			stmt.execute(q);
			stmt.close();
			System.out.println(q);
			return stmt.getResultSet().wasNull();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
}
