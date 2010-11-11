package com.metricsplugin.metrics.brito;

import java.util.Collection;
import java.util.List;

import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.sequence2.diagram.UMLSequenceDiagram;
import org.argouml.uml.diagram.ArgoDiagram;
import org.omg.uml.behavioralelements.collaborations.ClassifierRole;
import org.omg.uml.behavioralelements.collaborations.Message;
import org.omg.uml.behavioralelements.commonbehavior.CallAction;
import org.omg.uml.foundation.core.ModelElement;
import org.omg.uml.foundation.core.UmlClass;

import com.metricsplugin.charts.annotation.ChartType;
import com.metricsplugin.charts.annotation.MetricChart;
import com.metricsplugin.metrics.AbstractMetric;

/**
 * Evaluating the Impact of Object-OrientedDesign on Software Quality -Brito e Abreu
 * 2.2.6 - Coupling Factor
 * Calcula o fator de acoplamento
 * TODO Verificar porque está contando também o retorno. Está também pegando apenas o de um diagrama, deve pegar de
 * todos os diagramas de sequencia e todas as classes.
 * 
 * @author bruno
 *
 *
 *Pega todas as classes declaradas no sistema e todas as mensagens declaradas no sistema.
 *Divide o total de acoplamento pelo Numero de classes ao quadrado - o numero de classes.
 *
 */
@MetricChart(chartType=ChartType.PIE_CHART, labels={"metrics.CouplingFactor.label1", "metrics.CouplingFactor.label2"})
public class CouplingFactor extends AbstractMetric {
	
	
	

	@Override
	public void calculateMetric() {
		
		Project p = ProjectManager.getManager().getCurrentProject();
		double contAcoplamento=0, contClasses=0;
		
		contClasses = calcularNumeroClasses();
		for(ArgoDiagram diagram  : p.getDiagramList()){
			if(diagram instanceof UMLSequenceDiagram){
				
				UMLSequenceDiagram seqDiagram = (UMLSequenceDiagram)diagram;
				List<ClassifierRole> classifiers = seqDiagram.getNodes();
				List<Message> mensagens = seqDiagram.getEdges();
				
	
				
				for (ClassifierRole classifierRole : classifiers) {
	
					classifierRole.getName();
	
					for (Message m : mensagens) {
	
						if (m.getSender().equals(classifierRole)&& m.getAction() instanceof CallAction) {
							contAcoplamento++;
						}
	
					}
					//org.argouml.model.Model.getFacade().getCollaborations(classifierRole);
					
				}

			}
		}
		double resultado =  contAcoplamento/(Math.pow(contClasses, 2.0)-contClasses);
		setResultado(resultado);
	}

	protected int calcularNumeroClasses() {
		Project p = ProjectManager.getManager().getCurrentProject();
		Collection<ModelElement> elements =  ((org.omg.uml.modelmanagement.Model)p.getModel()).getOwnedElement();
		int cont=0;
		
		for(ModelElement elem : elements){
			
			if(elem instanceof UmlClass)
				cont++;

		}
		return cont;
	}
	

}
