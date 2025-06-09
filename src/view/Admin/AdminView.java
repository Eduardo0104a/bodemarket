package view.Admin;

import dao.ProductoDAO;
import dao.UsuarioDAO;
import dto.Producto;
import dto.Usuario;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import view.Usuario.UsuarioView;

/**
 *
 * @author EduardoPC
 */
public class AdminView extends javax.swing.JPanel {

    private Usuario usuario;
    private boolean isModify;
    private UsuarioView usuarioView;
    private int id;
    private AdminView adminVista;

    public AdminView(Usuario usuario) {
        this.usuario = usuario;
        initComponents();
        loadUsuarios();
        configureRoles();
        estilosUsuario();
        estilosProducto();
        loadProductosInventario();
        limpiarCamposProducto();
    }

    private void estilosUsuario() {

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Color labelColor = new Color(120, 30, 20);

        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(labelColor);

        lblNombre.setFont(labelFont);
        lblNombre.setForeground(labelColor);

        lblApellido.setFont(labelFont);
        lblApellido.setForeground(labelColor);

        lblCorreo.setFont(labelFont);
        lblCorreo.setForeground(labelColor);

        lblTelefono.setFont(labelFont);
        lblTelefono.setForeground(labelColor);

        lblUsuario.setFont(labelFont);
        lblUsuario.setForeground(labelColor);

        lblContrasena.setFont(labelFont);
        lblContrasena.setForeground(labelColor);

        lblRol.setFont(labelFont);
        lblRol.setForeground(labelColor);

        Font textFont = new Font("Segoe UI", Font.PLAIN, 14);
        Border textBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(188, 47, 33), 2),
                BorderFactory.createEmptyBorder(1, 20, 1, 20)
        );

        txtNombre.setFont(textFont);
        txtNombre.setBorder(textBorder);

        txtApellido.setFont(textFont);
        txtApellido.setBorder(textBorder);

        txtCorreo.setFont(textFont);
        txtCorreo.setBorder(textBorder);

        txtTelefono.setFont(textFont);
        txtTelefono.setBorder(textBorder);

        txtUsuario.setFont(textFont);
        txtUsuario.setBorder(textBorder);

        txtContrasena.setFont(textFont);
        txtContrasena.setBorder(textBorder);

        cmbRol.setFont(textFont);
        cmbRol.setBackground(Color.WHITE);
        cmbRol.setForeground(labelColor);
        cmbRol.setBorder(BorderFactory.createLineBorder(new Color(188, 47, 33), 2));

        btnGuardarUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGuardarUsuario.setBackground(new Color(188, 47, 33));
        btnGuardarUsuario.setForeground(Color.WHITE);
        btnGuardarUsuario.setFocusPainted(false);
        btnGuardarUsuario.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnGuardarUsuario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton(btnGuardarUsuario);
        
        btnLimpiarUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLimpiarUsuario.setBackground(new Color(188, 47, 33));
        btnLimpiarUsuario.setForeground(Color.WHITE);
        btnLimpiarUsuario.setFocusPainted(false);
        btnLimpiarUsuario.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnLimpiarUsuario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton(btnLimpiarUsuario);

        
        tblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblUsuario.setRowHeight(32);
        tblUsuario.setForeground(Color.BLACK);
        tblUsuario.setBackground(new Color(233, 164, 157));
        tblUsuario.setSelectionBackground(new Color(255, 153, 153));
        tblUsuario.setSelectionForeground(Color.BLACK);
        tblUsuario.setGridColor(Color.LIGHT_GRAY);

        JTableHeader header = tblUsuario.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(120, 30, 20));
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createLineBorder(header.getBackground()));

        JScrollPane scroll = (JScrollPane) tblUsuario.getParent().getParent();
        if (scroll != null) {
            scroll.setBorder(BorderFactory.createEmptyBorder());
            scroll.getViewport().setBackground(Color.WHITE);
        }

        tbPanel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tbPanel.setBackground(new Color(233, 164, 157)); 
        tbPanel.setForeground(new Color(120, 30, 20));
        tbPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tbPanel.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected void installDefaults() {
                super.installDefaults();
                selectedTabPadInsets = new Insets(5, 5, 5, 5);
                tabInsets = new Insets(5, 10, 5, 10);
                contentBorderInsets = new Insets(0, 0, 0, 0);
            }
        });
        
    }

    private void estilosProducto() {
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Color labelColor = new Color(120, 30, 20);

        lblTituloProducto.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTituloProducto.setForeground(labelColor);

        lblProducto.setFont(labelFont);
        lblProducto.setForeground(labelColor);

        lblDescripcion.setFont(labelFont);
        lblDescripcion.setForeground(labelColor);

        lblPrecio.setFont(labelFont);
        lblPrecio.setForeground(labelColor);

        lblCategoria.setFont(labelFont);
        lblCategoria.setForeground(labelColor);
        
        lblProveedor.setFont(labelFont);
        lblProveedor.setForeground(labelColor);
        
        lblMedida.setFont(labelFont);
        lblMedida.setForeground(labelColor);

        Font textFont = new Font("Segoe UI", Font.PLAIN, 14);
        Border textBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(188, 47, 33), 2),
                BorderFactory.createEmptyBorder(1, 20, 1, 20)
        );

        txtProducto.setFont(textFont);
        txtProducto.setBorder(textBorder);

        txtDescripcion.setFont(textFont);
        txtDescripcion.setBorder(textBorder);

        txtPrecio.setFont(textFont);
        txtPrecio.setBorder(textBorder);

        btnGuardarProducto.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGuardarProducto.setBackground(new Color(188, 47, 33));
        btnGuardarProducto.setForeground(Color.WHITE);
        btnGuardarProducto.setFocusPainted(false);
        btnGuardarProducto.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnGuardarProducto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton(btnGuardarProducto);

        
        btnLimpiarProducto.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLimpiarProducto.setBackground(new Color(188, 47, 33));
        btnLimpiarProducto.setForeground(Color.WHITE);
        btnLimpiarProducto.setFocusPainted(false);
        btnLimpiarProducto.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnLimpiarProducto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton(btnLimpiarProducto);        
        
        tblProducto.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblProducto.setRowHeight(32);
        tblProducto.setForeground(Color.BLACK);
        tblProducto.setBackground(new Color(233, 164, 157));
        tblProducto.setSelectionBackground(new Color(255, 153, 153));
        tblProducto.setSelectionForeground(Color.BLACK);
        tblProducto.setGridColor(Color.LIGHT_GRAY);

        JTableHeader header = tblProducto.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(120, 30, 20));
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createLineBorder(header.getBackground()));

        JScrollPane scroll = (JScrollPane) tblProducto.getParent().getParent();
        if (scroll != null) {
            scroll.setBorder(BorderFactory.createEmptyBorder());
            scroll.getViewport().setBackground(Color.WHITE);
        }
    }
    
    private void boton (JButton boton){
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(160, 0, 0));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(188, 47, 33));
            }
        });
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tbPanel = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        lblApellido = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        lblContrasena = new javax.swing.JLabel();
        txtContrasena = new javax.swing.JPasswordField();
        lblRol = new javax.swing.JLabel();
        cmbRol = new javax.swing.JComboBox<>();
        btnGuardarUsuario = new javax.swing.JButton();
        btnLimpiarUsuario = new javax.swing.JButton();
        lblCorreo = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuario = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        lblProducto = new javax.swing.JLabel();
        lblDescripcion = new javax.swing.JLabel();
        lblCategoria = new javax.swing.JLabel();
        txtProducto = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        btnGuardarProducto = new javax.swing.JButton();
        btnLimpiarProducto = new javax.swing.JButton();
        lblPrecio = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        lblTituloProducto = new javax.swing.JLabel();
        lblMedida = new javax.swing.JLabel();
        lblProveedor = new javax.swing.JLabel();
        cmbCategoria = new javax.swing.JComboBox<>();
        cmbProveedor = new javax.swing.JComboBox<>();
        cmbMedida = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProducto = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1140, 500));

        tbPanel.setBackground(new java.awt.Color(255, 255, 255));
        tbPanel.setPreferredSize(new java.awt.Dimension(1140, 500));
        tbPanel.setRequestFocusEnabled(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo.setText("REGISTRO USUARIO");
        jPanel3.add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));
        jPanel3.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 240, 130, -1));

        lblApellido.setText("Apellido:");
        jPanel3.add(lblApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, -1, -1));

        lblTelefono.setText("Telefono:");
        jPanel3.add(lblTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 200, -1, -1));

        lblUsuario.setText("Usuario: ");
        jPanel3.add(lblUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, -1, -1));
        jPanel3.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 130, -1));
        jPanel3.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 120, 130, -1));
        jPanel3.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 130, -1));

        lblContrasena.setText("Contraseña:");
        jPanel3.add(lblContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 280, -1, -1));
        jPanel3.add(txtContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 280, 130, -1));

        lblRol.setText("Rol:");
        jPanel3.add(lblRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 330, -1, -1));

        jPanel3.add(cmbRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 320, 130, -1));

        btnGuardarUsuario.setText("Guardar");
        btnGuardarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarUsuarioActionPerformed(evt);
            }
        });
        jPanel3.add(btnGuardarUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 400, -1, -1));

        btnLimpiarUsuario.setText("Limpiar");
        btnLimpiarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarUsuarioActionPerformed(evt);
            }
        });
        jPanel3.add(btnLimpiarUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 400, -1, -1));

        lblCorreo.setText("Correo:");
        jPanel3.add(lblCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, -1, -1));
        jPanel3.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 160, 130, -1));

        lblNombre.setText("Nombre:");
        jPanel3.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, -1, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 360, 470));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nombre", "Apellido", "Usuario", "Rol"
            }
        ));
        tblUsuario.setGridColor(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(tblUsuario);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 470));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 0, 780, 470));

        tbPanel.addTab("Usuario", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblProducto.setText("Nombre:");
        jPanel6.add(lblProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, -1, -1));

        lblDescripcion.setText("Descripcion:");
        jPanel6.add(lblDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, -1, -1));

        lblCategoria.setText("Categoria:");
        jPanel6.add(lblCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, -1, -1));
        jPanel6.add(txtProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 130, -1));
        jPanel6.add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, 130, -1));

        btnGuardarProducto.setText("Guardar");
        jPanel6.add(btnGuardarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 400, -1, -1));

        btnLimpiarProducto.setText("Limpiar");
        jPanel6.add(btnLimpiarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 400, -1, -1));

        lblPrecio.setText("Precio:");
        jPanel6.add(lblPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, -1, -1));
        jPanel6.add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 180, 130, -1));

        lblTituloProducto.setText("REGISTRO PRODUCTO");
        jPanel6.add(lblTituloProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        lblMedida.setText("Medida:");
        jPanel6.add(lblMedida, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, -1, -1));

        lblProveedor.setText("Proveedor:");
        jPanel6.add(lblProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 270, -1, -1));

        jPanel6.add(cmbCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 230, 130, -1));

        jPanel6.add(cmbProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 270, 130, -1));

        jPanel6.add(cmbMedida, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 310, 130, -1));

        jPanel2.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 360, 470));

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nombre Producto", "Descripcion", "Categoria"
            }
        ));
        tblProducto.setGridColor(new java.awt.Color(255, 255, 255));
        jScrollPane2.setViewportView(tblProducto);

        jPanel7.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 470));

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 0, 780, 470));

        tbPanel.addTab("Producto", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1140, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarUsuarioActionPerformed
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String correo = txtCorreo.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String usuarioName = txtUsuario.getText().trim();
        String contraseña = new String(txtContrasena.getPassword()).trim();
        String rol = (String) cmbRol.getSelectedItem();

        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || telefono.isEmpty()
                || usuarioName.isEmpty() || contraseña.isEmpty() || rol == null || rol.trim().isEmpty()) {

            JOptionPane.showMessageDialog(AdminView.this,
                    "Por favor, completa todos los campos antes de guardar.",
                    "Campos obligatorios",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario nuevoUsuario = new Usuario(id, nombre, apellido, correo, telefono, usuarioName, contraseña, rol);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        int errorCode;

        if ("admin".equalsIgnoreCase(usuario.getRol())) {
            if (isModify) {
                errorCode = usuarioDAO.modificar(nuevoUsuario);
            } else {
                errorCode = usuarioDAO.insertar(nuevoUsuario);
            }

            if (errorCode == 0) {
                JOptionPane.showMessageDialog(AdminView.this,
                        isModify ? "Usuario modificado exitosamente." : "Usuario registrado exitosamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

                refreshUsuarios();
                limpiarCamposUsuario();
            } else {
                JOptionPane.showMessageDialog(AdminView.this,
                        isModify ? "Error: Usuario no encontrado." : "Error: El correo electrónico ya está registrado.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(AdminView.this,
                    "Falta de permisos: solo los administradores pueden realizar esta acción.",
                    "Permisos insuficientes",
                    JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarUsuarioActionPerformed

    private void btnLimpiarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarUsuarioActionPerformed
        limpiarCamposUsuario();
    }//GEN-LAST:event_btnLimpiarUsuarioActionPerformed

    //PRODUCTOS
    private void loadProductosInventario() {
        DefaultTableModel model = (DefaultTableModel) tblProducto.getModel();
        model.setRowCount(0);

        ProductoDAO productoDAO = new ProductoDAO();
        List<Producto> productos = productoDAO.listar();

        for (Producto p : productos) {
            model.addRow(new Object[]{
                p.getNombre(),
                p.getDescripcion(),
                p.getPrecio(),
            });
        }
    }

    public void refreshProductosInventario() {
        DefaultTableModel model = (DefaultTableModel) tblProducto.getModel();
        model.setRowCount(0);

        loadProductosInventario();
    }

    private void limpiarCamposProducto() {
        txtProducto.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");

    }

    //USUARIOS
    private void configureRoles() {
        if ("admin".equalsIgnoreCase(usuario.getRol())) {
            cmbRol.addItem("admin");
            cmbRol.addItem("vendedor");
        } else {
            cmbRol.addItem("vendedor");
        }
    }

    private void loadUsuarios() {
        DefaultTableModel model = (DefaultTableModel) tblUsuario.getModel();
        model.setRowCount(0);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> usuarios = usuarioDAO.listar();

        for (Usuario u : usuarios) {
            model.addRow(new Object[]{
                u.getNombre(),
                u.getApellido(),
                u.getUsuario(),
                u.getRol()
            });
        }
    }

    public void refreshUsuarios() {
        DefaultTableModel model = (DefaultTableModel) tblUsuario.getModel();
        model.setRowCount(0);
        loadUsuarios();
    }

    private void limpiarCamposUsuario() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtUsuario.setText("");
        txtContrasena.setText("");
        cmbRol.setSelectedIndex(0);
    }

    private void a(){
        
    }
            
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardarProducto;
    private javax.swing.JButton btnGuardarUsuario;
    private javax.swing.JButton btnLimpiarProducto;
    private javax.swing.JButton btnLimpiarUsuario;
    private javax.swing.JComboBox<String> cmbCategoria;
    private javax.swing.JComboBox<String> cmbMedida;
    private javax.swing.JComboBox<String> cmbProveedor;
    private javax.swing.JComboBox<String> cmbRol;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JLabel lblContrasena;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblMedida;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblProducto;
    private javax.swing.JLabel lblProveedor;
    private javax.swing.JLabel lblRol;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTituloProducto;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTabbedPane tbPanel;
    private javax.swing.JTable tblProducto;
    private javax.swing.JTable tblUsuario;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JPasswordField txtContrasena;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtProducto;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
