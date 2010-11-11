package com.metricsplugin.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.argouml.application.helpers.ResourceLoaderWrapper;
import org.argouml.i18n.Translator;

import com.metricsplugin.metrics.Metric;

public class ActionClassMetric extends AbstractAction{
	

	private Class<? extends Metric> metricClass;
	
	public ActionClassMetric(String s, Class<? extends Metric> metricClass){
		
		
        super(Translator.localize(s),
                ResourceLoaderWrapper.lookupIcon(s));
        this.metricClass=metricClass;
        putValue(Action.SHORT_DESCRIPTION, 
                Translator.localize(s));
	}
	
	public ActionClassMetric(){
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Metric metric;
		try {
			metric = metricClass.newInstance();
			metric.calculateMetric();
			metric.showResult();
				
		} catch (InstantiationException e1) {
			
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			
			e1.printStackTrace();
		}

	}
	
	

}
