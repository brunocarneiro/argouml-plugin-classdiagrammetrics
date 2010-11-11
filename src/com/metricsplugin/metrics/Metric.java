package com.metricsplugin.metrics;

public interface Metric {
	
	
	void calculateMetric();
	
	String getMessage();
	
	Object getResult();
	
	void showResult();
	

}
