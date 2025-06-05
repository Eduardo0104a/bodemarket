
package view.Login;

import dao.UsuarioDAO;
import dto.Usuario;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import view.Main.MainView;


/**
 *
 * @author Eduardo
 */
public class LoginView extends javax.swing.JFrame {

    /**
     * Creates new form LoginVista
     */
    public LoginView() {
        initComponents();
         setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblSesion = new javax.swing.JLabel();
        lblCorreo = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        lblContra = new javax.swing.JLabel();
        txtContra = new javax.swing.JPasswordField();
        jSeparator1 = new javax.swing.JSeparator();
        btnIngresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bodegafondo (1).png"))); // NOI18N
        lblLogo.setName(""); // NOI18N
        jPanel1.add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 0, 330, 460));

        lblSesion.setFont(new java.awt.Font("Roboto Black", 1, 24)); // NOI18N
        lblSesion.setForeground(new java.awt.Color(188, 47, 33));
        lblSesion.setText("INICIAR SESIÓN");
        jPanel1.add(lblSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, -1, -1));

        lblCorreo.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        lblCorreo.setForeground(new java.awt.Color(188, 47, 33));
        lblCorreo.setText("USUARIO");
        jPanel1.add(lblCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, -1, -1));

        txtCorreo.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCorreo.setForeground(new java.awt.Color(204, 204, 204));
        txtCorreo.setText("Ingrese su nombre de usuario");
        txtCorreo.setBorder(null);
        txtCorreo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCorreoFocusLost(evt);
            }
        });
        txtCorreo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtCorreoMousePressed(evt);
            }
        });
        jPanel1.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 350, 20));

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 350, 20));

        lblContra.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        lblContra.setForeground(new java.awt.Color(188, 47, 33));
        lblContra.setText("CONTRASEÑA");
        jPanel1.add(lblContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, -1, -1));

        txtContra.setForeground(new java.awt.Color(204, 204, 204));
        txtContra.setText("*********");
        txtContra.setBorder(null);
        txtContra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtContraFocusLost(evt);
            }
        });
        txtContra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtContraMousePressed(evt);
            }
        });
        txtContra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContraActionPerformed(evt);
            }
        });
        jPanel1.add(txtContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, 370, 20));

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, 350, 20));

        btnIngresar.setBackground(new java.awt.Color(146, 152, 130));
        btnIngresar.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnIngresar.setForeground(new java.awt.Color(188, 47, 33));
        btnIngresar.setText("INGRESAR");
        btnIngresar.setBorder(null);
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });
        jPanel1.add(btnIngresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 340, 100, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtContraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtContraFocusLost
        
    }//GEN-LAST:event_txtContraFocusLost

    private void txtContraMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtContraMousePressed
        if(String.valueOf(txtContra.getPassword()).equals("*********")){
           txtContra.setText(""); 
           txtContra.setForeground(Color.black);
        }
        if(txtCorreo.getText().isEmpty()){
           txtCorreo.setText("Ingrese su correo electrónico");
           txtCorreo.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtContraMousePressed

    private void txtContraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtContraActionPerformed

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        String correo = txtCorreo.getText().trim();
                String contraseña = new String(txtContra.getPassword());

                if (correo.isEmpty() || contraseña.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginView.this, "Por favor ingrese correo y contraseña",
                            "Error de Inicio de Sesión", JOptionPane.ERROR_MESSAGE);
                    txtCorreo.requestFocus();
                    return;
                }

                UsuarioDAO usuarioDAO = new UsuarioDAO();
                Usuario usuario = usuarioDAO.validarUsuario(correo, contraseña);

                if (usuario != null) {
                    dispose();
                    new MainView(usuario).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(LoginView.this, "Correo electrónico o contraseña incorrectos",
                            "Error de Inicio de Sesión", JOptionPane.ERROR_MESSAGE);
                }
    }//GEN-LAST:event_btnIngresarActionPerformed

    private void txtCorreoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCorreoMousePressed
        if(txtCorreo.getText().equals("Ingrese su nombre de usuario")){
           txtCorreo.setText("");
           txtCorreo.setForeground(Color.black);
        }
        if(String.valueOf(txtContra.getPassword()).isEmpty()){
           txtContra.setText("*********");
           txtContra.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtCorreoMousePressed

    private void txtCorreoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCorreoFocusLost

    }//GEN-LAST:event_txtCorreoFocusLost


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIngresar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblContra;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblSesion;
    private javax.swing.JPasswordField txtContra;
    private javax.swing.JTextField txtCorreo;
    // End of variables declaration//GEN-END:variables
}
