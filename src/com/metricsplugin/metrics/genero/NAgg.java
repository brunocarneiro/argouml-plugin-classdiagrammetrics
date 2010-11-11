package com.metricsplugin.metrics.genero;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.model.Model;
import org.argouml.uml.diagram.ArgoDiagram;
import org.argouml.uml.diagram.DiagramUtils;
import org.argouml.uml.diagram.static_structure.ui.UMLClassDiagram;
import org.argouml.uml.diagram.ui.FigAssociation;
import org.argouml.uml.diagram.ui.UMLDiagram;
import org.omg.uml.foundation.core.UmlClass;
import org.omg.uml.foundation.datatypes.AggregationKindEnum;

import com.metricsplugin.charts.annotation.MetricChart;
import com.metricsplugin.metrics.AbstractMetric;

/**
 * Citada em Early measures for UML class diagrams. Genero
 * Calcula o número de agregações
 * Chamada NAggR.
 * @author bruno
 * 
 * 
 * DEFINITION. The Number of Aggregation Relationships metric (NAggR) is
 * defined as the number of aggregation relationships within a package.
 * GOAL. We propose this new metric to measure the package complexity due to
 * aggregation relationship (whole-part relationship).
 * COMMENTS. A higher number of aggregation relationships constitutes a greater
 * design complexity. Consequently, they may require greater cost in their
 * implementation and maintenance. This metric has been theoretically validated
 * [GEN 00a] as a size measure, following the formal measurement framework
 * proposed by Briand et al. in [BRI 96].

 * 
 */

@MetricChart(info="metrics.info.NAgg")
public class NAgg extends AbstractMetric {


	public NAgg() {
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
			int contAggregation=0;
			
			
			while(elements.hasMoreElements()){
				
				element = elements.nextElement();
				if(element instanceof FigAssociation && (
					AggregationKindEnum.AK_AGGREGATE.equals(Model.getFacade().getAggregation(((FigAssociation)element).getDestinationConnector())) ||
					AggregationKindEnum.AK_AGGREGATE.equals(Model.getFacade().getAggregation(((FigAssociation)element).getSourceConnector())))){
					
					contAggregation++;
					
				}
				
				
			}
			
			setResultado(contAggregation);	
		}
		
		

	}


}
