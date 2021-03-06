/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsimpresos;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author luis-pc
 */
public class VerNotas extends javax.swing.JFrame {

    private ArrayList<Nota> notas;
    private ArrayList<Nota> filtro;
    ConexionNotas conexion = new ConexionNotas();
    private int tipoFiltro = 0;

    /**
     * Creates new form VerNotas
     */
    public VerNotas() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        actualizarNotas(conexion.obtenerVistaNotas());
    }

    public void buscar() {
        String[] columnas = {"Folio", "Fecha", "Cliente", "Estado"};
        DefaultTableModel model1 = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        if (txtBuscar.getText().equals("") || txtBuscar.getText() == null) {

            for (int i = 0; i < notas.size(); i++) {
                Object[] obj = {notas.get(i).getIdNota(), notas.get(i).getFecha(), notas.get(i).getNombres() + " " + notas.get(i).getApPaterno() + " " + notas.get(i).getApMaterno(), notas.get(i).getEstado()};
                model1.addRow(obj);
            }
            tableNotas.setModel(model1);
        } else {
            if (tipoFiltro == 0) {
                filtro = new ArrayList();
                for (int i = 0; i < notas.size(); i++) {
                    String nombreCompleto = notas.get(i).getNombres() + " " + notas.get(i).getApPaterno() + " " + notas.get(i).getApMaterno();
                    if (nombreCompleto.length() >= txtBuscar.getText().length()) {
                        String nombreAux = nombreCompleto.substring(0, txtBuscar.getText().length());
                        if (nombreAux.compareToIgnoreCase(txtBuscar.getText()) == 0) {
                            filtro.add(notas.get(i));
                            Object[] obj = {notas.get(i).getIdNota(), notas.get(i).getFecha(), notas.get(i).getNombres() + " " + notas.get(i).getApPaterno() + " " + notas.get(i).getApMaterno(), notas.get(i).getEstado()};
                            model1.addRow(obj);
                        }
                    }

                }
            } else {
                filtro = new ArrayList();
                for (int i = 0; i < notas.size(); i++) {
                    String folio = notas.get(i).getIdNota()+"";
                    if (folio.length() >= txtBuscar.getText().length()) {
                        String folioAux = folio.substring(0, txtBuscar.getText().length());
                        if (folioAux.compareToIgnoreCase(txtBuscar.getText()) == 0) {
                            filtro.add(notas.get(i));
                            Object[] obj = {notas.get(i).getIdNota(), notas.get(i).getFecha(), notas.get(i).getNombres() + " " + notas.get(i).getApPaterno() + " " + notas.get(i).getApMaterno(), notas.get(i).getEstado()};
                            model1.addRow(obj);
                        }
                    }

                }

            }

            tableNotas.setModel(model1);
        }
        TableColumnModel modeloColumna = tableNotas.getColumnModel();
        modeloColumna.getColumn(0).setPreferredWidth(50);
        modeloColumna.getColumn(1).setPreferredWidth(100);
        modeloColumna.getColumn(2).setPreferredWidth(200);
        modeloColumna.getColumn(3).setPreferredWidth(50);
    }
    
    public void actualizarNotas(ArrayList<Nota> notas){
        this.notas = notas;
        Collections.reverse(this.notas);
        String[] columnas = {"Folio", "Fecha", "Nombre del cliente", "Estado"};

        DefaultTableModel model1 = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        
        for (int i = 0; i < notas.size(); i++) {
            Object[] obj = {notas.get(i).getIdNota(), notas.get(i).getFecha(), notas.get(i).getNombres() + " " + notas.get(i).getApPaterno() + " " + notas.get(i).getApMaterno(), notas.get(i).getEstado()};
            model1.addRow(obj);
        }
        tableNotas.setModel(model1);
        TableColumnModel modeloColumna = tableNotas.getColumnModel();
        modeloColumna.getColumn(0).setPreferredWidth(50);
        modeloColumna.getColumn(1).setPreferredWidth(100);
        modeloColumna.getColumn(2).setPreferredWidth(200);
        modeloColumna.getColumn(3).setPreferredWidth(50);
        txtBuscar.setText(null);
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
        tableNotas = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cmbFiltro = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Consultar Notas");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Notas"));

        tableNotas.setModel(new javax.swing.table.DefaultTableModel(
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
        tableNotas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableNotasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableNotas);

        jLabel1.setText("Buscar: ");

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        jLabel2.setText("Filtrar: ");

        cmbFiltro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Por nombre", "Por folio" }));
        cmbFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFiltroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 668, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void tableNotasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableNotasMouseClicked
        if (txtBuscar.getText().equals("") || txtBuscar.getText() == null) {
            System.out.println(notas.get(tableNotas.getSelectedRow()).getNombres() + " " + notas.get(tableNotas.getSelectedRow()).getIdNota());
            JFrame nuevo = new VerNota(notas.get(tableNotas.getSelectedRow()).getIdNota(), this);
            nuevo.setVisible(true);
        } else {
            System.out.println(filtro.get(tableNotas.getSelectedRow()).getNombres() + " " + filtro.get(tableNotas.getSelectedRow()).getIdNota());
            JFrame nuevo = new VerNota(filtro.get(tableNotas.getSelectedRow()).getIdNota(), this);
            nuevo.setVisible(true);
        }
    }//GEN-LAST:event_tableNotasMouseClicked

    private void cmbFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFiltroActionPerformed
        tipoFiltro = cmbFiltro.getSelectedIndex();
        txtBuscar.setText("");
        buscar();
    }//GEN-LAST:event_cmbFiltroActionPerformed

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
//            java.util.logging.Logger.getLogger(VerNotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(VerNotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(VerNotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(VerNotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
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
    private javax.swing.JComboBox cmbFiltro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableNotas;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
