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
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

public class ProductoInventarioView extends javax.swing.JPanel {

    private JTable tblProducto;
    private JButton btnInsertar;
    private Usuario usuario;
    private JTextField txtBuscar;
    private TableRowSorter<DefaultTableModel> sorter;

    public ProductoInventarioView(Usuario usuario) {
        this.usuario = usuario;
        setBackground(new Color(233, 164, 157));
        initComponents();
        inicio();
        loadProductosInventario();
    }

    private void inicio() {
        setLayout(new BorderLayout());

        JPanel panelTituloBuscar = new JPanel(new BorderLayout());
        panelTituloBuscar.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("INVENTARIO");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(120, 30, 20));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 0));

        txtBuscar = new JTextField();
        txtBuscar.setPreferredSize(new Dimension(250, 30));
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(188, 47, 33), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        JPanel panelBuscar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBuscar.setOpaque(false);
        panelBuscar.add(txtBuscar);

        panelTituloBuscar.add(lblTitulo, BorderLayout.WEST);
        panelTituloBuscar.add(panelBuscar, BorderLayout.EAST);
        add(panelTituloBuscar, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"", "", "ID Producto", "Nombre", "Descripcion", "Precio", "Stock", "Categoria", "Medida", "Proveedor"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 1; 
            }
        };

        tblProducto = new JTable(model);
        tblProducto.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblProducto.setRowHeight(32);
        tblProducto.setForeground(Color.BLACK);
        tblProducto.setBackground(new Color(233, 164, 157));
        tblProducto.setSelectionBackground(new Color(255, 153, 153));
        tblProducto.setSelectionForeground(Color.BLACK);
        tblProducto.setGridColor(Color.LIGHT_GRAY);
        sorter = new TableRowSorter<>(model);
        tblProducto.setRowSorter(sorter);

        JTableHeader header = tblProducto.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(120, 30, 20));
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createLineBorder(header.getBackground()));

        TableColumnModel columnModel = tblProducto.getColumnModel();
        TableColumn modificarColumn = columnModel.getColumn(0);
        modificarColumn.setCellRenderer(new IconButtonRenderer());
        modificarColumn.setCellEditor(new IconButtonEditor(new JCheckBox(), tblProducto, usuario, this));
        modificarColumn.setMaxWidth(35);

        TableColumn eliminarColumn = columnModel.getColumn(1);
        eliminarColumn.setCellRenderer(new IconButtonRenderer());
        eliminarColumn.setCellEditor(new IconButtonEditor(new JCheckBox(), tblProducto, usuario, this));
        eliminarColumn.setMaxWidth(35);

        JScrollPane scrollPane = new JScrollPane(tblProducto);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

    }

    private void loadProductosInventario() {
        ProductoDAO productoDAO = new ProductoDAO();
        List<Producto> productos = productoDAO.listar();
        DefaultTableModel model = (DefaultTableModel) tblProducto.getModel();
        for (Producto producto : productos) {
            int idProducto = producto.getIdProducto();
            String nombre = producto.getNombre();
            String descripcion = producto.getDescripcion();
            Double precio = producto.getPrecio();
            int Stock = producto.getStock();
            String nomCategoria = producto.getNomCategoria();
            String nomMedida = producto.getNombreCortoMedida();
            String nomProveedor = producto.getNombreProveedor();

            Object[] rowData = new Object[model.getColumnCount()];
            rowData[2] = idProducto;
            rowData[3] = nombre;
            rowData[4] = descripcion;
            rowData[5] = precio;
            rowData[6] = Stock;
            rowData[7] = nomCategoria;
            rowData[8] = nomMedida;
            rowData[9] = nomProveedor;

            model.addRow(rowData);
        }
    }

    private void abrirFormularioRegistro() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        ProductoInventarioRegister_Mod registroUsuarioForm = new ProductoInventarioRegister_Mod(parentFrame, usuario, ProductoInventarioView.this, false, 0);
        registroUsuarioForm.setVisible(true);
    }

    public void refreshProductosInventario() {
        DefaultTableModel model = (DefaultTableModel) tblProducto.getModel();
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
