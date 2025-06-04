package view.Vendedor;

import dao.VendedorDAO;
import dto.Vendedor;
import dto.Usuario;
import Utilitarios.IconButtonEditor;
import Utilitarios.IconButtonRenderer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.text.ParseException; 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableColumn;
import java.util.Date;

public class VendedorView extends javax.swing.JPanel {

    private JTable tblVendedor;
    private JButton btnInsertar;
    private Usuario usuario;
    
    public VendedorView(Usuario usuario) {
        this.usuario = usuario;
        setBackground(new Color(175, 18, 128)); // Fondo morado para todo el panel
        initComponents();
        inicio();
        loadVendedores();
    }
    
    private void inicio() {
        setLayout(new BorderLayout());

        JPanel panelTituloBotones = new JPanel(new BorderLayout());
        panelTituloBotones.setBackground(new Color(175, 18, 128)); // Color de fondo morado

        JLabel lblTitulo = new JLabel("VENDEDOR");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(255,238,0)); // Texto blanco
        panelTituloBotones.add(lblTitulo, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        btnInsertar = new JButton("Insertar");
        panelBotones.setBackground(new Color(175, 18, 128));

        panelBotones.add(btnInsertar);

        btnInsertar.setBackground(new Color(255,238,0));
        btnInsertar.setForeground(new Color(175, 18, 128));
        panelTituloBotones.add(panelBotones, BorderLayout.EAST);
        add(panelTituloBotones, BorderLayout.NORTH);

        tblVendedor = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"", "", "ID", "Correo Electrónico", "Fecha Inicio", "Fecha Fin", "Razon Social"}
        ));
        
        TableColumnModel columnModel = tblVendedor.getColumnModel();

        TableColumn modificarColumn = columnModel.getColumn(0);
        modificarColumn.setCellRenderer(new IconButtonRenderer());
        modificarColumn.setCellEditor(new IconButtonEditor(new JCheckBox(), tblVendedor, usuario, VendedorView.this));
        modificarColumn.setMaxWidth(25);

        TableColumn eliminarColumn = columnModel.getColumn(1);
        eliminarColumn.setCellRenderer(new IconButtonRenderer());
        eliminarColumn.setCellEditor(new IconButtonEditor(new JCheckBox(), tblVendedor, usuario, VendedorView.this));
        eliminarColumn.setMaxWidth(25);
        
        int alturaFila = 30;
        tblVendedor.setRowHeight(alturaFila);
        
        tblVendedor.setBackground(new Color(220, 190, 255)); 
        tblVendedor.setForeground(new Color(75, 0, 130)); 

        tblVendedor.setSelectionBackground(new Color(180, 130, 255)); 
        tblVendedor.setSelectionForeground(Color.WHITE); 

        tblVendedor.getTableHeader().setBackground(new Color(75, 0, 130)); 
        tblVendedor.getTableHeader().setForeground(new Color(75, 0, 130));
        
        JScrollPane scrollPane = new JScrollPane(tblVendedor);
        scrollPane.getViewport().setBackground(new Color(175, 18, 128));
        add(scrollPane, BorderLayout.CENTER);
        
        btnInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirFormularioRegistro();
            }
        });
    }
    
    private void loadVendedores() {
        VendedorDAO vendedorDAO = new VendedorDAO();
        List<Vendedor> vendedores = vendedorDAO.obtenerVendedores();
        DefaultTableModel model = (DefaultTableModel) tblVendedor.getModel();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Vendedor vendedor : vendedores) {
            int idVendedor = vendedor.getIdVendedor();
            String correoElectronico = vendedor.getCorreoElectronico();
            Date fechaInicio = null;
            Date fechaFin = null;
            fechaInicio = vendedor.getFechaInicio();
            fechaFin = vendedor.getFechaFin();

            String razonSocial = vendedor.getRazonSocial();

            Object[] rowData = new Object[model.getColumnCount()];
            rowData[2] = idVendedor;
            rowData[3] = correoElectronico;
            rowData[4] = fechaInicio;
            rowData[5] = fechaFin;
            rowData[6] = razonSocial;

            model.addRow(rowData);
        }
    }

    private void abrirFormularioRegistro() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        VendedorRegister_Modf registroUsuarioForm = new VendedorRegister_Modf(parentFrame, usuario, VendedorView.this, false, 0);
        registroUsuarioForm.setVisible(true);
    }
    
    public void refreshVendedores() {
        DefaultTableModel model = (DefaultTableModel) tblVendedor.getModel();
        model.setRowCount(0);
        
        loadVendedores();
    }
    
    public JButton getBtnInsertar() {
        return btnInsertar;
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
