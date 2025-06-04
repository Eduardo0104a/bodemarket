package view.Venta;

import dao.VendedorDAO;
import dao.VentaDAO;
import dto.VentaDTO;
import dto.DetalleVentaDTO;
import dao.ProductoInventarioDAO;
import dto.Vendedor;
import dto.ProductoInventario;
import dto.Usuario;
import Utilitarios.SpinnerEditor;

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

public class VentaRegister_Modf extends javax.swing.JDialog {

    private JComboBox<Vendedor> cmbVendedor;
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
    
    public VentaRegister_Modf(Frame parent, Usuario usuario, VentaView ventaView, boolean isModify, int id) {
        super(parent, isModify ? "Modificar Venta" : "Registro de Venta", true);
        this.usuario = usuario;
        this.isModify = isModify;
        this.id = id;
        this.ventaView = ventaView;
        setBackground(new Color(175, 18, 128)); 
        initComponents();
        inicio(parent);
        configureVendedores();
        configureProductosInventario();
        if (isModify) {
            loadVentaData();
        }
    }

    private void inicio(Frame parent) {
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setBackground(new Color(175, 18, 128)); // Amarillo suave
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblVendedor = new JLabel("Vendedor:");
        cmbVendedor = new JComboBox<>();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        panelCampos.add(lblVendedor, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panelCampos.add(cmbVendedor, gbc);

        JLabel lblFecha = new JLabel("Fecha:");
        spnFecha = new JSpinner(new SpinnerDateModel());
        spnFecha.setEditor(new JSpinner.DateEditor(spnFecha, "yyyy-MM-dd HH:mm:ss"));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.1;
        panelCampos.add(lblFecha, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panelCampos.add(spnFecha, gbc);

        JLabel lblTotal = new JLabel("Total:");
        txtTotal = new JTextField("0.00", 20);
        txtTotal.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.1;
        panelCampos.add(lblTotal, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panelCampos.add(txtTotal, gbc);

        JPanel panelProductos = new JPanel(new BorderLayout());
        panelProductos.setBackground(new Color(175, 18, 128)); // Amarillo suave
        panelProductos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblProductosInventario = new JLabel("Productos de Inventario");
        lblProductosInventario.setFont(new Font("Arial", Font.BOLD, 16));
        lblProductosInventario.setForeground(new Color(255,238,0)); 
        panelProductos.add(lblProductosInventario, BorderLayout.NORTH);

        tblProductosInventario = new JTable(new DefaultTableModel(new Object[]{"ID_Producto","Producto", "Stock", "Precio", "Cantidad"}, 0)) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4) {
                    return Integer.class;
                }
                return Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        TableColumnModel columnModel = tblProductosInventario.getColumnModel();
        TableColumn cantidadColumn = columnModel.getColumn(4);

        cantidadColumn.setCellEditor(new SpinnerEditor());

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.RIGHT);
        cantidadColumn.setCellRenderer(renderer);

        JScrollPane scrollPaneProductos = new JScrollPane(tblProductosInventario);
        scrollPaneProductos.getViewport().setBackground(new Color(175, 18, 128)); 
        panelProductos.add(scrollPaneProductos, BorderLayout.CENTER);

        JPanel panelDetalleVenta = new JPanel(new BorderLayout());
        panelDetalleVenta.setBackground(new Color(175, 18, 128)); // Amarillo suave
        panelDetalleVenta.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelDetalleVentaTop = new JPanel(new BorderLayout());
        JLabel lblDetalleVenta = new JLabel("Detalle de Venta");
        lblDetalleVenta.setFont(new Font("Arial", Font.BOLD, 16));
        lblDetalleVenta.setForeground(new Color(255,238,0)); 
        panelDetalleVentaTop.setBackground(new Color(175, 18, 128)); 
        panelDetalleVentaTop.add(lblDetalleVenta, BorderLayout.WEST);

        btnLimpiarDetalle = new JButton("Limpiar");
        btnLimpiarDetalle.setBackground(new Color(255,238,0));
        btnLimpiarDetalle.setForeground(new Color(175, 18, 128));
        panelDetalleVentaTop.add(btnLimpiarDetalle, BorderLayout.EAST);

        panelDetalleVenta.add(panelDetalleVentaTop, BorderLayout.NORTH);

        tblDetalleVenta = new JTable(new DefaultTableModel(new Object[]{"ID_Producto","Producto", "Cantidad", "Precio Unitario", "SubTotal"}, 0));
        JScrollPane scrollPaneDetalleVenta = new JScrollPane(tblDetalleVenta);
        scrollPaneDetalleVenta.getViewport().setBackground(new Color(175, 18, 128)); 
        panelDetalleVenta.add(scrollPaneDetalleVenta, BorderLayout.CENTER);

        JPanel panelDatosVenta = new JPanel(new BorderLayout());
        JLabel lblDatosVenta = new JLabel("Datos de Venta");
        lblDatosVenta.setFont(new Font("Arial", Font.BOLD, 16));
        lblDatosVenta.setForeground(new Color(255,238,0)); 
        panelDatosVenta.setBackground(new Color(175, 18, 128)); 
        panelDatosVenta.add(lblDatosVenta, BorderLayout.NORTH);
        panelDatosVenta.add(panelCampos, BorderLayout.CENTER);

        JSplitPane splitPaneVertical = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelDatosVenta, panelProductos);
        splitPaneVertical.setDividerLocation(300);

        JSplitPane splitPaneHorizontal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPaneVertical, panelDetalleVenta);
        splitPaneHorizontal.setDividerLocation(400);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(new Color(175, 18, 128)); 
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        btnGuardar.setBackground(new Color(255,238,0));
        btnGuardar.setForeground(new Color(175, 18, 128));
        btnCancelar.setBackground(new Color(255,238,0));
        btnCancelar.setForeground(new Color(175, 18, 128));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        splitPaneHorizontal.setBackground(new Color(175, 18, 128)); 
        getContentPane().add(splitPaneHorizontal, BorderLayout.CENTER);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
        tblDetalleVenta.setBackground(new Color(220, 190, 255)); 
        tblDetalleVenta.setForeground(new Color(75, 0, 130)); 

        tblDetalleVenta.setSelectionBackground(new Color(180, 130, 255)); 
        tblDetalleVenta.setSelectionForeground(Color.WHITE); 

        tblDetalleVenta.getTableHeader().setBackground(new Color(75, 0, 130)); 
        tblDetalleVenta.getTableHeader().setForeground(new Color(75, 0, 130));

        tblProductosInventario.setBackground(new Color(220, 190, 255)); 
        tblProductosInventario.setForeground(new Color(75, 0, 130)); 

        tblProductosInventario.setSelectionBackground(new Color(180, 130, 255)); 
        tblProductosInventario.setSelectionForeground(Color.WHITE); 

        tblProductosInventario.getTableHeader().setBackground(new Color(75, 0, 130)); 
        tblProductosInventario.getTableHeader().setForeground(new Color(75, 0, 130));
        
        tblProductosInventario.addMouseListener(new MouseAdapter() {
            @Override
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
                        int detalleRow = -1;

                        for (int i = 0; i < detalleModel.getRowCount(); i++) {
                            if (detalleModel.getValueAt(i, 0).equals(id_prod)) {
                                detalleRow = i;
                                break;
                            }
                        }

                        if (detalleRow >= 0) {
                            int cantidadActual = (int) detalleModel.getValueAt(detalleRow, 2);
                            int nuevaCantidad = cantidadActual + cantidad;

                            if (nuevaCantidad > stock) {
                                JOptionPane.showMessageDialog(null, "Stock insuficiente para el producto: " + producto);
                            } else {
                                detalleModel.setValueAt(nuevaCantidad, detalleRow, 2);
                                double nuevoSubtotal = nuevaCantidad * precio;
                                detalleModel.setValueAt(nuevoSubtotal, detalleRow, 4);
                            }
                        } else {
                            double subtotal = precio * cantidad;
                            detalleModel.addRow(new Object[]{id_prod, producto, cantidad, precio, subtotal});
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

        btnLimpiarDetalle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel detalleModel = (DefaultTableModel) tblDetalleVenta.getModel();
                detalleModel.setRowCount(0);
                txtTotal.setText("0.00");
            }
        });
        
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarVenta();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
   
    private void configureVendedores() {
        try {
            VendedorDAO vendedorDAO = new VendedorDAO();
            List<Vendedor> vendedores = vendedorDAO.obtenerVendedores();
            for (Vendedor vendedor : vendedores) {
                cmbVendedor.addItem(vendedor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void configureProductosInventario() {
        try {
            ProductoInventarioDAO productoInventarioDAO = new ProductoInventarioDAO();
            List<ProductoInventario> productosInventario = productoInventarioDAO.obtenerProductoInventario();
            DefaultTableModel model = (DefaultTableModel) tblProductosInventario.getModel();
            for (ProductoInventario producto : productosInventario) {
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

            VentaDTO venta = new VentaDTO();
            venta.setIdVendedor(((Vendedor) cmbVendedor.getSelectedItem()).getIdVendedor());
            venta.setFecha((java.util.Date) spnFecha.getValue());
            venta.setTotal(Double.parseDouble(txtTotal.getText()));

            List<DetalleVentaDTO> detallesVenta = new ArrayList<>();
            for (int i = 0; i < detalleModel.getRowCount(); i++) {
                DetalleVentaDTO detalle = new DetalleVentaDTO();
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
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar la venta.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    
    private void loadVentaData() {
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
