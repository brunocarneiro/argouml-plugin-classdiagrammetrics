package com.metricsplugin.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.argouml.application.helpers.ResourceLoaderWrapper;
import org.argouml.i18n.Translator;

public class ConfigurationAction extends AbstractAction {
	

	
	public ConfigurationAction(){
		
		
        super(Translator.localize("metrics.configurations"),
                ResourceLoaderWrapper.lookupIcon("metrics.configurations"));
        putValue(Action.SHORT_DESCRIPTION, 
                Translator.localize("metrics.configurations"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		new ConfigurationsPanel();
		
	}

	
}
