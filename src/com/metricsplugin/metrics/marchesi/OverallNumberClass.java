package com.metricsplugin.metrics.marchesi;

import java.util.List;

import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.uml.diagram.ArgoDiagram;
import org.argouml.uml.diagram.DiagramUtils;
import org.argouml.uml.diagram.static_structure.ui.UMLClassDiagram;
import org.argouml.uml.diagram.ui.UMLDiagram;
import org.omg.uml.foundation.core.UmlClass;

import com.metricsplugin.charts.annotation.ChartType;
import com.metricsplugin.charts.annotation.MetricChart;
import com.metricsplugin.exception.ConfigurationException;
import com.metricsplugin.metrics.AbstractMetric;

/**
 * Citada em OOA Metrics for the Unified Modeling Language. Marchesi.
 * OA1.
 * É uma medida de quantas classes ancestrais podem, potencialmente, afetar uma classe.
 * Mede Complexidade
 * @author bruno
 */
@MetricChart(chartType=ChartType.ONLY_DATA, info="metrics.info.OverallNumberClass")
public class OverallNumberClass extends AbstractMetric{
	
	

	public OverallNumberClass() {
		super();
	}


	
	
	public void calculateMetric(){

		List classes;
		try {
			classes = getClassDiagrams();

			UmlClass umlClass;
			int total=0;
			for (int cont=0; cont< classes.size(); cont++){
				total++;
			}
			
			setResultado(new Double(total));
		} catch (ConfigurationException e) {
			
			e.printStackTrace();
		}
	
	}

}
