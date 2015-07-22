/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsimpresos;

import com.mysql.jdbc.Connection;
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
    ConexionNotas conexionNota;
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

    }
    /*
     Para cambiar el usuario de la nota
     */

    public void cambiarCliente(Miembro cliente) {
        if (cliente != null) {
            this.cliente = cliente;
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

    public void quitarCliente() {
        this.cliente = null;
        txtNombres.setText("");
        txtNombres.setEnabled(true);
        txtApPaterno.setText("");
        txtApPaterno.setEnabled(true);
        txtApMaterno.setText("");
        txtApMaterno.setEnabled(true);
        txtTelefono.setText("");
        txtTelefono.setEnabled(true);
        txtDomicilio.setText("");
        txtDomicilio.setEnabled(true);
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
        listaArticulos = new <Articulo>ArrayList();
        for (int i = 0; i < numeroArticulos; i++) {
            total += ((Number) modelo.getValueAt(i, 3)).doubleValue();
            listaArticulos.add(new Articulo(1, modelo.getValueAt(i, 0).toString(), ((Number) modelo.getValueAt(i, 1)).doubleValue(), ((Number) modelo.getValueAt(i, 2)).intValue()));

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
        bandera = true;
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
        fechaEntrega = new com.toedter.calendar.JDateChooser();
        btnQuitarCliente = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtAbono = new javax.swing.JTextField();
        btnRegistrar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaObservaciones = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        sMenuRegNota = new javax.swing.JMenuItem();
        sMenuCancelNota = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        sMenuLogOut = new javax.swing.JMenuItem();
        menuOpciones = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenu4 = new javax.swing.JMenu();
        sMenuSeleccCliente = new javax.swing.JMenuItem();
        sMenuQuitCliente = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        menuNotas = new javax.swing.JMenu();
        sMenuConsulNota = new javax.swing.JMenuItem();
        sMenuModNota = new javax.swing.JMenuItem();
        menuCliente = new javax.swing.JMenu();
        sMenuCargSaldo = new javax.swing.JMenuItem();
        sMenuAddCliente = new javax.swing.JMenuItem();
        sMenuModCliente = new javax.swing.JMenuItem();
        sMenuElimCliente = new javax.swing.JMenuItem();
        menuAyuda = new javax.swing.JMenu();
        sMenuAcerca = new javax.swing.JMenuItem();

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

        txtApMaterno.setMaximumSize(new java.awt.Dimension(200, 2147483647));

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
        btnQuitarArticulo.setEnabled(false);
        btnQuitarArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarArticuloActionPerformed(evt);
            }
        });

        lblTotal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTotal.setText("Total : $0");

        fechaEntrega.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                fechaEntregaMouseReleased(evt);
            }
        });

        btnQuitarCliente.setText("Quitar cliente");
        btnQuitarCliente.setEnabled(false);
        btnQuitarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarClienteActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Fecha de entrega: ");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Abono :");

        btnRegistrar.setText("Registrar nota");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Observaciones:");

        txtAreaObservaciones.setColumns(20);
        txtAreaObservaciones.setRows(5);
        jScrollPane2.setViewportView(txtAreaObservaciones);

        menuArchivo.setText("Archivo");

        sMenuRegNota.setText("Registrar Nota");
        menuArchivo.add(sMenuRegNota);

        sMenuCancelNota.setText("Cancelar Nota");
        menuArchivo.add(sMenuCancelNota);
        menuArchivo.add(jSeparator1);

        sMenuLogOut.setText("Cerrar Sesión");
        sMenuLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sMenuLogOutActionPerformed(evt);
            }
        });
        menuArchivo.add(sMenuLogOut);

        jMenuBar1.add(menuArchivo);

        menuOpciones.setText("Opciones");

        jMenu1.setText("Corte de Caja");

        jMenuItem1.setText("Realizar corte de caja del día");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Consultar cortes de caja");
        jMenu1.add(jMenuItem2);

        menuOpciones.add(jMenu1);
        menuOpciones.add(jSeparator2);

        jMenu4.setText("Clientes");

        sMenuSeleccCliente.setText("Seleccionar Cliente");
        jMenu4.add(sMenuSeleccCliente);

        sMenuQuitCliente.setText("Quitar Cliente");
        sMenuQuitCliente.setEnabled(false);
        jMenu4.add(sMenuQuitCliente);

        menuOpciones.add(jMenu4);

        jMenu5.setText("Artículos");

        jMenuItem10.setText("Agregar Articulo");
        jMenu5.add(jMenuItem10);

        jMenuItem11.setText("Quitar Artículo");
        jMenuItem11.setEnabled(false);
        jMenu5.add(jMenuItem11);

        menuOpciones.add(jMenu5);

        jMenuBar1.add(menuOpciones);

        menuNotas.setText("Notas");

        sMenuConsulNota.setText("Consultar Nota");
        menuNotas.add(sMenuConsulNota);

        sMenuModNota.setText("Modificar Nota");
        sMenuModNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sMenuModNotaActionPerformed(evt);
            }
        });
        menuNotas.add(sMenuModNota);

        jMenuBar1.add(menuNotas);

        menuCliente.setText("Cliente");
        menuCliente.setEnabled(false);

        sMenuCargSaldo.setText("Cargar Saldo");
        menuCliente.add(sMenuCargSaldo);

        sMenuAddCliente.setText("Agregar Cliente");
        menuCliente.add(sMenuAddCliente);

        sMenuModCliente.setText("Modificar Cliente");
        menuCliente.add(sMenuModCliente);

        sMenuElimCliente.setText("Eliminar Cliente");
        menuCliente.add(sMenuElimCliente);

        jMenuBar1.add(menuCliente);

        menuAyuda.setText("Ayuda");

        sMenuAcerca.setText("Acerca De...");
        menuAyuda.add(sMenuAcerca);

        jMenuBar1.add(menuAyuda);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtAbono, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblTotal)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(46, 46, 46)
                        .addComponent(fechaEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(btnQuitarArticulo)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel6)))
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGap(15, 15, 15)
                                        .addComponent(jLabel7))
                                    .addComponent(txtApMaterno, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                                    .addComponent(txtTelefono, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDomicilio, javax.swing.GroupLayout.Alignment.LEADING)))
                            .addComponent(lblNombreUsuario)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnCliente)
                                .addGap(18, 18, 18)
                                .addComponent(btnQuitarCliente))
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
                                    .addComponent(txtApPaterno, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(btnRegistrar)))
                .addContainerGap(617, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNombreUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCliente)
                    .addComponent(btnQuitarCliente)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtApPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtApMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
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
                        .addComponent(lblTotal)
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtAbono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fechaEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addComponent(btnRegistrar))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(76, Short.MAX_VALUE))
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

    private void btnQuitarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarClienteActionPerformed
        quitarCliente();
    }//GEN-LAST:event_btnQuitarClienteActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        conexionNota = new ConexionNotas();
        String fecha=null;
        if(fechaEntrega.getDate()!=null){
        fecha = df.format(fechaEntrega.getDate());
        }
        
        if (!listaArticulos.isEmpty()) {
            if (cliente != null) {
                conexionNota.guardarNota(txtNombres.getText(), txtApPaterno.getText(), txtApMaterno.getText(), txtTelefono.getText(), fecha, Double.parseDouble(txtAbono.getText()), txtAreaObservaciones.getText(), cliente.getId(), listaArticulos);
            } else {
                conexionNota.guardarNota(txtNombres.getText(), txtApPaterno.getText(), txtApMaterno.getText(), txtTelefono.getText(), fecha, Double.parseDouble(txtAbono.getText()), txtAreaObservaciones.getText(), 1, listaArticulos);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tienes que agregar al menos un artículo a la nota");
        }


    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void sMenuLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sMenuLogOutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sMenuLogOutActionPerformed

    private void sMenuModNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sMenuModNotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sMenuModNotaActionPerformed

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
//            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JButton btnCliente;
    private javax.swing.JButton btnQuitarArticulo;
    private javax.swing.JButton btnQuitarCliente;
    private javax.swing.JButton btnRegistrar;
    private com.toedter.calendar.JDateChooser fechaEntrega;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JLabel lblNombreUsuario;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenu menuAyuda;
    private javax.swing.JMenu menuCliente;
    private javax.swing.JMenu menuNotas;
    private javax.swing.JMenu menuOpciones;
    private javax.swing.JMenuItem sMenuAcerca;
    private javax.swing.JMenuItem sMenuAddCliente;
    private javax.swing.JMenuItem sMenuCancelNota;
    private javax.swing.JMenuItem sMenuCargSaldo;
    private javax.swing.JMenuItem sMenuConsulNota;
    private javax.swing.JMenuItem sMenuElimCliente;
    private javax.swing.JMenuItem sMenuLogOut;
    private javax.swing.JMenuItem sMenuModCliente;
    private javax.swing.JMenuItem sMenuModNota;
    private javax.swing.JMenuItem sMenuQuitCliente;
    private javax.swing.JMenuItem sMenuRegNota;
    private javax.swing.JMenuItem sMenuSeleccCliente;
    private javax.swing.JTable tableArticulos;
    private javax.swing.JTextField txtAbono;
    private javax.swing.JTextField txtApMaterno;
    private javax.swing.JTextField txtApPaterno;
    private javax.swing.JTextArea txtAreaObservaciones;
    private javax.swing.JTextField txtDomicilio;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
