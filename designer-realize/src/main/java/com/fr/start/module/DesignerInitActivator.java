package com.fr.start.module;

import com.fr.module.Activator;
import com.fr.start.DesignerInitial;

/**
 * Created by juhaoyu on 2019-06-14.
 */
public class DesignerInitActivator extends Activator {
    
    @Override
    public void start() {
        
        DesignerInitial.init(upFindSingleton(StartupArgs.class).get());
    }
    
    @Override
    public void stop() {
    
    }
}
