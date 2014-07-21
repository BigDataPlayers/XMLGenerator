package com.bdc.XMLGenerator;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONReader {

	public static void main(String[] args) {
		JSONReader test = new JSONReader();
		test.generateXMl(test.generateJSONArray());
	}

	public String generateJSONArray() {
		JSONArray array = new JSONArray();
		
		JSONObject json = new JSONObject();
		json.put("id", "pipe1");
		json.put("name", "pipeline1");
		json.put("className", "com.test");
		json.put("startComponent", "reader");
		
		array.put(json); //header
		
		
		//Reader Comp Definition
		json = new JSONObject(); 
		json.put("id", "reader");
		json.put("className", "com.bdc.bdi.storm.integrator.test.WordReader"); //comp header
		
		JSONArray propArray = new JSONArray();
		JSONObject propJson = new JSONObject();
		
		propJson.put("name","fileNamePattern")	;
		propJson.put("value", "^building.*$")	;
		propArray.put(propJson) ;
		
		propJson = new JSONObject();
		propJson.put("name", "fileDir")	;
		propJson.put("value", "/data/stormIntegrator/")	;
		propArray.put(propJson) ;
		
		json.put("properties", propArray); //prop list
		
		propArray = new JSONArray();
		JSONObject compJson = new JSONObject();
		
		compJson.put("value", "HBaseComponent")	;
		propArray.put(compJson) ; 

		compJson = new JSONObject();
		compJson.put("value", "Agg")	;
		propArray.put(compJson) ; 
		
		json.put("components", propArray); //comp list
		
		array.put(json); //body

		
		
		//Hbase Comp defination
		json = new JSONObject(); 
		json.put("id", "HBaseComponent");
		json.put("className", "com.bdc.bdi.storm.integrator.test.HBaseWriter"); //comp header
		
		propArray = new JSONArray();
		propJson = new JSONObject();
		
		propJson.put("name","name")	;
		propJson.put("value", "HBaseComponent")	;
		propArray.put(propJson) ;
		
		propJson = new JSONObject();
		propJson.put("name", "hBaseConfig")	;
		propJson.put("value", "/data/common/git/bdi/BigDataConfig/core-site.xml")	;
		propArray.put(propJson) ;
		
		propJson = new JSONObject();
		propJson.put("name", "desthTableName")	;
		propJson.put("value", "building")	;
		propArray.put(propJson) ;
				
		propJson = new JSONObject();
		propJson.put("name", "primaryKey")	;
		propJson.put("value", "BuildingID")	;
		propArray.put(propJson) ;

		propJson = new JSONObject();
		propJson.put("name", "columnFamilyName")	;
		propJson.put("value", "cf1")	;
		propArray.put(propJson) ;

		propJson = new JSONObject();
		propJson.put("name", "metaDataTableName")	;
		propJson.put("value", "metadata")	;
		propArray.put(propJson) ; 

		json.put("properties", propArray); //prop list
		array.put(json); //body
		
		
		//Aggregate Comp Definition 
    	json = new JSONObject(); 
		json.put("id", "Agg");
		json.put("className", "com.bdc.bdi.storm.integrator.test.HBaseAggregatesWriterComponent"); //comp header
		
		propArray = new JSONArray();
		propJson = new JSONObject();
		
		propJson.put("name","name")	;
		propJson.put("value", "AggregateComponent")	;
		propArray.put(propJson) ;
		
		propJson = new JSONObject();
		propJson.put("name", "aggregateName")	;
		propJson.put("value", "building_agg")	;
		propArray.put(propJson) ;
		
		propJson = new JSONObject();
		propJson.put("name", "counterName")	;
		propJson.put("value", "cnt")	;
		propArray.put(propJson) ;

        propJson = new JSONObject();
		propJson.put("name", "metaDataTableName")	;
		propJson.put("value", "metadata")	;
		propArray.put(propJson) ;

		propJson = new JSONObject();
		propJson.put("name", "columnFamilyName")	;
		propJson.put("value", "cf1")	;
		propArray.put(propJson) ;

		propJson = new JSONObject();
		propJson.put("name", "fields")	;
		propJson.put("value", "country")	;
		propArray.put(propJson) ; 

		propJson = new JSONObject();
		propJson.put("name", "filter")	;
		propJson.put("value", "Country~EqualsExpression~USA,Country~EqualsExpression~USA")	;
		propArray.put(propJson) ; 
		
		propJson = new JSONObject();
		propJson.put("name", "hBaseConfig")	;
		propJson.put("value", "/data/common/git/bdi/BigDataConfig/core-site.xml")	;
		propArray.put(propJson) ; 
		
		json.put("properties", propArray); //prop list
		array.put(json); //body
		
		System.out.println("JSON Request - \n" + array.toString());
		return array.toString();

	}
	
	public String generateXMl(String jsonString) {

		Properties p = new Properties();
		p.put("runtime.introspector.uberspect", "com.bdc.XMLGenerator.util.CustomUberspector");
		Velocity.init(p);
		VelocityContext context = new VelocityContext();
	
		JSONArray array = new JSONArray(jsonString); 
		JSONObject header = (JSONObject) array.get(0);		
		context.put("Pipeline", header);
		array.remove(0);
		
		context.put("Beans", array);
		
		Template template = null;
		try {
			template = Velocity.getTemplate("./src/main/resources/FirstComp.vm");
		} catch (ResourceNotFoundException | ParseErrorException | MethodInvocationException rnfe) {
			// couldn't find the template
			rnfe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		StringWriter sw = new StringWriter();
		template.merge(context, sw);
		System.out.println(sw.toString());
				
		return sw.toString();
	}

}

