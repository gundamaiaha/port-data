package com.dataport.main;

import com.dataport.pojo.Agent;
import com.dataport.service.AgentService;

import java.util.List;

public class DataPortProcessor {

    public static void main(String[] args) {
        
        //reading agents csv and getting all agent beans
        List<Agent> agents = new AgentService().buildAgentsFromCsv();
        System.out.println("agents = " + agents);
        System.out.println("no of agents = "+agents.size());
        


    }

}
