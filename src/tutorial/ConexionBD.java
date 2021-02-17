/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author luisreylarag
 */
public class ConexionBD {
     Connection conectar = null;
     
     
     public Connection conexion() throws ClassNotFoundException, SQLException{
     Class.forName("com.mysql.jdbc.Driver");
      conectar= (Connection) DriverManager.getConnection("jdbc:mysql://aranzazu.tk:3306/ventas","admin_admin","123456789");
             
      return conectar;
     }
     
     

}
