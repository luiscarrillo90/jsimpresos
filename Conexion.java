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
    private String usuario = "root";
    private String pass = "root";
    private String driver = "com.mysql.jdbc.Driver";
    public Connection getConexion() {
        Connection conexion = null;
        try {
            Class.forName(driver);
            conexion = (Connection) DriverManager.getConnection(servidor, usuario, pass);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "No se encontró el driver");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos");
        }
        return conexion;
    }
}
