package view.Venta;

import dao.VentaDAO;
import dao.DetalleVentaDAO;
import dto.Venta;
import dto.DetalleVenta;
import dto.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.Font;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

public class VentaView extends javax.swing.JPanel {

    private JTable tblVenta;
    private JTable tblDetalleVenta;
    private JButton btnInsertar;
    private JButton btnVoucher;
    private Usuario usuario;
    private JTextField txtBuscar;
    private TableRowSorter<DefaultTableModel> sorter;

    public VentaView(Usuario usuario) {
        this.usuario = usuario;
        setBackground(new Color(175, 18, 128));
        initComponents();
        inicio();
        loadVentas();
    }

    private void inicio() {
        setLayout(new BorderLayout());
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Color.WHITE);

        txtBuscar = new JTextField();
        txtBuscar.setPreferredSize(new Dimension(250, 30));
        txtBuscar.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(188, 47, 33), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        JPanel panelBuscar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBuscar.setOpaque(false);
        panelBuscar.add(txtBuscar);

        btnInsertar = new JButton("Insertar");
        btnVoucher = new JButton("Voucher");

        Color colorFondo = new Color(255, 238, 0); 
        Color colorTexto = new Color(175, 18, 128);
        Color colorHover = new Color(255, 215, 0); 

        for (JButton btn : new JButton[]{btnInsertar, btnVoucher}) {
            btn.setBackground(colorFondo);
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
                    btn.setBackground(colorFondo);
                }
            });
        }

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotones.setOpaque(false);
        panelBotones.add(btnInsertar);
        panelBotones.add(btnVoucher);

        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setOpaque(false);
        panelTop.add(panelBotones, BorderLayout.WEST);
        panelTop.add(panelBuscar, BorderLayout.EAST);

        panelSuperior.add(panelTop, BorderLayout.CENTER);
        add(panelSuperior, BorderLayout.NORTH);

        // Tabla de ventas
        JLabel lblTablaVentas = new JLabel("Ventas");
        lblTablaVentas.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTablaVentas.setForeground(new Color(120, 30, 20));
        lblTablaVentas.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 0));

        DefaultTableModel modelVenta = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID Venta", "Vendedor", "Fecha", "Total"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblVenta = new JTable(modelVenta);
        tblVenta.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tblVenta.setRowHeight(32);
        tblVenta.setForeground(Color.BLACK);
        tblVenta.setBackground(new Color(233, 164, 157));
        tblVenta.setSelectionBackground(new Color(255, 153, 153));
        tblVenta.setSelectionForeground(Color.BLACK);
        tblVenta.setGridColor(Color.LIGHT_GRAY);
        tblVenta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sorter = new TableRowSorter<>(modelVenta);
        tblVenta.setRowSorter(sorter);

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

        tblVenta.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                cargarDetalleVenta();
            }
        });

        JTableHeader headerVenta = tblVenta.getTableHeader();
        headerVenta.setFont(new Font("SansSerif", Font.BOLD, 13));
        headerVenta.setBackground(new Color(120, 30, 20));
        headerVenta.setForeground(Color.WHITE);
        headerVenta.setBorder(BorderFactory.createLineBorder(headerVenta.getBackground()));

        JPanel panelVenta = new JPanel(new BorderLayout());
        panelVenta.setBackground(Color.WHITE);
        panelVenta.add(lblTablaVentas, BorderLayout.NORTH);
        panelVenta.add(new JScrollPane(tblVenta), BorderLayout.CENTER);

        JLabel lblTablaDetalle = new JLabel("Detalle de Venta");
        lblTablaDetalle.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTablaDetalle.setForeground(new Color(175, 18, 128));
        lblTablaDetalle.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 0));

        DefaultTableModel modelDetalleVenta = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID Detalle", "ID Venta", "Producto", "Cantidad", "Precio Unitario", "SubTotal"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblDetalleVenta = new JTable(modelDetalleVenta);
        tblDetalleVenta.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tblDetalleVenta.setRowHeight(30);
        tblDetalleVenta.setForeground(new Color(75, 0, 130));
        tblDetalleVenta.setBackground(Color.WHITE);
        tblDetalleVenta.setSelectionBackground(new Color(180, 130, 255));
        tblDetalleVenta.setSelectionForeground(Color.WHITE);
        tblDetalleVenta.setGridColor(Color.LIGHT_GRAY);

        JTableHeader headerDetalle = tblDetalleVenta.getTableHeader();
        headerDetalle.setFont(new Font("SansSerif", Font.BOLD, 13));
        headerDetalle.setBackground(new Color(175, 18, 128));
        headerDetalle.setForeground(Color.WHITE);

        JPanel panelDetalle = new JPanel(new BorderLayout());
        panelDetalle.setBackground(Color.WHITE);
        panelDetalle.add(lblTablaDetalle, BorderLayout.NORTH);
        panelDetalle.add(new JScrollPane(tblDetalleVenta), BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelVenta, panelDetalle);
        splitPane.setDividerLocation(500);
        splitPane.setResizeWeight(0.5);
        add(splitPane, BorderLayout.CENTER);

        btnInsertar.addActionListener(e -> abrirFormularioRegistro());
        btnVoucher.addActionListener(e -> exportarVoucher());
    }

    private void loadVentas() {
        VentaDAO ventaDAO = new VentaDAO();
        List<Venta> ventas = ventaDAO.obtenerTodasVentas();
        DefaultTableModel model = (DefaultTableModel) tblVenta.getModel();
        for (Venta venta : ventas) {
            Object[] rowData = new Object[model.getColumnCount()];
            rowData[0] = venta.getId();
            rowData[1] = venta.getUsuarioNombre();
            rowData[2] = venta.getFecha();
            rowData[3] = venta.getTotal();
            model.addRow(rowData);
        }

        if (tblVenta.getRowCount() > 0) {
            tblVenta.setRowSelectionInterval(0, 0);
        }
    }

    private void cargarDetalleVenta() {
        int selectedRow = tblVenta.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        int idVenta = (int) tblVenta.getValueAt(selectedRow, 0);
        DetalleVentaDAO detalleVentaDAO = new DetalleVentaDAO();
        List<DetalleVenta> detalles = detalleVentaDAO.obtenerDetallesVentaPorId(idVenta);
        DefaultTableModel model = (DefaultTableModel) tblDetalleVenta.getModel();
        model.setRowCount(0);

        for (DetalleVenta detalle : detalles) {
            Object[] rowData = new Object[model.getColumnCount()];
            rowData[0] = detalle.getIdDetalle();
            rowData[1] = detalle.getIdVenta();
            rowData[2] = detalle.getProducto();
            rowData[3] = detalle.getCantidad();
            rowData[4] = detalle.getPrecioUnitario();
            rowData[5] = detalle.getSubTotal();
            model.addRow(rowData);
        }
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

    private void abrirFormularioRegistro() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        VentaRegister_Modf registroUsuarioForm = new VentaRegister_Modf(parentFrame, usuario, VentaView.this, false, 0);
        registroUsuarioForm.setVisible(true);
    }

    private void exportarVoucher() {
        int selectedRow = tblVenta.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una venta para exportar.");
            return;
        }

        int idVenta = (int) tblVenta.getValueAt(selectedRow, 0);
        String vendedor = tblVenta.getValueAt(selectedRow, 1).toString();
        String fecha = tblVenta.getValueAt(selectedRow, 2).toString();
        double total = (double) tblVenta.getValueAt(selectedRow, 3);

        DefaultTableModel detalleModel = (DefaultTableModel) tblDetalleVenta.getModel();

        String desktopPath = System.getProperty("user.home") + "/OneDrive/Desktop/Vouchers";
        File folder = new File(desktopPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String fileName = "Voucher_Venta_" + idVenta + ".pdf";
        File file = new File(folder, fileName);

        Document document = new Document(PageSize.A4);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            PdfWriter writer = PdfWriter.getInstance(document, fos);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            float[] columnWidths = {4f, 1f};
            table.setWidths(columnWidths);

            PdfPCell contentCell = new PdfPCell();
            contentCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
            contentCell.setPadding(10);

            Paragraph title = new Paragraph("Voucher", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
            title.setAlignment(Element.ALIGN_CENTER);
            contentCell.addElement(title);
            contentCell.addElement(Chunk.NEWLINE);

            Paragraph ventaInfo = new Paragraph(
                    String.format("ID Venta: %d\nVendedor: %s\nFecha: %s\nTotal: %.2f",
                            idVenta, vendedor, fecha, total),
                    FontFactory.getFont(FontFactory.HELVETICA, 12)
            );
            ventaInfo.setSpacingBefore(10);
            ventaInfo.setSpacingAfter(10);
            contentCell.addElement(ventaInfo);

            PdfPTable detalleTable = new PdfPTable(detalleModel.getColumnCount());
            detalleTable.setWidthPercentage(100);

            for (int i = 0; i < detalleModel.getColumnCount(); i++) {
                PdfPCell headerCell = new PdfPCell(new Phrase(detalleModel.getColumnName(i)));
                headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                detalleTable.addCell(headerCell);
            }

            for (int i = 0; i < detalleModel.getRowCount(); i++) {
                for (int j = 0; j < detalleModel.getColumnCount(); j++) {
                    detalleTable.addCell(detalleModel.getValueAt(i, j).toString());
                }
            }

            contentCell.addElement(detalleTable);

            PdfPCell imageCell = new PdfPCell();
            imageCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
            imageCell.setPadding(10);

            table.addCell(contentCell);
            table.addCell(imageCell);

            document.add(table);

            document.close();
            JOptionPane.showMessageDialog(this, "Voucher exportado correctamente.");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al exportar el voucher: " + e.getMessage());
        }
    }

    public void refreshVentas() {
        DefaultTableModel model = (DefaultTableModel) tblVenta.getModel();
        model.setRowCount(0);

        loadVentas();
    }

    public JButton getBtnInsertar() {
        return btnInsertar;
    }

    public JButton getBtnVoucher() {
        return btnVoucher;
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
