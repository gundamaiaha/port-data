package com.dataport.main;

import com.dataport.entitysql.*;
import com.dataport.pojo.Agent;

import com.dataport.pojo.CreditGroupWithOwner;
import com.dataport.pojo.Customer;
import com.dataport.pojo.User;
import com.dataport.service.*;
import com.dataport.pojo.Business;
import com.dataport.pojo.CreditGroup;
import com.dataport.pojo.Customer;
import com.dataport.pojo.User;
import com.dataport.service.AgentService;
import com.dataport.util.ListCompare;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataPortProcessor {

	public static void main(String[] args) throws IOException {

		DataPortProcessor dataPortProcessor = new DataPortProcessor();

		// dataPortProcessor.generateInterpreterRelatedSQL();
//		dataPortProcessor.generateCreditGroupRelatedSQL();
//		dataPortProcessor.generateBusinessRelatedSQL();
//		dataPortProcessor.generateRemainingCustomerRelatedSQL();
// 		dataPortProcessor.generateRemainingMemberCustomerSQL();
		dataPortProcessor.compareCreditGroupFiles();
		dataPortProcessor.compareCreditPlanFiles();
	}
	
	
	private void generateExtnForExistingUsers() throws IOException {
		List<Customer> customers = new CustomerService().buildCustomerFromCsv();
//      System.out.println("customers = " + customers);
		System.out.println("no of customers = " + customers.size());

		List<String> existingCustomers = new CustomerService().buildexistingCustomerFromCsv();
//    System.out.println("customers = " + customers);
		System.out.println("no of existing = " + existingCustomers.size());
		
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.generateExtnForExistingUsers(customers, existingCustomers);
	}

	private void generateRemainingMemberCustomerSQL() throws IOException {
		List<Customer> customers = new CustomerService().buildCustomerFromCsv();
//      System.out.println("customers = " + customers);
		System.out.println("no of customers = " + customers.size());

		List<String> existingCustomers = new CustomerService().buildexistingCustomerFromCsv();
//    System.out.println("customers = " + customers);
		System.out.println("no of existing = " + existingCustomers.size());

		List<String> existingCreditGroup = new CreditGroupService().getExistingCreditGroupIds();
		System.out.println("no of existing = " + existingCustomers.size());
		
		List<String> exitingNumber = new CustomerService().buildexistingNumberFromCsv();
		System.out.println("no of existing = " + exitingNumber.size());
		
		List<String> exitingUserWithRoleUser = new CustomerService().buildexistingUserWithRoleUserFromCsv();
		System.out.println("no of existing User with Role = " + exitingUserWithRoleUser.size());
		
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.generateRemainingMemberCustomer(customers, existingCustomers, existingCreditGroup, 
				exitingNumber, exitingUserWithRoleUser);

		
	}
	
	private void generateBusinessRelatedSQL() throws IOException {
		// reading business csv and getting all business beans
		List<Business> businesses = new BusinessService().buildBusinesssFromCsv();
//        System.out.println("businesses = " + businesses);
		System.out.println("no of businesses = " + businesses.size());

		List<Customer> customers = new CustomerService().buildCustomerFromCsv();
//        System.out.println("customers = " + customers);
		System.out.println("no of customers = " + customers.size());

		BusinessEntity businessEntity = new BusinessEntity();
		businessEntity.generateBusiness(customers, businesses);
	}

	private void generateRemainingCustomerRelatedSQL() throws IOException {

		List<Customer> customers = new CustomerService().buildCustomerFromCsv();
//        System.out.println("customers = " + customers);
		System.out.println("no of customers = " + customers.size());

		List<String> existingCustomers = new CustomerService().buildexistingCustomerFromCsv();
//      System.out.println("customers = " + customers);
		System.out.println("no of existing = " + existingCustomers.size());

		List<String> existingCreditGroup = new CreditGroupService().getExistingCreditGroupIds();
		System.out.println("no of existing = " + existingCustomers.size());
		
		List<String> exitingNumber = new CustomerService().buildexistingNumberFromCsv();
		System.out.println("no of existing = " + exitingNumber.size());
		
		List<String> exitingUserWithRoleUser = new CustomerService().buildexistingUserWithRoleUserFromCsv();
		System.out.println("no of existing User with Role = " + exitingUserWithRoleUser.size());
		
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.generateRemainingCustomer(customers, existingCustomers, existingCreditGroup, 
				exitingNumber, exitingUserWithRoleUser);
	}

	private void generateInterpreterRelatedSQL() throws IOException {
		// reading agents csv and getting all agent beans
		List<Agent> agents = new AgentService().buildAgentsFromCsv();
		System.out.println("agents = " + agents);
		System.out.println("no of agents = " + agents.size());

		List<User> users = new UserService().buildUsersFromCsv();
		System.out.println("users = " + users);
		System.out.println("no of users = " + users.size());

		InterpreterEntity interpreterEntity = new InterpreterEntity();
		interpreterEntity.generateInterpreterSql(agents);

		InterpreterSpecialityMapEntity interpreterSpecialityMapEntity = new InterpreterSpecialityMapEntity();
		interpreterSpecialityMapEntity.generateInterpreterSpecialityMap(agents);

		InterpreterProviderMapEntity interpreterProviderMapEntity = new InterpreterProviderMapEntity();
		interpreterProviderMapEntity.generateInterpreterProviderMapSQL(users);

		Map<String, String> userNamesMap = agents.stream()
				.collect(Collectors.toMap(Agent::getUserId, Agent::getDescription));
		UserEntity userEntity = new UserEntity();
		userEntity.generateUserSqls(users, userNamesMap);

		UserRoleMapEntity userRoleMapEntity = new UserRoleMapEntity();
		userRoleMapEntity.generateUserRoleMapSql(users);

	}

	private void generateCustomerRelatedSQL() throws IOException {
		// reading customer csv and getting all customer beans
		List<Customer> customers = new CustomerService().buildCustomerFromCsv();
//        System.out.println("customers = " + customers);
		System.out.println("no of customers = " + customers.size());

//        CustomerEntity customerEntity = new CustomerEntity();
//        customerEntity.generateUserAuthDbSql(customers);
//
//        CustomerRoleMapEntity customerRoleMapEntity= new CustomerRoleMapEntity();
//        customerRoleMapEntity.generateUserRoleMapSql(customers);
//
//
		NumberEntity numberEntity = new NumberEntity();
		numberEntity.generateNumbersSqlForGuest(customers);
		numberEntity.generateNumbersSqlForMemberWithOnlyCreditPlan(customers);

		// get Selected CreditGroup owners Map
		CreditGroupService creditGroupService = new CreditGroupService();
		final Map<String, CreditGroupWithOwner> creditGroupWithOwnersMap = creditGroupService
				.getCreditGroupWithOwners(customers);

		// generate number sql for selected credit group
		numberEntity.generateNumbersSqlForCreditGroups(customers, creditGroupWithOwnersMap);

	}

	private void generateCreditGroupRelatedSQL() throws IOException {
		// reading credit group csv and getting all credit group beans
		List<CreditGroup> creditGroups = new CreditGroupService().buildCreditGroupsFromCsv();
//        System.out.println("creditGroups = " + creditGroups);
		System.out.println("no of creditGroups = " + creditGroups.size());

		List<Customer> customers = new CustomerService().buildCustomerFromCsv();
//        System.out.println("customers = " + customers);
		System.out.println("no of customers = " + customers.size());

		CreditGroupEntity creditGroupEntity = new CreditGroupEntity();
		creditGroupEntity.generateCreditGroups(customers, creditGroups);

	}

	/**
	 * This function will compare existing credit plan data with original credit plan
	 */
	private void compareCreditPlanFiles(){
		String existingCreditPlanFileName = "existingcreditplan.csv";
		String originalCreditPlanFileName = "credit_plans.csv";

		List<String> existingCreditPlanIds = new CreditPlanService().getCreditPlanIdsForFile(existingCreditPlanFileName);
		List<String> originalCreditPlanIds = new CreditPlanService().getCreditPlanIdsForFile(originalCreditPlanFileName);

		List<String> result = ListCompare.compare(existingCreditPlanIds, originalCreditPlanIds);

		System.out.println("Below are the credit plan ids present in existing credit plan file and not present in original credit plan file");
		System.out.println(result);
		System.out.println("--------------------------");
	}

	/**
	 * This function will compare existing credit group data with original credit group
	 */
	private void compareCreditGroupFiles(){
		String existingCreditGroupFileName = "existingcreditgroups.csv";
		String originalCreditGroupFileName = "credit_groups.csv";

		List<String> existingCreditGroupIds = new CreditGroupService().getCreditGroupIdsForFile(existingCreditGroupFileName);
		List<String> originalCreditGroupIds = new CreditGroupService().getCreditGroupIdsForFile(originalCreditGroupFileName);

		List<String> result = ListCompare.compare(originalCreditGroupIds, existingCreditGroupIds);

		System.out.println("Below are the credit group ids present in existing credit group file and not present in original credit group file");
		System.out.println(result);
		System.out.println("--------------------------");
	}

}
