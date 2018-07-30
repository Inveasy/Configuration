/*
 * Copyright 2018 Guillaume Gravetot
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.inveasy.configuration;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;

import java.util.List;

/**
 * This singleton class manages configurations<br>
 * It loads profile files ('application-profilename') with profiles defined in env var or in program argument "application.profiles"<br>
 * Profiles should be separated by commas (',')
 */
public class Configuration
{
	public static final String CONFIGURATION_FILENAME = "application";
	private static Configuration instance = new Configuration();
	
	public static Configuration getInstance()
	{
		return instance;
	}
	
	private Config config;

	private Configuration()
	{
		config = ConfigFactory.systemProperties()
				.withFallback(ConfigFactory.systemEnvironment());
		
		try
		{
			String profiles = config.getString("application.profiles");
			String[] parsedProfiles = profiles.split(",");
			
			for(String profile : parsedProfiles)
			{
				String profileFile = CONFIGURATION_FILENAME + "-" + profile;
				
				System.out.println("Using profile file " + profileFile);
				
				config = config.withFallback(ConfigFactory.parseResourcesAnySyntax(profileFile));
			}
		} catch(ConfigException.Missing ignored) { /* Do nothing as we ignore non present profiles */ }
		
		config = config.withFallback(ConfigFactory.defaultApplication())
				.withFallback(ConfigFactory.parseResourcesAnySyntax("pre-reference"))
				.withFallback(ConfigFactory.defaultReference())
				.resolve();
	}
	
	public Config getRaw()
	{
		return config;
	}
	
	public String get(String key)
	{
		return get(key, null);
	}
	
	public String get(String key, String defaultValue)
	{
		try
		{
			return config.getString(key);
		} catch(ConfigException.Missing e)
		{
			return defaultValue;
		}
	}
	
	public int getInt(String key)
	{
		return getInt(key, 0);
	}
	
	public int getInt(String key, int defaultValue)
	{
		try
		{
			return config.getInt(key);
		} catch(ConfigException.Missing e)
		{
			return defaultValue;
		}
	}
	
	public boolean getBoolean(String key)
	{
		return getBoolean(key, false);
	}
	
	public boolean getBoolean(String key, boolean defaultValue)
	{
		try
		{
			return config.getBoolean(key);
		} catch(ConfigException.Missing e)
		{
			return defaultValue;
		}
	}
	
	public long getLong(String key)
	{
		return getLong(key, 0L);
	}
	
	public long getLong(String key, long defaultValue)
	{
		try
		{
			return config.getLong(key);
		} catch(ConfigException.Missing e)
		{
			return defaultValue;
		}
	}
	
	public List<String> getStringArray(String key)
	{
		return getStringArray(key, null);
	}
	
	public List<String> getStringArray(String key, List<String> defaultValue)
	{
		try
		{
			return config.getStringList(key);
		} catch(ConfigException.Missing e)
		{
			return defaultValue;
		}
	}
}
