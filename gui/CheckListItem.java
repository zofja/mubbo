
class CheckListItem {

    private String label;
    private boolean isSelected = false;

    CheckListItem(String label) {
        this.label = label;
    }

    boolean isSelected() {
        return isSelected;
    }

    void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public String toString() {
        return label;
    }
}