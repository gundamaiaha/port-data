package com.dataport.service;

import com.dataport.pojo.Agent;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.opencsv.bean.MappingStrategy;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class AgentService {



      public List<Agent> buildAgentsFromCsv(){
          List<Agent> agents= new ArrayList<Agent>();
          final String file = "src/main/resources/csv/agents.csv";
          try {
              CSVReader csvReader= new CSVReader(new FileReader(file));
              CsvToBean<Agent> csvToBean= new CsvToBean<Agent>();

              HeaderColumnNameMappingStrategy strategy= new HeaderColumnNameMappingStrategy();
              strategy.setType(Agent.class);


              agents = csvToBean.parse(strategy,
                      csvReader);
          } catch (FileNotFoundException e) {
              e.printStackTrace();
          }

          return agents;

      }




}
