package com.fr.design.mainframe.widget.ui;

import com.fr.design.data.DataCreatorUI;
import com.fr.design.designer.creator.*;
import com.fr.design.dialog.BasicScrollPane;
import com.fr.design.foldablepane.UIExpandablePane;
import com.fr.design.gui.frpane.AbstractAttrNoScrollPane;
import com.fr.design.gui.frpane.AttributeChangeListener;
import com.fr.design.layout.FRGUIPaneFactory;
import com.fr.design.mainframe.DesignerContext;
import com.fr.design.mainframe.FormDesigner;
import com.fr.design.widget.DataModify;
import com.fr.design.widget.Operator;
import com.fr.design.widget.FormWidgetDefinePaneFactoryBase;
import com.fr.design.widget.ui.designer.component.WidgetAbsoluteBoundPane;
import com.fr.design.widget.ui.designer.component.WidgetBoundPane;
import com.fr.form.ui.Widget;
import com.fr.form.ui.container.WScaleLayout;
import com.fr.form.ui.container.WTitleLayout;
import com.fr.form.ui.widget.CRBoundsWidget;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ibm on 2017/7/25.
 */
public class FormWidgetCardPane extends AbstractAttrNoScrollPane {
    private AttributeChangeListener listener2;
    private FormDesigner designer;
    //当前的编辑器属性定义面板
    private DataModify<Widget> currentEditorDefinePane;
    private FormBasicPropertyPane widgetPropertyPane;
    private JPanel attriCardPane;

    private XCreator xCreator;
    private WidgetBoundPane widgetBoundPane;


    public FormWidgetCardPane(FormDesigner designer) {
        super();
        this.xCreator = findXcreator(designer);
        this.designer = designer;
        initComponents();
        initDefinePane();
        widgetBoundPane = createWidgetBoundPane(xCreator);
        if (widgetBoundPane != null) {
            attriCardPane.add(widgetBoundPane, BorderLayout.CENTER);
        }
    }

    public XLayoutContainer getParent(XCreator source) {
        XLayoutContainer container = XCreatorUtils.getParentXLayoutContainer(source);
        if (source.acceptType(XWFitLayout.class) || source.acceptType(XWParameterLayout.class)) {
            container = null;
        }
        return container;
    }

    public WidgetBoundPane createWidgetBoundPane(XCreator xCreator) {
        XLayoutContainer xLayoutContainer = getParent(xCreator);
        if (xLayoutContainer == null || xCreator instanceof XWParameterLayout || xCreator instanceof XWAbsoluteLayout) {
            return null;
        } else if (xLayoutContainer instanceof XWAbsoluteLayout) {
            return new WidgetAbsoluteBoundPane(xCreator);
        }
        return new WidgetBoundPane(xCreator);
    }

    protected JPanel createContentPane() {
        return null;
    }

    public XCreator findXcreator(FormDesigner designer) {
        int size = designer.getSelectionModel().getSelection().size();
        if (size == 0 || size == 1) {
            XCreator creator = size == 0 ? designer.getRootComponent() : designer.getSelectionModel().getSelection()
                    .getSelectedCreator();
            return creator;
        } else {
            return null;
        }

    }

    /**
     * 后台初始化所有事件.
     */
    public void initAllListeners() {

    }

    /**
     * 后台初始化所有事件.
     */
    public void reinitAllListeners() {
        initListener(this);
    }


    protected void initContentPane() {
    }

    private void initComponents() {
        this.setLayout(FRGUIPaneFactory.createBorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        final JPanel jPanel = FRGUIPaneFactory.createBorderLayout_S_Pane();
        BasicScrollPane basicScrollPane = new BasicScrollPane() {
            @Override
            protected JPanel createContentPane() {
                return jPanel;
            }

            @Override
            public void populateBean(Object ob) {

            }

            @Override
            protected String title4PopupWindow() {
                return null;
            }
        };
        this.add(basicScrollPane, BorderLayout.CENTER);

        if (xCreator.supportSetVisibleOrEnable()) {
            widgetPropertyPane = new FormBasicWidgetPropertyPane();
        } else {
            widgetPropertyPane = new FormBasicPropertyPane();
        }

        UIExpandablePane uiExpandablePane = new UIExpandablePane("基本", 280, 20, widgetPropertyPane);

        jPanel.add(uiExpandablePane, BorderLayout.NORTH);

        attriCardPane = FRGUIPaneFactory.createBorderLayout_S_Pane();

        jPanel.add(attriCardPane, BorderLayout.CENTER);

        this.listener2 = new AttributeChangeListener() {
            @Override
            public void attributeChange() {
                updateCreator();
            }
        };

    }

    private void initDefinePane() {
        currentEditorDefinePane = null;
        XCreator creator = xCreator;
        if (xCreator.acceptType(XWScaleLayout.class)) {
            if (xCreator.getComponentCount() > 0 && ((XCreator) xCreator.getComponent(0)).shouldScaleCreator()) {
                creator = (XCreator) xCreator.getComponent(0);
            }
        }
        if(xCreator.acceptType(XWTitleLayout.class)){
            creator = (XCreator) xCreator.getComponent(0);
        }
        FormWidgetDefinePaneFactoryBase.RN rn = FormWidgetDefinePaneFactoryBase.createWidgetDefinePane(creator, creator.toData(), new Operator() {
            @Override
            public void did(DataCreatorUI ui, String cardName) {

            }
        });
        DataModify<Widget> definePane = rn.getDefinePane();

        JComponent jComponent = definePane.toSwingComponent();

        attriCardPane.add(jComponent, BorderLayout.NORTH);
        currentEditorDefinePane = definePane;
    }


    @Override
    public String title4PopupWindow() {
        return "Widget";
    }

    public void populate() {
        //populate之前先移除所有的监听
        removeAttributeChangeListener();
        Widget cellWidget = xCreator.toData();
        if (widgetBoundPane != null) {
            widgetBoundPane.populate();
        }
        if (cellWidget instanceof WScaleLayout) {
            Widget crBoundsWidget = ((WScaleLayout) cellWidget).getBoundsWidget();
            currentEditorDefinePane.populateBean(((CRBoundsWidget) crBoundsWidget).getWidget());
        } else if(cellWidget instanceof WTitleLayout){
            CRBoundsWidget crBoundsWidget = ((WTitleLayout) cellWidget).getBodyBoundsWidget();
            currentEditorDefinePane.populateBean(crBoundsWidget.getWidget());
        }else{
            currentEditorDefinePane.populateBean(cellWidget);
        }
        widgetPropertyPane.populate(cellWidget);
        reinitAllListeners();
        this.addAttributeChangeListener(listener2);
    }


    public void updateCreator() {
        Widget widget = currentEditorDefinePane.updateBean();
        widgetPropertyPane.update(widget);
        if (widgetBoundPane != null) {
            widgetBoundPane.update();
        }
        fireValueChanged();

        if (xCreator instanceof XWScaleLayout) {
            XCreator xCreator1 = xCreator.getEditingChildCreator();
            xCreator1.resetData(widget);
            xCreator.removeAll();
            xCreator.add(xCreator1);
        }else if(xCreator instanceof XWTitleLayout){
            XCreator xCreator1 = ((XWTitleLayout) xCreator).getXCreator(0);
            xCreator1.resetData(widget);
            xCreator.removeAll();
            xCreator.add(xCreator1);
        } else {
            xCreator.resetData(widget);
        }
    }


    @Override
    /**
     *检查
     */
    public void checkValid() throws Exception {
        currentEditorDefinePane.checkValid();
    }

    public void fireValueChanged() {
        designer.repaint();
        DesignerContext.getDesignerFrame().getSelectedJTemplate().fireTargetModified();
    }

    public String getIconPath() {
        return "";
    }


}
