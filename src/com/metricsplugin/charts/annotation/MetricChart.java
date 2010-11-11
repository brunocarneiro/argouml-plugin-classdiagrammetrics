package com.metricsplugin.charts.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotacao para registrar dados para gerar um gráfico.
 * @author bruno
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MetricChart{
	
	String info() default "";
	
	ChartType chartType() default ChartType.ONLY_DATA;
	
	OrdemValor x() default OrdemValor.KEY;
	
	OrdemValor y() default OrdemValor.VALUES;
	
	Class<? extends Object> resultado() default Float.class;
	
	String [] labels() default {};
	
	
	

}


