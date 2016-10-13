package tp.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	
	public static Properties loadProperties(String propPathInCP){
		Properties props = new Properties();
		try {
			InputStream is = 
					Thread.currentThread()
					      .getContextClassLoader()
					      .getResourceAsStream(propPathInCP);
			props.load(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
		
	}

}
