package com.fr.design.mainframe.chart.info;

import com.fr.base.io.BaseBook;
import com.fr.chartx.attr.ChartProvider;
import com.fr.chartx.config.info.AbstractConfig;
import com.fr.chartx.config.info.constant.ConfigType;
import com.fr.design.mainframe.burying.point.AbstractPointCollector;
import com.fr.design.mainframe.template.info.TemplateInfo;
import com.fr.design.mainframe.template.info.TemplateProcessInfo;
import com.fr.general.ComparatorUtils;
import com.fr.log.FineLoggerFactory;
import com.fr.stable.ProductConstants;
import com.fr.stable.StableUtils;
import com.fr.stable.StringUtils;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLableReader;
import com.fr.third.joda.time.DateTime;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bjorn
 * @version 10.0
 * Created by Bjorn on 2020-02-18
 */
public class ChartInfoCollector extends AbstractPointCollector<ChartInfo> {
    private static final String XML_TAG = "ChartInfoCollector";
    private static final String XML_LAST_EDIT_DAY = "lastEditDay";

    private static final String XML_CHART_INFO_LIST = "ChartInfoList";
    private static final String XML_FILE_NAME = "chart.info";

    private static ChartInfoCollector instance;

    private Map<String, ChartInfo> chartInfoCacheMap;

    private String lastEditDay;

    private ChartInfoCollector() {
        init();
    }

    private void init() {
        chartInfoCacheMap = new HashMap<>();
    }

    public static ChartInfoCollector getInstance() {
        if (instance == null) {
            instance = new ChartInfoCollector();
        }
        return instance;
    }

    public void collection(ChartProvider chartProvider, String createTime) {
        collection(chartProvider, createTime, false);
    }

    /**
     * 新建图表，保存状态
     */
    public void collection(ChartProvider chartProvider, String createTime, boolean isReuse) {
        String chartId = chartProvider.getChartUuid();
        if (!shouldCollectInfo() || StringUtils.isEmpty(chartId)) {
            return;
        }
        ChartInfo chartInfo = ChartInfo.newInstance(chartProvider, createTime, true, isReuse);
        chartInfoCacheMap.put(chartId, chartInfo);
    }

    /**
     * 图表编辑，更新编辑时间
     */
    public void updateChartPropertyTime(ChartProvider chartProvider) {
        if (!shouldCollectInfo() || StringUtils.isEmpty(chartProvider.getChartUuid())) {
            return;
        }
        ChartInfo chartInfo = getOrCreateChartInfo(chartProvider);

        //更新编辑时间
        chartInfo.updatePropertyTime();

        //重置计数
        chartInfo.resetIdleDayCount();
    }

    /**
     * 图表编辑，更新编辑时间
     */
    public void updateChartConfig(ChartProvider chartProvider, ConfigType configType, AbstractConfig config) {
        if (!shouldCollectInfo() || StringUtils.isEmpty(chartProvider.getChartUuid())) {
            return;
        }
        ChartInfo chartInfo = getOrCreateChartInfo(chartProvider);

        //更新对应的配置
        chartInfo.updateChartConfig(configType, config);

        //重置计数
        chartInfo.resetIdleDayCount();
    }

    /**
     * 图表子类型更新
     */
    public void updateChartMiniType(ChartProvider chartProvider) {
        if (!shouldCollectInfo() || StringUtils.isEmpty(chartProvider.getChartUuid())) {
            return;
        }
        ChartInfo chartInfo = getOrCreateChartInfo(chartProvider);

        //图表子类型更新
        chartInfo.resetChartConfigInfo(chartProvider);

        //重置计数
        chartInfo.resetIdleDayCount();
    }

    /**
     * 图表类型变化，更新类型和类型确认时间
     */
    public void updateChartTypeTime(ChartProvider chartProvider, String oldType) {
        if (!shouldCollectInfo() || StringUtils.isEmpty(chartProvider.getChartUuid())) {
            return;
        }

        ChartInfo chartInfo = getOrCreateChartInfo(chartProvider, oldType);

        //更新类型确认时间和类型
        chartInfo.updateChartType(chartProvider);

        //重置计数
        chartInfo.resetIdleDayCount();
    }

    private ChartInfo getOrCreateChartInfo(ChartProvider chartProvider) {
        return getOrCreateChartInfo(chartProvider, null);
    }

    private ChartInfo getOrCreateChartInfo(ChartProvider chartProvider, String oldType) {
        String chartId = chartProvider.getChartUuid();
        //缓存中有从缓存中拿
        if (chartInfoCacheMap.containsKey(chartId)) {
            return chartInfoCacheMap.get(chartId);
        }
        //缓存中没有从文件中读取的信息中拷贝到缓存
        if (pointInfoMap.containsKey(chartId)) {
            ChartInfo chartInfo = pointInfoMap.get(chartId).clone();
            chartInfoCacheMap.put(chartId, chartInfo);
            return chartInfo;
        }
        //都没有的话创建一个并加入到缓存中
        ChartInfo chartInfo = ChartInfo.newInstance(chartProvider);
        if (StringUtils.isNotEmpty(oldType)) {
            chartInfo.updateFirstType(oldType);
        }
        chartInfoCacheMap.put(chartId, chartInfo);
        return chartInfo;
    }

    public void checkTestChart(ChartProvider chartProvider) {
        if (!shouldCollectInfo()) {
            return;
        }
        ChartInfo chartInfo = chartInfoCacheMap.get(chartProvider.getChartUuid());
        if (chartInfo != null) {
            boolean testChart = chartProvider.isTestChart();
            chartInfo.setTestChart(testChart);
        }
    }

    /**
     * 保存模板的时候将该模板中的图表埋点信息保存
     */
    @Override
    public void collectInfo(String templateId, String originID, TemplateProcessInfo processInfo, int timeConsume) {
        if (!shouldCollectInfo()) {
            return;
        }
        if (StringUtils.isEmpty(originID)) {
            originID = templateId;
        }
        boolean testTemplate = isTestTemplate(processInfo);

        for (ChartInfo chartInfo : pointInfoMap.values()) {
            if (originID.equals(chartInfo.getTemplateId())) {
                chartInfo.setTemplateId(templateId);
                chartInfo.setTestTemplate(testTemplate);
            }
        }

        for (ChartInfo chartInfo : chartInfoCacheMap.values()) {
            BaseBook book = chartInfo.getBook();
            if ((book != null && templateId.equals(book.getTemplateID())) ||
                    originID.equals(chartInfo.getTemplateId())) {
                chartInfo.setTemplateId(templateId);
                chartInfo.setTestTemplate(testTemplate);
                pointInfoMap.put(chartInfo.getChartId(), chartInfo);
            }
        }

        // 每次更新之后，都同步到暂存文件中
        saveInfo();
    }

    private boolean isTestTemplate(TemplateProcessInfo processInfo) {
        int reportType = processInfo.getReportType();
        int cellCount = processInfo.getCellCount();
        int floatCount = processInfo.getFloatCount();
        int blockCount = processInfo.getBlockCount();
        int widgetCount = processInfo.getWidgetCount();

        return TemplateInfo.isTestTemplate(reportType, cellCount, floatCount, blockCount, widgetCount);
    }

    /**
     * 更新 day_count：打开设计器却未编辑图表的连续日子
     */
    @Override
    protected void addIdleDayCount() {
        // 判断今天是否第一次打开设计器，为了防止同一天内，多次 addIdleDayCount
        String today = DateTime.now().toString("yyyy-MM-dd");
        if (ComparatorUtils.equals(today, lastEditDay)) {
            return;
        }
        for (ChartInfo chartInfo : pointInfoMap.values()) {
            chartInfo.addIdleDayCountByOne();
        }
        lastEditDay = today;
    }


    /**
     * 获取缓存文件存放路径
     */
    @Override
    protected File getInfoFile() {
        File file = new File(StableUtils.pathJoin(ProductConstants.getEnvHome(), XML_FILE_NAME));
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception ex) {
            FineLoggerFactory.getLogger().error(ex.getMessage(), ex);
        }
        return file;
    }

    @Override
    public void readXML(XMLableReader reader) {
        if (reader.isChildNode()) {
            try {
                String name = reader.getTagName();
                if (ChartInfo.XML_TAG.equals(name)) {
                    ChartInfo chartInfo = ChartInfo.newInstanceByRead(reader);
                    pointInfoMap.put(chartInfo.getChartId(), chartInfo);
                } else if (XML_LAST_EDIT_DAY.equals(name)) {
                    lastEditDay = reader.getElementValue();
                }
            } catch (Exception ex) {
                // 什么也不做，使用默认值
            }
        }
    }

    @Override
    public void writeXML(XMLPrintWriter writer) {
        writer.startTAG(XML_TAG);

        writer.startTAG(XML_LAST_EDIT_DAY);
        writer.textNode(lastEditDay);
        writer.end();

        writer.startTAG(XML_CHART_INFO_LIST);
        for (ChartInfo chartInfo : pointInfoMap.values()) {
            chartInfo.writeXML(writer);
        }
        writer.end();

        writer.end();
    }
}
