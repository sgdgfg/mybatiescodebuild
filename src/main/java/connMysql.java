

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class connMysql {

    String database="teach";
     String DRIVER = "com.mysql.jdbc.Driver";
        String URL = "jdbc:mysql://localhost:3306/"+database+"?useUnicode=true&characterEncoding=utf8";
      String USERNAME = "root";
      String PASSWORD = "123123";

      String SQL = "SELECT * FROM ";// 数据库操作

    {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {

        }
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    public   Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
          //  LOGGER.error("get connection failure", e);
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     * @param conn
     */
    public void closeConnection(Connection conn) {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
              //  LOGGER.error("close connection failure", e);
            }
        }
    }

    /**
     * 获取数据库下的所有表名
     */
    public   List<String> getTableNames() {
        List<String> tableNames = new ArrayList<>();
        Connection conn = getConnection();
        ResultSet rs = null;
        try {
            //获取数据库的元数据
            DatabaseMetaData db = conn.getMetaData();

            //从元数据中获取到所有的表名
            rs = db.getTables(database, null, null, new String[] { "TABLE" });
            while(rs.next()) {
                tableNames.add(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
          //  LOGGER.error("getTableNames failure", e);
        } finally {
            try {
               rs.close();
                closeConnection(conn);
            } catch (Exception e) {
             //   LOGGER.error("close ResultSet failure", e);
            }
        }
        return tableNames;
    }

    public List<String> getPrimary(String tableName){
        List<String> columnNames = new ArrayList<>();
        //与数据库的连接
        Connection conn = getConnection();

        try {

            //结果集元数据
            DatabaseMetaData rsmd = conn.getMetaData();


            //表列数
            ResultSet set = rsmd.getPrimaryKeys(database,null,tableName);

           while (set.next()){
              columnNames.add( set.getString("COLUMN_NAME"));
               System.out.print("数据库名称:"+set.getString("TABLE_CAT")+"                  ");
               System.out.print("表名称:"+set.getString("TABLE_NAME")+"                  ");
               System.out.print("主键列的名称:"+set.getString("COLUMN_NAME")+"                  ");
               System.out.print("类型:"+set.getString("PK_NAME")+"                  ");
               System.out.println("");


           }
        } catch (SQLException e) {
            //  LOGGER.error("getColumnNames failure", e);
        } finally {

                try {

                    closeConnection(conn);
                } catch (Exception e) {
                    //  LOGGER.error("getColumnNames close pstem and connection failure", e);
                }
            }
        return columnNames;
        }


    /**
     * 获取表中所有字段名称
     * @param tableName 表名
     * @return
     */
    public   List<String> getColumnNames(String tableName) {
        List<String> columnNames = new ArrayList<>();
        //与数据库的连接
        Connection conn = getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        try {
            pStemt = conn.prepareStatement(tableSql);
            //结果集元数据
            ResultSetMetaData rsmd = pStemt.getMetaData();


            //表列数
            int size = rsmd.getColumnCount();
            for (int i = 0; i < size; i++) {
                columnNames.add(rsmd.getColumnName(i + 1));
            }
        } catch (SQLException e) {
          //  LOGGER.error("getColumnNames failure", e);
        } finally {
            if (pStemt != null) {
                try {
                    pStemt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                  //  LOGGER.error("getColumnNames close pstem and connection failure", e);
                }
            }
        }
        return columnNames;
    }

    /**
     * 获取表中所有字段类型
     * @param tableName
     * @return
     */
    public   List<String> getColumnTypes(String tableName) {
        List<String> columnTypes = new ArrayList<>();
        //与数据库的连接
        Connection conn = getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        try {
            pStemt = conn.prepareStatement(tableSql);
            //结果集元数据
            ResultSetMetaData rsmd = pStemt.getMetaData();

            //表列数
            int size = rsmd.getColumnCount();
            for (int i = 0; i < size; i++) {
                columnTypes.add(rsmd.getColumnTypeName(i + 1));

            }
        } catch (SQLException e) {
            //LOGGER.error("getColumnTypes failure", e);
        } finally {
            if (pStemt != null) {
                try {
                    pStemt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                   // LOGGER.error("getColumnTypes close pstem and connection failure", e);
                }
            }
        }
        return columnTypes;
    }

    /**
     * 获取表中字段的所有注释
     * @param tableName
     * @return
     */
    public   List<String> getColumnComments(String tableName) {
        List<String> columnTypes = new ArrayList<>();
        //与数据库的连接
        Connection conn = getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        List<String> columnComments = new ArrayList<>();//列名注释集合
        ResultSet rs = null;
        try {
            pStemt = conn.prepareStatement(tableSql);
            rs = pStemt.executeQuery("show full columns from " + tableName);
            while (rs.next()) {
                columnComments.add(rs.getString("Comment"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    //LOGGER.error("getColumnComments close ResultSet and connection failure", e);
                }
            }
        }
        return columnComments;
    }
 /*   public static void main(String[] args) {
        List<String> tableNames = getTableNames();
        System.out.println("tableNames:" + tableNames);
        for (String tableName : tableNames) {
            System.out.println("ColumnNames:" + getColumnNames(tableName));
            System.out.println("ColumnTypes:" + getColumnTypes(tableName));
            System.out.println("ColumnComments:" + getColumnComments(tableName));
        }
    }*/
}
