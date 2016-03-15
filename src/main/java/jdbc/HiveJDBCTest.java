package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 运行方式 java -cp .:/usr/lib/hive/lib/*:$(hadoop classpath):./ HiveJDBCTest
 * 
 * @author lirui1
 * 
 */
public class HiveJDBCTest {
	private static String driverName = "org.apache.hive.jdbc.HiveDriver";

	public static void main(String[] args) throws SQLException {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		Connection con = DriverManager.getConnection("jdbc:hive2://10.201.0.179:10000/default", "", "");
		Statement stmt = con.createStatement();
		// use只能用execute 不能用返回ResultSet的方法
//		stmt.execute("use ott");
		String sql = "show create table ott.play";
		ResultSet res = stmt.executeQuery(sql);
		while (res.next()) {
			System.out.println(res.getString(1));
		}
		res.close();
		stmt.close();
		con.close();
	}
}
