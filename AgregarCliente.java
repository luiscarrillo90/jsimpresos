/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsimpresos;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.border.TitledBorder;

/**
 *
 * @author luis-pc
 */
public class AgregarCliente extends javax.swing.JFrame {

    ConexionMiembro conexion = new ConexionMiembro();
    VerMiembros anterior;
    Miembro miembro;
    Main anteriorMain;
    int idMiembro;
    //bandera para saber si ya pregunto si quiere contraseña en el evento focus (gained, lost)
    boolean preguntara = false;
    /**
     * Creates new form AgregarCliente
     */
    public AgregarCliente(VerMiembros anterior, Miembro miembro) {
        initComponents();
        this.miembro = miembro;
        this.anterior = anterior;
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            TitledBorder tb = BorderFactory.createTitledBorder("Editar Miembro");
            pnlTitulo.setBorder(tb);
            txtTarjeta.setText(miembro.getNumeroTarjeta());
            txtTarjeta.setEnabled(false);
            txtNombres.setText(miembro.getNombres());
            txtApPaterno.setText(miembro.getApPaterno());
            txtApMaterno.setText(miembro.getApMaterno());
            txtDomicilio.setText(miembro.getDomicilio());
            txtTelefono.setText(miembro.getTelefono());
            txtNombreUsuario.setText(miembro.getNombreUsuario());
            btnAceptar.setText("Guardar");
            idMiembro = miembro.getId();
            
            txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusGained(java.awt.event.FocusEvent evt) {
                    if(!preguntara){
                                if (!txtPassword.isEditable()) {
                                    preguntara=true;
                                    if (JOptionPane.showConfirmDialog(null, "¿Quieres modificar la contraseña para este miembro?") == 0) {
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
    public AgregarCliente(Main anterior, Miembro miembro) {
        initComponents();
        this.miembro = miembro;
        this.anteriorMain = anterior;
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        txtPassword.setEditable(true);
        txtPasswordConf.setEditable(true);
    }

    public boolean comprobarDatos() {
        if (!txtTarjeta.getText().equals("") && txtTarjeta.getText() != null && !txtNombres.getText().equals("") && txtNombres.getText() != null
                && !txtApPaterno.getText().equals("") && txtApPaterno.getText() != null && !txtDomicilio.getText().equals("") && txtDomicilio.getText() != null
                && !txtTelefono.getText().equals("") && txtTelefono.getText() != null && !txtNombreUsuario.getText().equals("") && txtNombreUsuario.getText() != null) {
            return true;
        } else {
            return false;
        }

    }
    
    public String sacarPass(JPasswordField txtPassword){
        char c1[] = txtPassword.getPassword();
        String pass = new String(c1);
        return pass;
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
    
    public void accionBtnAceptar(Miembro miembro, boolean conPassword){
        if (miembro == null) {
            if (conexion.guardarMiembro(new Miembro(1, txtTarjeta.getText(), txtNombres.getText(), txtApPaterno.getText(), txtApMaterno.getText(),
                    txtDomicilio.getText(), txtTelefono.getText(), 0, "", txtNombreUsuario.getText(), sacarPass(txtPassword)))) {
                JOptionPane.showMessageDialog(null, "El miembro fue agregado correctamente");
            }
        }else{
            if (conPassword) {
                if (conexion.actualizarMiembro(new Miembro(miembro.getId(), txtTarjeta.getText(), txtNombres.getText(), txtApPaterno.getText(), txtApMaterno.getText() ,
                            txtDomicilio.getText(), txtTelefono.getText(), 0, "", txtNombreUsuario.getText(), sacarPass(txtPassword)), idMiembro)) {
                    JOptionPane.showMessageDialog(null, "El usuario fue modificado correctamente");
                    anterior.actualizarMiembros(conexion.obtenerMiembros());
                }
            }else{
                if (conexion.actualizarMiembroSinPassword(new Miembro(miembro.getId(), txtTarjeta.getText(), txtNombres.getText(), txtApPaterno.getText(), txtApMaterno.getText() ,
                            txtDomicilio.getText(), txtTelefono.getText(), 0, "", txtNombreUsuario.getText(), sacarPass(txtPassword)), idMiembro)) {
                    JOptionPane.showMessageDialog(null, "El usuario fue modificado correctamente");
                    anterior.actualizarMiembros(conexion.obtenerMiembros());
                }
            }
        }
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
        jLabel3 = new javax.swing.JLabel();
        txtTarjeta = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNombres = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtApPaterno = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtApMaterno = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtDomicilio = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtNombreUsuario = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        jLabel11 = new javax.swing.JLabel();
        txtPasswordConf = new javax.swing.JPasswordField();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Administración de Miembros");

        pnlTitulo.setBorder(javax.swing.BorderFactory.createTitledBorder("Agregar Miembro"));
        pnlTitulo.setToolTipText("");

        jLabel2.setText("Los campos con * son obligatorios");

        jLabel3.setText("No. tarjeta:*");

        jLabel4.setText("Nombres:*");

        jLabel5.setText("Apellido Paterno:*");

        txtApPaterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApPaternoActionPerformed(evt);
            }
        });

        jLabel6.setText("Apellido Materno:");

        jLabel7.setText("Domicilio:*");

        jLabel8.setText("Teléfono:*");

        jLabel9.setText("Nombre de usuario:*");

        jLabel10.setText("Contraseña:*");

        txtPassword.setEditable(false);

        jLabel11.setText("Confirmar contraseña:*");

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
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPasswordConf, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlTituloLayout.createSequentialGroup()
                                .addGroup(pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtNombres)
                                    .addComponent(txtTarjeta, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                                    .addComponent(txtApPaterno, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtApMaterno)
                                    .addComponent(txtDomicilio, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTelefono, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNombreUsuario, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPassword)))))
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
                    .addComponent(jLabel3)
                    .addComponent(txtTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(txtDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPasswordConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(pnlTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtApPaternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApPaternoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApPaternoActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        if (comprobarDatos()) {
            if (comprobarPassword().equals("iguales")) {
                if (miembro == null) {
                    accionBtnAceptar(null, true);
                }else{
                    accionBtnAceptar(miembro, true);
                } 
            }else if (comprobarPassword().equals("No")){
                accionBtnAceptar(miembro, false);
            }else if (comprobarPassword().equals("distintos")) {
                JOptionPane.showMessageDialog(null, "La comprobación de contraseña no concuerda");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Todos los campos con * tienen que estar llenos");
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

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
//            java.util.logging.Logger.getLogger(AgregarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(AgregarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(AgregarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(AgregarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel pnlTitulo;
    private javax.swing.JTextField txtApMaterno;
    private javax.swing.JTextField txtApPaterno;
    private javax.swing.JTextField txtDomicilio;
    private javax.swing.JTextField txtNombreUsuario;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JPasswordField txtPasswordConf;
    private javax.swing.JTextField txtTarjeta;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
