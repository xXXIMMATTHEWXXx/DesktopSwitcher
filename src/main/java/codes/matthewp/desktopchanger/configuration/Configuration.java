package codes.matthewp.desktopchanger.configuration;

import codes.matthewp.desktopchanger.io.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    public String imagePath;
    private Properties prop;
    private File configFolder = new File("config/");
    private File imgFolder = new File("imgs/");
    private File configFile = new File("config/main.config");

    public Configuration() {
        prop = new Properties();
        testIntegrity();
        load();
    }

    /**
     * Used to read the config and determine the configured time setting
     *
     * @return Time setting in milliseconds
     */
    public int determineTimer() {
        String timerType = prop.getProperty("timer.type");
        int timeInterval = Integer.valueOf(prop.getProperty("timer.timeint"));

        if (timerType.equalsIgnoreCase("s")) {
            timeInterval = timeInterval * 1000;
        } else if (timerType.equalsIgnoreCase("m")) {
            timeInterval = timeInterval * 60 * 1000;
        } else if (timerType.equalsIgnoreCase("h")) {
            timeInterval = timeInterval * 60 * 60 * 1000;
        } else {
            // 5 minutes is the default time setting
            timeInterval = 5 * 60 * 1000;
        }
        return timeInterval;
    }

    private void load() {
        InputStream is;
        try {
            is = new FileInputStream(configFile);
            prop.load(is);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        imagePath = prop.getProperty("img.path");
    }

    private void testIntegrity() {
        if (!imgFolder.exists()) {
            imgFolder.mkdir();
        }
        if (!configFile.exists()) {
            configFolder.mkdir();
            try {
                configFile.createNewFile();
                FileUtil.copyFile(new File(getClass().getResource("/config/default.config").getFile()), configFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
