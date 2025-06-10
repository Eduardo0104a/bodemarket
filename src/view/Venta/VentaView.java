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

        Document document = new Document(PageSize.A4, 40, 40, 50, 50);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            PdfWriter.getInstance(document, fos);
            document.open();

            Paragraph titulo = new Paragraph("COMPROBANTE DE VENTA",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK));
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);

            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidths(new float[]{1, 2});
            infoTable.setWidthPercentage(100);
            infoTable.setSpacingAfter(20);

            infoTable.addCell(getCell("ID Venta:", PdfPCell.ALIGN_LEFT, true));
            infoTable.addCell(getCell(String.valueOf(idVenta), PdfPCell.ALIGN_LEFT, false));

            infoTable.addCell(getCell("Vendedor:", PdfPCell.ALIGN_LEFT, true));
            infoTable.addCell(getCell(vendedor, PdfPCell.ALIGN_LEFT, false));

            infoTable.addCell(getCell("Fecha:", PdfPCell.ALIGN_LEFT, true));
            infoTable.addCell(getCell(fecha, PdfPCell.ALIGN_LEFT, false));

            infoTable.addCell(getCell("Total:", PdfPCell.ALIGN_LEFT, true));
            infoTable.addCell(getCell(String.format("S/ %.2f", total), PdfPCell.ALIGN_LEFT, false));

            document.add(infoTable);

            Paragraph detalleTitulo = new Paragraph("DETALLE DE PRODUCTOS",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
            detalleTitulo.setAlignment(Element.ALIGN_LEFT);
            detalleTitulo.setSpacingAfter(10);
            document.add(detalleTitulo);

            PdfPTable detalleTable = new PdfPTable(detalleModel.getColumnCount());
            detalleTable.setWidthPercentage(100);
            detalleTable.setSpacingBefore(5);
            detalleTable.setSpacingAfter(20);

            for (int i = 0; i < detalleModel.getColumnCount(); i++) {
                PdfPCell headerCell = new PdfPCell(new Phrase(detalleModel.getColumnName(i),
                        FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11)));
                headerCell.setBackgroundColor(new BaseColor(230, 230, 250));
                headerCell.setPadding(8);
                detalleTable.addCell(headerCell);
            }

            for (int i = 0; i < detalleModel.getRowCount(); i++) {
                for (int j = 0; j < detalleModel.getColumnCount(); j++) {
                    PdfPCell dataCell = new PdfPCell(new Phrase(detalleModel.getValueAt(i, j).toString(),
                            FontFactory.getFont(FontFactory.HELVETICA, 10)));
                    dataCell.setPadding(6);
                    detalleTable.addCell(dataCell);
                }
            }

            document.add(detalleTable);

            Paragraph gracias = new Paragraph("¡Gracias por su compra!",
                    FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 12, BaseColor.DARK_GRAY));
            gracias.setAlignment(Element.ALIGN_CENTER);
            gracias.setSpacingBefore(30);
            document.add(gracias);

            document.close();
            JOptionPane.showMessageDialog(this, "Voucher exportado correctamente.");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al exportar el voucher: " + e.getMessage());
        }
    }

    private PdfPCell getCell(String text, int alignment, boolean bold) {
        com.itextpdf.text.Font font = bold
                ? FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11)
                : FontFactory.getFont(FontFactory.HELVETICA, 11);

        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER); 
        cell.setPadding(5);
        return cell;
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
