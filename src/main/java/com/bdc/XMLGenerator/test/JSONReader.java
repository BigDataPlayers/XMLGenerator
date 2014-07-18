package com.bdc.XMLGenerator.test;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

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
		test.generateXMlStartBlock(test.generateJSONArray());
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
		
		return array.toString();

	}
	
	public String generateXMlStartBlock(String jsonString) {

		Velocity.init();
		VelocityContext context = new VelocityContext();

		
	//	JSONObject incomingJSON = new JSONObject(jsonString);
		
		JSONArray array = new JSONArray(jsonString); //incomingJSON.getJSONArray(null);
		
		JSONObject header = (JSONObject) array.get(0);		
		context.put("Pipeline", header);
		array.remove(0);
		
		List<JSONWrapper> beans = new ArrayList(); 
		
		for (int i=0; i<array.length();i++)
		{
			
			JSONWrapper wrapper = new JSONWrapper(); 
			
			JSONObject obj = array.getJSONObject(i); 
			wrapper.setObject(obj);
			
			beans.add(wrapper); 
			
			List<JSONObject> properties = new ArrayList(); 
			List<JSONObject> components = new ArrayList(); 
			
			JSONArray a1 = obj.getJSONArray("properties"); 
			JSONArray a2 = obj.getJSONArray("components"); 
			
			for (int j=0;j<a1.length();j++)
			{
				properties.add((JSONObject)a1.get(j)); 
			}

			for (int j=0;j<a2.length();j++)
			{
				components.add((JSONObject)a2.get(j)); 
			}

			
			wrapper.setComponents(components);
			wrapper.setProperties(properties);
		
		}
			
			
		
		
		
		context.put("Beans", beans);
		
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
				
		return null; //sw.toString();
	}

}

