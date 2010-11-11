package com.metricsplugin.metrics.mine;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.uml.diagram.ArgoDiagram;
import org.argouml.uml.diagram.DiagramUtils;
import org.argouml.uml.diagram.static_structure.ui.FigClassifierBox;
import org.argouml.uml.diagram.static_structure.ui.UMLClassDiagram;
import org.argouml.uml.diagram.ui.ArgoFig;
import org.argouml.uml.diagram.ui.FigAbstraction;
import org.argouml.uml.diagram.ui.FigAssociation;
import org.argouml.uml.diagram.ui.FigDependency;
import org.argouml.uml.diagram.ui.FigEdgeModelElement;
import org.argouml.uml.diagram.ui.UMLDiagram;
import org.omg.uml.foundation.core.Classifier;

import com.metricsplugin.metrics.AbstractMetric;

/**
 * 
 * Verificar as métricas de dependencia, PK1, PK2, PK3. Marchesi
 * OOA Metrics for the Unified Modeling Language
 * 
 */
public class InterPackageClassCoupling extends AbstractMetric{
	
	HashMap<String, List<String>> deps = new HashMap<String, List<String>>();
	
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
				
				if(fig instanceof FigAssociation || (fig instanceof FigDependency && !(fig instanceof FigAbstraction))  ){
					adicionaItem(((FigClassifierBox)((FigEdgeModelElement)fig).getSourceFigNode()),
					((FigClassifierBox)((FigEdgeModelElement)fig).getDestFigNode()));
					cont++;
				}

			}
			//dois porque so e possivel associacao e dependencia.
			media=cont/combinacao((double)(umlDiagram.getNodes().size()), 2);
			setResultado(preparaResultadoHash2(deps, media));

		}
		
		

	}
	
	protected void adicionaItem(FigClassifierBox origem, FigClassifierBox dest){
		
		List<String> depsClass =  deps.get(origem.getName());
		if(depsClass==null)
			depsClass=new ArrayList<String>();
		
		depsClass.add(dest.getName());
		
		deps.put(origem.getName(), depsClass);
		
	}
	
	protected String preparaResultadoHash2(HashMap<String, List<String>> hash, double media) {
		
		String retorno="";
		Iterator<List<String>> profundidades = hash.values().iterator();
		Set<String> chaves = hash.keySet();
		int cont=0;
		
		for(String umlClass :chaves){
			
			retorno=retorno+umlClass+" =";
			retorno=retorno+profundidades.next();
			retorno=retorno+ "<br>";
			cont+=hash.get(umlClass).size();
		}
		
		retorno=retorno+"<br> Média: "+media;
		return "<html>Total: "+cont+"<br>"+retorno+"</html>";
	}
	
	
	public double combinacao(double num1, double num2){
		
		return fatorial(num1)/fatorial(num2)*fatorial(num1-num2);
		
		
	}
	
	public double fatorial(double num1){
		
		if(num1==0){
			return 1;
		}
		
		return num1*fatorial(num1-1);
		
	}


}
