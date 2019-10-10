package com.fr.design.remote.ui;

import com.fr.base.BaseUtils;
import com.fr.design.i18n.Toolkit;
import com.fr.design.remote.RemoteDesignAuthorityCreator;
import com.fr.design.remote.ui.list.AuthorityListCellRenderer;
import com.fr.design.remote.ui.list.cell.AuthorityCustomRoleListCellRender;
import com.fr.report.DesignAuthority;

public class AuthorityListCustomRolePane extends AbstractListControlPane {

    @Override
    protected RemoteDesignAuthorityCreator[] getAuthorityCreators() {
            return new RemoteDesignAuthorityCreator[]{
                new RemoteDesignAuthorityCreator(
                        // 远程设计用户
                        Toolkit.i18nText("Fine-Design_Basic_Remote_Design_Custom"),
                        BaseUtils.readIcon("com/fr/design/remote/images/icon_Member_normal@1x.png"),
                        DesignAuthority.class,
                        AuthorityEditorPane.class)
        };
    }

    @Override
    protected AuthorityListCellRenderer getAuthorityListCellRender() {
        return new AuthorityCustomRoleListCellRender();
    }

    @Override
    protected AbstractManagerPane getManagerPane() {
        return new CustomRoleManagerPane();
    }

    @Override
    protected String getKey() {
        return "Fine-Design_Basic_Utils_Are_You_Sure_To_Delete_The_Role_And_Its_Design_Authorities";
    }
}