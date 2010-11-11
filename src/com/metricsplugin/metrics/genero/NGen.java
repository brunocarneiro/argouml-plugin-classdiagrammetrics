package com.metricsplugin.metrics.genero;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.uml.diagram.ArgoDiagram;
import org.argouml.uml.diagram.DiagramUtils;
import org.argouml.uml.diagram.static_structure.ui.UMLClassDiagram;
import org.argouml.uml.diagram.ui.FigGeneralization;
import org.argouml.uml.diagram.ui.UMLDiagram;
import org.omg.uml.foundation.core.UmlClass;

import com.metricsplugin.charts.annotation.MetricChart;
import com.metricsplugin.metrics.AbstractMetric;

/**
 * Citada em Early measures for UML class diagrams. Genero
 * Calcula o número de agregações
 * Chamada NGen.
 * @author bruno
 * 
 */
@MetricChart(info="metrics.info.NGen")
public class NGen extends AbstractMetric {


	
	public NGen() {
		super();
		
	}


	@Override
	public void calculateMetric() {
		
		Project p = ProjectManager.getManager().getCurrentProject();
		ArgoDiagram diagram = p.getActiveDiagram();
		DiagramUtils.getActiveDiagram();
		
		if(diagram instanceof UMLClassDiagram){
			//aqui e possivel realizar a analise dos diagramas de classes.
			UMLDiagram umlDiagram = (UMLDiagram)diagram;
			List classes = umlDiagram.getNodes();
			HashMap<UmlClass, Integer> nAssociations = new HashMap<UmlClass, Integer>();
			Enumeration elements = umlDiagram.elements();
			Object element=null;
			int contGeneralization=0;
			
			
			while(elements.hasMoreElements()){
				
				element = elements.nextElement();
				if(element instanceof FigGeneralization){
					
					contGeneralization++;
					
				}
				
				
			}
			
			setResultado(contGeneralization);

		}
		
		

	}


}
