# BackgroundFramePluggableApp

## About
**BackgroundFramePluggableApp** - простейшее swing приложение с поддержкой добавления 
новых плагинов, не перезапуская приложение, для изменения цвета заднего фона окна.  
Приложение написано по примеру статьи: [Создание pluggable решений при помощи Java.](http://voituk.kiev.ua/2008/01/14/java-plugins/)

## Usage
1. ```git clone https://github.com/Ivanovskij/BackgroundFramePluggableApp.git```
или [скачать проект](https://github.com/Ivanovskij/BackgroundFramePluggableApp/archive/master.zip).
2. Открываем в своей ide и ```mvn clean install```.
3. Класс для запуска приложения находится ```app\Bootstrap.java```.
4. После запуска приложения уже будет доступен один плагин с кнопкой для изменения заднего фона на черный цвет.  
Чтобы добавить новый нажимаем ```Add plugin``` и выбираем файл ```plugins/RedBackgroundPlugin/target/RedBackgroundPlugin-1.0-SNAPSHOT.jar```, после этого появится кнопка для изменения заднего фона на красный цвет.

## Writing own plugin (maven)
1. Подключить в pom.xml  
```<groupId>org.ivanovskiy.api</groupId>```  
```<artifactId>plugins-api</artifactId>```  
```<version>1.0-SNAPSHOT</version>```
2. Наследуемся от ```AbstractBackgroundPlugin``` и реализуем методы.
3. Делаем ```mvn clean install```, запускаем приложение и через ```Add plugin``` добавляем свой плагин.

*В модуле (plugins) реализовано два плагина можно посмотреть на реализацию для нового плагина.*

