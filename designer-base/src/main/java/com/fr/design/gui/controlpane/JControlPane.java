package com.fr.design.gui.controlpane;

import com.fr.design.dialog.BasicPane;
import com.fr.design.gui.controlpane.shortcutfactory.AbstractShortCutFactory;
import com.fr.design.gui.controlpane.shortcutfactory.OldShortCutFactory;
import com.fr.design.gui.ilable.UILabel;
import com.fr.design.gui.itoolbar.UIToolbar;
import com.fr.design.layout.FRGUIPaneFactory;
import com.fr.design.menu.ToolBarDef;
import com.fr.design.utils.gui.GUICoreUtils;
import com.fr.stable.ArrayUtils;
import com.fr.stable.Nameable;

import javax.swing.*;
import java.awt.*;

/**
 * Coder: zack
 * Date: 2016/5/17
 * Time: 15:07
 */
abstract class JControlPane extends BasicPane implements UnrepeatedNameHelper, ShortCutListenerProvider {
    private static final int SHORT_WIDTH = 30; //每加一个short Divider位置加30
    private static final String SELECT = "SELECT";
    private static final String EDIT = "EDIT";
    JPanel controlUpdatePane;

    ShortCut4JControlPane[] shorts;
    NameableCreator[] creators;
    private ToolBarDef toolbarDef;

    UIToolbar toolBar;
    // peter:这是整体的一个cardLayout Pane
    protected CardLayout cardLayout;

    protected JPanel cardPane;
    protected AbstractShortCutFactory shortCutFactory;

    JControlPane() {
        this.initShortCutFactory();
        this.initComponentPane();
    }

    protected void initShortCutFactory() {
        this.shortCutFactory = OldShortCutFactory.newInstance(this);
    }

    /**
     * 生成添加按钮的NameableCreator
     *
     * @return 按钮的NameableCreator
     */
    public abstract NameableCreator[] createNameableCreators();

    public ShortCut4JControlPane[] getShorts() {
        return shorts;
    }

    public void setCreators(NameableCreator[] creators) {
        this.creators = creators;
    }

    public ToolBarDef getToolbarDef() {
        return toolbarDef;
    }

    public void setToolbarDef(ToolBarDef toolbarDef) {
        this.toolbarDef = toolbarDef;
    }

    public UIToolbar getToolBar() {
        return toolBar;
    }

    public void setToolBar(UIToolbar toolBar) {
        this.toolBar = toolBar;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public void setCardLayout(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
    }

    public JPanel getCardPane() {
        return cardPane;
    }

    public void setCardPane(JPanel cardPane) {
        this.cardPane = cardPane;
    }

    protected void initComponentPane() {
        this.setLayout(FRGUIPaneFactory.createBorderLayout());
        this.creators = this.createNameableCreators();
        initCardPane();
        // SplitPane
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, getLeftPane(), cardPane);
        mainSplitPane.setBorder(BorderFactory.createLineBorder(GUICoreUtils.getTitleLineBorderColor()));
        mainSplitPane.setOneTouchExpandable(true);

        this.add(mainSplitPane, BorderLayout.CENTER);
        mainSplitPane.setDividerLocation(getLeftPreferredSize());
        this.checkButtonEnabled();
    }

    protected void initCardPane() {
        this.controlUpdatePane = createControlUpdatePane();

        // p: edit card layout
        this.cardLayout = new CardLayout();
        cardPane = FRGUIPaneFactory.createCardLayout_S_Pane();
        cardPane.setLayout(this.cardLayout);
        // p:选择的Label
        UILabel selectLabel = new UILabel();
        cardPane.add(selectLabel, SELECT);
        cardPane.add(controlUpdatePane, EDIT);
    }

    public void showEditPane() {
        this.cardLayout.show(cardPane, EDIT);
    }

    public void showSelectPane() {
        this.cardLayout.show(cardPane, SELECT);
    }

    protected abstract JPanel createControlUpdatePane();

    protected void initToolBar() {
        toolbarDef = new ToolBarDef();
        for (ShortCut4JControlPane sj : shorts) {
            toolbarDef.addShortCut(sj.getShortCut());
        }
        toolBar = ToolBarDef.createJToolBar();
        toolbarDef.updateToolBar(toolBar);
    }

    protected JPanel getLeftPane() {
        // LeftPane
        JPanel leftPane = FRGUIPaneFactory.createBorderLayout_S_Pane();

        initLeftPane(leftPane);

        shorts = this.createShortcuts();
        if (ArrayUtils.isEmpty(shorts)) {
            return leftPane;
        }

        initToolBar();

        leftPane.add(toolBar, BorderLayout.NORTH);
        return leftPane;
    }

    /**
     * 初始化左边面板
     */
    protected void initLeftPane(JPanel leftPane) {

    }

    protected int getLeftPreferredSize() {
        return shorts.length * SHORT_WIDTH;
    }

    protected ShortCut4JControlPane[] createShortcuts() {
        return shortCutFactory.createShortCuts();
    }

    public abstract Nameable[] update();


    public void populate(Nameable[] nameableArray) {
    }

    /**
     * 检查按钮可用状态 Check button enabled.
     */
    public void checkButtonEnabled() {
    }

    public NameableCreator[] creators() {
        return creators == null ? new NameableCreator[0] : creators;
    }

    /**
    * 刷新 NameableCreator
    *
    * @param creators 生成器
    */
    public void refreshNameableCreator(NameableCreator[] creators) {
        this.creators = creators;
        shorts = this.createShortcuts();
        toolbarDef.clearShortCuts();
        for (ShortCut4JControlPane sj : shorts) {
            toolbarDef.addShortCut(sj.getShortCut());
        }

        toolbarDef.updateToolBar(toolBar);
        toolBar.validate();
        toolBar.repaint();
        this.repaint();
    }
}
