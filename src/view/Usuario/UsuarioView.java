package view.Usuario;

import dao.UsuarioDAO;
import dto.Usuario;
import Utilitarios.IconButtonEditor;
import Utilitarios.IconButtonRenderer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionAdapter;
import java.util.List;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableColumn;

public class UsuarioView extends javax.swing.JPanel {

    private JTable tblUsuario;
    private JButton btnInsertar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private Usuario usuario;
    private int hoveredRow = -1;
    private int hoveredColumn = -1;


    public UsuarioView(Usuario usuario) {
        this.usuario = usuario;
        setBackground(new Color(146, 152, 130)); // Fondo general
        initComponents();
        inicio();
        loadUsuarios();
    }

    private void inicio() {
        setLayout(new BorderLayout());

        JPanel panelTituloBotones = new JPanel(new BorderLayout());
        panelTituloBotones.setBackground(new Color(83, 86, 75)); 

        JLabel lblTitulo = new JLabel("GESTIÓN DE USUARIOS");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(255, 255, 255));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 0));
        panelTituloBotones.add(lblTitulo, BorderLayout.WEST);

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(83, 86, 75));

        btnInsertar = new JButton("Insertar Usuario");
        btnInsertar.setBackground(new Color(184, 199, 148));
        btnInsertar.setForeground(Color.BLACK);
        btnInsertar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnInsertar.setFocusPainted(false);
        btnInsertar.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        panelBotones.add(btnInsertar);

        panelTituloBotones.add(panelBotones, BorderLayout.EAST);
        add(panelTituloBotones, BorderLayout.NORTH);

        tblUsuario = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"", "", "ID", "Nombre", "Apellido", "Correo Electrónico", "Rol"}
        ));
        tblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblUsuario.setRowHeight(32);
        tblUsuario.setForeground(Color.DARK_GRAY);
        tblUsuario.setBackground(new Color(220, 224, 203));
        tblUsuario.setSelectionBackground(new Color(174, 187, 145));
        tblUsuario.setSelectionForeground(Color.BLACK);
        tblUsuario.setGridColor(new Color(180, 180, 180));

        Color headerColor = new Color(105, 108, 95);

        JTableHeader header = tblUsuario.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(headerColor);
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createLineBorder(headerColor));

        TableColumnModel columnModel = tblUsuario.getColumnModel();
        TableColumn modificarColumn = columnModel.getColumn(0);
        modificarColumn.setCellRenderer(new IconButtonRenderer());
        modificarColumn.setCellEditor(new IconButtonEditor(new JCheckBox(), tblUsuario, usuario, this));
        modificarColumn.setMaxWidth(35);

        TableColumn eliminarColumn = columnModel.getColumn(1);
        eliminarColumn.setCellRenderer(new IconButtonRenderer());
        eliminarColumn.setCellEditor(new IconButtonEditor(new JCheckBox(), tblUsuario, usuario, this));
        eliminarColumn.setMaxWidth(35);

        JScrollPane scrollPane = new JScrollPane(tblUsuario);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(146, 152, 130)); 
        add(scrollPane, BorderLayout.CENTER);
        
        btnInsertar.addActionListener(e -> abrirFormularioRegistro());
        btnInsertar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnInsertar.setBackground(new Color(168, 185, 132)); 
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnInsertar.setBackground(new Color(184, 199, 148)); 
            }
        });

    }

    private void loadUsuarios() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> usuarios = usuarioDAO.obtenerUsuarios();
        DefaultTableModel model = (DefaultTableModel) tblUsuario.getModel();
        for (Usuario usuario : usuarios) {
            int idUsuario = usuario.getIdUsuario();
            String nombre = usuario.getNombre();
            String apellido = usuario.getApellido();
            String correoElectronico = usuario.getCorreoElectronico();
            String rol = usuario.getRol();

            Object[] rowData = new Object[model.getColumnCount()];

            rowData[2] = idUsuario;
            rowData[3] = nombre;
            rowData[4] = apellido;
            rowData[5] = correoElectronico;
            rowData[6] = rol;

            model.addRow(rowData);
        }
    }

    private void abrirFormularioRegistro() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        UsuarioRegister_Modf registroUsuarioForm = new UsuarioRegister_Modf(parentFrame, usuario, UsuarioView.this, false, 0);
        registroUsuarioForm.setVisible(true);
    }

    public void refreshUsuarios() {
        DefaultTableModel model = (DefaultTableModel) tblUsuario.getModel();
        model.setRowCount(0);

        loadUsuarios();
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
