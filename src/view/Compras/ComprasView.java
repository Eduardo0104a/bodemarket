package view.Compras;

import dao.CompraDAO;
import dao.DetalleCompraDAO;
import dao.ProductoDAO;
import dao.ProveedorDAO;
import dto.Compra;
import dto.DetalleCompra;
import dto.Producto;
import dto.Proveedor;
import dto.Usuario;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author EduardoPC
 */
public class ComprasView extends javax.swing.JPanel {

    private Usuario usuario;
    private ComprasView compraVista;
    private int id;
    private DefaultTableModel modeloTabla;

    public ComprasView(Usuario usuario) {
        initComponents();
        iniciarModeloTabla();
        inicio();
        configurarEventos();
        cargarProveedoresComboBox();
    }

    private void inicio() {
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
            Color labelColor = Color.BLACK;

        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(120, 30, 20));

        lblId.setFont(labelFont);
        lblId.setForeground(labelColor);

        lblNombre.setFont(labelFont);
        lblNombre.setForeground(labelColor);

        lblPrecio.setFont(labelFont);
        lblPrecio.setForeground(labelColor);

        lblCantidad.setFont(labelFont);
        lblCantidad.setForeground(labelColor);

        lblTotalM.setFont(labelFont);
        lblTotalM.setForeground(labelColor);

        lblProveedor.setFont(labelFont);
        lblProveedor.setForeground(labelColor);

        lblTotalFinal.setFont(labelFont);
        lblTotalFinal.setForeground(labelColor);

        Font textFont = new Font("Segoe UI", Font.PLAIN, 14);
        Border textBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(188, 47, 33), 2),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)
        );

        txtIdProducto.setFont(textFont);
        txtIdProducto.setBorder(textBorder);

        txtNombreProducto.setFont(textFont);
        txtNombreProducto.setBorder(textBorder);

        txtCantidad.setFont(textFont);
        txtCantidad.setBorder(textBorder);

        txtPrecioCompra.setFont(textFont);
        txtPrecioCompra.setBorder(textBorder);

        txtTotal.setFont(textFont);
        txtTotal.setBorder(textBorder);

        txtTotalCompra.setFont(textFont);
        txtTotalCompra.setBorder(textBorder);

        Color fondoNoEditable = new Color(255, 233, 210);
        txtNombreProducto.setEditable(false);
        txtNombreProducto.setBackground(fondoNoEditable);
        txtPrecioCompra.setEditable(false);
        txtPrecioCompra.setBackground(fondoNoEditable);
        txtTotal.setEditable(false);
        txtTotal.setBackground(fondoNoEditable);

        txtTotalCompra.setEditable(false);
        txtTotalCompra.setBackground(fondoNoEditable);

        comboProveedor.setFont(textFont);
        comboProveedor.setBackground(Color.WHITE);
        comboProveedor.setBorder(BorderFactory.createLineBorder(new Color(188, 47, 33), 2));

        btnAgregar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAgregar.setBackground(new Color(188, 47, 33));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFocusPainted(false);
        btnAgregar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnAgregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton(btnAgregar);

        btnRealizarCompra.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRealizarCompra.setBackground(new Color(188, 47, 33));
        btnRealizarCompra.setForeground(Color.WHITE);
        btnRealizarCompra.setFocusPainted(false);
        btnRealizarCompra.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnRealizarCompra.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton(btnRealizarCompra);

        tablaDetalle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaDetalle.setRowHeight(32);
        tablaDetalle.setForeground(Color.BLACK);
        tablaDetalle.setBackground(new Color(233, 164, 157));
        tablaDetalle.setSelectionBackground(new Color(255, 153, 153));
        tablaDetalle.setSelectionForeground(Color.BLACK);
        tablaDetalle.setGridColor(Color.LIGHT_GRAY);

        JTableHeader header = tablaDetalle.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(120, 30, 20));
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createLineBorder(header.getBackground()));

        JScrollPane scroll = (JScrollPane) tablaDetalle.getParent().getParent();
        if (scroll != null) {
            scroll.setBorder(BorderFactory.createEmptyBorder());
            scroll.getViewport().setBackground(Color.WHITE);
        }
    }

    private void iniciarModeloTabla() {
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloTabla.setColumnIdentifiers(new Object[]{"ID", "Nombre", "Cantidad", "Precio Compra", "SubTotal"});
        tablaDetalle.setModel(modeloTabla);
    }

    private void boton(JButton boton) {
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

    private void configurarEventos() {
        txtIdProducto.addActionListener(e -> autocompletarNombreProducto());

        txtCantidad.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calcularTotal();
            }
        });

        txtPrecioCompra.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calcularTotal();
            }
        });

        btnAgregar.addActionListener(e -> agregarProductoATabla());

        btnRealizarCompra.addActionListener(e -> realizarCompra());

        tablaDetalle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = tablaDetalle.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        tablaDetalle.setRowSelectionInterval(row, row);
                        int confirm = JOptionPane.showConfirmDialog(ComprasView.this,
                                "¿Deseas eliminar este producto?",
                                "Confirmar eliminación",
                                JOptionPane.YES_NO_OPTION
                        );
                        if (confirm == JOptionPane.YES_OPTION) {
                            modeloTabla.removeRow(row);
                            actualizarTotalCompra();
                        }
                    }
                }
            }
        });
    }

    private void autocompletarNombreProducto() {
        try {
            int id = Integer.parseInt(txtIdProducto.getText().trim());
            ProductoDAO dao = new ProductoDAO();
            Producto p = dao.obtenerProductoPorId(id);
            if (p != null) {
                txtNombreProducto.setText(p.getNombre());
                txtPrecioCompra.setText(String.valueOf(p.getPrecioCompra()));
                calcularTotal();
            } else {
                txtNombreProducto.setText("");
                txtPrecioCompra.setText("");
                txtTotal.setText("");
            }
        } catch (NumberFormatException e) {
            txtNombreProducto.setText("");
            txtPrecioCompra.setText("");
            txtTotal.setText("");
        }
    }

    private void calcularTotal() {
        try {
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            double precio = Double.parseDouble(txtPrecioCompra.getText().trim());
            txtTotal.setText(String.format("%.2f", cantidad * precio));
        } catch (NumberFormatException e) {
            txtTotal.setText("");
        }
    }

    private void agregarProductoATabla() {
        String id = txtIdProducto.getText().trim();
        String nombre = txtNombreProducto.getText().trim();
        String cantidad = txtCantidad.getText().trim();
        String precio = txtPrecioCompra.getText().trim();
        String total = txtTotal.getText().trim();

        if (!id.isEmpty() && !nombre.isEmpty() && !cantidad.isEmpty() && !precio.isEmpty() && !total.isEmpty()) {
            modeloTabla.addRow(new Object[]{id, nombre, cantidad, precio, total});
            limpiarCamposProducto();
            actualizarTotalCompra();
        } else {
            JOptionPane.showMessageDialog(this, "Completa todos los campos para agregar un producto.");
        }
    }

    private void actualizarTotalCompra() {
        double total = 0.0;
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            try {
                double valor = Double.parseDouble(modeloTabla.getValueAt(i, 4).toString());
                total += valor;
            } catch (NumberFormatException e) {
            }
        }
        txtTotalCompra.setText(String.format("%.2f", total));
    }

    private void limpiarCamposProducto() {
        txtIdProducto.setText("");
        txtNombreProducto.setText("");
        txtCantidad.setText("");
        txtPrecioCompra.setText("");
        txtTotal.setText("");
    }

    private void cargarProveedoresComboBox() {
        ProveedorDAO proveedorDAO = new ProveedorDAO();
        List<Proveedor> proveedores = proveedorDAO.listar();

        comboProveedor.removeAllItems();

        for (Proveedor proveedor : proveedores) {
            comboProveedor.addItem(proveedor);
        }
    }

    private void realizarCompra() {
        if (modeloTabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No se puede registrar una compra sin productos.");
            return;
        }

        Proveedor proveedorSeleccionado = (Proveedor) comboProveedor.getSelectedItem();
        int idProveedor = proveedorSeleccionado.getIdProveedor();

        Date fecha = new Date();
        double total = Double.parseDouble(txtTotalCompra.getText().trim());

        Compra compra = new Compra();
        compra.setIdProveedor(idProveedor);
        compra.setFecha(new java.sql.Date(fecha.getTime()));
        compra.setTotal(total);

        CompraDAO compraDAO = new CompraDAO();
        int errorCompra = compraDAO.insertar(compra);

        if (errorCompra == 0) {
            int idCompra = compra.getIdCompra();

            DetalleCompraDAO detalleDAO = new DetalleCompraDAO();
            boolean errorDetalle = false;

            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                int idProducto = Integer.parseInt(modeloTabla.getValueAt(i, 0).toString());
                int cantidad = Integer.parseInt(modeloTabla.getValueAt(i, 2).toString());
                double precio = Double.parseDouble(modeloTabla.getValueAt(i, 3).toString());

                DetalleCompra detalle = new DetalleCompra();
                detalle.setIdCompra(idCompra);
                detalle.setIdProducto(idProducto);
                detalle.setCantidad(cantidad);
                detalle.setPrecioCompra(precio);

                int errorDetalleCompra = detalleDAO.insertar(detalle);
                if (errorDetalleCompra != 0) {
                    errorDetalle = true;
                }
            }

            if (!errorDetalle) {
                JOptionPane.showMessageDialog(this, "Compra registrada correctamente.");
                modeloTabla.setRowCount(0);
                txtTotalCompra.setText("0.00");
            } else {
                JOptionPane.showMessageDialog(this, "Hubo un error al registrar algunos detalles de compra.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar la compra.");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblPrecio = new javax.swing.JLabel();
        lblCantidad = new javax.swing.JLabel();
        lblTotalM = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        txtPrecioCompra = new javax.swing.JTextField();
        txtNombreProducto = new javax.swing.JTextField();
        txtIdProducto = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDetalle = new javax.swing.JTable();
        lblProveedor = new javax.swing.JLabel();
        comboProveedor = new javax.swing.JComboBox<>();
        lblTotalFinal = new javax.swing.JLabel();
        txtTotalCompra = new javax.swing.JTextField();
        btnRealizarCompra = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo.setText("COMPRA DE PRODUCTOS");
        add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, -1));

        lblId.setText("ID Producto");
        add(lblId, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, -1, -1));

        lblNombre.setText("Nombre Producto:");
        add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, -1, -1));

        lblPrecio.setText("Precio Compra:");
        add(lblPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, -1, -1));

        lblCantidad.setText("Cantidad:");
        add(lblCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 270, -1, -1));

        lblTotalM.setText("Total:");
        add(lblTotalM, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 320, -1, -1));
        add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 320, 130, -1));
        add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 270, 130, -1));
        add(txtPrecioCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, 130, -1));
        add(txtNombreProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 170, 130, -1));
        add(txtIdProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 120, 130, -1));

        btnAgregar.setText("AGREGAR PRODUCTO");
        add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 400, 270, 40));

        tablaDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Cantidad", "Precio Compra", "SubTotal"
            }
        ));
        jScrollPane1.setViewportView(tablaDetalle);
        if (tablaDetalle.getColumnModel().getColumnCount() > 0) {
            tablaDetalle.getColumnModel().getColumn(0).setHeaderValue("ID");
            tablaDetalle.getColumnModel().getColumn(1).setHeaderValue("Nombre");
            tablaDetalle.getColumnModel().getColumn(2).setHeaderValue("Cantidad");
            tablaDetalle.getColumnModel().getColumn(3).setHeaderValue("Precio Compra");
            tablaDetalle.getColumnModel().getColumn(4).setHeaderValue("SubTotal");
        }

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 40, 720, 390));

        lblProveedor.setText("Proveedor:");
        add(lblProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 460, -1, -1));

        add(comboProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 452, 200, 30));

        lblTotalFinal.setText("Total de la compra:");
        add(lblTotalFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 510, -1, -1));
        add(txtTotalCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 502, 100, 30));

        btnRealizarCompra.setText("Realizar Compra");
        add(btnRealizarCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 460, 250, 50));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnRealizarCompra;
    public javax.swing.JComboBox<Object> comboProveedor;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblProveedor;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotalFinal;
    private javax.swing.JLabel lblTotalM;
    private javax.swing.JTable tablaDetalle;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtIdProducto;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtPrecioCompra;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTotalCompra;
    // End of variables declaration//GEN-END:variables
}
