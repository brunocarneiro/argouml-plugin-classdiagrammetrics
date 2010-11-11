package com.metricsplugin.metrics.genero;

import java.util.HashMap;
import java.util.List;

import org.argouml.model.Model;
import org.omg.uml.foundation.core.Classifier;

import com.metricsplugin.charts.annotation.MetricChart;
import com.metricsplugin.exception.ConfigurationException;
import com.metricsplugin.metrics.AbstractMetric;

/**
 * Citada em Early measures for UML class diagrams. Genero
 * Calcula a razão entre o número de associações e o número de classes.
 * Chamada NAVCP ( Number of Associations vs. Classes ).
 * @author bruno
 *
 * TODO Verificar se o par da associação que conta ou se é para cada classe.
 *
 *
 * DEFINITION. The Number of Associations vs. Classes in a Package metric
 * (NAVCP) is defined as the ratio between the number of associations in a
 * package (NAP) divided by the number of classes in the package.
 * GOAL. This metric refines the previous one. The more association per class the
 * package has, the more complex it will be, and the more difficult to understand
 * and maintain.
 *
 */
@MetricChart(info="metrics.info.NAssocXClasses")
public class NAssocXClasses extends AbstractMetric {

	public NAssocXClasses() {
		super();
		
	}

	@Override
	public void calculateMetric() {
		
		List classes;

		try {
			classes = getClassDiagrams();

			HashMap<Classifier, Integer> nAssociations = new HashMap<Classifier, Integer>();
			Classifier umlClass;
			float numAssoc=0;
			
			for (int cont=0; cont< classes.size(); cont++){
				umlClass = (Classifier) classes.get(cont);
				numAssoc = numAssoc + Model.getFacade().getAssociatedClasses(umlClass).size();
				
	
			}
			
			double result = numAssoc/classes.size();
			setResultado(result);	
		} catch (ConfigurationException e) {
			
			e.printStackTrace();
		}

	
		
	}

}
