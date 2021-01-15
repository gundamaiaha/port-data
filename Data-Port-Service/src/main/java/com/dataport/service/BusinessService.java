package com.dataport.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.dataport.pojo.Business;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

public class BusinessService {
	public List<Business> buildBusinesssFromCsv() {
		List<Business> businesses = new ArrayList<Business>();
		final String file = "src/main/resources/csv/businesses.csv";
		try {
			CSVReader csvReader = new CSVReader(new FileReader(file));
			CsvToBean<Business> csvToBean = new CsvToBean<Business>();

			HeaderColumnNameMappingStrategy strategy = new HeaderColumnNameMappingStrategy();
			strategy.setType(Business.class);

			businesses = csvToBean.parse(strategy, csvReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return businesses;

	}
}
