/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsimpresos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author luis-pc
 */
public class ConexionUsuario extends Conexion{
    private String query = "select * from usuarios where nombreUsuario = ? and password = ?";
    public Usuario getUsuario(String nombreUsuario, String password){
        PreparedStatement st ;
        Usuario user = null;
        try {
            st = this.getConexion().prepareStatement(query);
            st.setString(1, nombreUsuario);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                user = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos");
        }
        return user;
    }
    
}
