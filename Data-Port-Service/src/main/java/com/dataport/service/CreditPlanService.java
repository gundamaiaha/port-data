package com.dataport.service;

import com.dataport.pojo.CreditPlan;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CreditPlanService {

    public List<String> getCreditPlanIdsForFile(String fileName) {
        List<CreditPlan> creditPlans = new ArrayList<>();
        final String file = "src/main/resources/csv/" + fileName;
        try {
            CSVReader csvReader = new CSVReader(new FileReader(file));
            CsvToBean<CreditPlan> csvToBean = new CsvToBean<>();

            HeaderColumnNameMappingStrategy strategy = new HeaderColumnNameMappingStrategy();
            strategy.setType(CreditPlan.class);

            creditPlans = csvToBean.parse(strategy, csvReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("no of credit plans -->" + creditPlans.size());
        return creditPlans.stream().map(CreditPlan::getId).collect(Collectors.toList());
    }
}
