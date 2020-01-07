package com.fr.design.fun.impl;

import com.fr.base.Style;
import com.fr.design.fun.StyleUIConfigProvider;
import com.fr.stable.StringUtils;
import com.fr.stable.fun.impl.AbstractProvider;
import com.fr.stable.fun.mark.API;

import javax.swing.JComponent;
import javax.swing.event.ChangeListener;

/**
 * Created by kerry on 2019-11-11
 */
@API(level = StyleUIConfigProvider.CURRENT_LEVEL)
public class AbstractStyleUIConfigProvider extends AbstractProvider implements StyleUIConfigProvider {
    @Override
    public int currentAPILevel() {
        return CURRENT_LEVEL;
    }

    @Override
    public String configName() {
        return StringUtils.EMPTY;
    }

    @Override
    public JComponent uiComponent(ChangeListener changeListener) {
        return null;
    }

    @Override
    public Style updateConfig() {
        return null;
    }

    @Override
    public void populateConfig(Style style) {

    }
}