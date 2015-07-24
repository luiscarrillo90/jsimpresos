/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsimpresos;

import com.mysql.jdbc.Connection;
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
public class ConexionNotas extends Conexion {

    public void guardarNota(String nombres, String apPaterno, String apMaterno, String telefono, String fechaEntrega, Double abono, String observaciones, int idMiembro, ArrayList<Articulo> articulos) {
        String query = "insert into notas values(default,?,?,?,?, default, ?, ?, ?, default,1,?);";
        PreparedStatement st;
        int clave =0;
        try {
            st = this.getConexion().prepareStatement(query);
            if (idMiembro == 1) {
                st.setString(1, nombres);
                st.setString(2, apPaterno);
                st.setString(3, apMaterno);
                st.setString(4, telefono);
                st.setString(5, fechaEntrega);
                st.setDouble(6, abono);
                st.setString(7, observaciones);
                st.setInt(8, idMiembro);
                st.executeUpdate();
                st = this.getConexion().prepareStatement("select last_insert_id()");
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    clave = rs.getInt("last_insert_id()");
                }
                
            }else{
                st.setString(1, "no");
                st.setString(2, "no");
                st.setString(3, "no");
                st.setString(4, telefono);
                st.setString(5, fechaEntrega);
                st.setDouble(6, abono);
                st.setString(7, observaciones);
                st.setInt(8, idMiembro);
                st.executeUpdate();
                st = this.getConexion().prepareStatement("select last_insert_id()");
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    clave = rs.getInt("last_insert_id()");
                }
            }
            for (int i = 0; i < articulos.size(); i++) {
                String queryAux = "insert into detalleservicios values(?,?,?,?)";
                PreparedStatement stAux;
                stAux = this.getConexion().prepareStatement(queryAux);
                stAux.setInt(1, clave);
                stAux.setString(2, articulos.get(i).getServicio());
                stAux.setDouble(3, articulos.get(i).getPrecio());
                stAux.setInt(4, articulos.get(i).getCantidad());
                stAux.executeUpdate();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar al servidor");
            System.out.println(ex.getMessage());
        }
    }

}
