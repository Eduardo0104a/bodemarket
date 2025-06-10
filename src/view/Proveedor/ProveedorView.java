package view.Proveedor;

import dto.Proveedor;
import dao.ProveedorDAO;
import dto.Usuario;
import Utilitarios.IconButtonEditor;
import Utilitarios.IconButtonRenderer;

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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author EduardoPC
 */
public class ProveedorView extends javax.swing.JPanel {

    private JTable tblProveedor;
    private Usuario usuario;
    private JTextField txtBuscar;
    private TableRowSorter<DefaultTableModel> sorter;

    public ProveedorView(Usuario usuario) {
        this.usuario = usuario;
        setBackground(new Color(233, 164, 157));
        initComponents();
        inicio();
        loadProveedor();
    }

    private void inicio() {
        setLayout(new BorderLayout());

        JPanel panelTituloBuscar = new JPanel(new BorderLayout());
        panelTituloBuscar.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("PROVEEDORES");
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

        tblProveedor = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"", "", "ID", "Nombre Prov.", "Correo", "Telefono", "RUC"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 1;
            }
        });
        tblProveedor.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblProveedor.setRowHeight(32);
        tblProveedor.setForeground(Color.BLACK);
        tblProveedor.setBackground(new Color(233, 164, 157));
        tblProveedor.setSelectionBackground(new Color(255, 153, 153));
        tblProveedor.setSelectionForeground(Color.BLACK);
        tblProveedor.setGridColor(Color.LIGHT_GRAY);
        DefaultTableModel model = (DefaultTableModel) tblProveedor.getModel();
        sorter = new TableRowSorter<>(model);
        tblProveedor.setRowSorter(sorter);

        JTableHeader header = tblProveedor.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(120, 30, 20));
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createLineBorder(header.getBackground()));

        TableColumnModel columnModel = tblProveedor.getColumnModel();
        TableColumn modificarColumn = columnModel.getColumn(0);
        modificarColumn.setCellRenderer(new IconButtonRenderer());
        modificarColumn.setCellEditor(new IconButtonEditor(new JCheckBox(), tblProveedor, usuario, this));
        modificarColumn.setMaxWidth(35);

        TableColumn eliminarColumn = columnModel.getColumn(1);
        eliminarColumn.setCellRenderer(new IconButtonRenderer());
        eliminarColumn.setCellEditor(new IconButtonEditor(new JCheckBox(), tblProveedor, usuario, this));
        eliminarColumn.setMaxWidth(35);

        JScrollPane scrollPane = new JScrollPane(tblProveedor);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrar();
            }
        });
    }

    private void filtrar() {
        if (sorter != null) {
            String texto = txtBuscar.getText();
            if (texto.trim().isEmpty()) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
            }
        }
    }

    private void loadProveedor() {
        ProveedorDAO proveedorDAO = new ProveedorDAO();
        List<Proveedor> proveedores = proveedorDAO.listar();
        DefaultTableModel model = (DefaultTableModel) tblProveedor.getModel();
        for (Proveedor proveedor : proveedores) {
            int idProveedor = proveedor.getIdProveedor();
            String nombre = proveedor.getNombre();
            String correo = proveedor.getCorreo();
            String telefono = proveedor.getTelefono();
            String ruc = proveedor.getRuc();

            Object[] rowData = new Object[model.getColumnCount()];
            rowData[2] = idProveedor;
            rowData[3] = nombre;
            rowData[4] = correo;
            rowData[5] = telefono;
            rowData[6] = ruc;

            model.addRow(rowData);
        }
    }

    public void refreshProveedor() {
        DefaultTableModel model = (DefaultTableModel) tblProveedor.getModel();
        model.setRowCount(0);

        loadProveedor();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

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
