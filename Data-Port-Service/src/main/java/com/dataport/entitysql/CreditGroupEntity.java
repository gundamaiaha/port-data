package com.dataport.entitysql;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.dataport.pojo.CreditGroup;
import com.dataport.pojo.Customer;

public class CreditGroupEntity {

	public void generateCreditGroups(List<Customer> customers, List<CreditGroup> creditGroups) throws IOException {
		
        final StringBuilder credit_group_data_sql =
                new StringBuilder("USE `convovridb` ;\n\n");
        credit_group_data_sql.append("INSERT IGNORE INTO credit_group(")
                .append("id,user_id,name,mins_type,mins_paid,mins_prepaid,mins_used,mins_owed,mins_updated_at,mins_reloaded_at,expire_on,status,credit_plan_id,created_on,created_by,modified_on,modified_by) ")
                .append("VALUES\n");
        String NULL = null;
        
        ArrayList<String> creditGroupids = new ArrayList<String>();
        
        for (Customer customer : customers) {
        	
        	if (customer.getCredit_groups_id().isEmpty()) {
        		continue;
        	}
        	if (!customer.getRole().equals("account_manager")) {
        		continue;
        	}
        	if (customer.getPhone().isEmpty()) {
        		continue;
        	}
//        	if (creditGroupids.contains(customer.getCredit_groups_id())) {
//        		continue;
//        	}
        	
        	for (CreditGroup creditGroup : creditGroups) {

        		if (customer.getCredit_groups_id().equals(creditGroup.getId())) {
        			creditGroupids.add(customer.getCredit_groups_id());
        			credit_group_data_sql.append("('")
        				.append(creditGroup.getId())
        				.append("', '")
        				.append(customer.getId())
        				.append("', '")
        				.append(creditGroup.getName())
        				.append("', '")
        				.append(creditGroup.getMins_type())
        				.append("', ")
        				.append(creditGroup.getMins_paid())
        				.append(", ")
        				.append(creditGroup.getMins_prepaid())
        				.append(", ")
        				.append(creditGroup.getMins_used())
        				.append(", ")
        				.append(creditGroup.getMins_owed())
        				.append(", '")
        				.append(creditGroup.getMins_updated_at())
        				.append("', '")
        				.append(creditGroup.getMins_reloaded_at())
        				.append("', '")
        				.append(creditGroup.getExpire_on())
        				.append("', '")
        				.append("active")
        				.append("', '")
        				.append(creditGroup.getCredit_plan_id())
        				.append("', '")
        				.append(creditGroup.getInserted_at())
        				.append("', ")
        				.append("null")
        				.append(", '")
        				.append(creditGroup.getUpdated_at())
        				.append("',")
        				.append("null")
        				.append("),\n");
        				break;
        		}
        	}
        }
        credit_group_data_sql.deleteCharAt(credit_group_data_sql.lastIndexOf(","));
        credit_group_data_sql.append(";");

        System.out.println("============== Credit Group SQL =============== ");
        System.out.println(credit_group_data_sql.toString());


        //File interpreter_sql_file= new File("src/main/resources/sql/interpreter_vridb_interpreter.sql");
        FileWriter fileWriter = new FileWriter("src/main/resources/sql/creditgroup_vridb_creditgroup.sql");
        fileWriter.write(credit_group_data_sql.toString());
        fileWriter.close();
		
	}
	
}
