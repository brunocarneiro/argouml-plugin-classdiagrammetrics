package com.metricsplugin.charts;

import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class DataChart implements Chart {

	private JPanel chartPanel;
	private String name;
	
	public DataChart(String title, Map<String, Number> dataSet) {
		name=title;
		chartPanel = createChart(dataSet);
	}
	
	@Override
	public JPanel getChartJPanel() {

		return chartPanel;
	}


	private JPanel createChart(Map<String, Number> dataSet) {
		JPanel chartPanel = new JPanel();		
		JLabel text = new JLabel(name);		
		chartPanel.add(text);
		
		for(Entry<String, Number> data : dataSet.entrySet()){
			
			text = new JLabel(data.getKey()+" = "+data.getValue());
			chartPanel.add(text);			
			
		}
		
		return chartPanel;
	}
	
	

}
