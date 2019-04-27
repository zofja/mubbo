package gui;

/**
 * An object od class is an item of {@code CheckBoxList}.
 */
class CheckListItem {

    /**
     * Label of an item.
     */
    private String label;

    /**
     * True if an item is marked as checked.
     */
    private boolean isSelected = false;


    /**
     * @param label label of an item.
     */
    CheckListItem(String label) {
        this.label = label;
    }

    /**
     * @return true if item is marked as checked. Otherwise false.
     */
    boolean isSelected() {
        return isSelected;
    }

    /**
     * Changes {@code isSelected} attribute.
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