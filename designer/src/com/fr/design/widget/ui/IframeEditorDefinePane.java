package com.fr.design.widget.ui;

import com.fr.design.constants.LayoutConstants;
import com.fr.design.dialog.DialogActionListener;
import com.fr.design.dialog.UIDialog;
import com.fr.design.foldablepane.UIExpandablePane;
import com.fr.design.gui.frpane.ReportletParameterViewPane;
import com.fr.design.gui.ibutton.UIButton;
import com.fr.design.gui.icheckbox.UICheckBox;
import com.fr.design.gui.ilable.UILabel;
import com.fr.design.gui.itextfield.UITextField;
import com.fr.design.layout.FRGUIPaneFactory;
import com.fr.design.layout.TableLayout;
import com.fr.design.layout.TableLayoutHelper;
import com.fr.form.ui.IframeEditor;
import com.fr.general.Inter;
import com.fr.stable.ParameterProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class IframeEditorDefinePane extends AbstractDataModify<IframeEditor> {

    private static final int P_W = 610;
    private static final int P_H = 580;
    private UITextField srcTextField;
    private ReportletParameterViewPane parameterViewPane;
    private UICheckBox horizontalCheck;
    private UICheckBox verticalCheck;
    private UIButton parameterViewPaneButton;
    private List<ParameterProvider> list;


    public IframeEditorDefinePane() {
        this.initComponents();
    }

    private void initComponents() {
        this.setLayout(FRGUIPaneFactory.createBorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        JPanel contentPane = FRGUIPaneFactory.createY_AXISBoxInnerContainer_L_Pane();
        contentPane.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
        JPanel attr = FRGUIPaneFactory.createNormalFlowInnerContainer_M_Pane();
        attr.add(horizontalCheck = new UICheckBox(Inter.getLocText("Preference-Horizontal_Scroll_Bar_Visible")));
        attr.add(verticalCheck = new UICheckBox(Inter.getLocText("Preference-Vertical_Scroll_Bar_Visible")));
        contentPane.add(attr);
        double p = TableLayout.PREFERRED;
        double f = TableLayout.FILL;
        double[] rowSize = {p, p, p, p};
        double[] columnSize = {p, f};

        parameterViewPaneButton = new UIButton(Inter.getLocText("FR-Designer_Edit"));
        parameterViewPaneButton.addActionListener(parameterListener);
        parameterViewPane = new ReportletParameterViewPane();

        java.awt.Component[][] coms = {
                {horizontalCheck, null},
                {verticalCheck, null},
                {new UILabel(Inter.getLocText("Form-Url")), srcTextField = new UITextField()},
                {new UILabel(Inter.getLocText("FR-Designer_Parameters")), parameterViewPaneButton}};
        int[][] rowCount = {{1, 1}, {1, 1}, {1, 1}, {1, 1}};
        JPanel panel = TableLayoutHelper.createGapTableLayoutPane(coms, rowSize, columnSize, rowCount, 45, LayoutConstants.VGAP_LARGE);


        contentPane.add(panel);

        UIExpandablePane uiExpandablePane = new UIExpandablePane(Inter.getLocText("FR-Designer_Advanced"), 280, 24, contentPane);
        this.add(uiExpandablePane, BorderLayout.NORTH);

    }

    ActionListener parameterListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<ParameterProvider> paraList = parameterViewPane.update();
            list = new ArrayList<ParameterProvider>();
            ParameterProvider pr = null;
            for (ParameterProvider parameterProvider : paraList) {
                try {
                    pr = (ParameterProvider) parameterProvider.clone();
                } catch (CloneNotSupportedException e1) {
                    e1.printStackTrace();
                }
                list.add(pr);
            }

            UIDialog dialog = parameterViewPane.showUnsizedWindow(SwingUtilities.getWindowAncestor(new JPanel()), new DialogActionListener() {
                @Override
                public void doOk() {
                }

                @Override
                public void doCancel() {
                    parameterViewPane.update(list);
                }
            });
            dialog.setSize(P_W, P_H);
            dialog.setVisible(true);
        }
    };

    @Override
    protected String title4PopupWindow() {
        return "iframe";
    }

    @Override
    public void populateBean(IframeEditor e) {
        srcTextField.setText(e.getSrc());
        parameterViewPane.populate(e.getParameters());
        this.horizontalCheck.setSelected(e.isOverflowx());
        this.verticalCheck.setSelected(e.isOverflowy());
    }

    @Override
    public IframeEditor updateBean() {
        IframeEditor ob = new IframeEditor();
        ob.setSrc(srcTextField.getText());
        List<ParameterProvider> parameterList = parameterViewPane.update();
        ob.setParameters(parameterList.toArray(new ParameterProvider[parameterList.size()]));
        ob.setOverflowx(this.horizontalCheck.isSelected());
        ob.setOverflowy(this.verticalCheck.isSelected());
        return ob;
    }
}