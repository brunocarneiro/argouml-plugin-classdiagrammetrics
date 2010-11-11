package com.metricsplugin.metrics.marchesi;

import java.util.HashMap;
import java.util.List;

import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.model.Model;
import org.argouml.uml.diagram.ArgoDiagram;
import org.argouml.uml.diagram.DiagramUtils;
import org.argouml.uml.diagram.static_structure.ui.UMLClassDiagram;
import org.argouml.uml.diagram.ui.UMLDiagram;
import org.omg.uml.foundation.core.Classifier;
import org.omg.uml.foundation.core.Generalization;
import org.omg.uml.foundation.core.UmlClass;

import com.metricsplugin.charts.annotation.ChartType;
import com.metricsplugin.charts.annotation.MetricChart;
import com.metricsplugin.exception.ConfigurationException;
import com.metricsplugin.metrics.AbstractMetric;

/**
 * Citada em OOA Metrics for the Unified Modeling Language. Marchesi.
 * DIT ou CL3.
 * É uma medida de quantas classes ancestrais podem, potencialmente, afetar uma classe.
 * @author bruno
 *
 */
@MetricChart(chartType=ChartType.BAR_CHART, info="metrics.info.DepthInheritance",
		labels={"metrics.DepthInheritance.labelX", "metrics.DepthInheritance.labelY"})
public class DepthInheritanceTree extends AbstractMetric{
	
	
	public DepthInheritanceTree() {
		super();
		
	}





	protected int profundidadeClasse(Classifier umlClass, int profundidadeAtual){
		
		//Iterator<Generalization> i = umlClass.getGeneralization().iterator();
		//existe o contador apenas para garantir que so consideramos heranca unica
		int cont=0;
		Classifier proximaClasse=null;
		for (Generalization g : umlClass.getGeneralization()){
			if(cont==0)
				proximaClasse = (Classifier) Model.getFacade().getGeneral(g);
			else
				break;
			cont++;
		
		}
		
		
		if(proximaClasse==null)
			return profundidadeAtual;
		
		return profundidadeClasse(proximaClasse, ++profundidadeAtual);


		
	}

	@Override
	public void calculateMetric() {

		List classes;
		try {
			classes = getClassDiagrams();
			Classifier umlClass;
			HashMap<String, Integer> depthInheritance = new HashMap<String, Integer>();
			
			for (int cont=0; cont< classes.size(); cont++){
				umlClass = (Classifier) classes.get(cont);
				int profundidade = profundidadeClasse(umlClass, 0);
				depthInheritance.put(umlClass.getName(), profundidade);
			}
			
			
			setResultado(depthInheritance);
		} catch (ConfigurationException e) {
			
			e.printStackTrace();
		}
		
	}

	@Override
	public String getMessage() {
		
		return preparaResultadoHash((HashMap<String, Integer>) getResult());
	}
	

}
