package com.metricsplugin.metrics.marchesi;

import java.util.ArrayList;
import java.util.List;

import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.uml.diagram.ArgoDiagram;
import org.argouml.uml.diagram.DiagramUtils;
import org.argouml.uml.diagram.static_structure.ui.UMLClassDiagram;
import org.argouml.uml.diagram.ui.UMLDiagram;
import org.omg.uml.foundation.core.Classifier;
import org.omg.uml.foundation.core.UmlClass;

import com.metricsplugin.charts.annotation.MetricChart;
import com.metricsplugin.exception.ConfigurationException;
import com.metricsplugin.metrics.AbstractMetric;

/**
 * Citada em OOA Metrics for the Unified Modeling Language. Marchesi.
 * Calcula o total de classes raizes de um diagrama de classes.
 * Chamada OA2.
 * @author bruno
 * TODO verificar se classes que nao possuem filhos devem entrar na lista.
 */

@MetricChart(info="metrics.info.NumberHierarchiesRoots")
public class NumberHierarchiesRoots extends AbstractMetric{
	
	private List<Classifier> rootClasses;

	
	
	
	
	public NumberHierarchiesRoots() {
		super();
	}




	private String preparaResultadoList(List<Classifier> rootClasses) {
		
		String retorno ="";
		for(Classifier umlClass : rootClasses)
			retorno = retorno+"\n"+umlClass.getName();
		
		return retorno;
	}


	@Override
	public void calculateMetric() {

		List classes;
		try {
			classes = getClassDiagrams();
	
			Classifier umlClass;
			rootClasses = new ArrayList<Classifier>();
			int numberRootClasses=0;
			
			for (int cont=0; cont< classes.size(); cont++){
				umlClass = (Classifier) classes.get(cont);
					if(umlClass.getGeneralization().isEmpty()){
						rootClasses.add(umlClass);
						numberRootClasses++;
					}
				}
			
			setResultado(numberRootClasses);
		} catch (ConfigurationException e) {
			
			e.printStackTrace();
		}

	}





	@Override
	public String getMessage() {
		String mensagemRetorno ="Total de classes raízes é: "+getResult()+"\n São elas:"+ preparaResultadoList((List<Classifier>) rootClasses);
		return mensagemRetorno;
	}
	
	
	

}
