package com.metricsplugin.metrics.brito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.model.Model;
import org.argouml.uml.diagram.ArgoDiagram;
import org.argouml.uml.diagram.DiagramUtils;
import org.argouml.uml.diagram.static_structure.ui.UMLClassDiagram;
import org.argouml.uml.diagram.ui.UMLDiagram;
import org.omg.uml.foundation.core.Attribute;
import org.omg.uml.foundation.core.Classifier;
import org.omg.uml.foundation.core.UmlClass;
import org.omg.uml.foundation.datatypes.VisibilityKind;
import org.omg.uml.foundation.datatypes.VisibilityKindEnum;

import com.metricsplugin.charts.annotation.ChartType;
import com.metricsplugin.charts.annotation.MetricChart;
import com.metricsplugin.exception.ConfigurationException;
import com.metricsplugin.metrics.AbstractMetric;

/**
 * Evaluating the Impact of Object-OrientedDesign on Software Quality -Brito e Abreu
 * Calcula o fator de invisibilidade de atributos.
 * 2.2.2 - AttributeHidingFactor
 * @author bruno
 *
 * TODO Verificar a utilidade da métrica no artigo
 *
 * TODO Verificar o valor se está correto
 * 
 *The AHF numerator is the sum of the invisibilities of all
  attributes defined in all classes. The invisibility of an
  attribute is the percentage of the total classes from which
  this attribute is not visible.
  The AHF denominator is the total number of attributes
  defined in the system under consideration.
 */

@MetricChart(chartType=ChartType.PIE_CHART, info="metrics.info.AttributeHidingFactor",
		labels={"metrics.AttributeHidingFactor.label1", "metrics.AttributeHidingFactor.label2"})
public class AttributeHidingFactor extends AbstractMetric {


	private static final long serialVersionUID = 1L;


	@Override
	public void calculateMetric() {
		

		List classes;
		try {
			classes = getClassDiagrams();

			HashMap<UmlClass, Integer> nAssociations = new HashMap<UmlClass, Integer>();
			Classifier umlClass;
			float contVis=0;
			int contAttr=0;
			for (int cont=0; cont< classes.size(); cont++){
				
				umlClass = (Classifier) classes.get(cont);
				ArrayList<Attribute> atts = (ArrayList<Attribute>) Model.getFacade().getAttributes(umlClass);
				for(Attribute att : atts){
					
					VisibilityKind visibilityKind = att.getVisibility();
					
					if(visibilityKind.equals(VisibilityKindEnum.VK_PRIVATE)){
						contVis++;
					}
					else if(visibilityKind.equals(VisibilityKindEnum.VK_PUBLIC)){
						//nao faz nada
					}
					else if(visibilityKind.equals(VisibilityKindEnum.VK_PROTECTED)){
						float filhos = Model.getFacade().getChildren(umlClass).size();
						contVis = contVis + (filhos/classes.size());
					}
					
					contAttr++;
				}

			}
			

			setResultado(contVis/contAttr);
	
		} catch (ConfigurationException e) {
			
			e.printStackTrace();
		}		

	}
}
