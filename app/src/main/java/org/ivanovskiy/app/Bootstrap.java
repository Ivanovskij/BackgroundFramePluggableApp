package org.ivanovskiy.app;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by Ivanovskij Oleg on 13.07.2018
 */
public class Bootstrap {

    public static void main(String[] args) throws Exception {
        File commonsDir = new File("app/common-plugins");
        File[] entries = commonsDir.listFiles();
        URL[] urls = new URL[entries.length];

        for (int i = 0; i < entries.length; i++) {
            urls[i] = entries[i].toURI().toURL();
        }

        URLClassLoader commonsLoader = new URLClassLoader(urls, null);
        URL targetClassesDirURL = new File("app/target/classes").toURI().toURL();
        URLClassLoader appLoader = new URLClassLoader(new URL[] {targetClassesDirURL}, commonsLoader);

        Class appClass = appLoader.loadClass("org.ivanovskiy.app.PluginLoader");
        Object appInstance = appClass.newInstance();
        Method methodStart = appClass.getMethod("start");
        methodStart.invoke(appInstance);
    }


}
