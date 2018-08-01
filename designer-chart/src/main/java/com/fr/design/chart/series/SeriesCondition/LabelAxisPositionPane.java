package com.fr.design.chart.series.SeriesCondition;

import com.fr.chart.base.AttrAxisPosition;
import com.fr.chart.base.ChartAxisPosition;
import com.fr.chart.base.DataSeriesCondition;
import com.fr.design.condition.ConditionAttrSingleConditionPane;
import com.fr.design.condition.ConditionAttributesPane;
import com.fr.design.gui.ibutton.UIButtonGroup;
import com.fr.design.gui.ilable.UILabel;
import com.fr.general.ComparatorUtils;


/**
* @author richie
* @date 2015-03-26
* @since 8.0
*/
public class LabelAxisPositionPane extends ConditionAttrSingleConditionPane<DataSeriesCondition> {

    private UILabel nameLabel;
    private AttrAxisPosition axisPosition = new AttrAxisPosition();

    private UIButtonGroup<String> positionGroup;

    public LabelAxisPositionPane(ConditionAttributesPane conditionAttributesPane) {
        this(conditionAttributesPane, true);
    }

    public LabelAxisPositionPane(ConditionAttributesPane conditionAttributesPane, boolean isRemove) {
        super(conditionAttributesPane, isRemove);

        nameLabel = new UILabel(com.fr.design.i18n.Toolkit.i18nTextArray(new String[]{"ChartF-Axis", "Selection"}));
        if (isRemove) {
            this.add(nameLabel);
        }

        String[] names = new String[]{com.fr.design.i18n.Toolkit.i18nText("ChartF-MainAxis"), com.fr.design.i18n.Toolkit.i18nText("ChartF-SecondAxis")};
        String[] values = new String[]{ChartAxisPosition.AXIS_LEFT.getAxisPosition(), ChartAxisPosition.AXIS_RIGHT.getAxisPosition()};

        positionGroup = new UIButtonGroup<String>(names, values);
        positionGroup.setAllToolTips(names);

        this.add(positionGroup);

        positionGroup.setSelectedItem(ChartAxisPosition.AXIS_LEFT.getAxisPosition());

    }

    @Override
    public String nameForPopupMenuItem() {
        return com.fr.design.i18n.Toolkit.i18nText("Fine-Design_Chart_MajorMinorAxis");
    }

    @Override
    protected String title4PopupWindow() {
        return nameForPopupMenuItem();
    }

    public void populate(DataSeriesCondition condition) {
        if (condition instanceof AttrAxisPosition) {
            axisPosition = (AttrAxisPosition) condition;
            if (ComparatorUtils.equals(axisPosition.getAxisPosition(), ChartAxisPosition.AXIS_LEFT)) {
                positionGroup.setSelectedItem(ChartAxisPosition.AXIS_LEFT.getAxisPosition());
            } else {
                positionGroup.setSelectedItem(ChartAxisPosition.AXIS_RIGHT.getAxisPosition());
            }
        }
    }

    public DataSeriesCondition update() {
        axisPosition.setAxisPosition(ChartAxisPosition.parse(positionGroup.getSelectedItem()));
        return axisPosition;
    }
}