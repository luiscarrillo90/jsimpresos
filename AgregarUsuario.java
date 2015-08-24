/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsimpresos;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.border.TitledBorder;

/**
 *
 * @author luis-pc
 */
public class AgregarUsuario extends javax.swing.JFrame {

    ConexionUsuario conexion = new ConexionUsuario();
    verUsuarios anterior;
    Usuario usuario = null;
    Main anteriorMain;
    int idUsuario;
    //bandera para saber si ya pregunto si quiere contraseña en el evento focus (gained, lost)
    boolean preguntara = false;
    //bander para saber si vengo a editar usuario que inicio sesion
    boolean usuarioConSesion = false;

    /**
     * Creates new form AgregarCliente
     */
    public AgregarUsuario(verUsuarios anterior, Usuario usuario) {
        initComponents();
        this.anterior = anterior;
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.usuario = usuario;
        inicializarVentanaParaEditar();
    }
    public AgregarUsuario(Main anterior, Usuario usuario) {
        initComponents();
        this.usuario = usuario;
        if (this.usuario != null) {
            usuarioConSesion = true;
            inicializarVentanaParaEditar();
            if (usuario.getTipoUsuario().equals("administrador")) {
                cmbTipoUsuario.setSelectedIndex(1);
            }
        }else{
            txtPassword.setEditable(true);
            txtPasswordConf.setEditable(true);
        }
        this.anteriorMain = anterior;
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    public void inicializarVentanaParaEditar(){
            TitledBorder tb = BorderFactory.createTitledBorder("Editar Usuario");
            pnlTitulo.setBorder(tb);
            txtNombres.setText(usuario.getNombres());
            txtApPaterno.setText(usuario.getApPaterno());
            txtApMaterno.setText(usuario.getApMaterno());
            txtNombreUsuario.setText(usuario.getNombreUsuario());
            lblContrasena.setText("Contraseña:");
            lblContrasena2.setText("Confirmar contraseña:");
            btnAceptar.setText("Guardar");
            idUsuario = usuario.getId();
            
            txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusGained(java.awt.event.FocusEvent evt) {
                    if(!preguntara){
                                if (!txtPassword.isEditable()) {
                                    preguntara=true;
                                    if (JOptionPane.showConfirmDialog(null, "¿Quieres modificar la contraseña para este usuario?") == 0) {
                                        txtPassword.setEditable(true);
                                    }else{
                                        btnAceptar.requestFocus();
                                    }
                                }
                            }
                }
                public void focusLost(java.awt.event.FocusEvent evt) {
                    if (!sacarPass(txtPassword).equals("")) {
                                txtPasswordConf.setEditable(true);
                    }
                }
            });
            
            btnAceptar.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusGained(java.awt.event.FocusEvent evt) {
                    if (!txtPassword.isEditable()) {
                            preguntara=false;
                    }
                }
            });
    }
    
    public String sacarPass(JPasswordField txtPassword){
        char c1[] = txtPassword.getPassword();
        String pass = new String(c1);
        return pass;
    }
    
    public boolean comprobarDatos() {
        if (!txtNombres.getText().equals("") && txtNombres.getText() != null
                && !txtApPaterno.getText().equals("") && txtApPaterno.getText() != null
                && !txtNombreUsuario.getText().equals("") && txtNombreUsuario.getText() != null) {
            return true;
        } else {
            return false;
        }
    }

    public String comprobarPassword() {
        if (txtPasswordConf.isEditable()) {
           String pass1 = sacarPass(txtPassword);
           String pass2 = sacarPass(txtPasswordConf);
           if (!pass1.equals("") && pass1.equals(pass2)) {
               return "iguales";
           } else {
               return "distintos";
           }   
        }
        return "No";
    }
    
    public String sacarTipoUsuario(JComboBox cmbTipoUsuario){
        if (cmbTipoUsuario.getSelectedIndex() == 0) {
            return "empleado";
        }else{
            return "administrador";
        }
        
    }
    
    public void accionBtnAceptar(Usuario user, boolean conPassword){
        if (user == null) {
            if (conexion.crearUsuario(new Usuario(1, txtNombres.getText(),txtApPaterno.getText(),txtApMaterno.getText(), txtNombreUsuario.getText(), sacarPass(txtPassword), sacarTipoUsuario(cmbTipoUsuario)))) {
                JOptionPane.showMessageDialog(null, "El usuario fue creado correctamente");
            }
        }else{
            if (conPassword) {
                if (conexion.modificarUsuario(new Usuario(usuario.getId(), txtNombres.getText(),txtApPaterno.getText(),txtApMaterno.getText(), txtNombreUsuario.getText(), sacarPass(txtPassword), sacarTipoUsuario(cmbTipoUsuario)))) {
                    JOptionPane.showMessageDialog(null, "El usuario fue modificado correctamente");
                    if (!usuarioConSesion) {
                        anterior.actualizarUsuarios(conexion.obtenerUsuariosEmpleados());
                    }
                }
            }else{
                if (conexion.modificarUsuarioSinPassword(new Usuario(usuario.getId(), txtNombres.getText(),txtApPaterno.getText(),txtApMaterno.getText(), txtNombreUsuario.getText(), sacarPass(txtPassword), sacarTipoUsuario(cmbTipoUsuario)))) {
                    JOptionPane.showMessageDialog(null, "El usuario fue modificado correctamente");
                    if (!usuarioConSesion) {
                        anterior.actualizarUsuarios(conexion.obtenerUsuariosEmpleados());
                    }
                }
            }
        }
        
        
//        if (user == null) {
//            conexion.guardarMiembro(new Miembro(1, txtTarjeta.getText(), txtNombres.getText(), txtApPaterno.getText(), txtApMaterno.getText(),
//            txtDomicilio.getText(), txtTelefono.getText(), 0, "", txtNombreUsuario.getText(), new String(txtPassword.getPassword())));
//
//
//        }else{
//            conexion.actualizarMiembro(new Miembro(miembro.getId(), txtTarjeta.getText(), txtNombres.getText(), txtApPaterno.getText(), txtApMaterno.getText() ,
//                    txtDomicilio.getText(), txtTelefono.getText(), 0, "", txtNombreUsuario.getText(), new String(txtPassword.getPassword())), idMiembro);
//            anterior.actualizarMiembros(conexion.obtenerMiembros());
//        }
        this.dispose();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTitulo = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNombres = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtApPaterno = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtApMaterno = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtNombreUsuario = new javax.swing.JTextField();
        lblContrasena = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblContrasena2 = new javax.swing.JLabel();
        txtPasswordConf = new javax.swing.JPasswordField();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        cmbTipoUsuario = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Administración de Miembros");

        pnlTitulo.setBorder(javax.swing.BorderFactory.createTitledBorder("Agregar Usuario"));
        pnlTitulo.setToolTipText("");

        jLabel2.setText("Los campos con * son obligatorios");

        jLabel4.setText("Nombres:*");

        jLabel5.setText("Apellido Paterno:*");

        jLabel6.setText("Apellido Materno:");

        jLabel9.setText("Nombre de usuario:*");

        lblContrasena.setText("Contraseña:*");

        txtPassword.setEditable(false);

        lblContrasena2.setText("Confirmar contraseña:*");

        txtPasswordConf.setEditable(false);

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        cmbTipoUsuario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "empleado", "administrador" }));

        jLabel1.setText("Tipo de usuario:");

        javax.swing.GroupLayout pnlTituloLayout = new javax.swing.GroupLayout(pnlTitulo);
        pnlTitulo.setLayout(pnlTituloLayout);
        pnlTituloLayout.setHorizontalGroup(
            pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTituloLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(51, 51, 51))
            .addGroup(pnlTituloLayout.createSequentialGroup()
                .addGroup(pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTituloLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlTituloLayout.createSequentialGroup()
                                .addGroup(pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel9)
                                    .addComponent(lblContrasena))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtNombres)
                                    .addComponent(txtApPaterno, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtApMaterno)
                                    .addComponent(txtNombreUsuario, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)))
                            .addGroup(pnlTituloLayout.createSequentialGroup()
                                .addGroup(pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblContrasena2)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmbTipoUsuario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtPasswordConf, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)))))
                    .addGroup(pnlTituloLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlTituloLayout.setVerticalGroup(
            pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTituloLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtApPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtApMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblContrasena))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPasswordConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblContrasena2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(26, 26, 26)
                .addGroup(pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        if (comprobarDatos()) {
            if (comprobarPassword().equals("iguales")) {
                if (usuario == null) {
                    accionBtnAceptar(null, true);
                }else{
                    accionBtnAceptar(usuario, true);
                } 
            }else if (comprobarPassword().equals("No")){
                accionBtnAceptar(usuario, false);
            }else if (comprobarPassword().equals("distintos")) {
                JOptionPane.showMessageDialog(null, "La comprobación de contraseña no concuerda");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Todos los campos con * tienen que estar llenos");
        }
        
//        if (comprobarDatos()) {
//            if (comprobarPassword()) {

//            } else {
//                JOptionPane.showMessageDialog(null, "La comprobación de contraseña no concuerda");
//            }
//        } else {
//            JOptionPane.showMessageDialog(null, "Todos los campos con * tienen que estar llenos");
//        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    /**
     * @param args the command line arguments
     */
////    public static void main(String args[]) {
////        /* Set the Nimbus look and feel */
////        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
////        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
////         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
////         */
////        try {
////            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
////                if ("Nimbus".equals(info.getName())) {
////                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
////                    break;
////                }
////            }
////        } catch (ClassNotFoundException ex) {
////            java.util.logging.Logger.getLogger(AgregarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
////        } catch (InstantiationException ex) {
////            java.util.logging.Logger.getLogger(AgregarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
////        } catch (IllegalAccessException ex) {
////            java.util.logging.Logger.getLogger(AgregarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
////        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
////            java.util.logging.Logger.getLogger(AgregarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
////        }
////        //</editor-fold>
////        //</editor-fold>
////        //</editor-fold>
////        //</editor-fold>
////
////        /* Create and display the form */
////        java.awt.EventQueue.invokeLater(new Runnable() {
////            public void run() {
////
////            }
////        });
////    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox cmbTipoUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblContrasena;
    private javax.swing.JLabel lblContrasena2;
    private javax.swing.JPanel pnlTitulo;
    private javax.swing.JTextField txtApMaterno;
    private javax.swing.JTextField txtApPaterno;
    private javax.swing.JTextField txtNombreUsuario;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JPasswordField txtPasswordConf;
    // End of variables declaration//GEN-END:variables
}
