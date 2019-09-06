package com.fr.van.chart.multilayer;

import com.fr.chart.chartattr.Chart;
import com.fr.chart.chartattr.Plot;
import com.fr.log.FineLoggerFactory;
import com.fr.plugin.chart.base.VanChartTools;
import com.fr.plugin.chart.multilayer.MultiPieIndependentVanChart;
import com.fr.plugin.chart.multilayer.VanChartMultiPiePlot;
import com.fr.plugin.chart.vanchart.VanChart;
import com.fr.van.chart.designer.type.AbstractVanChartTypePane;

/**
 * Created by Fangjie on 2016/6/15.
 */
public class VanChartMultiPiePlotPane extends AbstractVanChartTypePane {
    @Override
    protected String[] getTypeIconPath() {
        return new String[]{"/com/fr/van/chart/multilayer/image/multiPie.png"
        };
    }

    @Override
    protected Plot getSelectedClonedPlot(){
        VanChartMultiPiePlot newPlot = null;
        Chart[] multilayerCharts = MultiPieIndependentVanChart.MultilayerVanChartTypes;
        for(int i = 0, len = multilayerCharts.length; i < len; i++){
            if(typeDemo.get(i).isPressing){
                newPlot = (VanChartMultiPiePlot) multilayerCharts[i].getPlot();
            }
        }

        Plot cloned = null;
        try {
            cloned = (Plot)newPlot.clone();
        } catch (CloneNotSupportedException e) {
            FineLoggerFactory.getLogger().error("Error In MultiPieChart");
        }
        return cloned;
    }

    /**
     * 防止新建其他图表从而切换很卡
     * @return
     */
    public Chart getDefaultChart() {
        return MultiPieIndependentVanChart.MultilayerVanChartTypes[0];
    }

    protected void resetChartAttr(Chart chart, Plot newPlot){
        super.resetChartAttr(chart, newPlot);
        //重置工具栏选项
        VanChartTools tools = ((VanChart) chart).getVanChartTools();
        if (tools != null) {
            tools.setSort(false);
            tools.setFullScreen(false);
            tools.setExport(false);
        }
    }
}
