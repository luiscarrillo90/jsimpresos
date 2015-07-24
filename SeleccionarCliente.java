/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsimpresos;

import com.mysql.jdbc.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author luis-pc
 */
public class SeleccionarCliente extends javax.swing.JFrame {

    /**
     * Creates new form SeleccionarCliente
     */
    Main frameAnterior;
    Miembro cliente = null;
    ArrayList<Miembro> todos;
    ArrayList<Miembro> filtro;
    private Connection conexion;

    public SeleccionarCliente(Main frame) {
        frameAnterior = frame;
        initComponents();
        limpiarEtiquetas();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        Conexion c = new Conexion();
        conexion = c.getConexion();
        String[] columnas = {"Nombre", "Domicilio"};
        todos = (new ConexionClientes()).obtenerClientes(conexion);
        Collections.sort(todos, new Comparator() {
            @Override
            public int compare(Object p1, Object p2) {

                return new Integer(((Miembro) (p1)).getNombres().compareTo(((Miembro) (p2)).getNombres()));
            }

        });
        DefaultTableModel model1 = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        for (int i = 0; i < todos.size(); i++) {
            Object[] obj = {todos.get(i).getNombres() + " " + todos.get(i).getApPaterno() + " " + todos.get(i).getApMaterno(), todos.get(i).getDomicilio()};
            model1.addRow(obj);
        }
        tableClientes.setModel(model1);

    }

    public void buscar() {
        String[] columnas = {"Nombre", "Domicilio"};
        DefaultTableModel model1 = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        if (txtBuscar.getText().equals("") || txtBuscar.getText() == null) {

            for (int i = 0; i < todos.size(); i++) {
                Object[] obj = {todos.get(i).getNombres() + " " + todos.get(i).getApPaterno() + " " + todos.get(i).getApMaterno(), todos.get(i).getDomicilio()};
                model1.addRow(obj);
            }
            tableClientes.setModel(model1);
        } else {
            filtro = new ArrayList();
            for (int i = 0; i < todos.size(); i++) {
                String nombreCompleto = todos.get(i).getNombres() + " " + todos.get(i).getApPaterno() + " " + todos.get(i).getApMaterno();
                if (nombreCompleto.length() >= txtBuscar.getText().length()) {
                    String nombreAux = nombreCompleto.substring(0, txtBuscar.getText().length());
                    if (nombreAux.compareToIgnoreCase(txtBuscar.getText()) == 0) {
                        filtro.add(todos.get(i));
                        Object[] obj = {todos.get(i).getNombres() + " " + todos.get(i).getApPaterno() + " " + todos.get(i).getApMaterno(), todos.get(i).getDomicilio()};
                        model1.addRow(obj);
                    }
                }

            }
            tableClientes.setModel(model1);

        }
    }

    public void limpiarEtiquetas() {
        lblNombre.setText("Ninguno");
        lblDomicilio.setText("");
        lblSaldo.setText("");
        lblTarjeta.setText("");
        lblRfc.setText("");
        lblTelefono.setText("");
        lblNombreUsuario.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableClientes = new javax.swing.JTable();
        btnSeleccionar = new javax.swing.JButton();
        lblNombre = new javax.swing.JLabel();
        lblDomicilio = new javax.swing.JLabel();
        lblTarjeta = new javax.swing.JLabel();
        lblRfc = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        lblSaldo = new javax.swing.JLabel();
        lblNombreUsuario = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Clientes");

        jLabel2.setText("Buscar :");

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        tableClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableClientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableClientes);

        btnSeleccionar.setText("Aceptar");
        btnSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarActionPerformed(evt);
            }
        });

        lblNombre.setText("Ninguno");

        lblDomicilio.setText("jLabel3");

        lblTarjeta.setText("jLabel3");

        lblRfc.setText("jLabel3");

        lblTelefono.setText("jLabel3");

        lblSaldo.setText("jLabel3");

        lblNombreUsuario.setText("jLabel3");

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(72, 72, 72)
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNombre)
                            .addComponent(lblDomicilio)
                            .addComponent(lblTarjeta)
                            .addComponent(lblRfc)
                            .addComponent(lblTelefono)
                            .addComponent(lblSaldo)
                            .addComponent(lblNombreUsuario))))
                .addContainerGap(45, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnSeleccionar, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTarjeta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDomicilio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRfc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTelefono)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSaldo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNombreUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnSeleccionar))
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarActionPerformed
        if (cliente != null) {
            frameAnterior.cambiarCliente(cliente);
            frameAnterior.btnQuitarCliente.setEnabled(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "No ha seleccionado ningún cliente");
        }

//        frameAnterior.dispose();
//        Main otro = new Main(usuario, cliente);
//        otro.setVisible(true);

    }//GEN-LAST:event_btnSeleccionarActionPerformed

    private void tableClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableClientesMouseClicked
        if (txtBuscar.getText().equals("") || txtBuscar.getText() == null) {
            cliente = todos.get(tableClientes.getSelectedRow());
            lblNombre.setText("Nombre: " + todos.get(tableClientes.getSelectedRow()).getNombres() + " " + todos.get(tableClientes.getSelectedRow()).getApPaterno() + " " + todos.get(tableClientes.getSelectedRow()).getApMaterno());
            lblDomicilio.setText("Domicilio: " + todos.get(tableClientes.getSelectedRow()).getDomicilio());
            lblSaldo.setText("Saldo: " + todos.get(tableClientes.getSelectedRow()).getSaldo() + " pesos");
            lblTarjeta.setText("Número de membresía: " + todos.get(tableClientes.getSelectedRow()).getNumeroTarjeta());
            lblRfc.setText("RFC: " + todos.get(tableClientes.getSelectedRow()).getNumeroTarjeta());
            lblTelefono.setText("Teléfono: " + todos.get(tableClientes.getSelectedRow()).getTelefono());
            lblNombreUsuario.setText("Nombre de usuario: " + todos.get(tableClientes.getSelectedRow()).getNombreUsuario());
        }else{
            lblNombre.setText("Nombre: " + filtro.get(tableClientes.getSelectedRow()).getNombres() + " " + filtro.get(tableClientes.getSelectedRow()).getApPaterno() + " " + filtro.get(tableClientes.getSelectedRow()).getApMaterno());
            lblDomicilio.setText("Domicilio: " + filtro.get(tableClientes.getSelectedRow()).getDomicilio());
            lblSaldo.setText("Saldo: " + filtro.get(tableClientes.getSelectedRow()).getSaldo() + " pesos");
            lblTarjeta.setText("Número de membresía: " + filtro.get(tableClientes.getSelectedRow()).getNumeroTarjeta());
            lblRfc.setText("RFC: " + filtro.get(tableClientes.getSelectedRow()).getNumeroTarjeta());
            lblTelefono.setText("Teléfono: " + filtro.get(tableClientes.getSelectedRow()).getTelefono());
            lblNombreUsuario.setText("Nombre de usuario: " + filtro.get(tableClientes.getSelectedRow()).getNombreUsuario());
            cliente = filtro.get(tableClientes.getSelectedRow());
        }


    }//GEN-LAST:event_tableClientesMouseClicked

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        buscar();

    }//GEN-LAST:event_txtBuscarKeyReleased

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(SeleccionarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(SeleccionarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(SeleccionarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(SeleccionarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSeleccionar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDomicilio;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNombreUsuario;
    private javax.swing.JLabel lblRfc;
    private javax.swing.JLabel lblSaldo;
    private javax.swing.JLabel lblTarjeta;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JTable tableClientes;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
