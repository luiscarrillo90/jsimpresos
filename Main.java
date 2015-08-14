/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsimpresos;

import com.mysql.jdbc.Connection;
import java.awt.Desktop;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import static oracle.jrockit.jfr.events.Bits.doubleValue;

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
    String[] columnas = {"Artículo", "Precio Unitario", "Cantidad", "Total"};
    String[][] datos;
    //para dar formato a la fecha que nos de el "JCalendar"
    DateFormat df = DateFormat.getDateInstance();
    //número de artículos en la nota
    int numeroArticulos = 0;
    //bandera del escuchador de la tabla de artículos de la nota
    boolean bandera = true;
    ConexionNotas conexionNota;
    DefaultTableModel model;
    //para saber que tecla es presionada en el escuchador de la tabla
    int key;
    //para saber si estamos en la columna del total (bandera)
    boolean columnaFinal = false;
    //bandera de click del mouse sobre la tabla
    boolean clickEnTabla = false;
    Abono anticipo;
    //para obtener el miembro generico
    Miembro miembroGenerico;
    
    /*
     Recibimos en el constructor un nombre, solo se usa para saber el nombre de quién inició sesión
     */

    public Main(String nombre) {
        this.setResizable(false);
        initComponents();
        this.setLocationRelativeTo(null); 
        usuario = nombre;
        //asigna nombre a etiqueta        
        lblNombreUsuario.setText(nombre);
        miembroGenerico = new ConexionMiembro().obtenerMiembroGenerico();
        iniciarNota();
    }
    /*
     Para cambiar el usuario de la nota
     */
    
    public void ponerFocoEnCeldaNueva(){
        TableModel modelo = (TableModel) tableArticulos.getModel();
        if (numeroArticulos > 0) {
           tableArticulos.changeSelection(model.getRowCount()-1, 0, false, false); 
        }else{
            tableArticulos.changeSelection(0, 0, false, false); 
        }
        tableArticulos.requestFocus();
        calcularTotal(modelo, numeroArticulos);
        clickEnTabla = false;
    }
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
            ponerFocoEnCeldaNueva();

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
        btnQuitarCliente.setEnabled(false);
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
                if (columna == 3){
                    columnaFinal = true;
                    return;
                }
                if (columna == 0) {
                    columnaFinal = false;
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
                    for (int i = 0; i < modelo.getRowCount(); i++) {
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

            cambiarTotal(clickEnTabla);
        }
    }
    /*
     Cambia el total de la nota al momento de agregar artículos o de borrar
     lee los totales de cada artículo y los escribe en la etiqueta
     lblTotal
     */

    public void cambiarTotal(boolean clickEnTabla) {
        TableModel modelo = (TableModel) tableArticulos.getModel();
        if (!clickEnTabla || ((Number) modelo.getValueAt(modelo.getRowCount()-1, 3)).doubleValue() != 0.0) {
        lblTotal.setText("Total: $" + calcularTotal(modelo, numeroArticulos));
        }else if (clickEnTabla) {
           numeroArticulos--;
        lblTotal.setText("Total: $" + calcularTotal(modelo, numeroArticulos));
        }
        this.clickEnTabla = false;
    }
    /*
     Borra artículos (renglones) de la tabla de artículos, recibe el índice
     del renglón a borrar. 
     */

    public void quitarArticulo(TableModel modelo, int cual) {

//        if (cual + 1 <= numeroArticulos) {
//
//            TableModel modelo = (TableModel) tableArticulos.getModel();
//            for (int i = cual; i <= numeroArticulos - 2; i++) {
//
//                modelo.setValueAt(modelo.getValueAt(i + 1, 0), i, 0);
//                modelo.setValueAt(modelo.getValueAt(i + 1, 1), i, 1);
//                modelo.setValueAt(modelo.getValueAt(i + 1, 2), i, 2);
//                modelo.setValueAt(modelo.getValueAt(i + 1, 3), i, 3);
//
//            }
//            modelo.setValueAt(null, numeroArticulos - 1, 0);
//            modelo.setValueAt(null, numeroArticulos - 1, 1);
//            modelo.setValueAt(null, numeroArticulos - 1, 2);
//            modelo.setValueAt(null, numeroArticulos - 1, 3);
//
//        }
//        listaArticulos.remove(cual);
        numeroArticulos--;
        cambiarTotal(false);
        bandera = true;
        if (modelo.getRowCount() == 1) {
            btnQuitarArticulo.setEnabled(false);
        }
    }
    
    public double calcularTotal(TableModel modelo, int numeroArticulos){
        if (numeroArticulos > 0) {
        double total = 0;
        for (int i = 0; i < numeroArticulos; i++) {
            if (modelo.getValueAt(i, 1) != null || modelo.getValueAt(i, 2) != null) {
            total += ((Number) modelo.getValueAt(i, 3)).doubleValue();
            }
        }
            btnQuitarArticulo.setEnabled(true); 
        
        return total;
        }
        return 0;
    }
    
    public ArrayList actualizarListaArticulos(TableModel modelo, int numeroArticulos){
        listaArticulos = new <Articulo>ArrayList();
        for (int i = 0; i < numeroArticulos; i++) {
            if (modelo.getValueAt(i, 1) != null || modelo.getValueAt(i, 2) != null) {
                listaArticulos.add(new Articulo(1, modelo.getValueAt(i, 0).toString(), ((Number) modelo.getValueAt(i, 1)).doubleValue(), ((Number) modelo.getValueAt(i, 2)).intValue()));
            }
        }
        //para simular limpiar la pantalla (en realidad no lo hace, solo en aparencia)
//        for (int i = 0; i < 50; ++i) System.out.println();
//        for (int i = 0; i < listaArticulos.size(); i++) {
//            System.out.println(i+".- "+listaArticulos.get(i));
//        }
//        System.out.println("----");
        return listaArticulos;
    }
    
    public void limpiarTablaArticulos(){
        tableArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null,  new Double(0.0)}
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
        iniciarNota();
    }
    
    public void iniciarNota(){
        txtNombres.requestFocus();
        
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
        tableArticulos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                key = e.getKeyCode();
//                   
                if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
                    clickEnTabla = true;
                }
                if (key == KeyEvent.VK_ENTER && columnaFinal) {
                         model = (DefaultTableModel) tableArticulos.getModel();
                         if (((Number)model.getValueAt(tableArticulos.getRowCount()-1, 3)).doubleValue() != 0.0) {
                         model.addRow(new Object[]{null,null,null,0});
                         columnaFinal = false;
                         ponerFocoEnCeldaNueva();
                         }else{
                             ponerFocoEnCeldaNueva();
                         }
                    }
                    else if(key == KeyEvent.VK_ENTER && tableArticulos.getSelectedColumn() == 0 && tableArticulos.getSelectedRow() == 0){
                        ponerFocoEnCeldaNueva();
                    }else if(key == KeyEvent.VK_ENTER && tableArticulos.getSelectedColumn() == 3){
                        ponerFocoEnCeldaNueva();
                    }
            }
        });
    }
    
    public void cancelarNotaActual(){
        listaArticulos = new <Articulo>ArrayList();
        numeroArticulos = 0;
        columnaFinal = false;
        clickEnTabla = false;
        quitarCliente();
        limpiarTablaArticulos();
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
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        tBtnRegistrar = new javax.swing.JButton();
        tBtnCancelNota = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        tBtnAddArt = new javax.swing.JButton();
        tBtnQuitarArticulo = new javax.swing.JButton();
        tBtnSeleccCliente = new javax.swing.JButton();
        tBtnQuitarCliente = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        tBtnCorteCaja = new javax.swing.JButton();
        tBtnCargarSaldo = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jButton4 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
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
        btnCliente = new javax.swing.JButton();
        btnQuitarCliente = new javax.swing.JButton();
        btnClienteGenerico = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableArticulos = new javax.swing.JTable();
        btnQuitarArticulo = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        fechaEntrega = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        txtAbono = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaObservaciones = new javax.swing.JTextArea();
        btnRegistrar = new javax.swing.JButton();
        cmbTipoPago = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
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
        menuCliente = new javax.swing.JMenu();
        sMenuCargSaldo = new javax.swing.JMenuItem();
        sMenuAddCliente = new javax.swing.JMenuItem();
        sMenuModCliente = new javax.swing.JMenuItem();
        sMenuElimCliente = new javax.swing.JMenuItem();
        menuAyuda = new javax.swing.JMenu();
        sMenuAcerca = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JS Impresos y Diseños - Punto de Venta");

        lblNombreUsuario.setText("jLabel1");

        jToolBar1.setRollover(true);

        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton1);

        tBtnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/registrar.png"))); // NOI18N
        tBtnRegistrar.setToolTipText("Registrar Nota");
        tBtnRegistrar.setFocusable(false);
        tBtnRegistrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tBtnRegistrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(tBtnRegistrar);

        tBtnCancelNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/cancelar.png"))); // NOI18N
        tBtnCancelNota.setToolTipText("Cancelar Nota");
        tBtnCancelNota.setFocusable(false);
        tBtnCancelNota.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tBtnCancelNota.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(tBtnCancelNota);
        jToolBar1.add(jSeparator3);

        tBtnAddArt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/agregar.png"))); // NOI18N
        tBtnAddArt.setToolTipText("Agregar Articulo");
        tBtnAddArt.setFocusable(false);
        tBtnAddArt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tBtnAddArt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tBtnAddArt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tBtnAddArtActionPerformed(evt);
            }
        });
        jToolBar1.add(tBtnAddArt);

        tBtnQuitarArticulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/quitar.png"))); // NOI18N
        tBtnQuitarArticulo.setToolTipText("Quitar Articulo");
        tBtnQuitarArticulo.setEnabled(false);
        tBtnQuitarArticulo.setFocusable(false);
        tBtnQuitarArticulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tBtnQuitarArticulo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(tBtnQuitarArticulo);

        tBtnSeleccCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/buscar cliente.png"))); // NOI18N
        tBtnSeleccCliente.setToolTipText("Seleccionar cliente miembro");
        tBtnSeleccCliente.setFocusable(false);
        tBtnSeleccCliente.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tBtnSeleccCliente.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tBtnSeleccCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tBtnSeleccClienteActionPerformed(evt);
            }
        });
        jToolBar1.add(tBtnSeleccCliente);

        tBtnQuitarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/insertar cliente.png"))); // NOI18N
        tBtnQuitarCliente.setToolTipText("Insertar nuevo cliente");
        tBtnQuitarCliente.setEnabled(false);
        tBtnQuitarCliente.setFocusable(false);
        tBtnQuitarCliente.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tBtnQuitarCliente.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(tBtnQuitarCliente);
        jToolBar1.add(jSeparator4);

        tBtnCorteCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/corte.png"))); // NOI18N
        tBtnCorteCaja.setToolTipText("Corte de caja");
        tBtnCorteCaja.setFocusable(false);
        tBtnCorteCaja.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tBtnCorteCaja.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(tBtnCorteCaja);

        tBtnCargarSaldo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/cargar saldo cliente.png"))); // NOI18N
        tBtnCargarSaldo.setToolTipText("Cargar Saldo a miembro");
        tBtnCargarSaldo.setFocusable(false);
        tBtnCargarSaldo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tBtnCargarSaldo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(tBtnCargarSaldo);
        jToolBar1.add(jSeparator5);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/ayuda.png"))); // NOI18N
        jButton4.setToolTipText("Acerca de...");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton4);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del cliente"));

        jLabel2.setText("Nombres: ");

        jLabel3.setText("Apellido Paterno: ");

        jLabel4.setText("Apellido Materno:");

        txtApMaterno.setMaximumSize(new java.awt.Dimension(200, 2147483647));

        jLabel5.setText("Domicilio:");

        jLabel6.setText("Teléfono/celular:");

        btnCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/buscar cliente.png"))); // NOI18N
        btnCliente.setText("Seleccionar cliente miembro");
        btnCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteActionPerformed(evt);
            }
        });

        btnQuitarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/insertar cliente.png"))); // NOI18N
        btnQuitarCliente.setText("Insertar nuevo cliente");
        btnQuitarCliente.setEnabled(false);
        btnQuitarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarClienteActionPerformed(evt);
            }
        });

        btnClienteGenerico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/cliente.png"))); // NOI18N
        btnClienteGenerico.setText("Insertar un cliente generico");
        btnClienteGenerico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteGenericoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3))
                            .addComponent(txtDomicilio))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtApPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtApMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTelefono)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnQuitarCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClienteGenerico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtApPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCliente)
                    .addComponent(btnQuitarCliente)
                    .addComponent(btnClienteGenerico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Articulos"));

        tableArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null,  new Double(0.0)}
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
        tableArticulos.setCellSelectionEnabled(true);
        tableArticulos.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
                tableArticulosAncestorMoved(evt);
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
            }
        });
        tableArticulos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableArticulosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableArticulos);

        btnQuitarArticulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/quitar.png"))); // NOI18N
        btnQuitarArticulo.setText("Quitar artículo");
        btnQuitarArticulo.setEnabled(false);
        btnQuitarArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarArticuloActionPerformed(evt);
            }
        });

        lblTotal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTotal.setText("Total : $0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnQuitarArticulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblTotal)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(btnQuitarArticulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                        .addComponent(lblTotal))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos extra de la nota"));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Fecha de entrega: ");

        fechaEntrega.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                fechaEntregaMouseReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Abono :");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Observaciones:");

        txtAreaObservaciones.setColumns(20);
        txtAreaObservaciones.setRows(5);
        jScrollPane2.setViewportView(txtAreaObservaciones);

        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/registrar.png"))); // NOI18N
        btnRegistrar.setText("Registrar nota");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        cmbTipoPago.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Efectivo" }));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Tipo de pago :");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fechaEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAbono, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(btnRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(28, 28, 28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbTipoPago, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(22, 22, 22))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtAbono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(fechaEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(cmbTipoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)))
                .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menuArchivo.setText("Archivo");

        sMenuRegNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/registrar icono.png"))); // NOI18N
        sMenuRegNota.setText("Registrar Nota");
        menuArchivo.add(sMenuRegNota);

        sMenuCancelNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/cancelar icono.png"))); // NOI18N
        sMenuCancelNota.setText("Cancelar Nota");
        sMenuCancelNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sMenuCancelNotaActionPerformed(evt);
            }
        });
        menuArchivo.add(sMenuCancelNota);
        menuArchivo.add(jSeparator1);

        sMenuLogOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/sesion icono.png"))); // NOI18N
        sMenuLogOut.setText("Cerrar Sesión");
        sMenuLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sMenuLogOutActionPerformed(evt);
            }
        });
        menuArchivo.add(sMenuLogOut);

        jMenuBar1.add(menuArchivo);

        menuOpciones.setText("Opciones");

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/caja icono.png"))); // NOI18N
        jMenu1.setText("Corte de Caja");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/corte icono.png"))); // NOI18N
        jMenuItem1.setText("Realizar corte de caja");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/consultar cortes icono.png"))); // NOI18N
        jMenuItem2.setText("Consultar cortes de caja");
        jMenu1.add(jMenuItem2);

        menuOpciones.add(jMenu1);
        menuOpciones.add(jSeparator2);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/cliente icono.png"))); // NOI18N
        jMenu4.setText("Clientes");

        sMenuSeleccCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/buscar cliente icono.png"))); // NOI18N
        sMenuSeleccCliente.setText("Seleccionar Cliente");
        jMenu4.add(sMenuSeleccCliente);

        sMenuQuitCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/insertar cliente icono.png"))); // NOI18N
        sMenuQuitCliente.setText("Insertar nuevo cliente");
        sMenuQuitCliente.setEnabled(false);
        jMenu4.add(sMenuQuitCliente);

        menuOpciones.add(jMenu4);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/articulos icono.png"))); // NOI18N
        jMenu5.setText("Artículos");

        jMenuItem10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/agregar icono.png"))); // NOI18N
        jMenuItem10.setText("Agregar Articulo");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem10);

        jMenuItem11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/quitar icono.png"))); // NOI18N
        jMenuItem11.setText("Quitar Artículo");
        jMenuItem11.setEnabled(false);
        jMenu5.add(jMenuItem11);

        menuOpciones.add(jMenu5);

        jMenuBar1.add(menuOpciones);

        menuNotas.setText("Notas");

        sMenuConsulNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/consultar notas icono.png"))); // NOI18N
        sMenuConsulNota.setText("Consultar Nota");
        menuNotas.add(sMenuConsulNota);

        jMenuBar1.add(menuNotas);

        menuCliente.setText("Cliente");

        sMenuCargSaldo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/cargar saldo cliente icono.png"))); // NOI18N
        sMenuCargSaldo.setText("Cargar Saldo");
        menuCliente.add(sMenuCargSaldo);

        sMenuAddCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/agregar cliente icono.png"))); // NOI18N
        sMenuAddCliente.setText("Agregar Cliente");
        sMenuAddCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sMenuAddClienteActionPerformed(evt);
            }
        });
        menuCliente.add(sMenuAddCliente);

        sMenuModCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/maodificar cliene icono.png"))); // NOI18N
        sMenuModCliente.setText("Modificar Cliente");
        sMenuModCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sMenuModClienteActionPerformed(evt);
            }
        });
        menuCliente.add(sMenuModCliente);

        sMenuElimCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/eliminar cliente icono.png"))); // NOI18N
        sMenuElimCliente.setText("Eliminar Cliente");
        sMenuElimCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sMenuElimClienteActionPerformed(evt);
            }
        });
        menuCliente.add(sMenuElimCliente);

        jMenuBar1.add(menuCliente);

        menuAyuda.setText("Ayuda");

        sMenuAcerca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsimpresos/imagenes/ayuda icono.png"))); // NOI18N
        sMenuAcerca.setText("Acerca De...");
        menuAyuda.add(sMenuAcerca);

        jMenuBar1.add(menuAyuda);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71)
                        .addComponent(lblNombreUsuario))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                        .addGap(13, 13, 13))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNombreUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
        TableModel modelo = (TableModel) tableArticulos.getModel();
        
        if(tableArticulos.getSelectedRow()+1 == tableArticulos.getRowCount()){
            JOptionPane.showMessageDialog(null, "No puedes eliminar el articulo por que aun no ha sido añadido, intenta editarlo");
            ponerFocoEnCeldaNueva();
        }else if (((Number)modelo.getValueAt(tableArticulos.getRowCount()-1, 3)).doubleValue() != 0.0) {
            JOptionPane.showMessageDialog(null, "No puedes eliminar el articulo seleccionado porque aun no ha sido añadido. Termina de agregarlo para poder eliminar articulos.");
            ponerFocoEnCeldaNueva();
        }
        else{  
                int borrar = JOptionPane.showConfirmDialog(null, "¿Estás seguro que deseas quitar el artículo de la lista?");
                if (borrar == 0) {
                    bandera = false;
                    quitarArticulo(modelo, tableArticulos.getSelectedRow());
                    model.removeRow(tableArticulos.getSelectedRow());
                    ponerFocoEnCeldaNueva();
                }
        }
    }//GEN-LAST:event_btnQuitarArticuloActionPerformed

    private void tableArticulosAncestorMoved(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_tableArticulosAncestorMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_tableArticulosAncestorMoved

    private void fechaEntregaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fechaEntregaMouseReleased

    }//GEN-LAST:event_fechaEntregaMouseReleased

    private void btnQuitarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarClienteActionPerformed
        cliente = null;
        quitarCliente();
        txtNombres.requestFocus();
    }//GEN-LAST:event_btnQuitarClienteActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        TableModel modelo = (TableModel) tableArticulos.getModel();
        listaArticulos = actualizarListaArticulos(modelo, numeroArticulos);
        if (txtAbono.getText() != null && !txtAbono.getText().equals("")) {
            anticipo = new Abono(1, Double.parseDouble(txtAbono.getText()), "","no", "");
            conexionNota = new ConexionNotas();
            String fecha = null;
            if (fechaEntrega.getDate() != null) {
                fecha = df.format(fechaEntrega.getDate());
            }

            if (!listaArticulos.isEmpty()) {
                if (cliente != null) {
                    conexionNota.guardarNota(txtNombres.getText(), txtApPaterno.getText(), txtApMaterno.getText(), txtTelefono.getText(), fecha, anticipo, cmbTipoPago.getSelectedIndex(),txtAreaObservaciones.getText(), cliente.getId(), listaArticulos, txtDomicilio.getText());
                } else {
                    System.out.println("aqui");
                    conexionNota.guardarNota(txtNombres.getText(), txtApPaterno.getText(), txtApMaterno.getText(), txtTelefono.getText(), fecha, anticipo, cmbTipoPago.getSelectedIndex(),txtAreaObservaciones.getText(), 1, listaArticulos, txtDomicilio.getText());
                }
                try {
                    //Runtime.getRuntime().exec("c:/Users/luis-pc/Documents/NetBeansProjects/js/fichero.pdf");
                    File obj = new File("c:/Users/Toshiba/Documents/NetBeansProjects/js/fichero.pdf");
                    Desktop.getDesktop().open(obj);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "No se pudo abrir el archivo");
                    System.out.println(ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Tienes que agregar al menos un artículo a la nota");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Debes ingresar un anticipo antes de continuar");
        }
//        conexionNota = new ConexionNotas();
//        String fecha=null;
//        if(fechaEntrega.getDate()!=null){
//        fecha = df.format(fechaEntrega.getDate());
//        }
//        
//        if (!listaArticulos.isEmpty()) {
//            
//            if (cliente != null) {
//                 for (int i = 0; i < listaArticulos.size(); i++) {
//                     System.out.println(i+1+".- "+listaArticulos.get(i));
//                     System.out.println(doubleValue(modelo.getValueAt(modelo.getRowCount()-1, 3)));
//                }
////                conexionNota.guardarNota(txtNombres.getText(), txtApPaterno.getText(), txtApMaterno.getText(), txtTelefono.getText(), fecha, Double.parseDouble(txtAbono.getText()), txtAreaObservaciones.getText(), cliente.getId(), actualizarListaArticulos(modelo, numeroArticulos));
//            } else {
//                for (int i = 0; i < listaArticulos.size(); i++) {
//                     System.out.println(i+1+".- "+listaArticulos.get(i));
//                     System.out.println(doubleValue(modelo.getValueAt(modelo.getRowCount()-1, 3)));
//                }
////                conexionNota.guardarNota(txtNombres.getText(), txtApPaterno.getText(), txtApMaterno.getText(), txtTelefono.getText(), fecha, Double.parseDouble(txtAbono.getText()), txtAreaObservaciones.getText(), 1, actualizarListaArticulos(modelo, numeroArticulos));
//            }
//            try {
//                    //Runtime.getRuntime().exec("c:/Users/luis-pc/Documents/NetBeansProjects/js/fichero.pdf");
//                    File obj = new File("c:/Users/luis-pc/Documents/NetBeansProjects/js/fichero.pdf");
//                    Desktop.getDesktop().open(obj);
//                } catch (IOException ex) {
//                    JOptionPane.showMessageDialog(null, "No se pudo abrir el archivo");
//                    System.out.println(ex.getMessage());
//                }
//        } else {
//            JOptionPane.showMessageDialog(null, "Tienes que agregar al menos un artículo a la nota");
//        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void sMenuLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sMenuLogOutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sMenuLogOutActionPerformed

    private void tableArticulosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableArticulosMouseClicked
        clickEnTabla = true;
    }//GEN-LAST:event_tableArticulosMouseClicked

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        ponerFocoEnCeldaNueva();
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void sMenuCancelNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sMenuCancelNotaActionPerformed
        cancelarNotaActual();
    }//GEN-LAST:event_sMenuCancelNotaActionPerformed

    private void tBtnAddArtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tBtnAddArtActionPerformed
        ponerFocoEnCeldaNueva();
    }//GEN-LAST:event_tBtnAddArtActionPerformed

    private void tBtnSeleccClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tBtnSeleccClienteActionPerformed
        SeleccionarCliente nuevo = new SeleccionarCliente(this);
        nuevo.setVisible(true);
    }//GEN-LAST:event_tBtnSeleccClienteActionPerformed

    private void sMenuAddClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sMenuAddClienteActionPerformed
        AgregarCliente nuevo = new AgregarCliente(this, null);
        nuevo.setVisible(true);
    }//GEN-LAST:event_sMenuAddClienteActionPerformed

    private void sMenuModClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sMenuModClienteActionPerformed
        VerMiembros nuevo = new VerMiembros("editar");
        nuevo.setVisible(true);
    }//GEN-LAST:event_sMenuModClienteActionPerformed

    private void sMenuElimClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sMenuElimClienteActionPerformed
        VerMiembros nuevo = new VerMiembros("eliminar");
        nuevo.setVisible(true);
    }//GEN-LAST:event_sMenuElimClienteActionPerformed

    private void btnClienteGenericoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteGenericoActionPerformed
        cliente = miembroGenerico;
        cambiarCliente(cliente);
        btnQuitarCliente.setEnabled(true);
    }//GEN-LAST:event_btnClienteGenericoActionPerformed

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
    private javax.swing.JButton btnClienteGenerico;
    private javax.swing.JButton btnQuitarArticulo;
    public javax.swing.JButton btnQuitarCliente;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JComboBox cmbTipoPago;
    private com.toedter.calendar.JDateChooser fechaEntrega;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar jToolBar1;
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
    private javax.swing.JMenuItem sMenuQuitCliente;
    private javax.swing.JMenuItem sMenuRegNota;
    private javax.swing.JMenuItem sMenuSeleccCliente;
    private javax.swing.JButton tBtnAddArt;
    private javax.swing.JButton tBtnCancelNota;
    private javax.swing.JButton tBtnCargarSaldo;
    private javax.swing.JButton tBtnCorteCaja;
    private javax.swing.JButton tBtnQuitarArticulo;
    private javax.swing.JButton tBtnQuitarCliente;
    private javax.swing.JButton tBtnRegistrar;
    private javax.swing.JButton tBtnSeleccCliente;
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
