/*
* DBContext.java
*
* All Right Reserved
* Copyright (c) 2020 FPT University
*/
package Context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DBContext.<br>
 * 
 *<pre>
 * Class mở kết nối đến sqlServer
 * . constructor.
 </pre>
 * 
 * @author dangt
 */
public class DBContext {
    protected Connection connection=null;
    /**
     * Constructor.<br>
     * Mở kết nối tới SQLServer
     * Gặp lỗi thực hiện exception.
     */
    public DBContext()
    {
        try {
            // Edit URL , username, password to authenticate with your MS SQL Server
            String url = "jdbc:sqlserver://localhost:1433;databaseName=P0005;";
            String username = "sa";
            String password = "123";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
