package edu.techseekers.training.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class JDBC {
	private static String url = "jdbc:mysql://172.16.11.156:3306/";
	// MySQL配置时的用户名
	private static String user = "root";
	// Java连接MySQL配置时的密码
	private static String password = "139444";
	private static Connection conn = null;

	/**
	 * Execute sql with return value
	 * 
	 * @param sql
	 * @param database
	 * @return Map<String, String>
	 */
	public static Map<String, Double> QuerySalaryData() {
		String database = "employees";
		Map<String, Double> results=new HashMap<String, Double>();
		String sql = "SELECT AVG(salaries.salary),dept_emp.dept_no FROM employees.salaries inner join employees.dept_emp on employees.dept_emp.emp_no=employees.salaries.emp_no GROUP BY dept_no;";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 连续数据库
			conn = DriverManager.getConnection(url + database, user, password);
			// statement用来执行SQL语句
			Statement statement = conn.createStatement();
			statement.execute(sql);
			// 要执行的SQL语句
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				results.put(rs.getString("dept_no"),rs.getDouble("AVG(salaries.salary)"));
			}
			conn.close();
			return results;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
