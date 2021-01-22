package com.dataport.entitysql;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

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
                .append("VALUES\n ");
        String NULL = null;
        
        final StringBuilder business_user_map_data_sql =
                new StringBuilder("USE `convovridb` ;\n\n");
        business_user_map_data_sql.append("INSERT IGNORE INTO business_user_map(")
        .append("business_id,user_id,role_desc,status,"
        + "createdon,createdby,modifiedon,modifiedby,deletedon,deletedby) ")
        .append("VALUES\n");
        
        final StringBuilder number_data_sql = new StringBuilder("USE `convovridb` ;\n\n");
        number_data_sql.append("INSERT IGNORE INTO number (")
        .append("number,number_type_id,twilio_number_sid,owned_by,owned_by_type,owned_by_desc,used_by,used_by_desc,credit_type,credit_plan_id_or_group_id," +
                "mins_limit_type,mins_paid,mins_prepaid,mins_used,mins_owed,mins_updated_at" +
                ",mins_reloaded_at,expire_on,status,created_on,created_by,modified_on,modified_by,deleted_on,deleted_by) ")
        .append(" VALUES\n");
        
        
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

        
        FileWriter fileWriter = new FileWriter("src/main/resources/sql/business_vridb_business.sql");   
               
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
    			fileWriter.write("No Customer Data for \n");
    			fileWriter.write(business.toString() + "\n");
    			continue;
    		}

    		String numbertype= "";
        	if (!onlyDigits(phoneNumber)) {
				phoneNumber = customer.getPhone_extension();
				type = "Guest";
				numbertype = "extn";
        	} else {
        		if (business.getPhone_number().substring(0, 2).equals("44")) {
        			type = "Member";
        			numbertype = "twilio";
        			phoneNumber = "+" + business.getPhone_number();
        		} else if (business.getPhone_number().substring(0, 1).equals("0")) {
        			type = "External"; 
        			numbertype = "external";
        			phoneNumber = "+44" + business.getPhone_number().substring(1);
        		} else {
        			System.out.println("Number neither starts with 0 nor 44 nor is a login");
        			System.out.println(business);
        			continue;
        		}
        	}
        	
        	
        	String credit_type = "";
        	
        	if (type.equals("Member") || type.equals("External")) {
        		if (StringUtils.isBlank(customer.getCredit_plan_id()) && StringUtils.isBlank(customer.getCredit_groups_id())) {
        			System.out.println("No Credit Plan or Credit Group for this business number");
        			System.out.println(business);
        			System.out.println(customer);
        			
        			fileWriter.write("No Credit Plan or Credit Group for this business number\n");
        			fileWriter.write(business.toString() + "\n");
        			fileWriter.write(customer.toString() + "\n");
        		}
        		if (!StringUtils.isBlank(customer.getCredit_plan_id())) {
        			credit_type = "Credit-Plan";
        		}
        		if (!StringUtils.isBlank(customer.getCredit_groups_id())) {
        			credit_type = "Credit-Group";
        		}
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

        	
        	number_data_sql.append("('")
        		.append(phoneNumber)
				.append("', '")
				.append(numbertype)
				.append("', ")
				.append("null")
				.append(", '")
				.append(business.getId())
				.append("', 'business','")
				.append(business.getName())
				.append("', '")
				.append(customer.getId())
				.append("', '")
				.append(customer.getName())
				.append("', '")
				.append(credit_type)
				.append("', '");
				if (credit_type.equals("Credit-Plan"))
					number_data_sql.append(customer.getCredit_plan_id());
				if (credit_type.equals("Credit-Group")) 
					number_data_sql.append(customer.getCredit_groups_id());
				number_data_sql.append("', '")
				.append(customer.getMins_type())
				.append("', ")
				.append(customer.getMins_paid())
				.append(", ")
				.append(customer.getMins_prepaid())
				.append(", ")
				.append(customer.getMins_used())
				.append(", ")
				.append(customer.getMins_owed())
				.append(", '")
				.append(customer.getMins_updated_at())
				.append("', '")
				.append(customer.getMins_reloaded_at())
				.append("', '")
				.append(customer.getExpire_on())
				.append("', 'active','")
				.append(customer.getInserted_at())
				.append("',null, '")
				.append(customer.getUpdated_at())
				.append("',null,null,null")
				.append("),\n");
				
				
 
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
				.append(", '")
				.append(business.getUpdated_at())
				.append("', ")
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
				.append("',null,null,null,null, '")
				.append(business.getUrl())
				.append("', '")
				.append(business.getPhoto_url())
				.append("', ")
				.append(getPublic(business.getIspublic()))
				.append(", '")
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
        
        

        business_data_sql.deleteCharAt(business_data_sql.lastIndexOf(","));
        business_data_sql.append(";");

        System.out.println("============== Business SQL =============== ");
        System.out.println(business_data_sql.toString());
        fileWriter.write("============== Business SQL ===============\n");
        fileWriter.write(business_data_sql.toString());
        
        
        business_user_map_data_sql.deleteCharAt(business_user_map_data_sql.lastIndexOf(","));
        business_user_map_data_sql.append(";");

        System.out.println("============== Business User Map SQL =============== ");
        System.out.println(business_user_map_data_sql.toString());
        fileWriter.write("============== Business User Map SQL =============== \n");
        fileWriter.write(business_user_map_data_sql.toString());
        

        
        number_data_sql.deleteCharAt(number_data_sql.lastIndexOf(","));
        number_data_sql.append(";");

        System.out.println("============== Business Number SQL =============== ");
        System.out.println(number_data_sql.toString());
        fileWriter.write("============== Business Number SQL =============== \n");
        fileWriter.write(number_data_sql.toString());
     
        

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
