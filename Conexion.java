/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsimpresos;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author luis-pc
 */
public class Conexion {
    private String servidor = "jdbc:mysql://localhost/jsdb";
//    private String servidor = "jdbc:mysql://sql2.freesqldatabase.com:3306/sql287245";
//    private String usuario = "sql287245";
    private String usuario = "root";
//    private String pass = "qQ5!rG5!";
    private String pass = "root";
    private String driver = "com.mysql.jdbc.Driver";
    private Connection conexion;
    
    public Conexion(){
        try {
            Class.forName(driver);
            conexion = (Connection) DriverManager.getConnection(servidor, usuario, pass);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "No se encontró el driver");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos");
        }
    }

    public String cerrarConexion() {
        try {
            if (conexion != null) {
                conexion.close();
            }
            return "Conexión cerrada con éxito";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }
    public Connection getConexion() {
        return conexion;
    }
    
}
