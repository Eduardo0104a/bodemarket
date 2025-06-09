package view.Compras;

import dao.ProductoDAO;
import dto.Producto;
import dto.Usuario;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Eduardo
 */
public class ComprasView extends javax.swing.JPanel {

    private JTextField txtIdProducto, txtNombreProducto, txtCantidad, txtPrecioCompra, txtTotal;
    private JButton btnAgregar, btnRealizarCompra;
    private JTable tablaDetalle;
    private DefaultTableModel modeloTabla;
    private JComboBox<String> comboProveedor;

    public ComprasView(Usuario usuario) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- Título ---
        JLabel lblTitulo = new JLabel("Compra de Productos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo, BorderLayout.NORTH);

        // --- Panel Superior de Inputs ---
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtIdProducto = new JTextField(10);
        txtNombreProducto = new JTextField(15);
        txtNombreProducto.setEditable(false);

        txtCantidad = new JTextField(6);
        txtPrecioCompra = new JTextField(6);
        txtPrecioCompra.setEditable(false)
                ;
        txtTotal = new JTextField(8);
        txtTotal.setEditable(false);

        btnAgregar = new JButton("Agregar Producto");

        int fila = 0;

        // ID Producto
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelFormulario.add(new JLabel("ID Producto:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtIdProducto, gbc);

        // Nombre Producto
        gbc.gridx = 2;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 3;
        panelFormulario.add(txtNombreProducto, gbc);

        // Cantidad
        fila++;
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelFormulario.add(new JLabel("Cantidad:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtCantidad, gbc);

        // Precio
        gbc.gridx = 2;
        panelFormulario.add(new JLabel("Precio Compra:"), gbc);
        gbc.gridx = 3;
        panelFormulario.add(txtPrecioCompra, gbc);

        // Total
        fila++;
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelFormulario.add(new JLabel("Total:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtTotal, gbc);

        // Botón Agregar
        gbc.gridx = 3;
        panelFormulario.add(btnAgregar, gbc);

        add(panelFormulario, BorderLayout.CENTER);

        // --- Tabla de productos ---
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Cantidad", "Precio Compra", "Total"}, 0);
        tablaDetalle = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaDetalle);
        scrollTabla.setPreferredSize(new Dimension(700, 200));

        // --- Panel inferior: Tabla + Proveedor + Botón ---
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
        panelInferior.add(Box.createVerticalStrut(15));
        panelInferior.add(scrollTabla);

        JPanel panelProveedor = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        comboProveedor = new JComboBox<>();
        panelProveedor.add(new JLabel("Proveedor:"));
        panelProveedor.add(comboProveedor);

        btnRealizarCompra = new JButton("Realizar Compra");
        panelProveedor.add(btnRealizarCompra);

        panelInferior.add(panelProveedor);
        add(panelInferior, BorderLayout.SOUTH);

        // Eventos
        configurarEventos();
    }

    private void configurarEventos() {
        txtIdProducto.addActionListener(e -> autocompletarNombreProducto());
        txtCantidad.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                calcularTotal();
            }
        });
        txtPrecioCompra.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                calcularTotal();
            }
        });

        btnAgregar.addActionListener(e -> agregarProductoATabla());
    }

    private void autocompletarNombreProducto() {
        try {
            int id = Integer.parseInt(txtIdProducto.getText().trim());
            ProductoDAO dao = new ProductoDAO();
            Producto p = dao.obtenerProductoPorId(id); // Asegúrate de tener este método en tu DAO
            if (p != null) {
                txtNombreProducto.setText(p.getNombre());
                txtPrecioCompra.setText(String.valueOf(p.getPrecioCompra())); // aquí llenamos automáticamente
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
        } else {
            JOptionPane.showMessageDialog(this, "Completa todos los campos para agregar un producto.");
        }
    }

    private void limpiarCamposProducto() {
        txtIdProducto.setText("");
        txtNombreProducto.setText("");
        txtCantidad.setText("");
        txtPrecioCompra.setText("");
        txtTotal.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
