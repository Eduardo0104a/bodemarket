/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilitarios;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class IconButtonRenderer extends JButton implements TableCellRenderer {

    public IconButtonRenderer() {
        setOpaque(true);
        setPreferredSize(new Dimension(25, 25));
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false); // Para evitar efecto blanco feo
        setBackground(new Color(184, 199, 148)); // Color igual al encabezado
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        if (column == 0) {
            setIcon(new ImageIcon(getClass().getResource("/resources/edit_modify_icon_149489.png")));
        } else if (column == 1) {
            setIcon(new ImageIcon(getClass().getResource("/resources/small_x_icon_212667.png")));
        }

        // Evita parpadeo cuando se selecciona
        if (isSelected) {
            setBackground(new Color(184, 199, 148));
        } else {
            setBackground(new Color(184, 199, 148));
        }

        return this;
    }
}


