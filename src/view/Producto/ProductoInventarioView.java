package view.Producto;

import dao.ProductoDAO;
import dto.Producto;
import Utilitarios.IconButtonEditor;
import Utilitarios.IconButtonRenderer;
import dto.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableColumn;

public class ProductoInventarioView extends javax.swing.JPanel {

    private JTable tblProductoInventario;
    private JButton btnInsertar;
    private Usuario usuario;
    
    public ProductoInventarioView(Usuario usuario) {
        this.usuario = usuario;
        setBackground(new Color(175, 18, 128));
        initComponents();
        inicio();
        loadProductosInventario();
    }
    
    private void inicio() {
        setLayout(new BorderLayout());

        JPanel panelTituloBotones = new JPanel(new BorderLayout());
        panelTituloBotones.setBackground(new Color(175, 18, 128));

        JLabel lblTitulo = new JLabel("PRODUCTO - INVENTARIO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(255,238,0));
        panelTituloBotones.add(lblTitulo, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        btnInsertar = new JButton("Insertar");
        panelBotones.setBackground(new Color(175, 18, 128));

        panelBotones.add(btnInsertar);

        btnInsertar.setBackground(new Color(255,238,0));
        btnInsertar.setForeground(new Color(175, 18, 128));
        panelTituloBotones.add(panelBotones, BorderLayout.EAST);
        add(panelTituloBotones, BorderLayout.NORTH);

        tblProductoInventario = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"", "", "ID Producto", "Nombre", "Descripcion", "Precio", "Stock", "Categoria"}
        ));
        
        TableColumnModel columnModel = tblProductoInventario.getColumnModel();

        TableColumn modificarColumn = columnModel.getColumn(0);
        modificarColumn.setCellRenderer(new IconButtonRenderer());
        modificarColumn.setCellEditor(new IconButtonEditor(new JCheckBox(), tblProductoInventario, usuario, ProductoInventarioView.this));
        modificarColumn.setMaxWidth(25);

        TableColumn eliminarColumn = columnModel.getColumn(1);
        eliminarColumn.setCellRenderer(new IconButtonRenderer());
        eliminarColumn.setCellEditor(new IconButtonEditor(new JCheckBox(), tblProductoInventario, usuario, ProductoInventarioView.this));
        eliminarColumn.setMaxWidth(25);
        
        int alturaFila = 30;
        tblProductoInventario.setRowHeight(alturaFila);
        
        tblProductoInventario.setBackground(new Color(220, 190, 255)); 
        tblProductoInventario.setForeground(new Color(75, 0, 130)); 

        tblProductoInventario.setSelectionBackground(new Color(180, 130, 255)); 
        tblProductoInventario.setSelectionForeground(Color.WHITE); 

        tblProductoInventario.getTableHeader().setBackground(new Color(75, 0, 130)); 
        tblProductoInventario.getTableHeader().setForeground(new Color(75, 0, 130));
        
        JScrollPane scrollPane = new JScrollPane(tblProductoInventario);
        scrollPane.getViewport().setBackground(new Color(175, 18, 128)); // Amarillo suave
        add(scrollPane, BorderLayout.CENTER);
        
        btnInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirFormularioRegistro();
            }
        });
    }
    
    private void loadProductosInventario() {
        ProductoDAO productoDAO = new ProductoDAO();
        List<Producto> productos = productoDAO.listar();
        DefaultTableModel model = (DefaultTableModel) tblProductoInventario.getModel();
        for (Producto productoInventario : productos) {
            int idProducto = productoInventario.getIdProducto();
            String nombre = productoInventario.getNombre();
            String descripcion = productoInventario.getDescripcion();
            double precio = productoInventario.getPrecio();
            int stock = productoInventario.getStock();
            String categoria = productoInventario.getCategoria();
    
            Object[] rowData = new Object[model.getColumnCount()]; 

            rowData[2] = idProducto; 
            rowData[3] = nombre;
            rowData[4] = descripcion; 
            rowData[5] = precio;
            rowData[6] = stock;
            rowData[7] = categoria;

            model.addRow(rowData);
        }
    }
    
    private void abrirFormularioRegistro() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        ProductoInventarioRegister_Mod registroUsuarioForm = new ProductoInventarioRegister_Mod(parentFrame, usuario, ProductoInventarioView.this, false, 0);
        registroUsuarioForm.setVisible(true);
    }
    
    public void refreshProductosInventario() {
        DefaultTableModel model = (DefaultTableModel) tblProductoInventario.getModel();
        model.setRowCount(0);
        
        loadProductosInventario();
    }
    
    public JButton getBtnInsertar() {
        return btnInsertar;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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
