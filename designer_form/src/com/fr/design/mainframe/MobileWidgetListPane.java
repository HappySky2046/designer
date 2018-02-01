package com.fr.design.mainframe;

import com.fr.design.designer.creator.XCreator;
import com.fr.design.gui.controlpane.UISimpleListControlPane;
import com.fr.form.ui.Widget;
import com.fr.form.ui.container.WSortLayout;
import com.fr.general.Inter;
import com.fr.general.NameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by plough on 2018/1/31.
 */
public class MobileWidgetListPane extends UISimpleListControlPane {
    public static final String LIST_NAME = "Widget_List";

    private FormDesigner designer;
    private List<String> cellData;
    private static final List<String> EMPTY_LIST = new ArrayList<String>();

    public MobileWidgetListPane(FormDesigner designer) {
        super();
        this.designer = designer;
        cellData = getData();

        List<NameObject> nameObjectList = new ArrayList<NameObject>();
        for (String name : cellData) {
            nameObjectList.add(new NameObject(name, null));
        }
        populate(nameObjectList.toArray(new NameObject[nameObjectList.size()]));
    }

    /**
     * 获取选中控件的控件列表
     *
     * @return String[][] 二维数组，[0][0]widgetName
     */
    private List<String> getData() {
        if (designer.isFormParaDesigner()) {
            return EMPTY_LIST;
        }

        //选择的控件
        XCreator selectedCreator = designer.getSelectionModel().getSelection().getSelectedCreator();
        Widget selectedModel = selectedCreator != null ? selectedCreator.toData() : null;

        if (selectedModel == null) {
            return new ArrayList<>();
        }

        // 选择的控件有两种类型，一种是WLayout，代表容器，一种是Widget，代表控件
        if (selectedModel.acceptType(WSortLayout.class)) {
            java.util.List<String> mobileWidgetList = ((WSortLayout) selectedModel).getOrderedMobileWidgetList();
            List<String> widgetName = new ArrayList<String>();
//            [mobileWidgetList.size() + 1][1];
//            widgetName[0][0] = Inter.getLocText("FR-Designer_WidgetOrder");
            for (int i = 0; i < mobileWidgetList.size(); i++) {
                widgetName.add(mobileWidgetList.get(i));
            }
            return widgetName;
        } else {
            return EMPTY_LIST;
        }
    }
}