/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsimpresos;

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
public class ConexionMiembro extends Conexion{
    public ArrayList<Miembro> obtenerMiembros(){
        ArrayList miembros = new ArrayList();
        String query = "select * from miembros where idmiembro<>1 and idmiembro<>2";
        try {
            PreparedStatement st = this.getConexion().prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                miembros.add(new Miembro(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getDouble(8), Fechas.convertirFecha(rs.getString(9)),rs.getString(10), rs.getString(11)));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos");
        }
        return miembros;
    }
    public void guardarMiembro(Miembro miembro){
        String query = "insert into miembros values(default,?,?,?,?,?,?,200, default, ?,?)";
        try {
            PreparedStatement st = this.getConexion().prepareStatement(query);
            st.setString(1, miembro.getNumeroTarjeta());
            st.setString(2, miembro.getNombres());
            st.setString(3, miembro.getApPaterno());
            st.setString(4, miembro.getApMaterno());
            st.setString(5, miembro.getDomicilio());
            st.setString(6, miembro.getTelefono());
            st.setString(7, miembro.getNombreUsuario());
            st.setString(8, miembro.getPassword());
            st.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar al cliente");
        }
    }
    public void actualizarMiembro(Miembro miembro, int id){
        String query = "update miembros set nombres=?, appaterno=?, apmaterno=?, domicilio=?, telefono=?, nombreusuario=?, password=? where idMiembro=?";
        try {
            PreparedStatement st = this.getConexion().prepareStatement(query);
            st.setString(1, miembro.getNombres());
            st.setString(2, miembro.getApPaterno());
            st.setString(3, miembro.getApMaterno());
            st.setString(4, miembro.getDomicilio());
            st.setString(5, miembro.getTelefono());
            st.setString(6, miembro.getNombreUsuario());
            st.setString(7, miembro.getPassword());
            st.setInt(8, id);
            st.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la base de datos.");
        }
    }
    public boolean borrarMiembro(Miembro miembro){
        String query = "delete from miembros where idmiembro=?";
        try {
            PreparedStatement st = this.getConexion().prepareStatement(query);
            st.setInt(1, miembro.getId());
            st.execute();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        
    }
    
    public Miembro obtenerMiembroGenerico(){
        Miembro miembro = null;
        String query = "select * from miembros where idmiembro=2";
        try{
            PreparedStatement st = this.getConexion().prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                miembro = new Miembro(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getDouble(8), Fechas.convertirFecha(rs.getString(9)),rs.getString(10), rs.getString(11));
            }
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al obtener el cliente generico");
        }
        return miembro;
    }
}
