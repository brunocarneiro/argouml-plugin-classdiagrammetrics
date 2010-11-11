package com.metricsplugin.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.argouml.i18n.Translator;
import org.argouml.ui.ProjectBrowser;

import com.metricsplugin.charts.Chart;
import com.metricsplugin.charts.ChartFactory;
import com.metricsplugin.metrics.Metric;
import com.metricsplugin.metrics.brito.AttributeHidingFactor;
import com.metricsplugin.metrics.brito.MethodHidingFactor;
import com.metricsplugin.metrics.brito.PolymorphismFactor;
import com.metricsplugin.metrics.genero.NAgg;
import com.metricsplugin.metrics.genero.NAssoc;
import com.metricsplugin.metrics.genero.NAssocXClasses;
import com.metricsplugin.metrics.marchesi.ClassDependencyAverage;
import com.metricsplugin.metrics.marchesi.ClassDependencyStandardDesviation;
import com.metricsplugin.metrics.marchesi.DepthInheritanceTree;
import com.metricsplugin.metrics.marchesi.ImmediateSubClasses;
import com.metricsplugin.metrics.marchesi.NumberHierarchiesRoots;
import com.metricsplugin.metrics.marchesi.OverallNumberClass;
import com.metricsplugin.util.Context;

public class SwingHelper {
	
	public static void centerScreen(Component component) {
		  Dimension dim = ProjectBrowser.getInstance().getToolkit().getScreenSize();
		  Rectangle abounds = component.getBounds();
		  component.setLocation((dim.width - abounds.width) / 2,
		      (dim.height - abounds.height) / 2);
		  component.setVisible(true);
		  component.requestFocus();
	}
	
	
	public static JDialog createJDialog(Metric m, String message, String chave){
		
		final JDialog dialog = new JDialog(ProjectBrowser.getInstance(),Translator.localize("metrics."+chave),true );
		dialog.setSize(680,400);
		JPanel panelPrincipal = new JPanel();
		JLabel label = new JLabel(Translator.localize(Context.BUNDLE_PREFIX+chave));
		label.setSize(400, 300);
		JScrollPane panelSecundario = new JScrollPane(label);
		panelPrincipal.add(new JLabel(message));
		
		JTabbedPane tab1 = new JTabbedPane();
		tab1.addTab(Translator.localize(UiConstants.RESULTS_TAB_NAME), panelPrincipal);
		tab1.addTab(Translator.localize(UiConstants.INFO_TAB_NAME), panelSecundario);
		Chart chart = ChartFactory.createChart(m);
		if(chart!=null)
			tab1.addTab(Translator.localize(UiConstants.CHART_TAB_NAME), chart.getChartJPanel());

		JButton okButton = new JButton(Translator.localize(UiConstants.OK_BUTTON_NAME));
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});
		dialog.add(okButton, BorderLayout.PAGE_END);
		
		dialog.add(tab1);
		return dialog;
		
	}
	
	
	public static void createCategoryMenu(){
		
		JMenuBar menuBar = (JMenuBar) ProjectBrowser.getInstance().getJMenuBar();
		JMenu menu = (JMenu)Context.getInstance().get("metrics.menu");
		menuBar.remove(menu);
		JMenu metrics = new JMenu(Translator.localize(UiConstants.CATEGORY_MENU_NAME));
		JMenu ooMenu = new JMenu(Translator.localize(UiConstants.OO_MENU_NAME));
		JMenu encapsulamento = new JMenu(Translator.localize(UiConstants.ENCAPSULATION_MENU_NAME));
		encapsulamento.add(new ActionClassMetric("metrics.AttributeHidingFactor",AttributeHidingFactor.class));
		encapsulamento.add(new ActionClassMetric("metrics.MethodHidingFactor",MethodHidingFactor.class));
		ooMenu.add(encapsulamento);
		//heranca
		JMenu heranca = new JMenu(Translator.localize(UiConstants.INHERITANCE_MENU_NAME));
		heranca.add(new ActionClassMetric("DepthInheritanceTree",DepthInheritanceTree.class));
		heranca.add(new ActionClassMetric("ImmediateSubClasses",ImmediateSubClasses.class));
		heranca.add(new ActionClassMetric("NumberHierarchiesRoots",NumberHierarchiesRoots.class));		
		ooMenu.add(heranca);
		
		JMenu polimorfismo = new JMenu(Translator.localize(UiConstants.POLYMORPHISM_MENU_NAME));
		polimorfismo.add(new ActionClassMetric("PolymorphismFactor",PolymorphismFactor.class));
		ooMenu.add(polimorfismo);
		
		metrics.add(ooMenu);
		
		JMenu complexidade = new JMenu(Translator.localize(UiConstants.COMPLEXITY_MENU_NAME));
		complexidade.add(new ActionClassMetric("NAssoc",NAssoc.class));
		complexidade.add(new ActionClassMetric("NAgg",NAgg.class));
		complexidade.add(new ActionClassMetric("NAssocXClasses",NAssocXClasses.class));
		complexidade.add(new ActionClassMetric("ClassDependencyStandardDesviation",ClassDependencyStandardDesviation.class));
		complexidade.add(new ActionClassMetric("ClassDependencyAverage",ClassDependencyAverage.class));
		complexidade.add(new ActionClassMetric("OverallNumberClass",OverallNumberClass.class));
		metrics.add(complexidade);
		
		JMenu tamanho = new JMenu(Translator.localize(UiConstants.SIZE_MENU_NAME));
		tamanho.add(new ActionClassMetric("NAssoc",NAssoc.class));
		tamanho.add(new ActionClassMetric("OverallNumberClass",OverallNumberClass.class));
		metrics.add(tamanho);
		metrics.add(new ConfigurationAction());
		Context.getInstance().insere("metrics.categoryMenu", metrics);
		Context.getInstance().insere("metrics.categoryMenuEnabled", true);
		menuBar.add(metrics);
		menuBar.validate();
		ProjectBrowser.getInstance().repaint();
		
	}


	public static void restoreDefaultMenu() {
		

		JMenuBar menuBar = (JMenuBar) ProjectBrowser.getInstance().getJMenuBar();
		JMenu menu = (JMenu)Context.getInstance().get("metrics.categoryMenu");
		menuBar.remove(menu);

		menu = (JMenu)Context.getInstance().get("metrics.menu");
		menuBar.add(menu);		
		menuBar.validate();
		menuBar.updateUI();
		Context.getInstance().insere("metrics.categoryMenuEnabled", false);
	}
	


}
