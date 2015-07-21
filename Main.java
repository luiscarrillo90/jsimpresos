/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsimpresos;

import java.text.DateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author luis-pc
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    
    String usuario = null;
    //Cliente al que asignaremos la nota, puede ser null, en caso de que éste no sea un
    //miembro del sistema(con tarjeta) y el usuario escribirá los datos del mismo
    Miembro cliente = null;
    ArrayList<Articulo> listaArticulos = new ArrayList();
    //para dar formato a la fecha que nos de el "JCalendar"
    DateFormat df = DateFormat.getDateInstance();
    //número de artículos en la nota
    int numeroArticulos = 0;
    //bandera del escuchador de la tabla de artículos de la nota
    boolean bandera = true;

    /*
    Recibimos en el constructor un nombre, solo se usa para saber el nombre de quién inició sesión
    */
    public Main(String nombre) {
        usuario = nombre;
        this.setResizable(false);
        initComponents();
        //asigna nombre a etiqueta
        lblNombreUsuario.setText(nombre);
        this.setLocationRelativeTo(null);     
        /*
        Agregué un eschador a la tabla de los artículos que vamos a poner en la nota. tales como,
        impresiones, engargolados, etc. Lo hice para que ahí escriban el artículo, el precio y la
        cantidad, esta madre llenará el campo de "total" en diccha tabla.
        */
        tableArticulos.getModel().addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                /*
                Cuando una de las celdas de la tabla cambie, osea se escriba sobre ella,
                invocaremos este método, que va a hacer todo el jale
                */
                actualizarTotal(e, bandera);
            }

        });
        if (cliente != null) {
            txtNombres.setText(cliente.getNombres());
            txtNombres.setEnabled(false);
            txtApPaterno.setText(cliente.getApPaterno());
            txtApPaterno.setEnabled(false);
            txtApMaterno.setText(cliente.getApMaterno());
            txtApMaterno.setEnabled(false);
            txtTelefono.setText(cliente.getTelefono());
            txtTelefono.setEnabled(false);
            txtDomicilio.setText(cliente.getDomicilio());
            txtDomicilio.setEnabled(false);

        }
    }
    /*
        Para cambiar el usuario de la nota
    */
    public void cambiarCliente(Miembro cliente) {
        if (cliente != null) {
            txtNombres.setText(cliente.getNombres());
            txtNombres.setEnabled(false);
            txtApPaterno.setText(cliente.getApPaterno());
            txtApPaterno.setEnabled(false);
            txtApMaterno.setText(cliente.getApMaterno());
            txtApMaterno.setEnabled(false);
            txtTelefono.setText(cliente.getTelefono());
            txtTelefono.setEnabled(false);
            txtDomicilio.setText(cliente.getDomicilio());
            txtDomicilio.setEnabled(false);

        }
    }
    /*
        Recibe de parámetro un evento de un modelo de una tabla y una "bandera". 
        El bandera indica si se escribió de manera manual o lo hizo el sistema para
        el acomodo de la tabla a la hora de borrar un artículo.
    */
    public void actualizarTotal(TableModelEvent e, boolean bandera) {
        if (bandera) {

            if (e.getType() == TableModelEvent.UPDATE) {
                TableModel modelo = ((TableModel) (e.getSource()));
                int fila = e.getFirstRow();
                int columna = e.getColumn();
                if (columna == 0 || columna == 3) {
                    return;
                }
                try {
                    double valorPrimeraColumna = ((Number) modelo.getValueAt(fila,
                            1)).doubleValue();
                    double valorSegundaColumna = ((Number) modelo.getValueAt(fila,
                            2)).doubleValue();
                    modelo.setValueAt(valorPrimeraColumna * valorSegundaColumna,
                            fila, 3);
                    double total = 0;
                    numeroArticulos = 0;
                    for (int i = 0; i < 6; i++) {
                        if (modelo.getValueAt(i, 3) != null) {
                            total += ((Number) modelo.getValueAt(i, 3)).doubleValue();
                            numeroArticulos++;
                        } else {
                            break;
                        }
                    }
                    lblTotal.setText("Total: $" + total);

                } catch (NullPointerException ex) {

                }
            }
            
            cambiarTotal();
        }
    }
    /*
        Cambia el total de la nota al momento de agregar artículos o de borrar
        lee los totales de cada artículo y los escribe en la etiqueta
        lblTotal
    */
    public void cambiarTotal() {
        TableModel modelo = (TableModel) tableArticulos.getModel();
        double total = 0;
        for (int i = 0; i < numeroArticulos; i++) {
            total += ((Number) modelo.getValueAt(i, 3)).doubleValue();
        }
        lblTotal.setText("Total: $" + total);
    }
    /*
        Borra artículos (renglones) de la tabla de artículos, recibe el índice
        del renglón a borrar. 
    */
    public void quitarArticulo(int cual) {
        
        if (cual + 1 <= numeroArticulos) {
            
            TableModel modelo = (TableModel) tableArticulos.getModel();
            for (int i = cual; i <= numeroArticulos - 2; i++) {

                modelo.setValueAt(modelo.getValueAt(i + 1, 0), i, 0);
                modelo.setValueAt(modelo.getValueAt(i + 1, 1), i, 1);
                modelo.setValueAt(modelo.getValueAt(i + 1, 2), i, 2);
                modelo.setValueAt(modelo.getValueAt(i + 1, 3), i, 3);

            }
            modelo.setValueAt(null, numeroArticulos - 1, 0);
            modelo.setValueAt(null, numeroArticulos - 1, 1);
            modelo.setValueAt(null, numeroArticulos - 1, 2);
            modelo.setValueAt(null, numeroArticulos - 1, 3);

        }
        numeroArticulos--;
        cambiarTotal();
        bandera= true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNombreUsuario = new javax.swing.JLabel();
        btnCliente = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNombres = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtApPaterno = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtApMaterno = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDomicilio = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableArticulos = new javax.swing.JTable();
        btnQuitarArticulo = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        fechaEntrega = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblNombreUsuario.setText("jLabel1");

        btnCliente.setText("Seleccionar cliente");
        btnCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteActionPerformed(evt);
            }
        });

        jLabel1.setText("Datos del cliente :");

        jLabel2.setText("Nombres: ");

        jLabel3.setText("Apellido (Paterno): ");

        jLabel4.setText("Apellido (Materno):");

        jLabel5.setText("Domicilio:");

        jLabel6.setText("Teléfono/celular:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Artículos");

        tableArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Artículo", "Precio Unitario", "Cantidad", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableArticulos.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
                tableArticulosAncestorMoved(evt);
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
            }
        });
        jScrollPane1.setViewportView(tableArticulos);

        btnQuitarArticulo.setText("Quitar artículo");
        btnQuitarArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarArticuloActionPerformed(evt);
            }
        });

        lblTotal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTotal.setText("Total : $0");

        lblFecha.setText("jLabel8");

        fechaEntrega.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                fechaEntregaMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotal)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnQuitarArticulo)))
                .addGap(0, 362, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblNombreUsuario)
                        .addComponent(btnCliente)
                        .addComponent(jLabel1)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(36, 36, 36)
                                    .addComponent(jLabel2))
                                .addComponent(jLabel3))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtNombres)
                                .addComponent(txtApPaterno, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel5)
                                .addComponent(jLabel4)
                                .addComponent(jLabel6))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtApMaterno, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                                .addComponent(txtDomicilio)
                                .addComponent(txtTelefono)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(15, 15, 15)
                                    .addComponent(jLabel7)
                                    .addGap(0, 0, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fechaEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(lblFecha)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNombreUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtApPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnQuitarArticulo)
                        .addGap(111, 111, 111))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(fechaEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblFecha))
                .addContainerGap(175, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
        /*
        Se invoca al presionar el botón "agregar cliente" a la nota y abre un nuevo formulario
        que contiene una tabla con los cliente de la bd y un pequeño "buscar"
    */
    private void btnClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteActionPerformed
        SeleccionarCliente nuevo = new SeleccionarCliente(this);
        nuevo.setVisible(true);
    }//GEN-LAST:event_btnClienteActionPerformed
        /*
        Se invoca al presionar el botón de quitar artículo, cambia la bandera a falso
        la cual se utiliza en el evento de la tabla de artículos.
    */
    private void btnQuitarArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarArticuloActionPerformed
        if (tableArticulos.getSelectedRow() != -1) {

            int borrar = JOptionPane.showConfirmDialog(null, "¿Estás seguro que deseas quitar el artículo de la lista?");
            if (borrar == 0) {
                bandera = false;
                quitarArticulo(tableArticulos.getSelectedRow());
            }
        }
    }//GEN-LAST:event_btnQuitarArticuloActionPerformed

    private void tableArticulosAncestorMoved(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_tableArticulosAncestorMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_tableArticulosAncestorMoved

    private void fechaEntregaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fechaEntregaMouseReleased

    }//GEN-LAST:event_fechaEntregaMouseReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCliente;
    private javax.swing.JButton btnQuitarArticulo;
    private com.toedter.calendar.JDateChooser fechaEntrega;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblNombreUsuario;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tableArticulos;
    private javax.swing.JTextField txtApMaterno;
    private javax.swing.JTextField txtApPaterno;
    private javax.swing.JTextField txtDomicilio;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}