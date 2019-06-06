package gui;

import core.Symbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Class used to create a checkbox list.
 */
class ArrowList extends CheckBoxList {

    /**
     * Currently selected symbol.
     */
    private Symbol selectedSymbol = Symbol.EMPTY;

    /**
     * @param grid GUI grid.
     */
    ArrowList(Grid grid, CheckListItem[] items) {
        super(items);

        setFont(new Font("Courier", Font.PLAIN, 20));
        setCellRenderer(new CheckListRenderer());
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        addMouseListener(new MouseAdapter() {
            /**
             * Marks as checked clicked item and changes {@code selectedSymbol}.
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
                if (selectedSymbol != Symbol.EMPTY) {
                    ((CheckListItem) list.getModel().getElementAt(selectedSymbol.ordinal())).setSelected(false);
                }
                if (selectedSymbol.ordinal() == index) {
                    selectedSymbol = Symbol.EMPTY;
                } else selectedSymbol = Symbol.values()[index];
                if (grid.getSelectedInstrument() != -1) {
                    grid.setSelectedSymbol(selectedSymbol);
                }
            }
        });
    }
}
