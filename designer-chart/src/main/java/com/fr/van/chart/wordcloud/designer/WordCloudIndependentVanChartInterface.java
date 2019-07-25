package com.fr.van.chart.wordcloud.designer;

import com.fr.chart.chartattr.Chart;
import com.fr.chart.chartattr.Plot;
import com.fr.design.beans.BasicBeanPane;
import com.fr.design.chartx.WordCloudChartDataPane;
import com.fr.design.condition.ConditionAttributesPane;
import com.fr.design.dialog.BasicPane;
import com.fr.design.gui.frpane.AttributeChangeListener;
import com.fr.design.mainframe.chart.AbstractChartAttrPane;
import com.fr.design.mainframe.chart.gui.ChartDataPane;
import com.fr.design.mainframe.chart.gui.ChartStylePane;
import com.fr.design.mainframe.chart.gui.type.AbstractChartTypePane;
import com.fr.plugin.chart.base.VanChartConstants;
import com.fr.van.chart.designer.other.VanChartInteractivePaneWithOutSort;
import com.fr.van.chart.designer.other.VanChartOtherPane;
import com.fr.van.chart.designer.style.VanChartStylePane;
import com.fr.van.chart.vanchart.AbstractIndependentVanChartUI;
import com.fr.van.chart.wordcloud.designer.other.VanChartWordCloudConditionPane;
import com.fr.van.chart.wordcloud.designer.style.VanChartWordCloudSeriesPane;
import com.fr.van.chart.wordcloud.designer.type.VanChartWordCloudTypePane;

import java.util.List;

/**
 * Created by Mitisky on 16/11/29.
 */
public class WordCloudIndependentVanChartInterface extends AbstractIndependentVanChartUI{
    /**
     * 图表的类型定义界面类型，就是属性表的第一个界面
     *
     * @return 图表的类型定义界面类型
     */
    @Override
    public AbstractChartTypePane getPlotTypePane() {
        return new VanChartWordCloudTypePane();
    }

    /**
     * 图标路径
     *
     * @return 图标路径
     */
    @Override
    public String getIconPath() {
        return "com/fr/design/images/form/toolbar/wordcloud.png";
    }

    @Override
    public BasicBeanPane<Plot> getPlotSeriesPane(ChartStylePane parent, Plot plot) {
        return new VanChartWordCloudSeriesPane(parent, plot);
    }

    @Override
    public ConditionAttributesPane getPlotConditionPane(Plot plot) {
        return new VanChartWordCloudConditionPane(plot);
    }

    /**
     * 图表的属性界面数组
     * @return 属性界面
     */
    public AbstractChartAttrPane[] getAttrPaneArray(AttributeChangeListener listener){
        VanChartStylePane stylePane = new VanChartStylePane(listener){
            @Override
            protected void createVanChartLabelPane(List<BasicPane> paneList) {
            }
        };
        VanChartOtherPane otherPane = new VanChartOtherPane(){
            @Override
            protected BasicBeanPane<Chart> createInteractivePane() {
                return new VanChartInteractivePaneWithOutSort(){

                    @Override
                    protected String[] getNameArray() {
                        return new String[]{com.fr.design.i18n.Toolkit.i18nText("Fine-Design_Chart_XY_Axis"),com.fr.design.i18n.Toolkit.i18nText("Fine-Design_Chart_Use_None")};
                    }

                    @Override
                    protected String[] getValueArray() {
                        return new String[]{VanChartConstants.ZOOM_TYPE_XY, VanChartConstants.ZOOM_TYPE_NONE};

                    }
                };
            }
        };
        return new AbstractChartAttrPane[]{stylePane, otherPane};
    }

    public String getPlotTypeTitle4PopupWindow(){
        return VanChartWordCloudTypePane.TITLE;
    }

    @Override
    public ChartDataPane getChartDataPane(AttributeChangeListener listener) {
        return new WordCloudChartDataPane(listener);
    }
}
