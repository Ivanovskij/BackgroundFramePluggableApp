package org.ivanovskiy.app;

import org.ivanovskiy.api.AbstractBackgroundPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Ivanovskij Oleg on 13.07.2018
 */
public class PluginInfo {

    private AbstractBackgroundPlugin pluginInstance;
    private String buttonText;

    public PluginInfo(File jarFile) throws Exception {
        Properties props = getPluginProps(jarFile);
        if (props == null) {
            throw new IllegalArgumentException("No props file found");
        }
        String pluginClassName = props.getProperty("main.class");
        if (pluginClassName == null || pluginClassName.length() == 0) {
            throw new IllegalArgumentException("Missing property main.class");
        }
        buttonText = props.getProperty("button.text");
        if (buttonText == null || buttonText.length() == 0) {
            throw new IllegalArgumentException("Missing property button.text");
        }

        URL jarUrl = jarFile.toURI().toURL();
        try (URLClassLoader classLoader =
                     new URLClassLoader(new URL[]{jarUrl}, getClass().getClassLoader().getParent())) {
            Class<?> pluginClass = classLoader.loadClass(pluginClassName);
            pluginInstance = (AbstractBackgroundPlugin) pluginClass.newInstance();
        }
    }

    private Properties getPluginProps(File jarFile) throws IOException {
        Properties result = null;
        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().equals("settings.properties")) {
                    try (InputStream is = jar.getInputStream(entry)) {
                        result = new Properties();
                        result.load(is);
                    }
                }
            }
        }
        return result;
    }

    public AbstractBackgroundPlugin getPluginInstance() {
        return pluginInstance;
    }

    public String getButtonText() {
        return buttonText;
    }
}
