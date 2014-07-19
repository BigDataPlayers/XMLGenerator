package com.bdc.XMLGenerator;

import java.util.List;

import org.json.JSONObject;

public class JSONWrapper {
	
	JSONObject object; 
	
	List<JSONObject> components; 
	
	List <JSONObject> properties;

	public JSONObject getObject() {
		return object;
	}

	public void setObject(JSONObject object) {
		this.object = object;
	}

	public List<JSONObject> getComponents() {
		return components;
	}

	public void setComponents(List<JSONObject> components) {
		this.components = components;
	}

	public List<JSONObject> getProperties() {
		return properties;
	}

	public void setProperties(List<JSONObject> properties) {
		this.properties = properties;
	} 
	
	
	

}
