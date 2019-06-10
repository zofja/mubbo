package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Class enabling marking items of checkbox list.
 */

/**
 * A FileListCellRenderer for a File.
 */
class ListRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean selected,
            boolean expanded) {

        ListItem item = (ListItem) value;
        setIcon(item.getIcon());
        setText(value.toString());
        setFont(list.getFont());
        setBorder(new EmptyBorder(2, 2, 2, 0));

        if (selected) {
            setBackground(new Color(0xB8B8B8));
        } else {
            setBackground(new Color(0xffffff));
        }

        return this;
    }
}