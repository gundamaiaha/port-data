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
        
        final StringBuilder userauth_data_sql =
                new StringBuilder("USE `convouserauth` ;\n\n");
        userauth_data_sql.append("INSERT IGNORE INTO user(")
                .append("userid,email,username,password,email_verified,firstname,lastname,name,phonenumber," +
                        "street1,street2,city,state,country,postalcode,failedattempts,lastfailedattempton,accessedfromip" +
                        ",accessedon,designation,image_url,provider,provider_id,disabled,locked,expired,credexpired,status," +
                        "resettoken,tokenexpirydate,createdon,createdby,modifiedon,modifiedby,deletedon,deletedby,token) ")
                .append("VALUES\n");

        final StringBuilder uservri_data_sql =
                new StringBuilder("USE `convovridb` ;\n\n");
        uservri_data_sql.append("INSERT IGNORE INTO user(")
                .append("id,firstname,lastname,email,token,status,created_on,created_by,modified_on,modified_by,deleted_on,deleted_by) ")
                .append("VALUES\n");

        final StringBuilder user_role_data_sql =
                new StringBuilder("USE `convovridb` ;\n\n");
        user_role_data_sql.append("INSERT IGNORE INTO user_role_map(")
                .append("user_id,role_id,status,created_on,created_by,modified_on,modified_by,deleted_on,deleted_by)")
                .append(" VALUES\n");

        
        ArrayList<String> creditGroupids = new ArrayList<String>();
        
        FileWriter fileWriter = new FileWriter("src/main/resources/sql/creditgroup_vridb_creditgroup.sql");
        
        
        for (Customer customer : customers) {
        	
        	if (customer.getCredit_groups_id().isEmpty()) {
        		continue;
        	}
        	if (!customer.getRole().equals("account_manager")) {
        		continue;
        	}
//        	if (customer.getPhone().isEmpty()) {
//        		continue;
//        	}

        	boolean isFoundAccountManager = false;
        	for (CreditGroup creditGroup : creditGroups) {

        		if (customer.getCredit_groups_id().equals(creditGroup.getId())
        				&& customer.getRole().equals("account_manager")) {
        			isFoundAccountManager = true;
                	if (creditGroupids.contains(customer.getCredit_groups_id())) {
                		System.out.println("Credit Group Already Exists");
                		System.out.println(creditGroup.toString());
                		System.out.println(customer.toString());
                		fileWriter.write("Credit Group Already Exists\n");
                		fileWriter.write(creditGroup.toString() + "\n");
                		fileWriter.write(customer.toString() + "\n");
                		continue;        	
                	}
        			
                    String email = customer.getEmail().replaceAll("'","\\\\'");
                    String firstName = customer.getName().replaceAll("'","\\\\'");
                    String lastName = "";
                    
                	//Create User Auth User
                	userauth_data_sql.append("('")
                    	.append(customer.getId())
                    	.append("','")
                    	.append(email)
                    	.append("','")
                    	.append(customer.getUsername())
                    	.append("',")
                    	.append("null")
                    	.append(",")
                    	.append("b'1'")
                    	.append(",'")
                    	.append(firstName)
                    	.append("','")
                    	.append(lastName)
                    	.append("',")
                    	.append("null")
                    	.append(",'")
                    	.append(customer.getCellphone())
                    	.append("','")

                    //street1,street2,city,state,country,postalcode - 6
                    .append(customer.getAddress_1()).append("','")
                    .append(customer.getAddress_2()).append("','")
                    .append(customer.getCity()).append("','")
                    .append(customer.getCounty()).append("','")
                    .append(customer.getCountry()).append("','")
                    .append(customer.getPostal_code()).append("',")

                    // failedattempts,lastfailedattempton,accessedfromip,accessedon - 4
                    .append(0).append(",")
                    .append(NULL).append(",")
                    .append(NULL).append(",")
                    .append(NULL).append(",")

                    //designation,image_url,provider,provider_id,disabled,locked,expired
                    .append(NULL).append(",")
                    .append(NULL).append(",'")
                    .append("local").append("','")
                    .append("").append("',")
                    .append("b'0'").append(",")
                    .append("b'0'").append(",")
                    .append("b'0'").append(",")

                    //credexpired,status,resettoken,tokenexpirydate
                    .append("b'0'").append(",")
                    .append("'active'").append(",")
                    .append(NULL).append(",")
                    .append(NULL).append(",'")

                    //createdon,createdby,modifiedon,modifiedby,deletedon,deletedby,token
                    .append(customer.getInserted_at()).append("',")
                    .append(NULL).append(",'")
                    .append(customer.getUpdated_at()).append("',")
                    .append(NULL).append(",")
                    .append(NULL).append(",")
                    .append(NULL).append(",")
                    .append(NULL).append("),\n");
                	
                	
                	//Create User in VRI
                	uservri_data_sql.append("('")
                    //id,firstname,lastname,email,token,status,created_on,created_by,modified_on,modified_by,deleted_on,deleted_by
                    	.append(customer.getId()).append("','")
                    .append(firstName).append("','")
                    .append(lastName).append("','")
                    .append(email).append("',")
                    .append(NULL).append(",")
                    .append("'active'").append(",'")
                    .append(customer.getInserted_at()).append("',")
                    .append(NULL).append(",'")
                    .append(customer.getUpdated_at()).append("',")
                    .append(NULL).append(",")
                    .append(NULL).append(",")
                    .append(NULL).append("),\n");

                	
                	//Add Role Mapping for User
                	
                    user_role_data_sql.append("('")
                    //user_id,role_id,status,created_on,created_by,modified_on,modified_by,deleted_on,deleted_by
                    .append(customer.getId()).append("','")
                    .append("ROLE_USER").append("',")
                    .append("'active'").append(",'")
                    .append(customer.getInserted_at()).append("',")
                    .append(NULL).append(",'")
                    .append(customer.getUpdated_at()).append("',")
                    .append(NULL).append(",")
                    .append(NULL).append(",")
                    .append(NULL).append("),\n");

                	
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
        				.append(", ")
        				.append("null,null,null")
        				.append(", '")
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
        	if (!isFoundAccountManager) {
        		System.out.println("No account Manager found for the Credit Group");
        		System.out.println(customer.getCredit_groups_id());
        		System.out.println(customer);
        		fileWriter.write("No account Manager found for the Credit Group\n");
        		fileWriter.write(customer.getCredit_groups_id() + "\n");
        		fileWriter.write(customer.toString() + "\n");
        	}
        	
        }
        
        
        userauth_data_sql.deleteCharAt(userauth_data_sql.lastIndexOf(","));
        userauth_data_sql.append(";");

        System.out.println("============== UserAuth SQL =============== ");
        System.out.println(userauth_data_sql.toString());
        fileWriter.write("============== UserAuth SQL =============== \n");
        fileWriter.write(userauth_data_sql.toString());
        

        
        uservri_data_sql.deleteCharAt(uservri_data_sql.lastIndexOf(","));
        uservri_data_sql.append(";");

        System.out.println("============== UserVRI SQL =============== ");
        System.out.println(uservri_data_sql.toString());
        fileWriter.write("============== UserVRI SQL =============== \n");
        fileWriter.write(uservri_data_sql.toString());
        
        
        
        user_role_data_sql.deleteCharAt(user_role_data_sql.lastIndexOf(","));
        user_role_data_sql.append(";");

        System.out.println("============== User Role VRI SQL =============== ");
        System.out.println(user_role_data_sql.toString());
        fileWriter.write("============== User Role VRI SQL =============== \n");
        fileWriter.write(user_role_data_sql.toString());
        
        
        credit_group_data_sql.deleteCharAt(credit_group_data_sql.lastIndexOf(","));
        credit_group_data_sql.append(";");

        System.out.println("============== Credit Group SQL =============== ");
        System.out.println(credit_group_data_sql.toString());
        fileWriter.write("============== Credit Group SQL =============== \n");
        fileWriter.write(credit_group_data_sql.toString());

        //File interpreter_sql_file= new File("src/main/resources/sql/interpreter_vridb_interpreter.sql");


        fileWriter.close();
		
	}
	
}
