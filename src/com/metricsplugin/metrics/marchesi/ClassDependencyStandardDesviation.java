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

import com.metricsplugin.exception.ConfigurationException;
import com.metricsplugin.metrics.AbstractMetric;

/**
 * Citada em OOA Metrics for the Unified Modeling Language. Marchesi.
 * Calcula o desvio padrão de dependência de classes.
 * Chamada OA6.
 * @author bruno
 * TODO verificar se no numero de dependencia entra as associações, pois elas podem ser consideradas dependencias.
 * TODO verificar se o calculo do desvio padrao esta correto.
 */

public class ClassDependencyStandardDesviation extends AbstractMetric{
	


	public ClassDependencyStandardDesviation() {
		super();
		
	}


	@Override
	public void calculateMetric() {
		

		List classes;
		try {
			classes = getClassDiagrams();

			HashMap<Classifier, Integer> nAssociations = new HashMap<Classifier, Integer>();
			Classifier umlClass;
			double numDep =0, numDesvi=0;
			
			for (int cont=0; cont< classes.size(); cont++){
				umlClass = (Classifier) classes.get(cont);
				int numAssoc = Model.getFacade().getAssociatedClasses(umlClass).size();
				
				nAssociations.put(umlClass, numAssoc);
				numDep = numDep+umlClass.getClientDependency().size();

			}
			
			for (int cont=0; cont< classes.size(); cont++){
				umlClass = (Classifier) classes.get(cont);
				int numAssoc = Model.getFacade().getAssociatedClasses(umlClass).size();
				
				nAssociations.put(umlClass, numAssoc);
				numDesvi = numDesvi+Math.pow(umlClass.getClientDependency().size()-numDep,2);

			}
			
		
			double standardDesviation = Math.sqrt(numDesvi/classes.size()); 
			setResultado(standardDesviation);
	
	
		} catch (ConfigurationException e) {
			
			e.printStackTrace();
		}

		
	}

	@Override
	public String getMessage() {
		return ""+getResult();
	}

}
