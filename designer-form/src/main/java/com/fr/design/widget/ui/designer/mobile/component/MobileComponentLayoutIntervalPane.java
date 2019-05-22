package com.fr.design.widget.ui.designer.mobile.component;

import com.fr.base.iofile.attr.AttrMarkFactory;
import com.fr.base.iofile.attr.FormBodyPaddingAttrMark;
import com.fr.design.designer.IntervalConstants;
import com.fr.design.foldablepane.UIExpandablePane;
import com.fr.design.gui.ilable.UILabel;
import com.fr.design.gui.ispinner.UISpinner;
import com.fr.design.layout.FRGUIPaneFactory;
import com.fr.design.layout.TableLayout;
import com.fr.design.layout.TableLayoutHelper;
import com.fr.design.utils.gui.UIComponentUtils;
import com.fr.design.widget.FRWidgetFactory;
import com.fr.design.widget.ui.designer.XmlRelationedBasicPane;
import com.fr.form.ui.RichStyleWidgetProvider;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;

/**
 * 只有组件间隔的布局设置
 */
public class MobileComponentLayoutIntervalPane extends XmlRelationedBasicPane {
    private UISpinner componentIntervel;

    public MobileComponentLayoutIntervalPane(String xmlTag) {
        super(xmlTag);
        this.setLayout(FRGUIPaneFactory.createBorderLayout());
        UILabel intervalLabel = FRWidgetFactory.createLineWrapLabel(com.fr.design.i18n.Toolkit.i18nText("Fine-Design_Form_Component_Interval"));

        double f = TableLayout.FILL;
        double p = TableLayout.PREFERRED;
        double[] rowSize = {p, p};
        double[] columnSize = {p, f};
        int[][] rowCount = {{1, 1}, {1, 1}};
        componentIntervel = new UISpinner(0, Integer.MAX_VALUE, 1, FormBodyPaddingAttrMark.DEFAULT_SIZE);
        JPanel componentIntervelPane = UIComponentUtils.wrapWithBorderLayoutPane(componentIntervel);

        Component[][] components = new Component[][]{
                new Component[]{intervalLabel, componentIntervelPane}
        };
        JPanel centerPane = TableLayoutHelper.createGapTableLayoutPane(components, rowSize, columnSize, rowCount, IntervalConstants.INTERVAL_W1, IntervalConstants.INTERVAL_L1);
        centerPane.setBorder(BorderFactory.createEmptyBorder(IntervalConstants.INTERVAL_L1, IntervalConstants.INTERVAL_L5, 0, 0));
        JPanel holder = FRGUIPaneFactory.createBorderLayout_S_Pane();
        holder.add(centerPane, BorderLayout.NORTH);
        UIExpandablePane layoutExpandablePane = new UIExpandablePane(com.fr.design.i18n.Toolkit.i18nText("Fine-Design_Form_Layout"), 280, 20, holder);
        this.add(layoutExpandablePane, BorderLayout.NORTH);
    }

    @Override
    protected String title4PopupWindow() {
        return "ComponentIntervelPane";
    }

    public void update(RichStyleWidgetProvider marginWidget) {

        FormBodyPaddingAttrMark attrMark = marginWidget.getWidgetAttrMark(getXmlTag());
        attrMark = attrMark == null ? (FormBodyPaddingAttrMark) AttrMarkFactory.createAttrMark(getXmlTag()) : attrMark;
        attrMark.setInterval((int) componentIntervel.getValue());
        marginWidget.addWidgetAttrMark(attrMark);
    }

    public void populate(RichStyleWidgetProvider marginWidget) {
        FormBodyPaddingAttrMark attrMark = marginWidget.getWidgetAttrMark(getXmlTag());
        if (attrMark != null) {
            componentIntervel.setValueWithoutEvent(attrMark.getInterval());
        }
    }
}