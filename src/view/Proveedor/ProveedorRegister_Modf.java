package view.Proveedor;

import dao.ProveedorDAO;
import view.Usuario.*;
import dto.Usuario;
import dao.UsuarioDAO;
import dto.Proveedor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author EduardoPC
 */
public class ProveedorRegister_Modf extends javax.swing.JDialog {

    private Usuario usuario;
    private ProveedorView proveedorView;
    private boolean isModify;
    private int id;

    /**
     * Creates new form UsuarioPrueba
     */
    public ProveedorRegister_Modf(java.awt.Frame parent, Usuario usuario, ProveedorView proveedorView, boolean isModify, int id) {
        super(parent, isModify ? "Modificar Usuario" : "Registro de Usuario", true);
        this.usuario = usuario;
        this.proveedorView = proveedorView;
        this.isModify = isModify;
        this.id = id;
        initComponents();
        if (isModify) {
            loadProveedorData();
        }
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblNombre = new javax.swing.JLabel();
        lblCorreo = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        txtTelefono = new javax.swing.JTextField();
        lblNombre1 = new javax.swing.JLabel();
        lblCorreo1 = new javax.swing.JLabel();
        txtRuc = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setFocusTraversalPolicyProvider(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(233, 164, 157));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblNombre.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        lblNombre.setText("Telefono:");
        jPanel1.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, -1, -1));

        lblCorreo.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        lblCorreo.setText("RUC:");
        jPanel1.add(lblCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 120, -1, -1));
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 140, -1));
        jPanel1.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 50, 170, -1));

        btnGuardar.setBackground(new java.awt.Color(255, 238, 0));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-lazo-marcapáginas-24.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 190, -1, -1));

        btnCancelar.setBackground(new java.awt.Color(255, 238, 0));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-eliminar-24 (1).png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 190, -1, -1));
        jPanel1.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 140, -1));

        lblNombre1.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        lblNombre1.setText("Nombre:");
        jPanel1.add(lblNombre1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, -1, -1));

        lblCorreo1.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        lblCorreo1.setText("Correo Electronico:");
        jPanel1.add(lblCorreo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 50, -1, -1));
        jPanel1.add(txtRuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 120, 170, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String ruc = txtRuc.getText().trim();

        Proveedor nuevoProveedor = new Proveedor(id, nombre, correo, telefono, ruc);
        ProveedorDAO proveedorDAO = new ProveedorDAO();
        int errorCode;

        if ("Admin".equalsIgnoreCase(usuario.getRol())) {
            if (isModify) {
                errorCode = proveedorDAO.modificar(nuevoProveedor);
            } else {
                errorCode = proveedorDAO.insertar(nuevoProveedor);
            }

            if (errorCode == 0) {
                JOptionPane.showMessageDialog(ProveedorRegister_Modf.this,
                        isModify ? "Proveedor modificado exitosamente." : "Proveedor registrado exitosamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                if (proveedorView != null) {
                    proveedorView.refreshProveedor();
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(ProveedorRegister_Modf.this,
                        isModify ? "Error: Proveedor no encontrado." : "Error: El RUC ya está registrado.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(ProveedorRegister_Modf.this,
                    "Falta de permisos: solo los administradores pueden realizar esta acción.",
                    "Permisos insuficientes",
                    JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void loadProveedorData() {
        ProveedorDAO proveedorDAO = new ProveedorDAO();
        Proveedor proveedor = proveedorDAO.obtenerProveedorPorId(id);
        if (usuario != null) {
            txtNombre.setText(proveedor.getNombre());
            txtCorreo.setText(proveedor.getCorreo());
            txtTelefono.setText(proveedor.getTelefono());
            txtRuc.setText(proveedor.getRuc());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblCorreo1;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNombre1;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRuc;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
