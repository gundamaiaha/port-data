/**
 * 
 */
package com.dataport.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.dataport.pojo.CreditGroup;
import com.dataport.pojo.Customer;
import com.dataport.pojo.Id;
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
		List<Id> customers = new ArrayList<Id>();
		final String file = "src/main/resources/csv/existingcustomers.csv";
		try {
			CSVReader csvReader = new CSVReader(new FileReader(file));
			CsvToBean<Id> csvToBean = new CsvToBean<Id>();

			HeaderColumnNameMappingStrategy strategy = new HeaderColumnNameMappingStrategy();
			strategy.setType(Id.class);

			customers = csvToBean.parse(strategy, csvReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Existing Customers");
		System.out.println(customers);
		System.out.println("=======\n");
		
		return customers.stream().map(Id::getId).collect(Collectors.toList());
	}

	public List<String> buildexistingExtnNumberFromCsv() {
		List<Id> numbers = new ArrayList<Id>();
		final String file = "src/main/resources/csv/exitingExtnNumber.csv";
		try {
			CSVReader csvReader = new CSVReader(new FileReader(file));
			CsvToBean<Id> csvToBean = new CsvToBean<Id>();

			HeaderColumnNameMappingStrategy strategy = new HeaderColumnNameMappingStrategy();
			strategy.setType(Id.class);

			numbers = csvToBean.parse(strategy, csvReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Existing Extn Numbers");
		System.out.println(numbers);
		System.out.println("=======\n");
		
		return numbers.stream().map(Id::getId).collect(Collectors.toList());

	}

	
	public List<String> buildexistingNumberFromCsv() {
		List<Id> numbers = new ArrayList<Id>();
		final String file = "src/main/resources/csv/existingnumber.csv";
		try {
			CSVReader csvReader = new CSVReader(new FileReader(file));
			CsvToBean<Id> csvToBean = new CsvToBean<Id>();

			HeaderColumnNameMappingStrategy strategy = new HeaderColumnNameMappingStrategy();
			strategy.setType(Id.class);

			numbers = csvToBean.parse(strategy, csvReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Existing Numbers");
		System.out.println(numbers);
		System.out.println("=======\n");
		
		return numbers.stream().map(Id::getId).collect(Collectors.toList());

	}
	
	public List<String> buildexistingUserWithRoleUserFromCsv() {
		List<Id> customers = new ArrayList<Id>();
		final String file = "src/main/resources/csv/existinguserwithroleuser.csv";
		try {
			CSVReader csvReader = new CSVReader(new FileReader(file));
			CsvToBean<Id> csvToBean = new CsvToBean<Id>();

			HeaderColumnNameMappingStrategy strategy = new HeaderColumnNameMappingStrategy();
			strategy.setType(Id.class);

			customers = csvToBean.parse(strategy, csvReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Customers with User Role");
		System.out.println(customers);
		System.out.println("=======\n");
		
		return customers.stream().map(Id::getId).collect(Collectors.toList());
	}
	
}
