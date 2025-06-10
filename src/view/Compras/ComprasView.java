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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Eduardo
 */
public class ComprasView extends javax.swing.JPanel {

    private JTextField txtIdProducto, txtIdProveedor, txtNombreProducto, txtCantidad, txtPrecioCompra, txtTotal, txtTotalCompra;
    private JButton btnAgregar, btnRealizarCompra;
    private JTable tablaDetalle;
    private DefaultTableModel modeloTabla;
    private JComboBox<Object> comboProveedor;
    private Usuario usuario;

    public ComprasView(Usuario usuario) {
        this.usuario = usuario;
        setBackground(new Color(233, 164, 157));
        inicio();
        configurarEventos();
        cargarProveedoresComboBox();
    }

    private void inicio() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("COMPRA DE PRODUCTOS");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(120, 30, 20));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtIdProducto = crearCampoTexto(10);
        txtNombreProducto = crearCampoTexto(15);
        txtNombreProducto.setEditable(false);
        txtCantidad = crearCampoTexto(6);
        txtPrecioCompra = crearCampoTexto(6);
        txtPrecioCompra.setEditable(false);
        txtTotal = crearCampoTexto(8);
        txtTotal.setEditable(false);

        btnAgregar = crearBoton("Agregar Producto");

        int fila = 0;
        gbc.gridy = fila;
        gbc.gridx = 0;
        panelFormulario.add(new JLabel("ID Producto:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtIdProducto, gbc);
        gbc.gridx = 2;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 3;
        panelFormulario.add(txtNombreProducto, gbc);

        fila++;
        gbc.gridy = fila;
        gbc.gridx = 0;
        panelFormulario.add(new JLabel("Cantidad:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtCantidad, gbc);
        gbc.gridx = 2;
        panelFormulario.add(new JLabel("Precio Compra:"), gbc);
        gbc.gridx = 3;
        panelFormulario.add(txtPrecioCompra, gbc);

        fila++;
        gbc.gridy = fila;
        gbc.gridx = 0;
        panelFormulario.add(new JLabel("Total:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtTotal, gbc);
        gbc.gridx = 3;
        panelFormulario.add(btnAgregar, gbc);

        add(panelFormulario, BorderLayout.CENTER);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Cantidad", "Precio Compra", "Total"}, 0);
        tablaDetalle = new JTable(modeloTabla);
        tablaDetalle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaDetalle.setRowHeight(30);
        tablaDetalle.setBackground(Color.WHITE);
        JScrollPane scrollTabla = new JScrollPane(tablaDetalle);
        scrollTabla.setPreferredSize(new Dimension(700, 200));

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
        panelInferior.setOpaque(false);
        panelInferior.add(Box.createVerticalStrut(15));
        panelInferior.add(scrollTabla);

        JPanel panelProveedor = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelProveedor.setOpaque(false);
        comboProveedor = new JComboBox<>();
        comboProveedor.setPreferredSize(new Dimension(200, 30));
        comboProveedor.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelProveedor.add(new JLabel("Proveedor:"));
        panelProveedor.add(comboProveedor);

        btnRealizarCompra = crearBoton("Realizar Compra");
        panelProveedor.add(btnRealizarCompra);
        panelInferior.add(panelProveedor);

        JPanel panelTotalFinal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelTotalFinal.setOpaque(false);
        panelTotalFinal.add(new JLabel("Total de la Compra:"));
        txtTotalCompra = crearCampoTexto(10);
        txtTotalCompra.setEditable(false);
        txtTotalCompra.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtTotalCompra.setHorizontalAlignment(JTextField.RIGHT);
        panelTotalFinal.add(txtTotalCompra);
        panelInferior.add(panelTotalFinal);

        add(panelInferior, BorderLayout.SOUTH);

    }

    private JTextField crearCampoTexto(int cols) {
        JTextField txt = new JTextField(cols);
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(188, 47, 33), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return txt;
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(new Color(175, 18, 128));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        return btn;
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
                        int confirm = JOptionPane.showConfirmDialog(
                                ComprasView.this,
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
        // Validación: no permitir compra sin productos
        if (modeloTabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No se puede registrar una compra sin productos.");
            return;
        }

        Proveedor proveedorSeleccionado = (Proveedor) comboProveedor.getSelectedItem();
        int idProveedor = proveedorSeleccionado.getIdProveedor();

        Date fecha = new Date(); // Fecha actual
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

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
