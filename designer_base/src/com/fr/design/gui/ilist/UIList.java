package com.fr.design.gui.ilist;

import com.fr.data.core.db.TableProcedure;
import com.fr.design.gui.itooltip.UIToolTip;
import com.fr.design.mainframe.JTemplate;
import com.fr.stable.StringUtils;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Vector;

import static com.fr.design.gui.syntax.ui.rtextarea.RTADefaultInputMap.DEFAULT_MODIFIER;

/**
 * Created with IntelliJ IDEA.
 * User: pony
 * Date: 13-5-22
 * Time: 上午11:07
 * To change this template use File | Settings | File Templates.
 */
public class UIList extends JList{
    private Icon icon;

    public UIList() {
        super();
        InputMap inputMap = this.getInputMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, DEFAULT_MODIFIER),
                DefaultEditorKit.selectAllAction);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, DEFAULT_MODIFIER),
                DefaultEditorKit.copyAction);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, DEFAULT_MODIFIER),
                DefaultEditorKit.pasteAction);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, DEFAULT_MODIFIER),
                DefaultEditorKit.cutAction);
    }

    public UIList(ListModel dataModel) {
        super(dataModel);
    }

    public UIList(Object[] listData) {
        super(listData);
    }

    public UIList(Vector<?> listData) {
        super(listData);
    }

    public String getToolTipText(MouseEvent event) {
        int index = locationToIndex(event.getPoint());
        icon = new ImageIcon();
        if (index != -1) {
            Object value = getModel().getElementAt(index);
            ListCellRenderer renderer = getCellRenderer();
            Component rendererComp = renderer.getListCellRendererComponent(this, value, index, true, false);
            if (rendererComp.getPreferredSize().width > getVisibleRect().width) {
                String tips = (rendererComp instanceof JComponent) ? ((JComponent) rendererComp).getToolTipText() : null;
                if (tips == null) {
                    if(value instanceof JTemplate){
                        tips = ((JTemplate) value).getEditingFILE().getName();
                        icon = ((JTemplate) value).getEditingFILE().getIcon();
                    } else if (value instanceof ListModelElement || value instanceof TableProcedure){
                        tips = ((JLabel)rendererComp).getText();
                        icon = ((JLabel)rendererComp).getIcon();
                    }
                }
                return tips;
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    public Point getToolTipLocation(MouseEvent event) {
        int index = locationToIndex(event.getPoint());
        if (index != -1 && StringUtils.isNotEmpty(getToolTipText(event))) {
            Rectangle cellBounds = getCellBounds(index, index);
            return new Point(cellBounds.x - 2, cellBounds.y - 1);
        }
        return null;
    }
    public JToolTip createToolTip() {
        UIToolTip tip = new UIToolTip(icon);
        tip.setComponent(this);
        tip.setOpaque(false);
        return tip;
    }
}