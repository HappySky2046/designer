package com.fr.van.chart.bar;

import com.fr.chart.chartattr.Plot;
import com.fr.design.beans.BasicBeanPane;
import com.fr.design.condition.ConditionAttributesPane;
import com.fr.design.gui.frpane.AttributeChangeListener;
import com.fr.design.i18n.Toolkit;
import com.fr.design.mainframe.chart.AbstractChartAttrPane;
import com.fr.design.mainframe.chart.gui.ChartStylePane;
import com.fr.design.mainframe.chart.gui.type.AbstractChartTypePane;
import com.fr.van.chart.column.VanChartColumnConditionPane;
import com.fr.van.chart.column.VanChartColumnSeriesPane;
import com.fr.van.chart.designer.other.VanChartOtherPane;
import com.fr.van.chart.designer.style.VanChartStylePane;
import com.fr.van.chart.vanchart.AbstractMultiCategoryVanChartUI;

/**
 * Created by Mitisky on 15/10/20.
 */
public class BarIndependentVanChartInterface extends AbstractMultiCategoryVanChartUI {

    @Override
    public String getName() {
        return Toolkit.i18nText("Fine-Design_Chart_New_Bar");
    }

    @Override
    public String[] getSubName() {
        String bar = Toolkit.i18nText("Fine-Design_Chart_New_Bar");
        String stack = Toolkit.i18nText("Fine-Design_Chart_Stacked");
        String percent = Toolkit.i18nText("Fine-Design_Chart_Use_Percent");
        return new String[]{
                bar,
                stack + bar,
                percent + stack + bar,
                Toolkit.i18nText("Fine-Design_Chart_Mode_Custom")
        };
    }

    @Override
    public String getIconPath() {
        return "com/fr/design/images/form/toolbar/bar.png";
    }

    @Override
    public AbstractChartTypePane getPlotTypePane() {
        return new VanChartBarPlotPane();
    }

    public ConditionAttributesPane getPlotConditionPane(Plot plot){
        return new VanChartColumnConditionPane(plot);
    }

    public BasicBeanPane<Plot> getPlotSeriesPane(ChartStylePane parent, Plot plot){
        return new VanChartColumnSeriesPane(parent, plot);
    }

    /**
     * 图表的属性界面数组
     * @return 属性界面
     */
    public AbstractChartAttrPane[] getAttrPaneArray(AttributeChangeListener listener){
        VanChartStylePane stylePane = new VanChartBarStylePane(listener);
        VanChartOtherPane otherPane = new VanChartOtherPane();
        return new AbstractChartAttrPane[]{stylePane, otherPane};
    }

}