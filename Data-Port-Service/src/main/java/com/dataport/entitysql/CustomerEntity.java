package com.dataport.entitysql;

import com.dataport.pojo.Customer;
import com.dataport.util.SqlFileGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CustomerEntity {
	
    final StringBuilder number_data_sql = new StringBuilder("USE `convovridb` ;\n\n");
	StringBuilder userauth_data_sql = new StringBuilder("USE `convouserauth` ;\n\n");
    final StringBuilder uservri_data_sql = new StringBuilder("USE `convovridb` ;\n\n");
    final StringBuilder user_role_data_sql = new StringBuilder("USE `convovridb` ;\n\n");
    
	
    public void generateRemainingCustomer(List<Customer> customers, List<String> existingCustomers, 
    		List<String> existingCreditGroup, List<String> existingNumbers, 
    		List<String> exitingUserWithRoleUser)  throws IOException {
    	
        number_data_sql.append("INSERT IGNORE INTO number (")
        .append("number,number_type_id,twilio_number_sid,owned_by,owned_by_type,owned_by_desc,used_by,used_by_desc,credit_type,credit_plan_id_or_group_id," +
                "mins_limit_type,mins_paid,mins_prepaid,mins_used,mins_owed,mins_updated_at" +
                ",mins_reloaded_at,expire_on,status,created_on,created_by,modified_on,modified_by,deleted_on,deleted_by) ")
        .append(" VALUES\n");
        
        
        userauth_data_sql.append("INSERT IGNORE INTO user(")
                .append("userid,email,username,password,email_verified,firstname,lastname,name,phonenumber," +
                        "street1,street2,city,state,country,postalcode,failedattempts,lastfailedattempton,accessedfromip" +
                        ",accessedon,designation,image_url,provider,provider_id,disabled,locked,expired,credexpired,status," +
                        "resettoken,tokenexpirydate,createdon,createdby,modifiedon,modifiedby,deletedon,deletedby,token) ")
                .append("VALUES\n");


        uservri_data_sql.append("INSERT IGNORE INTO user(")
                .append("id,firstname,lastname,email,token,status,created_on,created_by,modified_on,modified_by,deleted_on,deleted_by) ")
                .append("VALUES\n");


        user_role_data_sql.append("INSERT IGNORE INTO user_role_map(")
                .append("user_id,role_id,status,created_on,created_by,modified_on,modified_by,deleted_on,deleted_by)")
                .append(" VALUES\n");

        
        FileWriter fileWriter = new FileWriter("src/main/resources/sql/user_vridb_remaining.sql"); 
        
        for (Customer customer : customers) {
        	
        	if (existingCustomers.contains(customer.getId())) {
        		System.out.println("User is present in db "  + customer.getId() + "| " + customer.getName());
        	} else {
        		createUserAuthRecord(customer);
        		createUserVRIRecord(customer);
        	}
        	
        	if (exitingUserWithRoleUser.contains(customer.getId())) {
        		System.out.println("User is present in db with role user "  + customer.getId() + "| " + customer.getName());
        	} else {
        		createUserRoleRecord(customer);
        	}
        	
        	if (existingNumbers.contains(customer.getPhone_extension())) {
        		System.out.println("User number is present in db " + customer.getId() + "| " + customer.getName() + "| " +
        				customer.getPhone_extension());
        	}
        	
        	
        }
        
        
        userauth_data_sql.deleteCharAt(userauth_data_sql.lastIndexOf(","));
        userauth_data_sql.append(";");

        System.out.println("============== UserAuth SQL =============== ");
        System.out.println(userauth_data_sql.toString());
        fileWriter.write("============== UserAuth SQL =============== \n");
        fileWriter.write(userauth_data_sql.toString());
        fileWriter.write("");

        
        uservri_data_sql.deleteCharAt(uservri_data_sql.lastIndexOf(","));
        uservri_data_sql.append(";");

        System.out.println("============== UserVRI SQL =============== ");
        System.out.println(uservri_data_sql.toString());
        fileWriter.write("============== UserVRI SQL =============== \n");
        fileWriter.write(uservri_data_sql.toString());
        fileWriter.write("");
        
        
        user_role_data_sql.deleteCharAt(user_role_data_sql.lastIndexOf(","));
        user_role_data_sql.append(";");

        System.out.println("============== User Role VRI SQL =============== ");
        System.out.println(user_role_data_sql.toString());
        fileWriter.write("============== User Role VRI SQL =============== \n");
        fileWriter.write(user_role_data_sql.toString());
        fileWriter.write("");

        
    }


    private void createUserAuthRecord(Customer customer) {
    	
        String email = customer.getEmail().replaceAll("'","\\\\'");
        String firstName = customer.getName().replaceAll("'","\\\\'");
        String lastName = "";
        String NULL = null;
    	
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

    }

    private void createUserVRIRecord(Customer customer) {
    	
        String email = customer.getEmail().replaceAll("'","\\\\'");
        String firstName = customer.getName().replaceAll("'","\\\\'");
        String lastName = "";
        String NULL = null;
        
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

    }

    private void createUserRoleRecord(Customer customer) {
        String NULL = null;
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

    }
    
    private void createExtnNumberRecord(Customer customer) {
    	number_data_sql.append("('")
		.append(customer.getPhone_extension())
		.append("', '")
		.append("Guest")
		.append("', ")
		.append("null")
		.append(", '")
		.append(customer.getId())
		.append("', 'person','")
		.append(customer.getName())
		.append("', '")
		.append(customer.getId())
		.append("', '")
		.append(customer.getName())
		.append("', '")
		.append("")
		.append("', '");
		number_data_sql.append("");
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

    }
    
    
    
    
    public void generateUserAuthDbSql(List<Customer> customers) throws IOException {

        final StringBuilder user_data_sql =
                new StringBuilder("USE `convouserauth` ;\n\n");
        user_data_sql.append("INSERT IGNORE INTO user(")
                .append("userid,email,username,password,email_verified,firstname,lastname,name,phonenumber," +
                        "street1,street2,city,state,country,postalcode,failedattempts,lastfailedattempton,accessedfromip" +
                        ",accessedon,designation,image_url,provider,provider_id,disabled,locked,expired,credexpired,status," +
                        "resettoken,tokenexpirydate,createdon,createdby,modifiedon,modifiedby,deletedon,deletedby,token) ")
                .append("VALUES\n");

        String NULL = null;
        FileWriter fileWriter = new FileWriter("src/main/resources/sql/customer_userauth_user.sql");
        for (Customer customer : customers) {
            String email = customer.getEmail().replaceAll("'", "\\\\'");
            String firstName = customer.getName().replaceAll("'", "\\\\'");
            String lastName = "";

            if ("admin".equalsIgnoreCase(customer.getRole())) {
                System.out.println("Reject Reason : Admin User");
                System.out.println(customer);
                fileWriter.write("Reject Reason : Admin User");
                fileWriter.write(customer.toString());
                continue;
            }
            user_data_sql.append("('")
                    //userid,email,username,password,email_verified,firstname,lastname,name,phonenumber --9
                    .append(customer.getId()).append("','")
                    .append(email).append("','")
                    .append(customer.getUsername()).append("',")
                    .append(NULL).append(",")
                    .append("b'1'").append(",'")
                    .append(firstName).append("','")
                    .append(lastName).append("','")
                    .append("").append("','")
                    .append(customer.getCellphone()).append("','")
                    
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
        }

        user_data_sql.deleteCharAt(user_data_sql.lastIndexOf(","));
        user_data_sql.append(";");

        System.out.println("============== userAuth_user_sql =============== ");
        System.out.println(user_data_sql.toString());
        fileWriter.write(user_data_sql.toString());
        fileWriter.close();


        generateVriUserSql(customers);


    }

    private void generateVriUserSql(List<Customer> customers) throws IOException {
        final StringBuilder user_data_sql =
                new StringBuilder("USE `convovridb` ;\n\n");
        user_data_sql.append("INSERT IGNORE INTO user(")
                .append("id,firstname,lastname,email,token,status,created_on,created_by,modified_on,modified_by,deleted_on,deleted_by) ")
                .append("VALUES\n");
        String NULL = null;
        FileWriter fileWriter = new FileWriter("src/main/resources/sql/customer_vri_user.sql");
        for (Customer customer : customers) {

            if ("admin".equalsIgnoreCase(customer.getRole())) {
                System.out.println("Reject Reason : Admin User");
                System.out.println(customer);
                fileWriter.write("Reject Reason : Admin User");
                fileWriter.write(customer.toString());
                continue;
            }

            String userName = customer.getUsername();
            String email = customer.getEmail().replaceAll("'", "\\\\'");
            String firstName = customer.getName().replaceAll("'", "\\\\'");
            String lastName = "";


            user_data_sql.append("('")
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
        }
        user_data_sql.deleteCharAt(user_data_sql.lastIndexOf(","));
        user_data_sql.append(";");

        System.out.println("============== userAuth_user_sql =============== ");
        System.out.println(user_data_sql.toString());

       fileWriter.write(user_data_sql.toString());
       fileWriter.close();


    }

    
    
}
