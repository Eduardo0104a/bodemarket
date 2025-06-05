
package Utilitarios;

import dao.UsuarioDAO;
import dao.ProductoDAO;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import dto.Usuario;
import view.Usuario.UsuarioRegister_Modf;
import view.Usuario.UsuarioView;
import view.Producto.ProductoInventarioView;
import view.Producto.ProductoInventarioRegister_Mod;

/**
 *
 * @author Eduardo
 */
public class IconButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;
    private JTable table;
    private UsuarioView usuarioView;
    private ProductoInventarioView productoInventarioView;

    public IconButtonEditor(JCheckBox checkBox, JTable table, Usuario usuario, UsuarioView usuarioView) {
        super(checkBox);
        button = new JButton();
        this.table = table;
        this.usuarioView = usuarioView;
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int modelRow = table.convertRowIndexToModel(selectedRow);
                    int id = (int) table.getModel().getValueAt(modelRow, 2);

                    if (table.getSelectedColumn() == 0) {
                        new UsuarioRegister_Modf((Frame) SwingUtilities.getWindowAncestor(table), usuario, usuarioView, true, id).setVisible(true);
                    } else {
                        UsuarioDAO usuarioDAO = new UsuarioDAO();
                        int result = usuarioDAO.eliminar(id);

                        if (result > 0) {
                            JOptionPane.showMessageDialog(button, "Usuario eliminado exitosamente.");
                            if (usuarioView != null) {
                                usuarioView.refreshUsuarios();
                            }
                        } else {
                            JOptionPane.showMessageDialog(button, "Error: Usuario no encontrado.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(button, "No hay fila seleccionada.");
                }
            }
        });
    }
     
    public IconButtonEditor(JCheckBox checkBox, JTable table, Usuario usuario, ProductoInventarioView productoInventarioView) {
        super(checkBox);
        button = new JButton();
        this.table = table;
        this.productoInventarioView = productoInventarioView;
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int modelRow = table.convertRowIndexToModel(selectedRow);
                    int id = (int) table.getModel().getValueAt(modelRow, 2);

                    if (table.getSelectedColumn() == 0) {
                        new ProductoInventarioRegister_Mod((Frame) SwingUtilities.getWindowAncestor(table), usuario, productoInventarioView, true, id).setVisible(true);
                    } else {  
                        ProductoDAO productoInventarioDAO = new ProductoDAO();
                        int result = productoInventarioDAO.eliminarProducto(id);

                        if (result > 0) {
                            JOptionPane.showMessageDialog(button, "Producto eliminado exitosamente.");
                            if (productoInventarioView != null) {
                                productoInventarioView.refreshProductosInventario();
                            }
                        } else {
                            JOptionPane.showMessageDialog(button, "Error: Producto no encontrado.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(button, "No hay fila seleccionada.");
                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        if (column == 0) {
            button.setIcon(new ImageIcon(getClass().getResource("/resources/edit_modify_icon_149489.png")));
        } else if (column == 1) {
            button.setIcon(new ImageIcon(getClass().getResource("/resources/small_x_icon_212667.png")));
        }

        button.setText("Fila " + (row + 1));

        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return button.getText();
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}

