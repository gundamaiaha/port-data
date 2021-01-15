package com.dataport.entitysql;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dataport.pojo.Business;
import com.dataport.pojo.Customer;

public class BusinessEntity {
	
	//External Member Guest  - type

	public void generateBusiness(List<Customer> customers, List<Business> businesses) throws IOException {
		
        final StringBuilder business_data_sql =
                new StringBuilder("USE `convovridb` ;\n\n");
        business_data_sql.append("INSERT IGNORE INTO business(")
                .append("id,parent_id,name,phonenumber,number,type,description,businesscol,street1,street2,city,"
                		+ "state,country,postalcode,lat,lon,url,photo_url,public,category_tags,priority,"
                		+ "billing_code,status,createdon,createdby,modifiedon,modifiedby,deletedon,deletedby) ")
                .append("VALUES\n");
        String NULL = null;
        
        final StringBuilder business_user_map_data_sql =
                new StringBuilder("USE `convovridb` ;\n\n");
        business_user_map_data_sql.append("INSERT IGNORE INTO business_user_map(")
        .append("business_id,user_id,name,role_desc,status,"
        + "createdon,createdby,modifiedon,modifiedby,deletedon,deletedby) ")
        .append("VALUES\n");
        
        for (Business business : businesses) {
 
        	String username = business.getUsername();
    		String phoneNumber = business.getPhone_number();
    		String type = "";
    		Customer customer = null;

    		for (Customer cust : customers) {
    			if (username.equals(cust.getUsername())) {
    				customer = cust;
    				break;
    			}
    		}
    		if (customer == null) {
    			System.out.println("No Customer Data for ");
    			System.out.println(business);
    			continue;
    		}

    		
        	if (!onlyDigits(phoneNumber)) {
				phoneNumber = customer.getPhone_extension();
				type = "Guest";
        	} else {
        		if (business.getPhone_number().substring(0, 2).equals("44")) {
        			type = "Member";
        			phoneNumber = "+" + business.getPhone_number();
        			
//        			if (customer.)
        			
        		} else if (business.getPhone_number().substring(0, 1).equals("0")) {
        			type = "External"; 
        			phoneNumber = "+44" + business.getPhone_number().substring(1);
        		} else {
        			System.out.println("Number neither starts with 0 nor 44 nor is a login");
        			System.out.println(business);
        			continue;
        		}
        	}
 
        	business_user_map_data_sql.append("('")
        		.append(business.getId())
				.append("', '")
        		.append(customer.getId())
        		.append("', '")
        		.append("Business_Owner")
        		.append("', '")
        		.append("active")
				.append("', '")
				.append(business.getInserted_at())
				.append("', ")
				.append("null")
				.append(", ")
				.append(business.getUpdated_at())
				.append(", ")
				.append("null,null,null")
				.append("),\n");
        		
        	
			business_data_sql.append("('")
				.append(business.getId())
				.append("', '")
				.append(business.getParent_id())
				.append("', '")
				.append(business.getName())
				.append("', '")
				.append(phoneNumber)
				.append("', '")
				.append(phoneNumber)
				.append("', '")
				.append(type)
				.append("', '")
				.append(business.getDescription())
				.append("', ")
				.append("null,null,null,null")
				.append(", '")
				.append(business.getLocation())
				.append("',null,null,null,null, ")
				.append(business.getUrl())
				.append("', '")
				.append(business.getPhoto_url())
				.append("', '")
				.append(getPublic(business.getIspublic()))
				.append("', '")
				.append(business.getCategory_id())
				.append("', ")
				.append(business.getPriority())
				.append(", '")
				.append(business.getBilling_code())
				.append("', '")
				.append("active")
				.append("', '")
				.append(business.getInserted_at())
				.append("', ")
				.append("null")
				.append(", '")
				.append(business.getUpdated_at())
				.append("', ")
				.append("null,null,null")
				.append("),\n");
    	}
        

        business_data_sql.deleteCharAt(business_data_sql.lastIndexOf(","));
        business_data_sql.append(";");

        System.out.println("============== Business SQL =============== ");
        System.out.println(business_data_sql.toString());

        
        business_user_map_data_sql.deleteCharAt(business_user_map_data_sql.lastIndexOf(","));
        business_user_map_data_sql.append(";");

        System.out.println("============== Business User Map SQL =============== ");
        System.out.println(business_user_map_data_sql.toString());
        
        //File interpreter_sql_file= new File("src/main/resources/sql/interpreter_vridb_interpreter.sql");
        FileWriter fileWriter = new FileWriter("src/main/resources/sql/business_vridb_business.sql");
        fileWriter.write(business_data_sql.toString());
        fileWriter.close();

        //File interpreter_sql_file= new File("src/main/resources/sql/interpreter_vridb_interpreter.sql");
        fileWriter = new FileWriter("src/main/resources/sql/business_vridb_business_user_map.sql");
        fileWriter.write(business_user_map_data_sql.toString());
        fileWriter.close();
        
	}

	private String getPublic(String input) {
		if (input.equals("TRUE")) {
			return "b'1'";
		} else {
			return "b'0'";
		}
	}
	
    private boolean onlyDigits(String str) 
    { 

        String regex = "[0-9]+"; 
        Pattern p = Pattern.compile(regex); 
  
        if (str == null) { 
            return false; 
        } 
   
        Matcher m = p.matcher(str); 
  
        return m.matches(); 
    } 
	
}
