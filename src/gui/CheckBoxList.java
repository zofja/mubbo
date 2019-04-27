package gui;

import core.Symbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class CheckBoxList extends JList<CheckListItem> {

    private Symbol selected_symbol = Symbol.EMPTY;

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

    CheckBoxList(Grid grid) {
        super(new CheckListItem[]{
                new CheckListItem("Left"),
                new CheckListItem("Up"),
                new CheckListItem("Right"),
                new CheckListItem("Down")});

        setFont(new Font("Courier", Font.PLAIN, 20));
        setCellRenderer(new CheckListRenderer());
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ListModel<CheckListItem> model = getModel();
        model.getElementAt(0).setSelected(true);
        selected_symbol = Symbol.values()[0];
        grid.setSelectedIcon(selected_symbol);

        addMouseListener(new MouseAdapter() {
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
