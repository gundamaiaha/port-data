/**
 * 
 */
package com.dataport.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.dataport.pojo.Customer;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

/**
 * To Load Customers which is in user.csv renamed to customer.csv
 *
 */
public class CustomerService {

	public List<Customer> buildCustomerFromCsv() {
		List<Customer> customers = new ArrayList<Customer>();
		final String file = "src/main/resources/csv/customers.csv";
		try {
			CSVReader csvReader = new CSVReader(new FileReader(file));
			CsvToBean<Customer> csvToBean = new CsvToBean<Customer>();

			HeaderColumnNameMappingStrategy strategy = new HeaderColumnNameMappingStrategy();
			strategy.setType(Customer.class);

			customers = csvToBean.parse(strategy, csvReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return customers;
	}

	public List<String> buildexistingCustomerFromCsv() {
		List<String> customers = new ArrayList<String>();
		final String file = "src/main/resources/csv/existingcustomers.csv";
		try {
			CSVReader csvReader = new CSVReader(new FileReader(file));
			CsvToBean<Customer> csvToBean = new CsvToBean<Customer>();

			HeaderColumnNameMappingStrategy strategy = new HeaderColumnNameMappingStrategy();
			strategy.setType(Customer.class);

			customers = csvToBean.parse(strategy, csvReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return customers;
	}
	
	
	public List<String> buildexistingNumberFromCsv() {
		List<String> customers = new ArrayList<String>();
		final String file = "src/main/resources/csv/existingnumber.csv";
		try {
			CSVReader csvReader = new CSVReader(new FileReader(file));
			CsvToBean<Customer> csvToBean = new CsvToBean<Customer>();

			HeaderColumnNameMappingStrategy strategy = new HeaderColumnNameMappingStrategy();
			strategy.setType(Customer.class);

			customers = csvToBean.parse(strategy, csvReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return customers;
	}
	
	public List<String> buildexistingUserWithRoleUserFromCsv() {
		List<String> customers = new ArrayList<String>();
		final String file = "src/main/resources/csv/existinguserwithroleuser.csv";
		try {
			CSVReader csvReader = new CSVReader(new FileReader(file));
			CsvToBean<Customer> csvToBean = new CsvToBean<Customer>();

			HeaderColumnNameMappingStrategy strategy = new HeaderColumnNameMappingStrategy();
			strategy.setType(Customer.class);

			customers = csvToBean.parse(strategy, csvReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return customers;
	}
	
}
