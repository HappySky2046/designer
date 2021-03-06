package com.fr.design.mainframe.chart.gui.style;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.*;

import com.fr.base.BaseUtils;
import com.fr.chart.chartattr.Axis;
import com.fr.design.gui.ibutton.UIButtonGroup;
import com.fr.design.gui.ilable.UILabel;
import com.fr.design.gui.icombobox.LineComboBox;
import com.fr.design.layout.TableLayout;
import com.fr.design.layout.TableLayoutHelper;
import com.fr.design.dialog.BasicPane;

import com.fr.stable.Constants;
import com.fr.stable.CoreConstants;
import com.fr.design.style.color.ColorSelectBox;

public class ChartAxisLineStylePane extends BasicPane{
	private static final long serialVersionUID = -2750577050658842948L;
	private LineComboBox axisLineStyle;
	private ColorSelectBox axisLineColor;
	private UIButtonGroup<Integer> mainTickPosition;
	private UIButtonGroup<Integer> secondTickPosition;
	
	public ChartAxisLineStylePane(){
		initComponents();
	}

	private void initComponents() {
		axisLineStyle = new LineComboBox(CoreConstants.LINE_STYLE_ARRAY_4_AXIS);
		axisLineColor = new ColorSelectBox(100);
		

		String[] nameArray = {com.fr.design.i18n.Toolkit.i18nText("Fine-Design_Basic_External"),com.fr.design.i18n.Toolkit.i18nText("Fine-Design_Report_Inner"),com.fr.design.i18n.Toolkit.i18nText("Fine-Design_Chart_Cross"),com.fr.design.i18n.Toolkit.i18nText("Fine-Design_Report_None")};
		Integer[] valueArray = {Constants.TICK_MARK_OUTSIDE, Constants.TICK_MARK_INSIDE, Constants.TICK_MARK_CROSS, Constants.TICK_MARK_NONE};
        Icon[] iconArray = {BaseUtils.readIcon("/com/fr/design/images/chart/ChartAxisLineStyle/external.png"),
                BaseUtils.readIcon("/com/fr/design/images/chart/ChartAxisLineStyle/inside.png"),
                BaseUtils.readIcon("/com/fr/design/images/chart/ChartAxisLineStyle/cross.png"),
                BaseUtils.readIcon("/com/fr/design/images/expand/none16x16.png")

        };
        mainTickPosition = new UIButtonGroup<Integer>(iconArray, valueArray);
        mainTickPosition.setAllToolTips(nameArray);
        secondTickPosition = new UIButtonGroup<Integer>(iconArray, valueArray);
        secondTickPosition.setAllToolTips(nameArray);


		double p = TableLayout.PREFERRED;
		double f = TableLayout.FILL;
		double[] columnSize = { p, f };
		double[] rowSize = { p,p,p,p,p,p};

        Component[][] components = new Component[][]{
                new Component[]{new UILabel(com.fr.design.i18n.Toolkit.i18nText("Fine-Design_Basic_Type")),axisLineStyle} ,
                new Component[]{new UILabel(com.fr.design.i18n.Toolkit.i18nText("Fine-Design_Basic_Color")),axisLineColor},
                new Component[]{new UILabel(com.fr.design.i18n.Toolkit.i18nText("Fine-Design_Chart_Main_Graduation_Line")),null},
                new Component[]{null, mainTickPosition},
                new Component[]{new UILabel(com.fr.design.i18n.Toolkit.i18nText("Fine-Design_Chart_Second_Graduation_Line")),null},
                new Component[]{null,secondTickPosition}
        } ;
        JPanel panel = TableLayoutHelper.createTableLayoutPane4Chart(new String[]{"Fine-Design_Chart_Axis_Style"},components,rowSize,columnSize);
        this.setLayout(new BorderLayout());
        this.add(panel,BorderLayout.CENTER);
	}
	
	@Override
	protected String title4PopupWindow() {
		return null;
	}
	
	public void populate(Axis axis) {
		axisLineStyle.setSelectedLineStyle(axis.getAxisStyle());
		axisLineColor.setSelectObject(axis.getAxisColor());
		mainTickPosition.setSelectedItem(axis.getTickMarkType());
		secondTickPosition.setSelectedItem(axis.getSecTickMarkType());
	}
	
	public void update(Axis axis) {
		axis.setAxisStyle(axisLineStyle.getSelectedLineStyle());
		axis.setAxisColor(axisLineColor.getSelectObject());
		if(mainTickPosition.getSelectedItem() != null) {
			axis.setTickMarkType(mainTickPosition.getSelectedItem());
		}
		if(secondTickPosition.getSelectedItem() != null) {
			axis.setSecTickMarkType(secondTickPosition.getSelectedItem());
		}
	}

}
