package com.dataport.entitysql;

import com.dataport.pojo.Customer;
import com.dataport.util.SqlFileGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CustomerEntity {

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
                    .append("").append("','")
                    .append("").append("','")
                    .append("").append("','")
                    .append("").append("','")
                    .append("").append("','")
                    .append("").append("',")

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
