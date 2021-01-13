package com.dataport.main;

import com.dataport.entitysql.*;
//import com.dataport.entitysql.InterpreterSpecialityMap;
import com.dataport.pojo.Agent;
import com.dataport.pojo.Customer;
import com.dataport.pojo.User;
import com.dataport.service.AgentService;
import com.dataport.service.CustomerService;
import com.dataport.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataPortProcessor {

    public static void main(String[] args) throws IOException {
        
    	DataPortProcessor dataPortProcessor = new DataPortProcessor();
        
    	dataPortProcessor.generateInterpreterRelatedSQL();
    	dataPortProcessor.generateCustomerRelatedSQL();

    }
    
    private void generateInterpreterRelatedSQL() throws IOException {
        //reading agents csv and getting all agent beans
        List<Agent> agents = new AgentService().buildAgentsFromCsv();
        System.out.println("agents = " + agents);
        System.out.println("no of agents = "+agents.size());
        
        InterpreterEntity interpreterEntity = new InterpreterEntity();
        interpreterEntity.generateInterpreterSql(agents);

        InterpreterSpecialityMapEntity interpreterSpecialityMapEntity= new InterpreterSpecialityMapEntity();
        interpreterSpecialityMapEntity.generateInterpreterSpecialityMap(agents);

        List<User> users= new UserService().buildUsersFromCsv();
        System.out.println("users = "+users);
        System.out.println("no of users = "+users.size());

        Map<String, String> userNamesMap = agents.stream().collect(Collectors.toMap(Agent::getUserId, Agent::getDescription));
        UserEntity userEntity= new UserEntity();
        userEntity.generateUserSqls(users,userNamesMap);

        UserRoleMapEntity userRoleMapEntity= new UserRoleMapEntity();
        userRoleMapEntity.generateUserRoleMapSql(users);




    }

    private void generateCustomerRelatedSQL() throws IOException {
        //reading agents csv and getting all agent beans
        List<Customer> customers = new CustomerService().buildAgentsFromCsv();
        System.out.println("customers = " + customers);
        System.out.println("no of customers = " + customers.size());
        
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.generateUserAuthDbSql(customers);

//        new InterpreterSpecialityMap().generateInterpreterSpecialityMap(agents);
    }
    
}
