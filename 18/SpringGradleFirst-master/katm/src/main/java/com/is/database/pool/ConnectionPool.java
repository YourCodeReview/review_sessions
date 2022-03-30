package com.is.database.pool;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionPool {

    public static String getValue(String key) {
        Connection c = null;
        PreparedStatement ps = null;
        String res = "";
        try {
            c = getConnection();
            ps = c.prepareStatement("SELECT value FROM rustam_set WHERE id=?");
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                res = rs.getString("value");
            }
            rs.close();

        } catch (Exception e) {
            com.is.ISLogger.getLogger().error(getPstr(e));
        } finally {
            close(c);
            if (ps!=null)
            {
                try {
                    ps.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return res;
    }

    public static Connection getConnection() throws SQLException {
        try {
            Context context = new InitialContext();
            Context envContext = (Context) context.lookup("java:/comp/env");
            DataSource datasource = (DataSource) envContext.lookup("jdbc/myoracle");
            if (datasource == null) {
                throw new Exception("No DataSource");
            }
            return datasource.getConnection();
        } catch (Exception e) {
            com.is.ISLogger.getLogger().error(getPstr(e));
            e.printStackTrace();
            throw new RuntimeException("Database Not Available.");
        }
    }

    public static String getPstr(Exception ex){
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public static void close(Connection c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (SQLException e) {
            com.is.ISLogger.getLogger().error(getPstr(e));
        }
    }

}
