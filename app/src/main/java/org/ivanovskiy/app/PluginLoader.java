package org.ivanovskiy.app;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Ivanovskij Oleg on 13.07.2018
 */
public class PluginLoader {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private Map<String, PluginInfo> plugins = new HashMap<>();
    private JFrame mainFrame;

    public void start() {
        putJarFilesToPluginsMap();
        initMainFrame();
        addBtnAddPluginToMainFrame();

        synchronized (plugins) {
            for (PluginInfo pluginInfo : plugins.values()) {
                addEventListenerToButtonAndAddButtonToMainFrame(pluginInfo);
            }
        }

        mainFrame.setVisible(true);
    }

    private void addEventListenerToButtonAndAddButtonToMainFrame(PluginInfo pluginInfo) {
        JButton button = new JButton(pluginInfo.getButtonText());
        button.addActionListener(e -> {
            pluginInfo.getPluginInstance().init(() -> mainFrame);
            pluginInfo.getPluginInstance().changeBackground();
        });
        mainFrame.getContentPane().add(button);
    }

    private void putJarFilesToPluginsMap() {
        File pluginDir = new File("app/user-plugins");
        File[] jars = pluginDir.listFiles(file -> file.isFile() && file.getName().endsWith(".jar"));
        if (jars != null) {
            for (File file : jars) {
                try {
                    plugins.put(file.getName(), new PluginInfo(file));
                } catch (Exception e) {
                    logger.severe("Exception: " + e.getMessage());
                    System.exit(1);
                }
            }
        }
    }

    private void addBtnAddPluginToMainFrame() {
        JButton btnAddPlugin = new JButton("Add plugin");
        btnAddPlugin.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser("plugins/");
            fileChooser.setDialogTitle("Select a plugin");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showOpenDialog(mainFrame);
            if (result == JFileChooser.APPROVE_OPTION ) {
                try {
                    addPluginByName(fileChooser.getSelectedFile().getAbsolutePath());
                } catch (Exception ex) {
                    logger.severe("Exception: " + ex.getMessage());
                }
            }
        });
        mainFrame.getContentPane().add(btnAddPlugin);
    }

    private void initMainFrame() {
        mainFrame = new JFrame("Background Frame Pluggable App");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(350, 150);
        mainFrame.getContentPane().setLayout(new FlowLayout());
        mainFrame.setLocationRelativeTo(null);
    }

    public void addPluginByName(String jarName) throws Exception {
        if (plugins.containsKey(jarName)) {
            throw new Exception(jarName + " not loaded");
        }
        File jar = new File(jarName);
        synchronized (plugins) {
            plugins.put(jarName, new PluginInfo(jar));
            PluginInfo pluginInfo = plugins.get(jarName);
            addEventListenerToButtonAndAddButtonToMainFrame(pluginInfo);
        }
        validateAndRepaintMainFrame();
    }

    private void validateAndRepaintMainFrame() {
        mainFrame.validate();
        mainFrame.repaint();
    }
}
