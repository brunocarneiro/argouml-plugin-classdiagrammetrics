package com.metricsplugin.metrics.brito;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.model.Model;
import org.argouml.uml.diagram.ArgoDiagram;
import org.argouml.uml.diagram.DiagramUtils;
import org.argouml.uml.diagram.static_structure.ui.UMLClassDiagram;
import org.argouml.uml.diagram.ui.UMLDiagram;
import org.omg.uml.foundation.core.Classifier;
import org.omg.uml.foundation.core.Operation;
import org.omg.uml.foundation.core.Parameter;
import org.omg.uml.foundation.core.UmlClass;
import org.omg.uml.foundation.datatypes.VisibilityKind;
import org.omg.uml.foundation.datatypes.VisibilityKindEnum;

import com.metricsplugin.charts.annotation.ChartType;
import com.metricsplugin.charts.annotation.MetricChart;
import com.metricsplugin.exception.ConfigurationException;
import com.metricsplugin.metrics.AbstractMetric;

/**
 * Evaluating the Impact of Object-OrientedDesign on Software Quality -Brito e Abreu
 * Calcula o fator de polimorfismo..
 * 2.2.5 - PolymorphismFactor
 * @author bruno
 *
 * TODO Verificar a utilidade da métrica no artigo
 *
 * TODO Verificar o valor se está correto
 * 
 *The POF numerator represents the actual number of
possible different polymorphic situations. Indeed, a given
message sent to class Cican be bound, statically or dy-
namically, to a named method implementation. The latter
can have as many shapes (“morphos” in ancient Greek) as
the number of times this same method is overridden (in C
i
descendants).
The POF denominator represents the maximum number
o possible distinct polymorphic situations for class C .
f
i
This would be the case where all new methods defined in
Ci
would be overridden in all of their derived classes.


 */

@MetricChart(chartType=ChartType.PIE_CHART, info="metrics.info.PolymorphismFactor",
		labels={"metrics.PolymorphismFactor.label1", "metrics.PolymorphismFactor.label2"})
public class PolymorphismFactor extends AbstractMetric {


	private static final long serialVersionUID = 1L;

		
	public PolymorphismFactor() {
		super();
		
	}

	@Override
	public void calculateMetric() {
		

		List classes;
		try {
			classes = getClassDiagrams();
	
			Classifier umlClass;
			float countDescendents=0, contOverrideMethods=0, contNewMethods=0, polymorphismFactor=0, mult=0;
			
			for (int cont=0; cont< classes.size(); cont++){
				umlClass = (Classifier) classes.get(cont);
				int descendents =Model.getFacade().getSpecializations(umlClass).size();
				countDescendents=countDescendents + descendents;
				List<Operation> overrideMethods = new ArrayList<Operation>();
				contOverrideMethods=contOverrideMethods+ descobreMetodosOverride(umlClass, overrideMethods).size();
				contNewMethods=contNewMethods+descobreNovosMetodos(umlClass,overrideMethods).size();
				
				mult=mult+(contNewMethods*descendents);
	
			}
			
			polymorphismFactor = contOverrideMethods /mult;
			setResultado(polymorphismFactor);
	
	
	
		} catch (ConfigurationException e) {
			
			e.printStackTrace();
		}

	}
	
	
	private List<Operation> descobreNovosMetodos(Classifier umlClass,
			List<Operation> overrideMethods) {
		
		ArrayList<Operation> operations = (ArrayList<Operation>) Model.getFacade().getOperations(umlClass);
		List<Operation> novosMetodos = new ArrayList<Operation>();
		boolean isOverriden=false;
		for(Operation operation : operations){
			for(Operation operationOverride : overrideMethods){
				
				if(operation.getName().equals(operationOverride.getName())&& comparaParametros(operation.getParameter(),operationOverride.getParameter()))
					isOverriden=true;
			}
			if(!isOverriden){
				novosMetodos.add(operation);
			}
		}
		
		return novosMetodos;
		
	}


	/**
	 * Descobre os metodos herdados. Algoritmo recursivo. 
	 * @param leafClassifier classe inicial da recusao, a classe folha, da qual se deseja descobrir quais sao os metodos herdados
	 * @param parentClassifier classe pai, na primeira chamada esse parametro deve ser nulo ou o mesmo da classe filha.
	 * @param overrideMethods
	 * @return lista de metodos herdados.
	 */
	protected List<Operation> descobreMetodosOverride(Classifier leafClassifier, List<Operation> overrideMethods){
		
		
		Classifier proximaClasse=null;
		for (Classifier g : (Collection<UmlClass>) Model.getFacade().getChildren(leafClassifier)){

				proximaClasse=g;
				if(!proximaClasse.equals(leafClassifier)){
					overrideMethods  = adicionaMetodosOverride(leafClassifier,proximaClasse,overrideMethods);
					descobreMetodosOverride(proximaClasse, overrideMethods);
				}
		}		
		
		return overrideMethods;
		
	}
	

	protected List<Operation> adicionaMetodosOverride(Classifier leafClass, Classifier parentClass, List<Operation> overrideMethods){
		
		ArrayList<Operation> operations = (ArrayList<Operation>) Model.getFacade().getOperations(parentClass);
		for(Operation operation : operations){
			
			VisibilityKind visibilityKind = operation.getVisibility();
			
			if(visibilityKind.equals(VisibilityKindEnum.VK_PUBLIC) && checkIfIsOverrided(leafClass, operation)){
				overrideMethods.add(operation);
			}
			else if(visibilityKind.equals(VisibilityKindEnum.VK_PROTECTED) && checkIfIsOverrided(leafClass, operation)){
				overrideMethods.add(operation);
			}
		}
		
		return overrideMethods;
	}


	/**
	 * Verifica se foi sobrescrito o metodo na classe filha
	 * @param leafClass - classe incial da recusao
	 * @param method
	 * @return
	 */
	protected boolean checkIfIsOverrided(Classifier leafClass, Operation method) {
		
		ArrayList<Operation> operations = (ArrayList<Operation>) Model.getFacade().getOperations(leafClass);
		for(Operation operationLeaf : operations){
		//TODO Comparar a tipagem de cada parametro
			if(operationLeaf.getName().equals(method.getName())&& comparaParametros(operationLeaf.getParameter(),method.getParameter()))
				return true;
		}
		
		
		return false;
	}


	//TODO verificar se a ordem dos parametros afeta.
	private boolean comparaParametros(List<Parameter> parameter,
			List<Parameter> parameter2) {
		
		
		for (Parameter p: parameter){
			
			for (Parameter p2: parameter2){
				
				if(p !=null && p2!=null && p.getKind()!=null && p2.getKind()!=null && !p.getKind().equals(p2.getKind()))
					return false;
			}
			
		}
		
		
		return true;
	}
	
	

}
