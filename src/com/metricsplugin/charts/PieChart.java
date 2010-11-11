package com.metricsplugin.charts;

import java.util.Map;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class PieChart implements Chart {

	private String name;
	private ChartPanel chartPanel;
	
	public PieChart(String title, Map<String, Number> dataSet) {
		name=title;
		chartPanel = createDemoPanel(dataSet);
	}


	private static PieDataset createDataset(Map<String, Number> dataSet2) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		Set<String> chaves = dataSet2.keySet();
		
		for(String chave : chaves){
			dataset.setValue(chave, dataSet2.get(chave));
		}
		
		
		return dataset;
	}


	private JFreeChart createChart(PieDataset dataset) {

		JFreeChart chart = ChartFactory.createPieChart(name, // chart
																			// title
				dataset, // data
				true, // include legend
				true, true);

		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setSectionOutlinesVisible(false);
		plot.setNoDataMessage("No data available");

		return chart;

	}


	public ChartPanel createDemoPanel(Map<String, Number> dataSet) {
		JFreeChart chart = createChart(createDataset(dataSet));
		return new ChartPanel(chart);
	}


	@Override
	public ChartPanel getChartJPanel() {
		
		return chartPanel;
	}


}
