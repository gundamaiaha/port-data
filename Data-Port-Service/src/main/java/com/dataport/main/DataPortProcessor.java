package com.dataport.main;

import com.dataport.entitysql.CustomerEntity;
import com.dataport.entitysql.InterpreterEntity;
//import com.dataport.entitysql.InterpreterSpecialityMap;
import com.dataport.pojo.Agent;
import com.dataport.pojo.Customer;
import com.dataport.service.AgentService;
import com.dataport.service.CustomerService;

import java.io.IOException;
import java.util.List;

public class DataPortProcessor {

    public static void main(String[] args) throws IOException {
        
    	DataPortProcessor dataPortProcessor = new DataPortProcessor();
        
    	dataPortProcessor.GenerateInterpreterRelatedSQL();

    }
    
    private void GenerateInterpreterRelatedSQL() throws IOException {
        //reading agents csv and getting all agent beans
        List<Agent> agents = new AgentService().buildAgentsFromCsv();
        System.out.println("agents = " + agents);
        System.out.println("no of agents = "+agents.size());
        
        InterpreterEntity interpreterEntity = new InterpreterEntity();
        interpreterEntity.generateInterpreterSql(agents);

//        new InterpreterSpecialityMap().generateInterpreterSpecialityMap(agents);
    }

    private void GenerateCustomerRelatedSQL() throws IOException {
        //reading agents csv and getting all agent beans
        List<Customer> customers = new CustomerService().buildAgentsFromCsv();
        System.out.println("customers = " + customers);
        System.out.println("no of customers = " + customers.size());
        
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.generateUserAuthDbSql(customers);

//        new InterpreterSpecialityMap().generateInterpreterSpecialityMap(agents);
    }
    
}
