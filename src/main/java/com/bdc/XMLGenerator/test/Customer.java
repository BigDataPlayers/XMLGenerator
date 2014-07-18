package com.bdc.XMLGenerator.test;


import java.util.List;

public class Customer {

    String customerNumber;
    String name;
    String address;
    String city;
    String state;
    String zip;
    List<String> phone;

    public String getCustomerNumber() {return customerNumber;}
    public void setCustomerNumber(String s) {customerNumber = s;}
    public String getName() {return name;}
    public void setName(String s) {name = s;}
    public String getAddress() {return address;}
    public void setAddress(String s) {address = s;}
    public String getCity() {return city;}
    public void setCity(String s) {city = s;}
    public String getState() {return state;}
    public void setState(String s) {state = s;}
    public String getZip() {return zip;}
    public void setZip(String s) {zip = s;}
    public List<String> getPhone() {return phone;}
    public void setPhone(List<String> s) {phone = s;}
}
