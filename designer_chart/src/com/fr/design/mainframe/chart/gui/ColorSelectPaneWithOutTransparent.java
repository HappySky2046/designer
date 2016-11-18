package com.fr.design.mainframe.chart.gui;

import com.fr.chart.base.ChartConstants;
import com.fr.design.style.color.ColorCell;
import com.fr.design.style.color.ColorSelectPane;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Fangjie on 2016/4/8.
 */
public class ColorSelectPaneWithOutTransparent extends ColorSelectPane {

    public ColorSelectPaneWithOutTransparent(){
        super(false);
    }

    public void initCenterPaneChildren(JPanel centerPane) {
        JPanel menuColorPane1 = new JPanel();
        centerPane.add(menuColorPane1);
        menuColorPane1.setLayout(new GridLayout(5, 8, 5, 5));
        for (int i = 0; i < ChartConstants.MAP_COLOR_ARRAY.length; i++) {
            menuColorPane1.add(new ColorCell(ChartConstants.MAP_COLOR_ARRAY[i], this));
        }
        centerPane.add(Box.createVerticalStrut(5));
        centerPane.add(new JSeparator());
    }

    protected Color[] getColorArray(){
        return ChartConstants.MAP_COLOR_ARRAY;
    }
}
