package com.fr.design.upm.database;

import com.fr.design.dialog.BasicPane;
import com.fr.design.dialog.UIDialog;
import com.fr.design.utils.gui.GUICoreUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @author richie
 * @version 10.0
 * Created by richie on 2019-05-16
 */
public class UniverseDatabaseDialog extends UIDialog {

    public UniverseDatabaseDialog(Frame frame, BasicPane pane) {
        super(frame);
        setUndecorated(true);
        JPanel panel = (JPanel) getContentPane();
        panel.setLayout(new BorderLayout());
        add(pane, BorderLayout.CENTER);
        setSize(new Dimension(800, 500));
        GUICoreUtils.centerWindow(this);
        setResizable(false);
    }

    @Override
    public void checkValid() throws Exception {

    }
}
