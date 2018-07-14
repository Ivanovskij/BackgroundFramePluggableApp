package org.ivanovskiy.plugin;

import org.ivanovskiy.api.AbstractBackgroundPlugin;
import org.ivanovskiy.api.BackgroundContext;

import java.awt.*;

/**
 * Created by Ivanovskij Oleg on 14.07.2018
 */
public class BlackBackgroundPluginImpl implements AbstractBackgroundPlugin{

    private BackgroundContext bgContext;

    public void changeBackground() {
        bgContext.getFrame().getContentPane().setBackground(Color.BLACK);
    }

    public void init(BackgroundContext bgContext) {
        this.bgContext = bgContext;
    }
}
