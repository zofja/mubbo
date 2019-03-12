import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class CheckBoxList extends JList<CheckListItem> {

    private int selected_idx = -1;

    private class CheckListRenderer extends JCheckBox implements ListCellRenderer {
        public Component getListCellRendererComponent(JList list, Object value,
                                                      int index, boolean isSelected, boolean hasFocus) {
            setEnabled(list.isEnabled());
            setSelected(((CheckListItem) value).isSelected());
            setFont(list.getFont());
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            setText(value.toString());
            return this;
        }
    }

    CheckBoxList(Grid window) {
        super(new CheckListItem[]{
                new CheckListItem("Bottom"),
                new CheckListItem("Top"),
                new CheckListItem("Left"),
                new CheckListItem("Right")});


        setCellRenderer(new CheckListRenderer());
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                JList list = (JList) event.getSource();
                int index = list.locationToIndex(event.getPoint());

                CheckListItem item = (CheckListItem) list.getModel()
                        .getElementAt(index);
                item.setSelected(!item.isSelected());
                list.repaint(list.getCellBounds(index, index));
                if (selected_idx != -1) ((CheckListItem) list.getModel()
                        .getElementAt(selected_idx)).setSelected(false);
                if (selected_idx == index) selected_idx = -1;
                else selected_idx = index;
                window.setSelected(selected_idx);
            }
        });

    }
}
