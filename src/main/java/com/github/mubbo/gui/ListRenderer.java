package com.github.mubbo.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static com.github.mubbo.gui.GameUI.BORDER_COLOR;
import static com.github.mubbo.gui.GameUI.OFF_WHITE;


/**
 * A FileListCellRenderer for a File.
 * Class enabling marking items of checkbox list.
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
            setBackground(BORDER_COLOR);
        } else {
            setBackground(OFF_WHITE);
        }

        return this;
    }
}