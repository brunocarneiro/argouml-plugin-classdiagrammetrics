package com.metricsplugin.metrics.marchesi;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.uml.diagram.ArgoDiagram;
import org.argouml.uml.diagram.DiagramUtils;
import org.argouml.uml.diagram.static_structure.ui.FigClassifierBox;
import org.argouml.uml.diagram.static_structure.ui.UMLClassDiagram;
import org.argouml.uml.diagram.ui.ArgoFig;
import org.argouml.uml.diagram.ui.FigAbstraction;
import org.argouml.uml.diagram.ui.FigEdgeModelElement;
import org.argouml.uml.diagram.ui.FigGeneralization;
import org.argouml.uml.diagram.ui.UMLDiagram;
import org.omg.uml.foundation.core.Classifier;

import com.metricsplugin.charts.annotation.ChartType;
import com.metricsplugin.charts.annotation.MetricChart;
import com.metricsplugin.metrics.AbstractMetric;

/**
 * Numero de SubClasses Imediatas
 * @author bruno
 *
 */

@MetricChart(chartType=ChartType.BAR_CHART, info="metrics.info.DepthInheritance",
		labels={"metrics.DepthInheritance.labelX", "metrics.DepthInheritance.labelY"})
public class ImmediateSubClasses extends AbstractMetric{
	
	Map<String,Integer> deps = new HashMap<String, Integer>();

	@Override
	public void calculateMetric() {
		
		Project p = ProjectManager.getManager().getCurrentProject();
		ArgoDiagram diagram = p.getActiveDiagram();
		DiagramUtils.getActiveDiagram();
		
		if(diagram instanceof UMLClassDiagram){
			//aqui e possivel realizar a analise dos diagramas de classes.
			UMLDiagram umlDiagram = (UMLDiagram)diagram;
		
			
			double cont=0,media=0;
			Classifier umlClass;
			double numDep =0;
			Enumeration e = umlDiagram.elements();
			while (e.hasMoreElements()){
				ArgoFig fig = (ArgoFig) e.nextElement();
				
				if(fig instanceof FigAbstraction || fig instanceof FigGeneralization){
					adicionaItem(((FigClassifierBox)((FigEdgeModelElement)fig).getDestFigNode()),((FigClassifierBox)((FigEdgeModelElement)fig).getSourceFigNode()));
				}

			}
			//dois porque so e possivel associacao e dependencia.
			setResultado(deps);

		}
		
		

	}
	
	protected void adicionaItem(FigClassifierBox origem, FigClassifierBox dest){
		
		Integer depsClass =  deps.get(origem.getName());
		if(depsClass==null)
			depsClass=new Integer(0);
		
		++depsClass;
		
		deps.put(origem.getName(), depsClass);
		
	}
}
