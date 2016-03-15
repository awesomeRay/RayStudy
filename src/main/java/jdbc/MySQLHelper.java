package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySQLHelper {

    private final static Logger logger = LoggerFactory.getLogger(MySQLHelper.class);

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("", e);
        }
    }

    public static Connection getConnection(String url, String username, String password) throws Exception {
        return DriverManager.getConnection(url, username, password);
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error("", e);
            }
        }
    }
    
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.error("", e);
            }
        }
    }

    public static void closePreparedStatement(PreparedStatement stat) {
        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                logger.error("", e);
            }
        }
    }

    public static boolean execute(Connection conn, String sql) {
        return execute(conn, sql, null);
    }

    public static boolean execute(Connection conn, String sql, Object[] params) {
        PreparedStatement stat = null;
        try {
            stat = createPreparedStatement(conn, sql, params);
            return stat.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e1) {
                    logger.error("", e1);
                }
            }
        }
    }

    public static PreparedStatement createPreparedStatement(Connection conn, String sql) throws Exception {
        return createPreparedStatement(conn, sql, null);
    }

    public static PreparedStatement createPreparedStatement(Connection conn, String sql, Object[] params) throws Exception {
        PreparedStatement stat = conn.prepareStatement(sql);
        if (params != null) {
            int idx = 1;
            for (Object param : params) {
                stat.setObject(idx++, param);
            }
        }
        return stat;
    }
    
    public static ResultSet executeQuery (Connection conn, String sql) {
        return executeQuery(conn, sql, null);
    }

    
    public static ResultSet executeQuery (Connection conn, String sql, Object[] params) {
    	PreparedStatement stat = null;
    	try {
    		stat = createPreparedStatement(conn, sql, params);
    		return stat.executeQuery();
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
}
