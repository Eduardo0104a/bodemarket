package Utilitarios;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

public class SpinnerEditor extends AbstractCellEditor implements TableCellEditor {

    private JSpinner spinner;

    public SpinnerEditor() {
        spinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
    }

    @Override
    public Object getCellEditorValue() {
        return ((Number) spinner.getValue()).intValue();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        spinner.setValue(value);
        return spinner;
    }

    @Override
    public boolean stopCellEditing() {
        fireEditingStopped();
        return true;
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }
}

