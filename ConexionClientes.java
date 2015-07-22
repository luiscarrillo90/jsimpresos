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
import javax.swing.JOptionPane;

/**
 *
 * @author luis-pc
 */
public class ConexionClientes {

    private final String query = "select * from miembros where idMiembro<>1";

    public ArrayList obtenerClientes(Connection con) {
        ArrayList<Miembro> clientes = new ArrayList();
        PreparedStatement st;
        Usuario user = null;
        try {
            st = con.prepareStatement(query);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                clientes.add(new Miembro(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getDouble(9), rs.getString(10), rs.getString(11), rs.getString(12)));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos");
        }

        return clientes;
    }
}
