/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsimpresos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author luis-pc
 */
public class VerMiembros extends javax.swing.JFrame {

    ArrayList<Miembro> miembros;
    ArrayList<Miembro> filtro;
    ConexionMiembro conexion = new ConexionMiembro();

    /**
     * Creates new form VerMiembros
     */
    public VerMiembros(String accion) {
        initComponents();
        this.setLocationRelativeTo(null);
        actualizarMiembros(conexion.obtenerMiembros());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        if (accion == "editar") {
            btnAccion.setText("Editar Miembro");
        }else if (accion == "eliminar") {
            btnAccion.setText("Eliminar Miembro");
        }else if (accion == "cargar"){
            btnAccion.setText("Cargar Saldo");
        }
    }

    public void actualizarMiembros(ArrayList<Miembro> miembros) {
        this.miembros = miembros;
        Collections.sort(this.miembros, new Comparator() {
            @Override
            public int compare(Object p1, Object p2) {

                return new Integer(((Miembro) (p1)).getNombres().compareTo(((Miembro) (p2)).getNombres()));
            }

        });
        String[] columnas = {"Nombre", "Número de tarjeta"};
        TableColumnModel modeloColumna = tableMiembros.getColumnModel();
        DefaultTableModel model1 = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        for (int i = 0; i < miembros.size(); i++) {
            Object[] obj = {miembros.get(i).getNombres() + " " + miembros.get(i).getApPaterno() + " " + miembros.get(i).getApMaterno(), miembros.get(i).getNumeroTarjeta()};
            model1.addRow(obj);
        }
        tableMiembros.setModel(model1);
        modeloColumna.getColumn(0).setPreferredWidth(200);
        modeloColumna.getColumn(1).setPreferredWidth(75);

    }

    public void buscar() {
        String[] columnas = {"Nombre", "Número de tarjeta"};
        DefaultTableModel model1 = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        if (txtBuscar.getText().equals("") || txtBuscar.getText() == null) {

            for (int i = 0; i < miembros.size(); i++) {
                Object[] obj = {miembros.get(i).getNombres() + " " + miembros.get(i).getApPaterno() + " " + miembros.get(i).getApMaterno(), miembros.get(i).getNumeroTarjeta()};
                model1.addRow(obj);
            }
            tableMiembros.setModel(model1);
        } else {
            filtro = new ArrayList();
            for (int i = 0; i < miembros.size(); i++) {
                String nombreCompleto = miembros.get(i).getNombres() + " " + miembros.get(i).getApPaterno() + " " + miembros.get(i).getApMaterno();
                if (nombreCompleto.length() >= txtBuscar.getText().length()) {
                    String nombreAux = nombreCompleto.substring(0, txtBuscar.getText().length());
                    if (nombreAux.compareToIgnoreCase(txtBuscar.getText()) == 0) {
                        filtro.add(miembros.get(i));
                        Object[] obj = {miembros.get(i).getNombres() + " " + miembros.get(i).getApPaterno() + " " + miembros.get(i).getApMaterno(), miembros.get(i).getNumeroTarjeta()};
                        model1.addRow(obj);
                    }
                }

            }
            tableMiembros.setModel(model1);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableMiembros = new javax.swing.JTable();
        btnAccion = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Administración de Miembros");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Administración de miembros"));
        jPanel1.setToolTipText("");

        tableMiembros.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tableMiembros);

        btnAccion.setText("Aceptar");
        btnAccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccionActionPerformed(evt);
            }
        });

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        jLabel1.setText("Buscar Miembro: ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAccion, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                        .addComponent(btnAccion, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        buscar();
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void btnAccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccionActionPerformed
        if (btnAccion.getText().equals("Editar Miembro")) {
            
        if(tableMiembros.getSelectedRow()>-1) {
            if(txtBuscar.getText().equals("")||txtBuscar.getText()==null){
                AgregarCliente nuevo = new AgregarCliente(this, miembros.get(tableMiembros.getSelectedRow()));
                nuevo.setVisible(true);
            }else{
                AgregarCliente nuevo = new AgregarCliente(this, filtro.get(tableMiembros.getSelectedRow()));
                nuevo.setVisible(true);
            }
            }else{
                JOptionPane.showMessageDialog(null, "No has elegido ningún miembro de la tabla");
            }
        }else if (btnAccion.getText().equals("Eliminar Miembro")) {
            if(tableMiembros.getSelectedRow()>-1) {
            Miembro aux = null;
            if(txtBuscar.getText().equals("")||txtBuscar.getText()==null){
                aux = miembros.get(tableMiembros.getSelectedRow());
            }else{
                aux = filtro.get(tableMiembros.getSelectedRow());
            }
            int a = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea borrar a "+aux.getNombres()+" "+aux.getApPaterno()+"?");
            if(a==0){
                if(conexion.borrarMiembro(aux)){
                    JOptionPane.showMessageDialog(null, "Miembro borrado exitosamente");
                }else{
                    JOptionPane.showMessageDialog(null, "No se puede borrar el cliente por que tiene notas a su nombre.");
                }
                actualizarMiembros(conexion.obtenerMiembros());
            }
        }else{
            JOptionPane.showMessageDialog(null, "No has elegido ningún miembro de la tabla");
        }
        }else if (btnAccion.getText().equals("Cargar Saldo")) {
            Miembro aux = null;
            if(txtBuscar.getText().equals("")||txtBuscar.getText()==null){
                aux = miembros.get(tableMiembros.getSelectedRow());
            }else{
                aux = filtro.get(tableMiembros.getSelectedRow());
            }
            Double saldo = Double.parseDouble(JOptionPane.showInputDialog("¿Cuánto saldo desea cargar a la tarjeta del cliente "+aux.getNombres()+" "+aux.getApPaterno()+" ?"));
            if ( saldo > 0 ) {
                if(conexion.cargarSaldo(saldo, aux.getId())){
                    JOptionPane.showMessageDialog(null, "El saldo ha sido agregado exitosamente");
               }else{
                    JOptionPane.showMessageDialog(null, "Ocurrió un error al agregar el saldo a la tajeta del cliente.");
               }
            }else{
                JOptionPane.showMessageDialog(null, "Por favor introduzca una cantidad valida");
            } 
        }
    }//GEN-LAST:event_btnAccionActionPerformed

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
//            java.util.logging.Logger.getLogger(VerMiembros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(VerMiembros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(VerMiembros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(VerMiembros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new VerMiembros().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableMiembros;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
