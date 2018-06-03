package com.fr.design.remote.ui.list;

import com.fr.base.BaseUtils;
import com.fr.design.gui.ibutton.UIButton;
import com.fr.design.gui.ilable.UILabel;
import com.fr.env.RemoteDesignMember;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import java.awt.Component;
import java.awt.FlowLayout;

public class AddedMemberListCellRender extends JPanel implements ListCellRenderer<RemoteDesignMember> {


    private UILabel label;

    private UIButton uiButton;

    public AddedMemberListCellRender() {
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        label = new UILabel();
        label.setIcon(BaseUtils.readIcon("com/fr/design/remote/images/icon_Member_normal@1x.png"));

        uiButton = new UIButton();
        uiButton.setIcon(BaseUtils.readIcon("com/fr/design/remote/images/icon_Remove_x.png"));

        this.add(label);
        this.add(uiButton);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends RemoteDesignMember> list, RemoteDesignMember member, int index, boolean isSelected, boolean cellHasFocus) {
        this.setLabelText(member.getRealName() + "(" + member.getUsername() + ")");
        return this;
    }

    private void setLabelText(String name) {
        label.setText(name);
    }
}
