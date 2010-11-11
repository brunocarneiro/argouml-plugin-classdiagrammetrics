package com.metricsplugin.util;

public class MetricsConfiguration {
	
	private MeasureScope escopo;
	
	public MetricsConfiguration(){		
		escopo=MeasureScope.PACKAGE;
		
	}

	public MeasureScope getEscopo() {
		return escopo;
	}

	public void setEscopo(MeasureScope escopo) {
		this.escopo = escopo;
	}
	

}


