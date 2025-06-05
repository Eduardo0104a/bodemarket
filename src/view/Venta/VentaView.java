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

public class VentaView extends javax.swing.JPanel {

    private JTable tblVenta;
    private JTable tblDetalleVenta;
    private JButton btnInsertar;
    private JButton btnVoucher;
    private Usuario usuario;

    public VentaView(Usuario usuario) {
        this.usuario = usuario;
        setBackground(new Color(175, 18, 128));
        initComponents();
        inicio();
        loadVentas();
    }

    private void inicio() {
        setLayout(new BorderLayout());

        JPanel panelTituloBotones = new JPanel(new BorderLayout());
        panelTituloBotones.setBackground(new Color(175, 18, 128));

        JLabel lblTitulo = new JLabel("VENTA");
        lblTitulo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        lblTitulo.setForeground(new Color(255, 238, 0));
        panelTituloBotones.add(lblTitulo, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        btnInsertar = new JButton("Insertar");
        btnVoucher = new JButton("Voucher");
        panelBotones.setBackground(new Color(175, 18, 128));

        panelBotones.add(btnInsertar);
        panelBotones.add(btnVoucher);

        btnInsertar.setBackground(new Color(255, 238, 0));
        btnInsertar.setForeground(new Color(175, 18, 128));
        btnVoucher.setBackground(new Color(255, 238, 0));
        btnVoucher.setForeground(new Color(175, 18, 128));
        panelTituloBotones.add(panelBotones, BorderLayout.EAST);
        add(panelTituloBotones, BorderLayout.NORTH);

        tblVenta = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID Venta", "Vendedor", "Fecha", "Total"}
        ));

        tblVenta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblVenta.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    cargarDetalleVenta();
                }
            }
        });
        tblVenta.setBackground(new Color(220, 190, 255));
        tblVenta.setForeground(new Color(75, 0, 130));

        tblVenta.setSelectionBackground(new Color(180, 130, 255));
        tblVenta.setSelectionForeground(Color.WHITE);

        tblVenta.getTableHeader().setBackground(new Color(75, 0, 130));
        tblVenta.getTableHeader().setForeground(new Color(75, 0, 130));

        JScrollPane scrollPaneVenta = new JScrollPane(tblVenta);
        scrollPaneVenta.getViewport().setBackground(new Color(175, 18, 128)); // Amarillo suave
        scrollPaneVenta.setBorder(BorderFactory.createTitledBorder("Venta"));

        tblDetalleVenta = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID Detalle", "ID Venta", "Producto", "Cantidad", "Precio Unitario", "SubTotal"}
        ));
        tblDetalleVenta.setBackground(new Color(220, 190, 255));
        tblDetalleVenta.setForeground(new Color(75, 0, 130));

        tblDetalleVenta.setSelectionBackground(new Color(180, 130, 255));
        tblDetalleVenta.setSelectionForeground(Color.WHITE);

        tblDetalleVenta.getTableHeader().setBackground(new Color(75, 0, 130));
        tblDetalleVenta.getTableHeader().setForeground(new Color(75, 0, 130));

        JScrollPane scrollPaneDetalleVenta = new JScrollPane(tblDetalleVenta);
        scrollPaneDetalleVenta.getViewport().setBackground(new Color(175, 18, 128)); // Amarillo suave
        scrollPaneDetalleVenta.setBorder(BorderFactory.createTitledBorder("Detalle de Venta"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPaneVenta, scrollPaneDetalleVenta);
        splitPane.setDividerLocation(300);
        add(splitPane, BorderLayout.CENTER);

        btnInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirFormularioRegistro();
            }
        });

        btnVoucher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarVoucher();
            }
        });
    }

    private void loadVentas() {
        VentaDAO ventaDAO = new VentaDAO();
        List<Venta> ventas = ventaDAO.listar();
        DefaultTableModel model = (DefaultTableModel) tblVenta.getModel();
        for (Venta venta : ventas) {
            Object[] rowData = new Object[model.getColumnCount()];
            rowData[0] = venta.getId();
            rowData[1] = venta.getFecha();
            rowData[2] = venta.getUsuarioNombre();
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
            rowData[2] = detalle.getIdProducto();
            rowData[3] = detalle.getProducto();
            rowData[4] = detalle.getPrecioUnitario();
            rowData[5] = detalle.getSubTotal();
            model.addRow(rowData);
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
