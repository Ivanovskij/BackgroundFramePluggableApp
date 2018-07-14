package org.ivanovskiy.app;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ivanovskij Oleg on 13.07.2018
 */
public class PluginLoader {

    private Map<String, PluginInfo> plugins;
    private JFrame mainFrame;

    public void start() {
        File pluginDir = new File("app/user-plugins");

        File[] jars = pluginDir.listFiles(file -> file.isFile() && file.getName().endsWith(".jar"));

        plugins = new HashMap<>();

        for (File file : jars) {
            try {
                plugins.put(file.getName(), new PluginInfo(file));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mainFrame = new JFrame("Background Frame Pluggable App");
        final JFrame frame = mainFrame;
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(250, 80);
        frame.getContentPane().setLayout(new FlowLayout());

        synchronized (plugins) {
            for (PluginInfo pluginInfo : plugins.values()) {
                final JButton button = new JButton(pluginInfo.getButtonText());
                button.addActionListener(e -> {
                    pluginInfo.getPluginInstance().init(() -> frame);
                    pluginInfo.getPluginInstance().changeBackground();
                });
                frame.getContentPane().add(button);
            }
        }

        frame.setVisible(true);
    }
}
