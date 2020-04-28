package com.fr.van.chart.area;

import com.fr.chart.chartattr.Plot;
import com.fr.design.beans.BasicBeanPane;
import com.fr.design.layout.TableLayout;
import com.fr.design.layout.TableLayoutHelper;
import com.fr.design.mainframe.chart.gui.ChartStylePane;
import com.fr.van.chart.column.VanChartCustomStackAndAxisConditionPane;
import com.fr.van.chart.designer.component.VanChartBeautyPane;
import com.fr.van.chart.designer.component.VanChartLineTypePane;
import com.fr.van.chart.designer.component.VanChartLineTypePaneWithoutDashed;
import com.fr.van.chart.line.VanChartLineSeriesPane;

import javax.swing.JPanel;
import java.awt.Component;

public class VanChartAreaSeriesPane extends VanChartLineSeriesPane {

    private static final long serialVersionUID = 5497989595104913025L;

    public VanChartAreaSeriesPane(ChartStylePane parent, Plot plot) {
        super(parent, plot);
    }

    protected JPanel getContentInPlotType() {

        double p = TableLayout.PREFERRED;
        double f = TableLayout.FILL;
        double[] row = {p, p, p, p, p, p, p};
        double[] col = {f};

        Component[][] components = new Component[][]{
                new Component[]{createLineTypePane()},
                new Component[]{createMarkerPane()},
                new Component[]{createAreaFillColorPane()},
                new Component[]{createStackedAndAxisPane()},
                //大数据模式 恢复用注释。下面1行删除。
                new Component[]{createLargeDataModelPane()},
                new Component[]{createTrendLinePane()},
        };

        contentPane = TableLayoutHelper.createTableLayoutPane(components, row, col);
        return contentPane;
    }

    protected Class<? extends BasicBeanPane> getStackAndAxisPaneClass() {
        return VanChartCustomStackAndAxisConditionPane.class;
    }

    //风格
    protected VanChartBeautyPane createStylePane() {
        return new VanChartBeautyPane();
    }

    protected VanChartLineTypePane getLineTypePane() {
        return new VanChartLineTypePaneWithoutDashed();
    }
}