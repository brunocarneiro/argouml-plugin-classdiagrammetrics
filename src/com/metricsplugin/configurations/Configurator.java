package com.metricsplugin.configurations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.argouml.i18n.Translator;
import org.argouml.ui.ProjectBrowser;

import com.metricsplugin.metrics.Metric;
import com.metricsplugin.ui.ActionClassMetric;
import com.metricsplugin.ui.ConfigurationAction;
import com.metricsplugin.util.Context;
import com.metricsplugin.util.MetricsConfiguration;

/**
 * executa operacoes antes de inicializar o plugin. Utilizar o método configur
 * @author bruno
 *
 */
public class Configurator {
	
	public static final String PROPS_FILE = "com/metricsplugin/metrics/availableMetrics";
	
	public static void configure(){
		
		init();
		loadMetrics();
		createMetricsMenuBar();

	}
	

	protected static void init() {
		initClassesFromContext();
	}


	protected static void initClassesFromContext() {
		
		Context context = Context.getInstance();
		context.insere(Context.METRICS_CONFIGURATION, new MetricsConfiguration());
		
	}


	/**
	 * Cria menu de metricas
	 * @return menu de metricas
	 */
	protected static JMenu createMetricsMenuBar() {
		
		JMenu metricsMenu = new JMenu(menuLocalize(Context.MENU));
		List<Class<Metric>> metricas = (List<Class<Metric>>) Context.getInstance().get(Context.METRICS_CLASSES);
		JMenuItem metricaTest;
		for (Class<Metric> metric : metricas){
			
			metricaTest = metricsMenu.add(new ActionClassMetric(Context.BUNDLE_PREFIX+metric.getSimpleName(),metric));
			metricsMenu.add(metricaTest);
			
		}
		
		addMenuToMenuBar(metricsMenu);
		Context.getInstance().insere("metrics.menu", metricsMenu);
		return metricsMenu;
		
		
	}
	
	protected static void addMenuToMenuBar(JMenu menu){
		//menu de configuracoes
		menu.add(new ConfigurationAction());
		
		JMenuBar menuBar = (JMenuBar) ProjectBrowser.getInstance().getJMenuBar();
		menuBar.add(menu);
		menuBar.validate();
	}
	
	protected static void loadMetrics(){
		
		List<Class<? extends Metric> > metrics = loadAvailableMetrics();
		Context.getInstance().insere(Context.METRICS_CLASSES, metrics);
		
	}
	
	
	
    private static List<Class<? extends Metric> > loadAvailableMetrics() {
    	List<Class<? extends Metric> > metrics = new ArrayList<Class<? extends Metric> >();
    	
		BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(PROPS_FILE)));
    	String linha;
    	Class<? extends Metric> metrica;
    	try {
			while((linha = reader.readLine()) != null){
				
				
				metrica = (Class<? extends Metric> )Class.forName(linha);
				metrics.add(metrica);
				
			}
			return metrics;
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		 catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			}
			return null;
    	
    	
    	
    	
    	
	}



	protected static final String menuLocalize(String key) {
        return Translator.localize(key);
    }

}
