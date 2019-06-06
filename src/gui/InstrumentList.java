package gui;

import core.Symbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Class used to create a checkbox list.
 */
class InstrumentList extends CheckBoxList {

    /**
     * Currently selected symbol.
     */
    Integer selectedInstrument = -1;

    /**
     * @param grid GUI grid.
     */
    InstrumentList(Grid grid, CheckListItem[] items) {
        super(items);

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
                if (selectedInstrument != -1) {
                    ((CheckListItem) list.getModel().getElementAt(selectedInstrument)).setSelected(false);
                }
                if (selectedInstrument == index) {
                    selectedInstrument = -1;
                } else selectedInstrument = index;
                grid.setSelectedInstrument(selectedInstrument);
            }
        });
    }
}
