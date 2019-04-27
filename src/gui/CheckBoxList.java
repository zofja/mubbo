package gui;

import core.Symbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Class used to create a checkbox list.
 */
class CheckBoxList extends JList<CheckListItem> {

    /**
     * Currently selected symbol.
     */
    private Symbol selected_symbol = Symbol.EMPTY;

    /**
     * Class enabling marking items of checkbox list.
     */
    private class CheckListRenderer extends JCheckBox implements ListCellRenderer {
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
            setEnabled(list.isEnabled());
            setSelected(((CheckListItem) value).isSelected());
            setFont(list.getFont());
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            setText(value.toString());
            return this;
        }
    }

    /**
     * @param grid GUI grid.
     */
    CheckBoxList(Grid grid) {
        super(new CheckListItem[]{
                new CheckListItem("Left"),
                new CheckListItem("Up"),
                new CheckListItem("Right"),
                new CheckListItem("Down")});

        setFont(new Font("Courier", Font.PLAIN, 20));
        setCellRenderer(new CheckListRenderer());
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        addMouseListener(new MouseAdapter() {
            /**
             * Marks as checked clicked item and changes {@code selected_symbol}.
             *
             * @param event click.
             */
            @Override
            public void mouseClicked(MouseEvent event) {

                JList list = (JList) event.getSource();
                int index = list.locationToIndex(event.getPoint());

                CheckListItem item = (CheckListItem) list.getModel().getElementAt(index);
                item.setSelected(!item.isSelected());
                list.repaint(list.getCellBounds(index, index));
                if (selected_symbol != Symbol.EMPTY) {
                    ((CheckListItem) list.getModel().getElementAt(selected_symbol.ordinal())).setSelected(false);
                }
                if (selected_symbol.ordinal() == index) {
                    selected_symbol = Symbol.EMPTY;
                } else selected_symbol = Symbol.values()[index];
                grid.setSelectedIcon(selected_symbol);
            }
        });
    }
}
