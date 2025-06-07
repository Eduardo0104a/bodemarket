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
import java.util.List;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

public class UsuarioView extends javax.swing.JPanel {

    private JButton btnInsertar;
    private JTable tblUsuario;
    private Usuario usuario;
    private JTextField txtBuscar;
    private TableRowSorter<DefaultTableModel> sorter;

    public UsuarioView(Usuario usuario) {
        this.usuario = usuario;
        setBackground(new Color(233, 164, 157)); 
        initComponents();
        inicio();
        loadUsuarios();
    }

    private void inicio() {
        setLayout(new BorderLayout());

        JPanel panelTituloBuscar = new JPanel(new BorderLayout());
        panelTituloBuscar.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("GESTIÓN DE USUARIOS");
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

        tblUsuario = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"", "", "ID", "Nombre", "Apellido", "Correo Electrónico", "telefono", "UserName", "Rol"}
        ));
        tblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblUsuario.setRowHeight(32);
        tblUsuario.setForeground(Color.BLACK);
        tblUsuario.setBackground(new Color(233, 164, 157)); // Tonalidad cálida cercana al amarillo
        tblUsuario.setSelectionBackground(new Color(255, 153, 153)); // Amarillo fuerte
        tblUsuario.setSelectionForeground(Color.BLACK);
        tblUsuario.setGridColor(Color.LIGHT_GRAY);
        DefaultTableModel model = (DefaultTableModel) tblUsuario.getModel();
        sorter = new TableRowSorter<>(model);
        tblUsuario.setRowSorter(sorter);

        JTableHeader header = tblUsuario.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(120, 30, 20));
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createLineBorder(header.getBackground()));

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
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

    }

    private void loadUsuarios() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> usuarios = usuarioDAO.listar();
        DefaultTableModel model = (DefaultTableModel) tblUsuario.getModel();
        for (Usuario usuario : usuarios) {
            int idUsuario = usuario.getIdUsuario();
            String nombre = usuario.getNombre();
            String apellido = usuario.getApellido();
            String correoElectronico = usuario.getCorreo();
            String telefono = usuario.getTelefono();
            String userName = usuario.getUsuario();
            String rol = usuario.getRol();

            Object[] rowData = new Object[model.getColumnCount()];
            rowData[2] = idUsuario;
            rowData[3] = nombre;
            rowData[4] = apellido;
            rowData[5] = correoElectronico;
            rowData[6] = telefono;
            rowData[7] = userName;
            rowData[8] = rol;

            model.addRow(rowData);
        }

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

    private void abrirFormularioRegistro() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        UsuarioRegister_Modf registroUsuarioForm = new UsuarioRegister_Modf(parentFrame, usuario, UsuarioView.this, false, 0);
        registroUsuarioForm.setVisible(true);
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

    public void refreshUsuarios() {
        DefaultTableModel model = (DefaultTableModel) tblUsuario.getModel();
        model.setRowCount(0);

        loadUsuarios();
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
