package com.bdc.XMLGenerator.test;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.bdc.XMLGenerator.pojo.Customer;
import com.bdc.XMLGenerator.pojo.Service;

public class JSONReader {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JSONReader test = new JSONReader();
		test.generateXMlStartBlock(test.generateJSON());
	}

	public String generateJSON() {
		// Service svc = new Service();
		JSONObject json = new JSONObject();
		json.put("id", "pipe1");
		json.put("name", "pipeline1");
		json.put("className", "com.test");
		json.put("startComponent", "reader");

		return json.toString();

	}

	public String generateJSONArray() {
		// Service svc = new Service();
		JSONArray array = new JSONArray();
		
		JSONObject json = new JSONObject();
		json.put("id", "pipe1");
		json.put("name", "pipeline1");
		json.put("className", "com.test");
		json.put("startComponent", "reader");
		
		array.put(json); //header
		
		json = new JSONObject();
		json.put("id", "reader");
		json.put("class", "com.bdc.bdi.storm.integrator.test.WordReader"); //comp header
		
		JSONArray propArray = new JSONArray();
		JSONObject propJson = new JSONObject();
		
		propJson.put("fileNamePattern", "^building.*$")	;
		propArray.put(propJson) ;
		
		propJson = new JSONObject();
		propJson.put("fileDir", "/data/stormIntegrator/")	;
		propArray.put(propJson) ;
		
		json.put("properties", propArray); //prop list
		
		propArray = new JSONArray();
		JSONObject compJson = new JSONObject();
		
		compJson.put("value", "HBaseComponent")	;
		propArray.put(compJson) ; 

		compJson = new JSONObject();
		compJson.put("value", "Agg")	;
		propArray.put(compJson) ; 
		
		json.put("properties", propArray); //comp list
		
		array.put(json); //body
		
		return array.toString();

	}
	
	public String generateXMlStartBlock(String jsonString) {

		Velocity.init();
		VelocityContext context = new VelocityContext();

		
		JSONArray array = new JSONArray(jsonString);
		
		JSONObject header = (JSONObject) array.get(0);		
		context.put("Pipeline", header);
		
		Template template = null;
		try {
			template = Velocity.getTemplate("./src/main/resources/FirstComp.vm");
		} catch (ResourceNotFoundException | ParseErrorException | MethodInvocationException rnfe) {
			// couldn't find the template
		} catch (Exception e) {
		}

		StringWriter sw = new StringWriter();
		template.merge(context, sw);
		System.out.println(sw.toString());
		
		for (int i = 1; i <= array.length(); i++) {
			JSONObject bean = (JSONObject) array.get(0);		
			context.put("Bean", bean);
			
			try {
				template = Velocity.getTemplate("./src/main/resources/BeanComp.vm");
			} catch (ResourceNotFoundException | ParseErrorException | MethodInvocationException rnfe) {
				// couldn't find the template
			} catch (Exception e) {
			}

			sw = new StringWriter();
			template.merge(context, sw);
		}
		
		return null; //sw.toString();
	}

}

// String xmlFile = "test.xml" ;
// JSONArray jsonList = new JSONArray(args[0]) ;
//
// while (JSONObject json : JSON)
