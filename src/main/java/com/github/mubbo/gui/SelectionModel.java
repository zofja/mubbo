package com.github.mubbo.gui;

import javax.swing.*;

public class SelectionModel extends DefaultListSelectionModel {
    private JList list;
    private int maxCount;

    public SelectionModel(JList list, int maxCount) {
        this.list = list;
        this.maxCount = maxCount;
    }

    @Override
    public void setSelectionInterval(int index0, int index1) {
        if (index1 - index0 >= maxCount) {
            index1 = index0 + maxCount - 1;
        }
        super.setSelectionInterval(index0, index1);
    }

    @Override
    public void addSelectionInterval(int index0, int index1) {
        int selectionLength = list.getSelectedIndices().length;
        if (selectionLength >= maxCount)
            return;

        if (index1 - index0 >= maxCount - selectionLength) {
            index1 = index0 + maxCount - 1 - selectionLength;
        }
        if (index1 < index0)
            return;
        super.addSelectionInterval(index0, index1);
    }
}