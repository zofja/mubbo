package gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import sound.Scale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.File;
import java.util.Objects;

public class StartScreenUI {
    private JPanel rootPanel;
    private JLabel MuBbo;
    private JPanel NamePanel;
    private JPanel ReverbPanel;
    private JPanel StartPanel;
    private JSlider ReverbSlider;
    private JLabel ReverbSliderName;
    private JButton StartButton;
    private JPanel ScalePanel;
    private JComboBox ScaleList;
    private JLabel ScaleListName;
    private JPanel presetsPanel;
    private JLabel presetPanelDescription;
    private JComboBox presetsList;

    private static final int REVERB_MAX = 2000;
    private static final int REVERB_MIN = 25;
    private static final int REVERB_DEFAULT = 200;

    private String scaleToMuBbo = Scale.MAJOR.getDisplayName();
    private int reverbToMuBbo = 250;
    private String presetToMuBbo = "preset";

    private static JFrame frame;

    // have to add forms_rt.jar to project dependencies
    public StartScreenUI() {
        $$$setupUI$$$();
        StartButton.addActionListener(actionEvent -> {
            frame.dispose();
            new GameUI().main2(presetToMuBbo, scaleToMuBbo, reverbToMuBbo);
        });
        ReverbSlider.addChangeListener(changeEvent -> {
            JSlider source = (JSlider) changeEvent.getSource();
            int x = source.getValue();
            reverbToMuBbo = source.getValue();
        });
        ScaleList.addItemListener(itemEvent -> {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                ItemSelectable is = itemEvent.getItemSelectable();
                scaleToMuBbo = selectedString(is);
            }
        });
        presetsList.addItemListener(itemEvent -> {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                ItemSelectable is = itemEvent.getItemSelectable();
                presetToMuBbo = selectedString(is);
            }
        });
    }

    static private String selectedString(ItemSelectable is) {
        Object selected[] = is.getSelectedObjects();
        return ((selected.length == 0) ? "null" : (String) selected[0]);
    }

    public static void main(String[] args) {
        frame = new JFrame("StartScreenUI");
        frame.setContentPane(new StartScreenUI().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // scale list custom create
        ScaleList = new JComboBox();
        for (var scale : Scale.values()) {
            ScaleList.addItem(scale.getDisplayName());
        }
        ScaleList.setSelectedItem(Scale.MAJOR.getDisplayName());

        // reverb slider custom create
        ReverbSlider = new JSlider(REVERB_MIN, REVERB_MAX, REVERB_DEFAULT);

        // presets list custom create
        presetsList = new JComboBox();
        File folder = new File("presets");

        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.isFile()) {
                presetsList.addItem(file.getName());
            } else if (file.isDirectory()) {
            }
        }
        presetsList.setSelectedItem("preset");
    }


    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.setAutoscrolls(false);
        rootPanel.setBackground(new Color(-14869219));
        rootPanel.setEnabled(true);
        rootPanel.setFocusCycleRoot(false);
        rootPanel.setFocusTraversalPolicyProvider(true);
        rootPanel.setFocusable(false);
        Font rootPanelFont = this.$$$getFont$$$(null, -1, -1, rootPanel.getFont());
        if (rootPanelFont != null) rootPanel.setFont(rootPanelFont);
        rootPanel.setForeground(new Color(-5789785));
        rootPanel.setMinimumSize(new Dimension(700, 700));
        rootPanel.setOpaque(true);
        rootPanel.setPreferredSize(new Dimension(700, 700));
        NamePanel = new JPanel();
        NamePanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        NamePanel.setBackground(new Color(-13882324));
        NamePanel.setOpaque(false);
        rootPanel.add(NamePanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        MuBbo = new JLabel();
        MuBbo.setBackground(new Color(-13882324));
        Font MuBboFont = this.$$$getFont$$$("Noto Sans Mono CJK KR Bold", Font.PLAIN, 72, MuBbo.getFont());
        if (MuBboFont != null) MuBbo.setFont(MuBboFont);
        MuBbo.setForeground(new Color(-5789785));
        MuBbo.setHorizontalAlignment(0);
        MuBbo.setHorizontalTextPosition(0);
        MuBbo.setText("MuBbo");
        NamePanel.add(MuBbo, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ReverbPanel = new JPanel();
        ReverbPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        Font ReverbPanelFont = this.$$$getFont$$$("Noto Sans Mono CJK JP Regular", -1, 16, ReverbPanel.getFont());
        if (ReverbPanelFont != null) ReverbPanel.setFont(ReverbPanelFont);
        ReverbPanel.setOpaque(false);
        rootPanel.add(ReverbPanel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        ReverbSliderName = new JLabel();
        ReverbSliderName.setBackground(new Color(-5789785));
        ReverbSliderName.setFocusable(false);
        Font ReverbSliderNameFont = this.$$$getFont$$$("Noto Sans Mono CJK KR Regular", -1, 24, ReverbSliderName.getFont());
        if (ReverbSliderNameFont != null) ReverbSliderName.setFont(ReverbSliderNameFont);
        ReverbSliderName.setForeground(new Color(-5789785));
        ReverbSliderName.setOpaque(false);
        ReverbSliderName.setText("Set reverb:");
        ReverbPanel.add(ReverbSliderName, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ReverbSlider.setBackground(new Color(-5789785));
        Font ReverbSliderFont = this.$$$getFont$$$("Noto Sans Mono CJK KR Regular", -1, 20, ReverbSlider.getFont());
        if (ReverbSliderFont != null) ReverbSlider.setFont(ReverbSliderFont);
        ReverbSlider.setForeground(new Color(-5789785));
        ReverbSlider.setMajorTickSpacing(1975);
        ReverbSlider.setMaximum(2000);
        ReverbSlider.setMinimum(25);
        ReverbSlider.setOpaque(false);
        ReverbSlider.setPaintLabels(true);
        ReverbSlider.setPaintTrack(true);
        ReverbPanel.add(ReverbSlider, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        StartPanel = new JPanel();
        StartPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        StartPanel.setOpaque(false);
        StartPanel.setVisible(true);
        rootPanel.add(StartPanel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        StartButton = new JButton();
        StartButton.setBackground(new Color(-5789785));
        StartButton.setFocusPainted(true);
        Font StartButtonFont = this.$$$getFont$$$("Noto Sans Mono CJK KR Regular", Font.BOLD, 36, StartButton.getFont());
        if (StartButtonFont != null) StartButton.setFont(StartButtonFont);
        StartButton.setForeground(new Color(-14869219));
        StartButton.setOpaque(true);
        StartButton.setText("Start");
        StartPanel.add(StartButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ScalePanel = new JPanel();
        ScalePanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        ScalePanel.setOpaque(false);
        rootPanel.add(ScalePanel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        ScaleListName = new JLabel();
        ScaleListName.setBackground(new Color(-14869219));
        Font ScaleListNameFont = this.$$$getFont$$$("Noto Sans Mono CJK KR Regular", -1, 24, ScaleListName.getFont());
        if (ScaleListNameFont != null) ScaleListName.setFont(ScaleListNameFont);
        ScaleListName.setForeground(new Color(-5789785));
        ScaleListName.setText("Choose scale");
        ScalePanel.add(ScaleListName, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ScaleList.setBackground(new Color(-14869219));
        Font ScaleListFont = this.$$$getFont$$$("Noto Sans Mono CJK KR Regular", -1, 18, ScaleList.getFont());
        if (ScaleListFont != null) ScaleList.setFont(ScaleListFont);
        ScaleList.setForeground(new Color(-5789785));
        ScaleList.setName("");
        ScaleList.setOpaque(false);
        ScalePanel.add(ScaleList, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        presetsPanel = new JPanel();
        presetsPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        presetsPanel.setOpaque(false);
        rootPanel.add(presetsPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        presetPanelDescription = new JLabel();
        presetPanelDescription.setBackground(new Color(-14869219));
        Font presetPanelDescriptionFont = this.$$$getFont$$$("Noto Sans Mono CJK KR Regular", -1, 24, presetPanelDescription.getFont());
        if (presetPanelDescriptionFont != null) presetPanelDescription.setFont(presetPanelDescriptionFont);
        presetPanelDescription.setForeground(new Color(-5789785));
        presetPanelDescription.setText("Choose preset:");
        presetsPanel.add(presetPanelDescription, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        presetsList.setBackground(new Color(-14869219));
        Font presetsListFont = this.$$$getFont$$$("Noto Sans Mono CJK KR Regular", -1, 16, presetsList.getFont());
        if (presetsListFont != null) presetsList.setFont(presetsListFont);
        presetsList.setForeground(new Color(-5789785));
        presetsList.setOpaque(false);
        presetsPanel.add(presetsList, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

}
