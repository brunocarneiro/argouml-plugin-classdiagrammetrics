package com.metricsplugin.metrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.metricsplugin.exception.ConfigurationException;
import javax.swing.JDialog;

import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.uml.diagram.ArgoDiagram;
import org.argouml.uml.diagram.DiagramUtils;
import org.argouml.uml.diagram.static_structure.ui.UMLClassDiagram;
import org.argouml.uml.diagram.ui.UMLDiagram;
import org.omg.uml.foundation.core.Classifier;

import com.metricsplugin.ui.SwingHelper;
import com.metricsplugin.util.Context;
import com.metricsplugin.util.MeasureScope;
import com.metricsplugin.util.MetricsConfiguration;

public abstract class AbstractMetric implements Metric {
	
	private Object resultado;
	
	
	protected String preparaResultadoHash(
			HashMap<String, Integer> depthInheritance) {
		
		String retorno="";
		Iterator<Integer> profundidades = depthInheritance.values().iterator();
		Set<String> chaves = depthInheritance.keySet();
		int cont=0;
		
		for(String umlClass :chaves){
			
			retorno=retorno+umlClass+" =";
			retorno=retorno+profundidades.next();
			retorno=retorno+ "<br>";
			cont++;
		}
		
		
		return "<html>Total: "+cont+"<br>"+retorno+"</html>";
	}

	
	public List getClassDiagrams() throws ConfigurationException{
		MetricsConfiguration config = (MetricsConfiguration) Context.getInstance().get("metricsConfiguration");
		Project p = ProjectManager.getManager().getCurrentProject();
		if(config!=null){
			
			if(config.getEscopo()==MeasureScope.PACKAGE){
				
				ArgoDiagram diagram = p.getActiveDiagram();
				DiagramUtils.getActiveDiagram();
				if(diagram instanceof UMLClassDiagram){
					//aqui e possivel realizar a analise dos diagramas de classes.
					UMLDiagram umlDiagram = (UMLDiagram)diagram;
				//	List associations = umlDiagram.getEdges();
					return umlDiagram.getNodes();
				}
			}
			else{
				List<Classifier> diagrams = new ArrayList<Classifier>();
				for(ArgoDiagram diagram  : p.getDiagramList()){
					if(diagram instanceof UMLClassDiagram){
						
						for(int i=0; i< diagram.getNodes().size();i++){
							if(diagram.getNodes().get(i) instanceof Classifier)
								diagrams.add((Classifier) diagram.getNodes().get(i));
						}
						
					}
				}
				return diagrams;
			}
		}
		
		throw new ConfigurationException();

			
		
	}
	

	public Object getResult() {
		return resultado;
	}


	public void setResultado(Object resultado) {
		this.resultado = resultado;
	}

	

	public String getMessage() {
		if(getResult()!=null)
			return getResult().toString();
		else
			return "";
	}
	
	@Override
	public void showResult() {
		
		JDialog dialog = SwingHelper.createJDialog(this,getMessage(),this.getClass().getSimpleName());
		SwingHelper.centerScreen(dialog);
		
	}
}
