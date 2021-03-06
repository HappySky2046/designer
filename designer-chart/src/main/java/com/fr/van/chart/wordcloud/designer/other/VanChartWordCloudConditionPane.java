package com.fr.van.chart.wordcloud.designer.other;

import com.fr.chart.base.AttrBackground;
import com.fr.chart.chartattr.Plot;
import com.fr.design.chart.series.SeriesCondition.ChartConditionPane;
import com.fr.design.chart.series.SeriesCondition.DataSeriesConditionPane;
import com.fr.plugin.chart.attr.EffectHelper;
import com.fr.plugin.chart.base.AttrEffect;
import com.fr.plugin.chart.base.AttrTooltip;
import com.fr.plugin.chart.type.ConditionKeyType;
import com.fr.plugin.chart.wordcloud.VanChartWordCloudPlot;
import com.fr.van.chart.designer.other.condition.item.VanChartEffectConditionPane;
import com.fr.van.chart.designer.other.condition.item.VanChartSeriesColorConditionPane;
import com.fr.van.chart.designer.other.condition.item.VanChartTooltipConditionPane;

import java.awt.Dimension;

/**
 * Created by Mitisky on 16/11/29.
 */
public class VanChartWordCloudConditionPane  extends DataSeriesConditionPane {

    public VanChartWordCloudConditionPane(Plot plot) {
        super(plot);
    }

    protected void initComponents() {
        super.initComponents();
        //添加全部条件属性后被遮挡
        liteConditionPane.setPreferredSize(new Dimension(300, 400));
    }

    @Override
    protected ChartConditionPane createListConditionPane() {
        return new ChartConditionPane(){
            @Override
            protected ConditionKeyType[] conditionKeyTypes() {
                return ConditionKeyType.WORD_CLOUD_CONDITION_KEY_TYPES;
            }
        };
    }

    @Override
    protected void addBasicAction() {
        classPaneMap.put(AttrBackground.class, new VanChartSeriesColorConditionPane(this));
        classPaneMap.put(AttrTooltip.class, new VanChartTooltipConditionPane(this, plot));
        classPaneMap.put(AttrEffect.class, new VanChartEffectConditionPane(this, EffectHelper.getWordCloudPlotDefaultEffect()));
    }

    protected void addStyleAction() {
    }

    /**
     * 返回图表class对象
     * @return class对象
     */
    public Class<? extends Plot> class4Correspond() {
        return VanChartWordCloudPlot.class;
    }
}
