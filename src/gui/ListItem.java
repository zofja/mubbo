package gui;

import javax.swing.*;

/**
 * An object od class is an item of {@code ArrowList}.
 */
class ListItem {

    /**
     * Label of an item.
     */
    private String label;
    private ImageIcon icon;

    /**
     * True if an item is marked as checked.
     */
    private boolean isSelected = false;


    /**
     * @param label label of an item.
     */
    ListItem(String label, ImageIcon icon) {
        this.label = label;
        this.icon = icon;
    }

    ListItem(String label) {
        this.label = label;
    }

    ImageIcon getIcon() {
        return this.icon;
    }

    /**
     * @return true if item is marked as checked. Otherwise false.
     */
    boolean isSelected() {
        return isSelected;
    }

    /**
     * Changes {@code isSelected} attribute.
     *
     * @param isSelected new state of selection.
     */
    void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public String toString() {
        return label;
    }
}