package com.metricsplugin.metrics.genero;

import java.util.HashMap;
import java.util.List;

import org.argouml.model.Model;
import org.omg.uml.foundation.core.Classifier;
import org.omg.uml.foundation.core.UmlAssociation;

import com.metricsplugin.charts.annotation.ChartType;
import com.metricsplugin.charts.annotation.MetricChart;
import com.metricsplugin.exception.ConfigurationException;
import com.metricsplugin.metrics.AbstractMetric;

/**
 * Calcula o numero de Associacoes por classe
 * @author bruno
 *
 *
 * TODO Verificar se o par da associação que conta ou se é para cada classe.
 * 
 * GOAL. The complexity of a class depends on the number of associations it has
 * with other classes. This metric could identify which classes are the main ones in
 * a class model.

 */

@MetricChart(chartType=ChartType.BAR_CHART, info="metrics.info.NAssoc", labels={"metrics.NAssoc.x", "metrics.NAssoc.y"})
public class NAssoc extends AbstractMetric {


	
	public NAssoc() {
		super();
		
	}

	@Override
	public void calculateMetric() {
		

			List classes;
			try {
				classes = getClassDiagrams();
			
			HashMap<String, Integer> nAssociations = new HashMap<String, Integer>();
			UmlAssociation umlAssociation;
			Classifier umlClass;
			
			for (int cont=0; cont< classes.size(); cont++){
				umlClass = (Classifier) classes.get(cont);
				int numAssoc = Model.getFacade().getAssociatedClasses(umlClass).size();
				nAssociations.put(umlClass.getName(), numAssoc);

			}
			
			
//			for (int cont=0; cont< classes.size(); cont++){
//				if(associations.get(0)instanceof UmlAssociation){
//					
//					umlAssociation = (UmlAssociation) associations.get(cont);
//					Model.getFacade().getAss
//				}
//				else{
//					continue;
//				}
//				Model.getFacade().get
//				int profundidade = profundidadeClasse(umlClass, 0);
//				depthInheritance.put(umlClass, profundidade);
//			}
			
			setResultado(nAssociations);

			} catch (ConfigurationException e) {
				
				e.printStackTrace();
			}
		
		

	}
	
	@Override
	public String getMessage() {
	
		return preparaResultadoHash((HashMap<String, Integer>) getResult());
	}

}
