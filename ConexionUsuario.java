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
    private String query = "select * from usuarios where nombreUsuario = ? and password = SHA(?)";
    
    public Usuario getUsuario(String nombreUsuario, String password){
        PreparedStatement st ;
        Usuario user = null;
        try {
            st = this.getConexion().prepareStatement(query);
            st.setString(1, nombreUsuario);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                user = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos");
        }
        return user;
    }
    
    public boolean crearUsuario(Usuario user){
        String query = "insert into usuarios values (default, ?, ?, ?, ?, SHA(?), ?)";
        PreparedStatement st ;
        try {
            st = this.getConexion().prepareStatement(query);
            st.setString(1, user.getNombres());
            st.setString(2, user.getApPaterno());
            st.setString(3, user.getApMaterno());
            st.setString(4, user.getNombreUsuario());
            st.setString(5, user.getPassword());
            st.setString(6, user.getTipoUsuario());
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar nuevo usuario");
            return false;
        }
    }
    
    public boolean modificarUsuario(Usuario user){
            String query = "update usuarios set nombres=?, appaterno=?, apmaterno=?, nombreUsuario=?, password=SHA(?), tipoUsuario=? where idUsuario=?";
        try {
            PreparedStatement st = this.getConexion().prepareStatement(query);
            st.setString(1, user.getNombres());
            st.setString(2, user.getApPaterno());
            st.setString(3, user.getApMaterno());
            st.setString(4, user.getNombreUsuario());
            st.setString(5, user.getPassword());
            st.setString(6, user.getTipoUsuario());
            st.setInt(7, user.getId());
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar al usuario.");
            return false;
        }
    }
        
        public boolean modificarUsuarioSinPassword(Usuario user){
            String query = "update usuarios set nombres=?, appaterno=?, apmaterno=?, nombreUsuario=?, tipoUsuario=? where idUsuario=?";
            try {
                PreparedStatement st = this.getConexion().prepareStatement(query);
                st.setString(1, user.getNombres());
                st.setString(2, user.getApPaterno());
                st.setString(3, user.getApMaterno());
                st.setString(4, user.getNombreUsuario());
                st.setString(5, user.getTipoUsuario());
                st.setInt(6, user.getId());
                st.executeUpdate();
                return true;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar al usuario.");
                return false;
            }  
        }
    
        public boolean hacerAdmin(int id){
            String query = "update usuarios set tipoUsuario='administrador' where idUsuario=?";
            try {
                PreparedStatement st = this.getConexion().prepareStatement(query);
                st.setInt(1, id);
                st.executeUpdate();
                return true;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar al usuario.");
                return false;
            }  
        }
        
    public boolean borrarUsuario(Usuario user){
        String query = "delete from usuarios where idusuario=?";
        try {
            PreparedStatement st = this.getConexion().prepareStatement(query);
            st.setInt(1, user.getId());
            st.execute();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public ArrayList<Usuario> obtenerUsuariosEmpleados(){
        ArrayList usuarios = new ArrayList();
        String query = "select * from usuarios where tipoUsuario='empleado'";
        try {
            PreparedStatement st = this.getConexion().prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                usuarios.add(new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener lista de usuarios empleados");
        }
        return usuarios;
    }
    
    public Usuario obtenerUsuario(){
        Usuario user=null;
        String query = "select * from usuarios where idusuario=?";
        try {
            PreparedStatement st = this.getConexion().prepareStatement(query);
            st.setInt(1, user.getId());
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                user = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener datos del usuario actual");
        }
        return user;
    }
    
}
