package view.Producto;

import dao.CategoriaDAO;
import dto.Producto;
import dao.ProductoDAO;
import dto.Categoria;
import dto.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author user
 */
public class ProductoInventarioRegister_Mod extends javax.swing.JDialog {

    private Usuario usuario;
    private ProductoInventarioView productoInventarioView;
    private boolean isModify;
    private int idProducto;

    /**
     * Creates new form ProductoPrueba
     */
    public ProductoInventarioRegister_Mod(java.awt.Frame parent, Usuario usuario, ProductoInventarioView productoInventarioView, boolean isModify, int idProducto) {
        super(parent, isModify ? "Modificar Producto Inventario" : "Registro de Producto Inventario", true);
        this.usuario = usuario;
        this.productoInventarioView = productoInventarioView;
        this.isModify = isModify;
        this.idProducto = idProducto;
        initComponents();
        addNumberInputValidation();
        cargarCategoriasComboBox();
        if (isModify) {
            loadProductData();
        }
        setLocationRelativeTo(null);
    }

    private void addNumberInputValidation() {
        KeyAdapter numberInputValidation = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                JTextField textField = (JTextField) e.getSource();
                String text = textField.getText();

                if (!Character.isDigit(c) && c != '.') {
                    e.consume();
                } else if (c == '.' && text.contains(".")) {
                    e.consume();
                }
            }
        };

        txtPrecio.addKeyListener(numberInputValidation);
    }

    private void cargarCategoriasComboBox() {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        java.util.List<Categoria> categorias = categoriaDAO.listar();

        cmbCat.removeAllItems();

        for (Categoria categoria : categorias) {
            cmbCat.addItem(categoria);
        }
    }

    private void loadProductData() {
        ProductoDAO productoDAO = new ProductoDAO();
        Producto producto = productoDAO.obtenerProductoPorId(idProducto);
        if (producto != null) {
            txtNombre.setText(producto.getNombre());
            txtDescripcion.setText(producto.getDescripcion());
            txtPrecio.setText(String.valueOf(producto.getPrecio()));

            // Seleccionar la categoría correspondiente en el JComboBox
            int idCategoriaProducto = producto.getIdCategoria();

            for (int i = 0; i < cmbCat.getItemCount(); i++) {
                Categoria categoria = (Categoria) cmbCat.getItemAt(i);
                if (categoria.getIdCat() == idCategoriaProducto) {
                    cmbCat.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblNombre = new javax.swing.JLabel();
        lblDescripcion = new javax.swing.JLabel();
        lblPrecio = new javax.swing.JLabel();
        lblCategoria = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        cmbCat = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(233, 164, 157));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblNombre.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        lblNombre.setText("Nombre:");
        jPanel1.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        lblDescripcion.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        lblDescripcion.setText("Descripción:");
        jPanel1.add(lblDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, -1, -1));

        lblPrecio.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        lblPrecio.setText("Precio:");
        jPanel1.add(lblPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        lblCategoria.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        lblCategoria.setText("Categoria:");
        jPanel1.add(lblCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, -1, -1));
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 140, -1));
        jPanel1.add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, 200, -1));
        jPanel1.add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 140, -1));

        btnGuardar.setBackground(new java.awt.Color(255, 238, 0));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-lazo-marcapáginas-24.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 160, -1, -1));

        btnCancelar.setBackground(new java.awt.Color(255, 238, 0));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-eliminar-24 (1).png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 160, -1, -1));

        jPanel1.add(cmbCat, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 100, 200, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        double precio = Double.parseDouble(txtPrecio.getText().trim());
        Categoria categoriaSeleccionada = (Categoria) cmbCat.getSelectedItem();
        int idCategoria = categoriaSeleccionada.getIdCat();

        ProductoDAO productoDAO = new ProductoDAO();

        try {
            Producto productoOriginal = productoDAO.obtenerProductoPorId(idProducto);

            if (productoOriginal != null) {
                productoOriginal.setNombre(nombre);
                productoOriginal.setDescripcion(descripcion);
                productoOriginal.setPrecio(precio);
                productoOriginal.setIdCategoria(idCategoria);

                int resultado = productoDAO.modificarBasico(productoOriginal);

                if (resultado == 0) {
                    JOptionPane.showMessageDialog(this, "Producto actualizado con éxito.");
                    productoInventarioView.refreshProductosInventario();
                    this.dispose();

                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar el producto.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el producto.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado.");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    public javax.swing.JComboBox<Object> cmbCat;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
