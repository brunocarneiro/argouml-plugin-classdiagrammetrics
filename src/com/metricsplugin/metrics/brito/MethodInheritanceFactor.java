package com.metricsplugin.metrics.brito;

import java.util.ArrayList;
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
 * Calcula o fator de invisibilidade de atributos.
 * 2.2.3 - MethodInheritanceFactor
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

@MetricChart(chartType=ChartType.PIE_CHART, info="metrics.info.MethodInheritanceFactor",
		labels={"metrics.MethodInheritanceFactor.label1", "metrics.MethodInheritanceFactor.label2"})
public class MethodInheritanceFactor extends AbstractMetric {


	private static final long serialVersionUID = 1L;


	public MethodInheritanceFactor() {
		super();
		
	}

	@Override
	public void calculateMetric() {

		List classes;
		try {
			classes = getClassDiagrams();

			Classifier umlClass;
			float contMethods=0;
			int contInheritanceMethods=0;
			for (int cont=0; cont< classes.size(); cont++){
				umlClass = (Classifier) classes.get(cont);
				contMethods=contMethods + Model.getFacade().getOperations(umlClass).size();
				List<Operation> metodosHerdados = new ArrayList<Operation>();
				descobreMetodosHerdados(umlClass, umlClass, metodosHerdados);
				contInheritanceMethods = contInheritanceMethods + metodosHerdados.size();

			}
			
			
			
			String mensagemRetorno;
			setResultado(contInheritanceMethods/contMethods);		
		} catch (ConfigurationException e) {
			
			e.printStackTrace();
		}

	}
	
	
	/**
	 * Descobre os metodos herdados. Algoritmo recursivo. 
	 * @param leafUmlClass classe inicial da recusao, a classe folha, da qual se deseja descobrir quais sao os metodos herdados
	 * @param parentUmlClass classe pai, na primeira chamada esse parametro deve ser nulo ou o mesmo da classe filha.
	 * @param metodosHerdados
	 * @return lista de metodos herdados.
	 */
	protected List<Operation> descobreMetodosHerdados(Classifier leafUmlClass, Classifier parentUmlClass, List<Operation> metodosHerdados){
		
		//Iterator<Generalization> i = umlClass.getGeneralization().iterator();
		//existe o contador apenas para garantir que so consideramos heranca unica
		int cont=0;
		if(parentUmlClass==null)
			parentUmlClass=leafUmlClass;
		
		UmlClass proximaClasse=null;
		for (Generalization g : parentUmlClass.getGeneralization()){
			if(cont==0){
				proximaClasse=(UmlClass) Model.getFacade().getGeneral(g);
				metodosHerdados  = adicionaMetodosHerdados(leafUmlClass,proximaClasse,metodosHerdados);
			}
			else
				break;
			cont++;
		
		}
		
		
		if(proximaClasse==null)
			return metodosHerdados;
		
		return descobreMetodosHerdados(leafUmlClass,proximaClasse, metodosHerdados);

		
	}
	
	/**
	 * Popula uma lista com os metodos herdados pela classe folha, a inicial da recursao
	 * @param leafClass
	 * @param parentClass
	 * @param inheritanceMethods
	 * @return
	 */
	protected List<Operation> adicionaMetodosHerdados(Classifier leafClass, Classifier parentClass, List<Operation> inheritanceMethods){
		
		ArrayList<Operation> operations = (ArrayList<Operation>) Model.getFacade().getOperations(parentClass);
		for(Operation operation : operations){
			
			VisibilityKind visibilityKind = operation.getVisibility();
			
			if(visibilityKind.equals(VisibilityKindEnum.VK_PUBLIC) && !checkIfIsOverrided(leafClass, operation)){
				inheritanceMethods.add(operation);
			}
			else if(visibilityKind.equals(VisibilityKindEnum.VK_PROTECTED) && !checkIfIsOverrided(leafClass, operation)){
				inheritanceMethods.add(operation);
			}
		}
		
		return inheritanceMethods;
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
