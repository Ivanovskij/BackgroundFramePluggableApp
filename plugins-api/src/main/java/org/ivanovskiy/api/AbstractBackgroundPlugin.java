package org.ivanovskiy.api;

public interface AbstractBackgroundPlugin {

    void changeBackground();

    void init(BackgroundContext bgContext);

}
