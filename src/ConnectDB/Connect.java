/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Toan Ho
 */
public abstract class Connect {
    private final String url = "jdbc:mysql://localhost:3306/baolibrary";
    private final String user = "root";
    private final String password = "Haha123456789";
    private String table;
    private Connection connection;
    
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connect success!");
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
        return this.connection;
    }
}
