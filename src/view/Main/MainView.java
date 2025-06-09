package view.Main;

import dto.Usuario;
import view.Usuario.UsuarioView;
import view.Login.LoginView;
import view.Producto.ProductoInventarioView;
import view.Movimiento.MovimientoInventarioView;
import view.Venta.VentaView;
import view.Admin.AdminView;

import javax.swing.*;
import java.awt.*;
import view.Admin.AdminView;
import view.Compras.ComprasView;
import view.Proveedor.ProveedorView;

/**
 *
 * @author Eduardo
 */
public class MainView extends javax.swing.JFrame {

    private final Usuario usuario;

    /**
     * Creates new form MainVista
     */
    public MainView(Usuario usuario) {
        this.usuario = usuario;
        initComponents();
        inicio();
        setTitle("Sistema de Ventas");
        setLocationRelativeTo(null);
        showUsuarioView();
    }

    private void inicio() {
        btnUsuarios.addActionListener(e -> {
            showUsuarioView();
        });

        btnVenta.addActionListener(e -> {
            showVentaView();
        });

        btnProductos.addActionListener(e -> {
            showProductoView();
        });

        btnCompras.addActionListener(e -> {
            showComprasView();
        });
        
        btnInventario.addActionListener(e -> {
            showMovimientoView();
        });
        
        btnProveedores.addActionListener(e -> {
            showProveedorView();
        });
        
        btnAdmin.addActionListener(e -> {
            showAdminView();
        });
        
        btnReportes.addActionListener(e -> {
        });
        
        btnSalir.addActionListener(e -> {
            dispose();
            new LoginView().setVisible(true);
        });

        Font fuenteBoton = new Font("Segoe UI", Font.BOLD, 14);
        Color colorPrincipal = new Color(233, 164, 157);
        Color textoBlanco = Color.BLACK;
        Color colorHover = Color.WHITE;

        btnEstilos(btnAdmin, colorPrincipal, colorHover, textoBlanco, fuenteBoton);
        btnEstilos(btnCompras, colorPrincipal, colorHover, textoBlanco, fuenteBoton);
        btnEstilos(btnInventario, colorPrincipal, colorHover, textoBlanco, fuenteBoton);
        btnEstilos(btnProductos, colorPrincipal, colorHover, textoBlanco, fuenteBoton);
        btnEstilos(btnProveedores, colorPrincipal, colorHover, textoBlanco, fuenteBoton);
        btnEstilos(btnReportes, colorPrincipal, colorHover, textoBlanco, fuenteBoton);
        btnEstilos(btnSalir, new Color(120, 30, 20), new Color(160, 0, 0), Color.WHITE, fuenteBoton); // Botón rojo
        btnEstilos(btnUsuarios, colorPrincipal, colorHover, textoBlanco, fuenteBoton);
        btnEstilos(btnVenta, colorPrincipal, colorHover, textoBlanco, fuenteBoton);
    }

    private void showUsuarioView() {
        conPanel.removeAll();
        conPanel.add(new UsuarioView(usuario), BorderLayout.CENTER);
        conPanel.revalidate();
        conPanel.repaint();
    }

    private void showProductoView() {
        conPanel.removeAll();
        conPanel.add(new ProductoInventarioView(usuario), BorderLayout.CENTER);
        conPanel.revalidate();
        conPanel.repaint();
    }

    private void showMovimientoView() {
        conPanel.removeAll();
        conPanel.add(new MovimientoInventarioView(usuario), BorderLayout.CENTER);
        conPanel.revalidate();
        conPanel.repaint();
    }
    
    private void showProveedorView(){
        conPanel.removeAll();
        conPanel.add(new ProveedorView(usuario), BorderLayout.CENTER);
        conPanel.revalidate();
        conPanel.repaint();
    }

    private void showVentaView() {
        conPanel.removeAll();
        conPanel.add(new VentaView(usuario), BorderLayout.CENTER);
        conPanel.revalidate();
        conPanel.repaint();
    }
    
    private void showAdminView(){
        conPanel.removeAll();
        conPanel.add(new AdminView(usuario), BorderLayout.CENTER);
        conPanel.revalidate();
        conPanel.repaint();
    }
    
    private void showComprasView(){
        conPanel.removeAll();
        conPanel.add(new ComprasView(usuario), BorderLayout.CENTER);
        conPanel.revalidate();
        conPanel.repaint();
    }

    private void btnEstilos(JButton boton, Color fondo, Color fondoHover, Color texto, Font fuente) {
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setOpaque(true);
        boton.setBackground(fondo);
        boton.setForeground(texto);
        boton.setFont(fuente);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(fondoHover);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(fondo);
            }
        });
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuPanel = new javax.swing.JPanel();
        conPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnUsuarios = new javax.swing.JButton();
        btnCompras = new javax.swing.JButton();
        btnVenta = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnProveedores = new javax.swing.JButton();
        btnAdmin = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnInventario = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setResizable(false);

        menuPanel.setBackground(new java.awt.Color(255, 255, 255));
        menuPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        conPanel.setBackground(new java.awt.Color(255, 255, 255));
        conPanel.setLayout(new java.awt.BorderLayout());
        menuPanel.add(conPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 1140, 540));

        jPanel1.setBackground(new java.awt.Color(233, 164, 157));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bodemarketlogo (1).png"))); // NOI18N
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 130, 90));

        btnUsuarios.setBackground(new java.awt.Color(233, 164, 157));
        btnUsuarios.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/clientes.png"))); // NOI18N
        btnUsuarios.setText("Usuarios");
        btnUsuarios.setBorder(null);
        btnUsuarios.setFocusable(false);
        btnUsuarios.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUsuarios.setIconTextGap(5);
        btnUsuarios.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosActionPerformed(evt);
            }
        });
        jPanel1.add(btnUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 110, 80));

        btnCompras.setBackground(new java.awt.Color(233, 164, 157));
        btnCompras.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnCompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/agregar-producto (1).png"))); // NOI18N
        btnCompras.setText("Compras");
        btnCompras.setBorder(null);
        btnCompras.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCompras.setIconTextGap(10);
        btnCompras.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(btnCompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 110, 80));

        btnVenta.setBackground(new java.awt.Color(233, 164, 157));
        btnVenta.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/ventas.png"))); // NOI18N
        btnVenta.setText("Venta");
        btnVenta.setBorder(null);
        btnVenta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVenta.setIconTextGap(5);
        btnVenta.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(btnVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, 110, 80));

        btnReportes.setBackground(new java.awt.Color(233, 164, 157));
        btnReportes.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnReportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/generar_reporte.png"))); // NOI18N
        btnReportes.setText("Reportes");
        btnReportes.setBorder(null);
        btnReportes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReportes.setIconTextGap(3);
        btnReportes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(btnReportes, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 20, 120, 80));

        btnSalir.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        btnSalir.setText("Cerrar sesión");
        btnSalir.setBorder(null);
        jPanel1.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 50, 120, 33));

        btnProveedores.setBackground(new java.awt.Color(233, 164, 157));
        btnProveedores.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnProveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/proveedores.png"))); // NOI18N
        btnProveedores.setText("Proveedores");
        btnProveedores.setBorder(null);
        btnProveedores.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProveedores.setIconTextGap(5);
        btnProveedores.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(btnProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 20, 120, 80));

        btnAdmin.setBackground(new java.awt.Color(233, 164, 157));
        btnAdmin.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnAdmin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/administracion.png"))); // NOI18N
        btnAdmin.setText("Administracion");
        btnAdmin.setBorder(null);
        btnAdmin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAdmin.setIconTextGap(5);
        btnAdmin.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(btnAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 20, 120, 80));

        btnProductos.setBackground(new java.awt.Color(233, 164, 157));
        btnProductos.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/articulos.png"))); // NOI18N
        btnProductos.setText("Productos");
        btnProductos.setBorder(null);
        btnProductos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProductos.setIconTextGap(5);
        btnProductos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(btnProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 110, 80));

        btnInventario.setBackground(new java.awt.Color(233, 164, 157));
        btnInventario.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnInventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/proveedores.png"))); // NOI18N
        btnInventario.setText("Mov. Inventario");
        btnInventario.setBorder(null);
        btnInventario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnInventario.setIconTextGap(5);
        btnInventario.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(btnInventario, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 20, 120, 80));

        menuPanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1240, 120));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1225, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(menuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 726, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUsuariosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdmin;
    private javax.swing.JButton btnCompras;
    private javax.swing.JButton btnInventario;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnProveedores;
    private javax.swing.JButton btnReportes;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JButton btnVenta;
    private javax.swing.JPanel conPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel menuPanel;
    // End of variables declaration//GEN-END:variables
}
