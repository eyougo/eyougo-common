package com.eyougo.common.preference;

import com.eyougo.common.util.SystemInfoParser;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Iterator;

public class PreferenceService {
	private static Log log = LogFactory.getLog(PreferenceService.class);
	private static CompositeConfiguration configuration;

	private PreferenceService() {
	}

	public static String getConfigBasePath(){
		return CommonPropertyPlaceholderConfigurer.class.getClassLoader()
				.getResource("").getPath() + "config/";
	}
	
	public static synchronized void init() {
		if (configuration == null){
			configuration = new CompositeConfiguration();
			
			PropertiesConfiguration propertiesConfiguration;

			try {
				String hostname = SystemInfoParser.getHostName();
				String propPath = getConfigBasePath() + hostname
						+ ".properties";
				propertiesConfiguration = new PropertiesConfiguration(propPath);
				for (Iterator<String> iterator = propertiesConfiguration.getKeys(); iterator.hasNext();) {
					String key = iterator.next();
					log.debug("Init property from: " + hostname
							+ ".properties : " +  key
							+ " = "
							+ propertiesConfiguration.getProperty(key));
				}
				configuration.addConfiguration(propertiesConfiguration);
			} catch (ConfigurationException e) {
				log.error(e.getMessage(), e);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
			
			try {
				propertiesConfiguration = new PropertiesConfiguration(getConfigBasePath() + "common/common.properties");
				for (Iterator<String> iterator = propertiesConfiguration.getKeys(); iterator.hasNext();) {
					String key = iterator.next();
					log.debug("Init property from preference.properties : "
							+ key
							+ " = "
							+ propertiesConfiguration.getProperty(key));
				}

				configuration.addConfiguration(propertiesConfiguration);
			} catch (ConfigurationException e) {
				log.error(e.getMessage(), e);
			}
			
		}
	}

	/**
	 * hardcode...
	 * 
	 * @return
	 */
	public static Configuration getCommonConfiguration() {
		if (configuration != null)
			return configuration;
		init();
		return getCommonConfiguration();
	}
}