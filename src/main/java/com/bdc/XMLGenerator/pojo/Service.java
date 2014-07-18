package com.bdc.XMLGenerator.pojo;

import java.util.List;

public class Service {
	String service;
	String id;
	String className;
	String name ;
	List<String> pipelines ;
	
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getPipelines() {
		return pipelines;
	}
	public void setPipelines(List<String> pipelines) {
		this.pipelines = pipelines;
	}

}
