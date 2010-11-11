package com.metricsplugin.metrics;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import org.argouml.i18n.Translator;

import com.metricsplugin.charts.Chart;
import com.metricsplugin.charts.ChartFactory;
import com.metricsplugin.charts.annotation.MetricChart;
import com.metricsplugin.util.Context;

/**
 * Metrica que gera o relatório completo, executando todas as métricas
 * disponíveis no arquivo availableMetrics.
 * Em seguida, gera os gráficos basedo na anotação '@MetricChart'.
 * @author bruno
 *
 */
public class ClassReport extends AbstractMetric{
	
	private int altura, largura;
	private JFrame frame = new JFrame(Translator.localize("metrics.report"));
	
	
	public void calculateMetric() {

		executeMetrics();		
	}
	
	
	/**
	 * Executa todas as metricas disponiveis e retorna o resultado em um Mapa, que contem o nome da Classe como
	 * chave e o resultado do calculo como valor do mapa.
	 * @return lista de resultados da execucao das metricas
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	protected void executeMetrics() {
		
		List<Class<? extends Metric>> metrics = (List<Class<? extends Metric>>) Context.getInstance().get(Context.METRICS_CLASSES);
		Map<String, Object> resultados = new HashMap<String, Object>();		
		Metric m;
		
		try {
			for(Class<? extends Metric> metric : metrics){
				
				m = metric.newInstance();
				if(m instanceof ClassReport) continue;
				m.calculateMetric();
				addChartToPanel(createChart(m));				
			}

		} catch (InstantiationException e) {
			
			e.printStackTrace();

		} catch (IllegalAccessException e) {
			
			e.printStackTrace();

		}
		
	}

	protected Chart createChart(Metric m) {
		
		MetricChart config = m.getClass().getAnnotation(MetricChart.class);
		
		if(config!=null){
			
			return ChartFactory.createChart(config, m);			
		}		
		
		return null;
	}
	
	protected void addChartToPanel(Chart chart){
		
		if(chart!=null){
			//TODO RETIRAR QUANDO CRIAR ALGUM GERENCIADOR DE LAYOUT.
			if(largura%4==0){altura++;}
			frame.setLayout(new GridLayout(altura,++largura));			
			frame.add(chart.getChartJPanel());
		}
		
	}
	
	@Override
	public void showResult() {
		
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(800, 600);
		frame.setVisible(true); 
	}


	
}
