package GenericUtility;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyUtility {
	public static String getProperty(String key) throws Exception {
	FileInputStream fis =
            new FileInputStream(".\\src\\main\\resources\\commonData.properties");

    Properties properties = new Properties();

    properties.load(fis);
    fis.close();

    return properties.getProperty(key);
    
	}

}
