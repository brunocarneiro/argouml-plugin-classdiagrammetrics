package com.metricsplugin.test;

import org.argouml.application.Main;

import com.metricsplugin.configurations.Configurator;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//o que sera executado após o carregamento do argo.
		Main.addPostLoadAction(new Thread(){
			
			@Override
			public void run() {
				super.run();
				Configurator.configure();				
			}
			
		});
		
		Main.main(args);
		
		

	}

}
