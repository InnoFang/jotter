package io.innofang.utils;

import com.sun.istack.internal.NotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * Created by Inno Fang on 2018/4/15.
 */
public class JDBCUtils {

    private String driver;
    private String url;
    private String username;
    private String password;

    /**
     * e.g. file name is `mysql.ini` and the content are as follow:
     * url=jdbc:mysql://127.0.0.1:3306/test # `test` is your database name
     * driver=com.mysql.jdbc.Driver         # MySQL Driver, replace with your own db driver
     * username=your_username
     * password=your_password
     */
    private final static String INIT_FILE = "<Your `ini` file location>";

    private static volatile JDBCUtils sInstance;

    private JDBCUtils() {
    }

    public static JDBCUtils get() {
        if (null == sInstance) {
            synchronized (JDBCUtils.class) {
                if (null == sInstance) {
                    sInstance = new JDBCUtils();
                }
            }
        }
        sInstance.initParam(INIT_FILE);
        return sInstance;
    }

    private void initParam(String paramFile) {
        try {
            // 使用 properties 类类加载属性文件
            Properties prop = new Properties();
            prop.load(new FileInputStream(paramFile));
            driver = prop.getProperty("driver");
            url = prop.getProperty("url");
            username = prop.getProperty("username");
            password = prop.getProperty("password");

            // 加载驱动
            Class.forName(driver);
            connection = getConnection();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private Connection connection;
    private PreparedStatement pstmt;
    private ResultSet resultSet;

    /**
     * 获取数据库连接
     * @return
     */
    private Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * 增删改
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public boolean update(String sql, Object... params) throws SQLException {

        int result = -1;
        pstmt = connection.prepareStatement(sql);
        int index = 1;
        if (params != null && params.length != 0) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(index++, params[i]);
            }
        }

        result = pstmt.executeUpdate();
        return result > 0;
    }

    /**
     * 查询单条记录
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public Map<String, Object> getQueryResult(String sql, Object ... params) throws SQLException {
        Map<String, Object> map = new HashMap<>();
        int index = 1;
        pstmt = connection.prepareStatement(sql);
        if (params != null && params.length != 0) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(index++, params[i]);
            }
        }

        resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columns = metaData.getColumnCount();
        while (resultSet.next()) {
            getResultSet(metaData, columns, map);
        }
        return map;
    }

    public List<Map<String, Object>> getQueryResults(String sql, Object... params) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        int index = 1;
        pstmt = getConnection().prepareStatement(sql);
        if (null != params && params.length != 0) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(index++, params[i]);
            }
        }

        resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columns = metaData.getColumnCount();
        while (resultSet.next()) {
            Map<String , Object> map = new HashMap<>();
            getResultSet(metaData, columns, map);
            list.add(map);
        }
        return list;
    }

    public <T> T handleResultSet(String sql, Object[] params, ResultSetHandler<T> rs) throws SQLException {
        try {
            return rs.handler(getQueryResult(sql, params));
        } catch (SQLException e) {
            throw e;
        }
    }
    public <T> List<T> handleResultSets(String sql, Object[] params, ResultSetHandler<T> rs) throws SQLException {
        try {
            List<T> list = new ArrayList<>();
            getQueryResults(sql, params).forEach(obj -> list.add(rs.handler(obj)));
            return list;
        } catch (SQLException e) {
            throw e;
        }
    }



    private void getResultSet(ResultSetMetaData metaData, int columns, Map<String, Object> map) throws SQLException {
        for (int i = 1; i <= columns ; i++) {
            String name = metaData.getColumnName(i);
            Object value = resultSet.getObject(name);
            if (null == value) value = "";
            map.put(name, value);
        }
    }

    public interface ResultSetHandler<T> {

        public T handler(Map<String, Object> queryResult);

    }

}
