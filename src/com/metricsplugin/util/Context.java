package com.metricsplugin.util;

import java.util.HashMap;
import java.util.Map;

public class Context {
	
	/**
	 * CONSTANTS
	 */
	public static final String METRICS_CLASSES = "metricsClasses";
	public static final String METRICS_CONFIGURATION="metricsConfiguration";
	public static final String MENU="metrics.Menu";
	public static final String BUNDLE_PREFIX = "metrics.";
	public static final String CATEGORY_MENU_ENABLED = "metrics.categoryMenuEnabled";
	public static final String OO_MENU_NAME ="OO";
	
	
	private Map<String, Object> context;
	

	private static Context INSTANCE;
	
	protected Context(){
		context = new HashMap<String, Object>();
	}

	public static Context getInstance() {
		
		if(INSTANCE ==null)
			return INSTANCE = new Context();
		
		return INSTANCE;
	}
	
	
	public void insere(String chave, Object valor){
		
		context.put(chave, valor);
		
	}
	
	public Object get(String key){
		
		return context.get(key);
		
	}


	
	
	
	
	
}
