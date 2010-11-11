package com.metricsplugin.metrics.brito;

import java.util.ArrayList;
import java.util.List;

import org.argouml.model.Model;
import org.omg.uml.foundation.core.Classifier;
import org.omg.uml.foundation.core.Operation;
import org.omg.uml.foundation.datatypes.VisibilityKind;
import org.omg.uml.foundation.datatypes.VisibilityKindEnum;

import com.metricsplugin.charts.annotation.ChartType;
import com.metricsplugin.charts.annotation.MetricChart;
import com.metricsplugin.exception.ConfigurationException;
import com.metricsplugin.metrics.AbstractMetric;

/**
 * Evaluating the Impact of Object-OrientedDesign on Software Quality -Brito e Abreu
 * Calcula o fator de invisibilidade de atributos.
 * 2.2.1 - MethodHidingFactor
 * @author bruno
 *
 * TODO Verificar a utilidade da métrica no artigo
 *
 * TODO Verificar o valor se está correto
 * 
 *The MHF numerator is the sum of the invisibilities of
all methods defined in all classes. The invisibility of a
method is the percentage of the total classes from which
this method is not visible.
The MHF denominator is the total number of methods
defined in the system under consideration.

 */
@MetricChart(chartType=ChartType.PIE_CHART, info="metrics.info.MethodHidingFactor",
		labels={"metrics.MethodHidingFactor.label1", "metrics.MethodHidingFactor.label2"})
public class MethodHidingFactor extends AbstractMetric {


	private static final long serialVersionUID = 1L;


	
	public MethodHidingFactor() {
		super();
		
	}


	@Override
	public void calculateMetric() {
		
		List classes;
		try {
			classes = getClassDiagrams();


			Classifier umlClass;
			float contVis=0;
			int contOperations=0;
			for (int cont=0; cont< classes.size(); cont++){
				umlClass = (Classifier) classes.get(cont);
				ArrayList<Operation> operations = (ArrayList<Operation>) Model.getFacade().getOperations(umlClass);
				for(Operation operation : operations){
					
					VisibilityKind visibilityKind = operation.getVisibility();
					
					if(visibilityKind.equals(VisibilityKindEnum.VK_PRIVATE)){
						contVis++;
					}
					else if(visibilityKind.equals(VisibilityKindEnum.VK_PUBLIC)){
						//nao faz nada
					}
					else if(visibilityKind.equals(VisibilityKindEnum.VK_PROTECTED)){
						float children = Model.getFacade().getChildren(umlClass).size();
						contVis = contVis + (children/classes.size());
					}
					
					contOperations++;
				}

			}
			

			setResultado( contVis/contOperations);		
	
		} catch (ConfigurationException e) {
			
			e.printStackTrace();
		}
		

	}

}
