package com.metricsplugin.charts;

import java.util.HashMap;
import java.util.Map;

import org.argouml.i18n.Translator;

import com.metricsplugin.charts.annotation.ChartType;
import com.metricsplugin.charts.annotation.MetricChart;
import com.metricsplugin.charts.annotation.OrdemValor;
import com.metricsplugin.metrics.Metric;

/**
 * Fabrica de graficos
 * @author bruno
 *
 */
public class ChartFactory {
	
	/**
	 * Cria um gráfico a partir de a anotação de configuração e da Métrica,
	 * já com seus resultados calculados.
	 * @param config
	 * @param metric
	 * @return
	 */
	public static Chart createChart(MetricChart config, Metric metric){
		
		Chart createdChart=null;
		String info, chartName;
		String [] labels;
		ChartType chartType;
		OrdemValor x,y;
		Object resultado = metric.getResult();
		config = metric.getClass().getAnnotation(MetricChart.class);
		if(config!=null && resultado!=null){
			
			info = Translator.localize(config.info());
			chartType = config.chartType();
			x=config.x();
			y=config.y();
			chartName = Translator.localize("metrics."+metric.getClass().getSimpleName());
			labels=config.labels();
			
			if( chartType==ChartType.BAR_CHART){
				
				createdChart = createBarChart(resultado, labels, chartName);
			
			}else if(chartType==ChartType.PIE_CHART){
				
				createdChart = createPieChart(resultado, labels, chartName);
				
			}			
			else if (chartType==ChartType.ONLY_DATA){
				
				createdChart = createDataChart(resultado, labels, chartName);				
			}			
		}
		
		
		return createdChart;
	}
	
	


	/**
	 * Cria um grafico tipo dados, apenas exibe os dados.
	 * @param resultado
	 * @param labels
	 * @param chartName
	 * @return
	 */
	protected static Chart createDataChart(Object resultado, String[] labels,
			String chartName) {
		
		Map<String, Number> dataSet =null ;
		if(resultado instanceof Number){
			dataSet = new HashMap<String, Number>();
			dataSet.put("", (Number)resultado);
			
		}
		else if(resultado instanceof Map){
			dataSet=(Map<String, Number>) resultado;
		}
		return new DataChart(chartName, dataSet);
	}



	/**
	 * Cria um grafico tipo Barras.
	 * @param resultado
	 * @param labels
	 * @param chartName
	 * @return
	 */
	protected static Chart createBarChart(Object resultado, String[] labels,
			String chartName) {

		return new BarChart(chartName, (Map<String, Number>) resultado, labels);
	}

	
	/**
	 * Cria um grafico tipo Pizza.
	 * @param resultado
	 * @param labels
	 * @param chartName
	 * @return
	 */
	protected static PieChart createPieChart(Object resultado, String [] labels, String chartName) {
		
		Map<String, Number> dataSet = new HashMap<String, Number>();

		if(resultado instanceof Integer){
			dataSet.put(Translator.localize(labels[0])+" (" + ((Integer)resultado)+" )", (Number)resultado);
			dataSet.put(Translator.localize(labels[1])+" (" + (1- (Integer)resultado)+" )", 1- (Integer)resultado);
		}
		else if(resultado instanceof Double){
			dataSet.put(Translator.localize(labels[0])+" (" + ((Double)resultado)+" )", (Number)resultado);
			dataSet.put(Translator.localize(labels[1])+"(" + (1- (Double)resultado)+" )", 1- (Double)resultado);
		}
		else if(resultado instanceof Float){
			dataSet.put(Translator.localize(labels[0])+" (" + ((Float)resultado)+" )", (Number)resultado);
			dataSet.put(Translator.localize(labels[1])+"(" + (1- (Float)resultado)+" )", 1- (Float)resultado);
		}
		return new PieChart(chartName, dataSet);
		
	}
	
	
	public static Chart createChart(Metric m) {
		
		MetricChart config = m.getClass().getAnnotation(MetricChart.class);
		
		if(config!=null){
			
			return createChart(config, m);			
		}		
		
		return null;
	}
	
}
