package jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

public class TestJDBC {
	static String url = "jdbc:mysql://10.201.16.19:3306/test?useUnicode=true&characterEncoding=UTF-8";
	static String username = "root";
	static String password = "root";
	public static void test1() throws Exception{
		Connection con = MySQLHelper.getConnection(url, username, password);
		String sql = "select count(*) from history";
		ResultSet rs = MySQLHelper.executeQuery(con, sql);
		while (rs.next()) {
			System.out.println(rs.getInt(1));
		}
		MySQLHelper.closeResultSet(rs);
		MySQLHelper.closeConnection(con);
	}
	
	public static void insertHistory() throws Exception{
		Connection con = MySQLHelper.getConnection(url, username, password);
		String sql = "insert into history(clock, value, ns) values (?, ?, 0)";
		for (int i = 0; i < 500; i++) {
			int clock = (int)(System.currentTimeMillis()/1000);
			MySQLHelper.execute(con, sql, new Object[]{clock, i});
			Thread.sleep(6000);
		}
		MySQLHelper.closeConnection(con);
	}
	
	public static void test2() throws Exception {
		Connection con = MySQLHelper.getConnection(url, username, password);
		String sql = "insert into history(clock, value, ns) values (?, ?, 0)";
		int clock = (int)(System.currentTimeMillis()/1000);
		for (int i = 0; i < 500; i++) {
			
			MySQLHelper.execute(con, sql, new Object[]{clock, i});
//			Thread.sleep(6000);
		}
		MySQLHelper.closeConnection(con);
	}
	
	public static void main(String[] args) throws Exception{
		insertHistory();
	}
}
