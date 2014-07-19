package com.bdc.XMLGenerator;


import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.MethodInvocationException;

import com.bdc.XMLGenerator.pojo.Customer;


public class Test {

public static void main(String[] args)
{
    Velocity.init();

    VelocityContext context = new VelocityContext();

    Customer customer = getCustomer();

    context.put( "customer", customer );

    Template template = null;

    try
    {
        template = Velocity.getTemplate("./src/main/resources/test.vm");
    }
    catch( ResourceNotFoundException rnfe )
    {
        // couldn't find the template
    }
    catch( ParseErrorException pee )
    {
        // syntax error: problem parsing the template
    }
    catch( MethodInvocationException mie )
    {
        // something invoked in the template
        // threw an exception
    }
    catch( Exception e )
    {}

    StringWriter sw = new StringWriter();

    template.merge( context, sw );

    System.out.println(sw.toString());
}


    private static  Customer getCustomer()  {
        Customer customer = new Customer();
        customer.setCustomerNumber("ABC123");
        customer.setName("Joe JavaRanch");
        customer.setAddress("123 Rancho Javo");
        customer.setCity("Bueno Ranch");
        customer.setState("CO");
        customer.setZip("63121");

        List<String> sa = new ArrayList<String>();
        sa.add("999-999-9999");
        sa.add("444-555-7777");
        sa.add("777-888-9999");

        customer.setPhone(sa);
        return customer;
    }



}

