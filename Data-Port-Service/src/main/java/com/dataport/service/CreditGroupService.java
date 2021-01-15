package com.dataport.service;

import com.dataport.pojo.CreditGroup;
import com.dataport.pojo.CreditGroupWithOwner;
import com.dataport.pojo.Customer;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class CreditGroupService {


    public Map<String, CreditGroupWithOwner> getCreditGroupWithOwners(List<Customer> customers) {

        Map<String, CreditGroupWithOwner> creditGroupWithOwnersMap = new HashMap<>();
        List<String> selectedCreditGroupIds = getSelectedCreditGroupIds();


        for (Customer customer : customers) {
            if ("account_manager".equalsIgnoreCase(customer.getRole()) &&
                    selectedCreditGroupIds.contains(customer.getCredit_groups_id())) {
                CreditGroupWithOwner creditGroupWithOwner = new CreditGroupWithOwner();
                creditGroupWithOwner.setCreditGroupId(customer.getCredit_groups_id());
                creditGroupWithOwner.setOwnerId(customer.getId());
                creditGroupWithOwner.setOwnerName(customer.getName());
                creditGroupWithOwnersMap.put(creditGroupWithOwner.getCreditGroupId(), creditGroupWithOwner);
            }
        }


        return creditGroupWithOwnersMap;
    }


    public List<String> getSelectedCreditGroupIds() {
        List<CreditGroup> creditGroups = new ArrayList<>();
        final String file = "src/main/resources/csv/selected_credit_groups.csv";
        try {
            CSVReader csvReader = new CSVReader(new FileReader(file));
            CsvToBean<CreditGroup> csvToBean = new CsvToBean<CreditGroup>();

            HeaderColumnNameMappingStrategy strategy = new HeaderColumnNameMappingStrategy();
            strategy.setType(CreditGroup.class);

            creditGroups = csvToBean.parse(strategy, csvReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("no of credit groups -->"+creditGroups.size());


        return creditGroups.stream().map(CreditGroup::getId).collect(Collectors.toList());
    }

    
	public List<CreditGroup> buildCreditGroupsFromCsv() {
		List<CreditGroup> creditGroups = new ArrayList<CreditGroup>();
		final String file = "src/main/resources/csv/credit_groups.csv";
		try {
			CSVReader csvReader = new CSVReader(new FileReader(file));
			CsvToBean<CreditGroup> csvToBean = new CsvToBean<CreditGroup>();

			HeaderColumnNameMappingStrategy strategy = new HeaderColumnNameMappingStrategy();
			strategy.setType(CreditGroup.class);

			creditGroups = csvToBean.parse(strategy, csvReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return creditGroups;

	}
}


