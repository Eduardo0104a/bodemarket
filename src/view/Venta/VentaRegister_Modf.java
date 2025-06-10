package view.Venta;

import dao.VentaDAO;
import dto.Venta;
import dto.DetalleVenta;
import dao.ProductoDAO;
import dto.Producto;
import dto.Usuario;
import Utilitarios.SpinnerEditor;
import dao.UsuarioDAO;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.JTableHeader;
import view.Movimiento.MovimientoInventarioView;

public class VentaRegister_Modf extends javax.swing.JDialog {

    private JComboBox<Usuario> cmbVendedor;
    private JSpinner spnFecha;
    private JTextField txtTotal;
    private JTable tblProductosInventario;
    private JTable tblDetalleVenta;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JButton btnLimpiarDetalle;
    private Usuario usuario;
    private VentaView ventaView;
    private boolean isModify;
    private int id;
    private MovimientoInventarioView movimientoInventarioView;

    public VentaRegister_Modf(Frame parent, Usuario usuario, VentaView ventaView, boolean isModify, int id) {
        super(parent, isModify ? "Modificar Venta" : "Registro de Venta", true);
        this.movimientoInventarioView = movimientoInventarioView;
        this.usuario = usuario;
        this.isModify = isModify;
        this.id = id;
        this.ventaView = ventaView;

        setBackground(new Color(175, 18, 128));
        initComponents();
        inicio(parent);

        configureVendedores();
        configureProductosInventario();
    }

    private void inicio(Frame parent) {
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Font fuentePrincipal = new Font("Segoe UI", Font.PLAIN, 14);
        Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 16);
        Color colorFondo = Color.WHITE;
        Color colorPrincipal = new Color(175, 18, 128);
        Color colorAcento = new Color(255, 238, 0);

        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setBackground(colorFondo);
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblVendedor = new JLabel("Vendedor:");
        lblVendedor.setFont(fuentePrincipal);
        cmbVendedor = new JComboBox<>();
        cmbVendedor.setFont(fuentePrincipal);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        panelCampos.add(lblVendedor, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panelCampos.add(cmbVendedor, gbc);

        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setFont(fuentePrincipal);
        spnFecha = new JSpinner(new SpinnerDateModel());
        spnFecha.setEditor(new JSpinner.DateEditor(spnFecha, "yyyy-MM-dd HH:mm:ss"));
        spnFecha.setFont(fuentePrincipal);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        panelCampos.add(lblFecha, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panelCampos.add(spnFecha, gbc);

        JLabel lblTotal = new JLabel("Total:");
        lblTotal.setFont(fuentePrincipal);
        txtTotal = new JTextField("0.00");
        txtTotal.setFont(fuentePrincipal);
        txtTotal.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.2;
        panelCampos.add(lblTotal, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panelCampos.add(txtTotal, gbc);

        JPanel panelDatosVenta = new JPanel(new BorderLayout());
        panelDatosVenta.setBackground(colorFondo);
        JLabel lblDatosVenta = new JLabel("Datos de Venta");
        lblDatosVenta.setFont(fuenteTitulo);
        lblDatosVenta.setForeground(colorPrincipal);
        lblDatosVenta.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelDatosVenta.add(lblDatosVenta, BorderLayout.NORTH);
        panelDatosVenta.add(panelCampos, BorderLayout.CENTER);

        JPanel panelProductos = new JPanel(new BorderLayout());
        panelProductos.setBackground(colorFondo);
        panelProductos.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        JLabel lblProductos = new JLabel("Productos de Inventario");
        lblProductos.setFont(fuenteTitulo);
        lblProductos.setForeground(colorPrincipal);
        panelProductos.add(lblProductos, BorderLayout.NORTH);

        tblProductosInventario = new JTable(new DefaultTableModel(new Object[]{"ID", "Producto", "Stock", "Precio", "Cantidad"}, 0)) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 4 ? Integer.class : Object.class;
            }
        };
        tblProductosInventario.setFont(fuentePrincipal);
        tblProductosInventario.setRowHeight(28);
        tblProductosInventario.setSelectionBackground(new Color(230, 200, 255));
        tblProductosInventario.setSelectionForeground(Color.BLACK);
        tblProductosInventario.setGridColor(Color.LIGHT_GRAY);

        JTableHeader headerInv = tblProductosInventario.getTableHeader();
        headerInv.setFont(fuentePrincipal.deriveFont(Font.BOLD));
        headerInv.setBackground(colorPrincipal);
        headerInv.setForeground(Color.WHITE);

        tblProductosInventario.getColumnModel().getColumn(4).setCellEditor(new SpinnerEditor());

        JScrollPane scrollInv = new JScrollPane(tblProductosInventario);
        panelProductos.add(scrollInv, BorderLayout.CENTER);

        // Panel Detalle Venta
        JPanel panelDetalleVenta = new JPanel(new BorderLayout());
        panelDetalleVenta.setBackground(colorFondo);
        panelDetalleVenta.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblDetalleVenta = new JLabel("Detalle de Venta");
        lblDetalleVenta.setFont(fuenteTitulo);
        lblDetalleVenta.setForeground(colorPrincipal);

        btnLimpiarDetalle = new JButton("Limpiar");

        JPanel topDetalle = new JPanel(new BorderLayout());
        topDetalle.setOpaque(false);
        topDetalle.add(lblDetalleVenta, BorderLayout.WEST);
        topDetalle.add(btnLimpiarDetalle, BorderLayout.EAST);

        tblDetalleVenta = new JTable(new DefaultTableModel(new Object[]{"ID", "Producto", "Cantidad", "P. Unitario", "Subtotal"}, 0));
        tblDetalleVenta.setFont(fuentePrincipal);
        tblDetalleVenta.setRowHeight(28);
        tblDetalleVenta.setSelectionBackground(new Color(230, 200, 255));
        tblDetalleVenta.setSelectionForeground(Color.BLACK);

        JTableHeader headerDet = tblDetalleVenta.getTableHeader();
        headerDet.setFont(fuentePrincipal.deriveFont(Font.BOLD));
        headerDet.setBackground(colorPrincipal);
        headerDet.setForeground(Color.WHITE);

        JScrollPane scrollDet = new JScrollPane(tblDetalleVenta);

        panelDetalleVenta.add(topDetalle, BorderLayout.NORTH);
        panelDetalleVenta.add(scrollDet, BorderLayout.CENTER);

        // Split Panes
        JSplitPane splitVertical = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelDatosVenta, panelProductos);
        splitVertical.setDividerLocation(220);

        JSplitPane splitHorizontal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitVertical, panelDetalleVenta);
        splitHorizontal.setDividerLocation(420);

        // Panel Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(colorFondo);

        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        Color colorF = new Color(255, 238, 0);
        Color colorTexto = new Color(175, 18, 128);
        Color colorHover = new Color(255, 215, 0);

        for (JButton btn : new JButton[]{btnGuardar, btnCancelar, btnLimpiarDetalle}) {
            btn.setBackground(colorF);
            btn.setForeground(colorTexto);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(true);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(colorHover);
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(colorF);
                }
            });
        }
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        // Añadir todo al frame
        getContentPane().add(splitHorizontal, BorderLayout.CENTER);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);

        // Listeners
        tblProductosInventario.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = tblProductosInventario.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        int id_prod = (int) tblProductosInventario.getValueAt(row, 0);
                        String producto = tblProductosInventario.getValueAt(row, 1).toString();
                        int stock = (int) tblProductosInventario.getValueAt(row, 2);
                        int cantidad = (int) tblProductosInventario.getValueAt(row, 4);
                        double precio = (double) tblProductosInventario.getValueAt(row, 3);

                        if (cantidad > stock) {
                            JOptionPane.showMessageDialog(null, "Stock insuficiente para el producto: " + producto);
                            return;
                        }

                        DefaultTableModel detalleModel = (DefaultTableModel) tblDetalleVenta.getModel();
                        int fila = -1;
                        for (int i = 0; i < detalleModel.getRowCount(); i++) {
                            if ((int) detalleModel.getValueAt(i, 0) == id_prod) {
                                fila = i;
                                break;
                            }
                        }

                        if (fila >= 0) {
                            int cantidadActual = (int) detalleModel.getValueAt(fila, 2);
                            int nuevaCantidad = cantidadActual + cantidad;
                            if (nuevaCantidad > stock) {
                                JOptionPane.showMessageDialog(null, "Stock insuficiente para el producto: " + producto);
                            } else {
                                detalleModel.setValueAt(nuevaCantidad, fila, 2);
                                detalleModel.setValueAt(nuevaCantidad * precio, fila, 4);
                            }
                        } else {
                            detalleModel.addRow(new Object[]{id_prod, producto, cantidad, precio, cantidad * precio});
                        }

                        double total = 0.0;
                        for (int i = 0; i < detalleModel.getRowCount(); i++) {
                            total += (double) detalleModel.getValueAt(i, 4);
                        }
                        txtTotal.setText(String.format("%.2f", total));
                    }
                }
            }
        });

        btnLimpiarDetalle.addActionListener(e -> {
            DefaultTableModel detalleModel = (DefaultTableModel) tblDetalleVenta.getModel();
            detalleModel.setRowCount(0);
            txtTotal.setText("0.00");
        });

        btnGuardar.addActionListener(e -> guardarVenta());

        btnCancelar.addActionListener(e -> dispose());
    }

    private void configureVendedores() {
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            List<Usuario> usuarios = usuarioDAO.listar();
            for (Usuario usuario : usuarios) {
                cmbVendedor.addItem(usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configureProductosInventario() {
        try {
            ProductoDAO productoDAO = new ProductoDAO();
            List<Producto> productosInventario = productoDAO.listar();
            DefaultTableModel model = (DefaultTableModel) tblProductosInventario.getModel();
            for (Producto producto : productosInventario) {
                model.addRow(new Object[]{
                    producto.getIdProducto(),
                    producto.getNombre(),
                    producto.getStock(),
                    producto.getPrecio(),
                    1
                });
            }

            TableColumnModel columnModel = tblProductosInventario.getColumnModel();
            TableColumn cantidadColumn = columnModel.getColumn(4);

            cantidadColumn.setCellEditor(new SpinnerEditor());

            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(JLabel.RIGHT);
            cantidadColumn.setCellRenderer(renderer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void guardarVenta() {
        try {
            DefaultTableModel detalleModel = (DefaultTableModel) tblDetalleVenta.getModel();
            if (detalleModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No hay productos en el detalle de la venta.");
                return;
            }

            Venta venta = new Venta();
            venta.setIdUsuario(((Usuario) cmbVendedor.getSelectedItem()).getIdUsuario());
            venta.setFecha((java.util.Date) spnFecha.getValue());
            venta.setTotal(Double.parseDouble(txtTotal.getText()));

            List<DetalleVenta> detallesVenta = new ArrayList<>();
            for (int i = 0; i < detalleModel.getRowCount(); i++) {
                DetalleVenta detalle = new DetalleVenta();
                detalle.setIdProducto((int) detalleModel.getValueAt(i, 0));
                detalle.setCantidad((int) detalleModel.getValueAt(i, 2));
                detalle.setPrecioUnitario((double) detalleModel.getValueAt(i, 3));
                detalle.setSubTotal((double) detalleModel.getValueAt(i, 4));
                detallesVenta.add(detalle);
            }

            VentaDAO ventaDAO = new VentaDAO();
            int resultado = ventaDAO.insertarVenta(venta, detallesVenta);

            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Venta registrada correctamente.");

                if (ventaView != null) {
                    ventaView.refreshVentas();
                }
                if (movimientoInventarioView != null) {
                    movimientoInventarioView.refresh();
                }

                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar la venta.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
