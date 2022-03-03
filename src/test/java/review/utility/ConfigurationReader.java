package review.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {

    static private Properties properties;

    static {

        String path = "configuration.properties";

        properties = new Properties();

        try {

            FileInputStream file = new FileInputStream(path);

            properties.load(file);

        }catch (IOException e){

            e.printStackTrace();

            e.getMessage();

            throw new RuntimeException("Unable to find configuration.properties file!");
        }
    }


    public static String getProperty(String keyWord){

       return properties.getProperty(keyWord);

    }


}
