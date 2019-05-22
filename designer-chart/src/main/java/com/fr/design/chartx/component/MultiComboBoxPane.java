package com.fr.design.chartx.component;

import com.fr.chartx.data.field.ColumnField;
import com.fr.design.chartx.fields.AbstractDataSetFieldsPane;
import com.fr.design.gui.icombobox.UIComboBox;
import com.fr.extended.chart.UIComboBoxWithNone;

/**
 * Created by shine on 2019/4/12.
 */
public class MultiComboBoxPane extends AbstractMultiComponentPane<UIComboBox> {

    @Override
    protected UIComboBox createFirstFieldComponent() {
        return new UIComboBoxWithNone();
    }

    @Override
    protected UIComboBox createOtherFieldComponent() {
        return new UIComboBox();
    }

    @Override
    protected void populateField(UIComboBox component, ColumnField field) {
        AbstractDataSetFieldsPane.populateField(component, field);
    }

    @Override
    protected void updateField(UIComboBox component, ColumnField field) {
        AbstractDataSetFieldsPane.updateField(component, field);
    }
}
