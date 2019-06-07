package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Class enabling marking items of checkbox list.
 */
class ListRenderer extends JRadioButton implements ListCellRenderer {
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
        setEnabled(list.isEnabled());
        setSelected(((ListItem) value).isSelected());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setFont(list.getFont());
        setBackground(list.getBackground());
        setForeground(list.getForeground());
        setText(value.toString());
        return this;
    }
}
