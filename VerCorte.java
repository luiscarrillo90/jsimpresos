/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsimpresos;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author luis-pc
 */
public class VerCorte extends javax.swing.JFrame {

    /**
     * Creates new form VerCorte
     */
    CorteCaja corte;
    ConexionCorteCaja conexion = new ConexionCorteCaja();

    public VerCorte(int idCorteCaja) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        corte = conexion.obtenerCortePorId(idCorteCaja);
        lblTitulo.setText("Corte de caja #" + corte.getIdCorte());
        lblFecha.setText("Fecha: " + corte.getFecha());
        ArrayList<Abono> abonosEfectivo = new ArrayList();
        ArrayList<Abono> abonosTarjeta = new ArrayList();
        ArrayList<TransaccionesMiembro> recargasSaldo = new ArrayList();
        ArrayList<TransaccionesMiembro> transaccionesPaypal = new ArrayList();
        ArrayList<TransaccionesMiembro> registrosCliente = new ArrayList();
        for (int i = 0; i < corte.getAbonos().size(); i++) {
            String tipo = corte.getAbonos().get(i).getTipoPago();
            switch (tipo) {
                case "Efectivo":
                    abonosEfectivo.add(corte.getAbonos().get(i));
                    break;
                case "Tarjeta":
                    abonosTarjeta.add(corte.getAbonos().get(i));
                    break;
            }

        }
        for (int i = 0; i < corte.getTransaccionesMiembro().size(); i++) {
            String tipo = corte.getTransaccionesMiembro().get(i).getMovimiento();
            switch (tipo) {
                case "Recarga":
                    recargasSaldo.add(corte.getTransaccionesMiembro().get(i));
                    break;
                case "Paypal":
                    transaccionesPaypal.add(corte.getTransaccionesMiembro().get(i));
                    break;
                case "Registro":
                    registrosCliente.add(corte.getTransaccionesMiembro().get(i));
                    break;
            }

        }
        String[] columnas = {"Concepto", "Monto"};
        TableColumnModel modeloColumna = tableAbonosEfectivo.getColumnModel();

        DefaultTableModel model1 = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        double totalAbonosEfectivo =0;
        for (int i = 0; i < abonosEfectivo.size(); i++) {
            Object[] obj = {"Abono a la nota #"+abonosEfectivo.get(i).getId(), "$"+abonosEfectivo.get(i).getMonto()};
            model1.addRow(obj);
            totalAbonosEfectivo+= abonosEfectivo.get(i).getMonto();
        }
        lblTotalAbonosEfectivo.setText("Total: $"+totalAbonosEfectivo);

        tableAbonosEfectivo.setModel(model1);
        modeloColumna.getColumn(0).setPreferredWidth(100);
        modeloColumna.getColumn(1).setPreferredWidth(30);
        TableColumnModel modeloColumna2 = tableRecargas.getColumnModel();

        DefaultTableModel model2 = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        double totalRecargas =0;
        for (int i = 0; i < recargasSaldo.size(); i++) {
            Object[] obj = {"Recarga del cliente #"+recargasSaldo.get(i).getIdMiembro(), "$"+recargasSaldo.get(i).getMonto()};
            model2.addRow(obj);
            totalRecargas+= recargasSaldo.get(i).getMonto();
        }
        lblTotalRecargas.setText("Total: $"+totalRecargas);

        tableRecargas.setModel(model2);
        modeloColumna2.getColumn(0).setPreferredWidth(100);
        modeloColumna2.getColumn(1).setPreferredWidth(30);
        TableColumnModel modeloColumna3 = tableRegistroClientes.getColumnModel();

        DefaultTableModel model3 = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        double totalRegistrosCliente =0;
        for (int i = 0; i < registrosCliente.size(); i++) {
            Object[] obj = {"Registro del cliente #"+registrosCliente.get(i).getIdMiembro(), "$"+registrosCliente.get(i).getMonto()};
            model3.addRow(obj);
            totalRegistrosCliente+= registrosCliente.get(i).getMonto();
        }
        lblTotalRegistros.setText("Total: $"+totalRegistrosCliente);

        tableRegistroClientes.setModel(model3);
        modeloColumna3.getColumn(0).setPreferredWidth(100);
        modeloColumna3.getColumn(1).setPreferredWidth(30);
        lblTotalEfectivo.setText("Total efectivo: $"+(totalAbonosEfectivo+totalRegistrosCliente+totalRecargas));
        TableColumnModel modeloColumna4 = tableAbonosTarjeta.getColumnModel();

        DefaultTableModel model4 = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        double totalAbonosTarjeta =0;
        for (int i = 0; i < abonosTarjeta.size(); i++) {
            Object[] obj = {"Abono con tarjeta a la nota #"+abonosTarjeta.get(i).getId(), "$"+abonosTarjeta.get(i).getMonto()};
            model4.addRow(obj);
            totalAbonosTarjeta+= abonosTarjeta.get(i).getMonto();
        }
        lblTotalAbonosTarjeta.setText("Total: $"+totalAbonosTarjeta);

        tableAbonosTarjeta.setModel(model4);
        modeloColumna4.getColumn(0).setPreferredWidth(110);
        modeloColumna4.getColumn(1).setPreferredWidth(25);
        TableColumnModel modeloColumna5 = tablePaypal.getColumnModel();

        DefaultTableModel model5 = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        double totalPaypal =0;
        for (int i = 0; i < transaccionesPaypal.size(); i++) {
            Object[] obj = {"Transacción Paypal del cliente #"+transaccionesPaypal.get(i).getIdMiembro(), "$"+transaccionesPaypal.get(i).getMonto()};
            model5.addRow(obj);
            totalPaypal+= transaccionesPaypal.get(i).getMonto();
        }
        lblTotalPaypal.setText("Total: $"+totalPaypal);

        tablePaypal.setModel(model5);
        modeloColumna5.getColumn(0).setPreferredWidth(115);
        modeloColumna5.getColumn(1).setPreferredWidth(20);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableAbonosEfectivo = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableRecargas = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableRegistroClientes = new javax.swing.JTable();
        lblTotalEfectivo = new javax.swing.JLabel();
        lblTotalAbonosEfectivo = new javax.swing.JLabel();
        lblTotalRecargas = new javax.swing.JLabel();
        lblTotalRegistros = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableAbonosTarjeta = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablePaypal = new javax.swing.JTable();
        lblTotalAbonosTarjeta = new javax.swing.JLabel();
        lblTotalPaypal = new javax.swing.JLabel();
        btnImprimir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitulo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTitulo.setText("jLabel1");

        lblFecha.setText("jLabel1");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Transacciones en efectivo");

        jLabel2.setText("Abonos a notas");

        tableAbonosEfectivo.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tableAbonosEfectivo);

        jLabel3.setText("Recargas de saldo");

        tableRecargas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tableRecargas);

        jLabel4.setText("Registro de clientes");

        tableRegistroClientes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tableRegistroClientes);

        lblTotalEfectivo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblTotalEfectivo.setText("Total efectivo: ");

        lblTotalAbonosEfectivo.setText("Total: ");

        lblTotalRecargas.setText("jLabel8");

        lblTotalRegistros.setText("jLabel8");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalEfectivo)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTotalAbonosEfectivo)
                            .addComponent(lblTotalRecargas)
                            .addComponent(lblTotalRegistros))))
                .addContainerGap(105, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalAbonosEfectivo))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalRecargas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalRegistros))
                .addGap(18, 18, 18)
                .addComponent(lblTotalEfectivo)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Otras transacciones");

        jLabel6.setText("Abonos a notas con membresía");

        tableAbonosTarjeta.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tableAbonosTarjeta);

        jLabel7.setText("Transacciones con Paypal");

        tablePaypal.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tablePaypal);

        lblTotalAbonosTarjeta.setText("Total: ");

        lblTotalPaypal.setText("jLabel8");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalAbonosTarjeta)
                    .addComponent(lblTotalPaypal))
                .addContainerGap(104, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalAbonosTarjeta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalPaypal))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnImprimir.setText("Imprimir corte de caja");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                            .addComponent(lblFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(btnImprimir)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImprimir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFecha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        conexion.crearPdf(this.corte.getIdCorte());
    }//GEN-LAST:event_btnImprimirActionPerformed

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
//            java.util.logging.Logger.getLogger(VerCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(VerCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(VerCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(VerCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JButton btnImprimir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotalAbonosEfectivo;
    private javax.swing.JLabel lblTotalAbonosTarjeta;
    private javax.swing.JLabel lblTotalEfectivo;
    private javax.swing.JLabel lblTotalPaypal;
    private javax.swing.JLabel lblTotalRecargas;
    private javax.swing.JLabel lblTotalRegistros;
    private javax.swing.JTable tableAbonosEfectivo;
    private javax.swing.JTable tableAbonosTarjeta;
    private javax.swing.JTable tablePaypal;
    private javax.swing.JTable tableRecargas;
    private javax.swing.JTable tableRegistroClientes;
    // End of variables declaration//GEN-END:variables
}
