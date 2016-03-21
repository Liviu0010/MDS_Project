package Database;

import java.sql.*;

/**
 * DatabaseHandler is a singleton class, 
 * the instance of which handles the database.
 */
public class DatabaseHandler {

    // JDBC driver name and database URL
    private static final String JDBC_Driver = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/";

    //  Database attributes
    private static final String USER = "root";
    private static final String PASS = "Hai_sa_programam";
    private static final String dbName = "BattleAI";

    Connection conn;

    private static DatabaseHandler instance = new DatabaseHandler();
    
    public static DatabaseHandler getInstance(){
        return instance;
    }

    private DatabaseHandler() {
        preliminaries();
        if(checkDBExists() == true){
            create_Database();
        }
        closeConnection();
    }
    
    
    /**
     * Register JDBC driver and open a connection
     */
    private void preliminaries(){
        try {
            Class.forName(JDBC_Driver);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException se) {
            // Handle errors for JDBC   
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
    }
    
    private void closeConnection(){
        try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
    } 

    /**
     * Check if database dbName already exists.
     *
     * @return false if database already exist, otherwise return true
     */
    private boolean checkDBExists() {

        try {
            ResultSet resultSet = conn.getMetaData().getCatalogs();

            while (resultSet.next()) {

                String databaseName = resultSet.getString(1);
                if (databaseName.equals(dbName)) {
                    return true;
                }
            }
            resultSet.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    private void create_Database() {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            
            String sql = "CREATE DATABASE " + dbName;
            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }
        }
    }
}
