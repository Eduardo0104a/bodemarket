package view.Movimiento;

import Utilitarios.IconButtonEditor;
import Utilitarios.IconButtonRenderer;
import dto.Usuario;
import dao.MovimientoInventarioDAO;
import dto.MovimientoInventario;

import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Eduardo
 */
public class MovimientoInventarioView extends javax.swing.JPanel {

    private JTable tblMovimientoInventario;
    private JButton btnModificar;
    private JButton btnEliminar;
    private Usuario usuario;
    private JTextField txtBuscar;
    private TableRowSorter<DefaultTableModel> sorter;

    public MovimientoInventarioView(Usuario usuario) {
        this.usuario = usuario;
        setBackground(new Color(233, 164, 157));
        initComponents();
        inicio();
        loadMovimientosInventario();
    }

    private void inicio() {
        setLayout(new BorderLayout());

        JPanel panelTituloBuscar = new JPanel(new BorderLayout());
        panelTituloBuscar.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("SALIDAS E INGRESOS DE PRODUCTOS");
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

        tblMovimientoInventario = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID Movimiento", "Producto", "Tipo", "Cantidad", "Fecha", "Descripción"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        tblMovimientoInventario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblMovimientoInventario.setRowHeight(32);
        tblMovimientoInventario.setForeground(Color.BLACK);
        tblMovimientoInventario.setBackground(new Color(233, 164, 157));
        tblMovimientoInventario.setSelectionBackground(new Color(255, 153, 153));
        tblMovimientoInventario.setSelectionForeground(Color.BLACK);
        tblMovimientoInventario.setGridColor(Color.LIGHT_GRAY);
        DefaultTableModel model = (DefaultTableModel) tblMovimientoInventario.getModel();
        sorter = new TableRowSorter<>(model);
        tblMovimientoInventario.setRowSorter(sorter);

        JTableHeader header = tblMovimientoInventario.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(120, 30, 20));
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createLineBorder(header.getBackground()));

        JScrollPane scrollPane = new JScrollPane(tblMovimientoInventario);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadMovimientosInventario() {
        MovimientoInventarioDAO movimientoInventarioDAO = new MovimientoInventarioDAO();
        List<MovimientoInventario> movimientos = movimientoInventarioDAO.obtenerMovimientosInventario();
        DefaultTableModel model = (DefaultTableModel) tblMovimientoInventario.getModel();
        for (MovimientoInventario movimiento : movimientos) {
            int idMovimiento = movimiento.getIdMovimiento();
            String nombreProducto = movimiento.getNombreProducto();
            String tipo = movimiento.getTipo();
            int cantidad = movimiento.getCantidad();
            Date fecha = movimiento.getFecha();
            String descripcion = movimiento.getDescripcion();

            Object[] rowData = new Object[model.getColumnCount()];

            rowData[0] = idMovimiento;
            rowData[1] = nombreProducto;
            rowData[2] = tipo;
            rowData[3] = cantidad;
            rowData[4] = fecha;
            rowData[5] = descripcion;

            model.addRow(rowData);
        }
    }

    public void refresh() {
        DefaultTableModel model = (DefaultTableModel) tblMovimientoInventario.getModel();
        model.setRowCount(0);

        loadMovimientosInventario();
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
