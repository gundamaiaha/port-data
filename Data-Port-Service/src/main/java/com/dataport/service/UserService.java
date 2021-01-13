package com.dataport.service;

import com.dataport.pojo.Agent;
import com.dataport.pojo.User;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class UserService {



    public List<User> buildUsersFromCsv(){
        List<User> users= new ArrayList<>();
        final String file = "src/main/resources/csv/users.csv";
        try {
            CSVReader csvReader= new CSVReader(new FileReader(file));
            CsvToBean<User> csvToBean= new CsvToBean<>();

            HeaderColumnNameMappingStrategy strategy= new HeaderColumnNameMappingStrategy();
            strategy.setType(User.class);


            users = csvToBean.parse(strategy,
                    csvReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return users;

    }


}
