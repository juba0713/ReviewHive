package com.reviewhive.common.util;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author Julius P. Basas
 * @added 12/18/2024
 */
@Component
public class ApplicationPropertiesRead {
	
	/**
     * Get the value of a specific property from application.properties.
     *
     * @param key the key of the property
     * @return the value of the property, or null if not found
     */
    public static String getProperty(String key) {
        Environment environment = SpringApplicationContext.getApplicationContext().getBean(Environment.class);
        return environment.getProperty(key);
    }
}
