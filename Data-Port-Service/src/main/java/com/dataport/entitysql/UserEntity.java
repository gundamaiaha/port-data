package com.dataport.entitysql;

import com.dataport.pojo.User;
import org.apache.commons.lang3.StringUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class UserEntity {


    public void generateUserSqls(List<User> users, Map<String, String> userNamesMap) throws IOException {

        final StringBuilder user_data_sql =
                new StringBuilder("USE `convouserauth` ;\n\n");
        user_data_sql.append("INSERT IGNORE INTO user(")
                .append("userid,email,username,password,email_verified,firstname,lastname,name,phonenumber," +
                        "street1,street2,city,state,country,postalcode,failedattempts,lastfailedattempton,accessedfromip" +
                        ",accessedon,designation,image_url,provider,provider_id,disabled,locked,expired,credexpired,status," +
                        "resettoken,tokenexpirydate,createdon,createdby,modifiedon,modifiedby,deletedon,deletedby,token) ")
                .append("VALUES\n");

        String NULL = null;
        FileWriter fileWriter = new FileWriter("src/main/resources/sql/interpreter_userauth_user.sql");

        for (User user : users) {

            if (!"AGENT".equalsIgnoreCase(user.getRole())) {
                System.out.println("Rejected Reason : not an agent");
                System.out.println(user);
                fileWriter.write("Rejected Reason : not an agent");
                fileWriter.write(user.toString());

                continue;
            }

            String userName = userNamesMap.get(user.getUserId());
            String firstName = "";
            String lastName = "";
            if (StringUtils.isNotBlank(userName)) {
                String userNameParts[] = userName.split(" ", 2);
                firstName = userNameParts[0];
                if (userNameParts.length > 1) {
                    lastName = userNameParts[1];
                }
            }
            String phoneNumber = user.getSms();
            if (StringUtils.isNotBlank(phoneNumber)) {
                phoneNumber = "+" + phoneNumber;
            }


            user_data_sql.append("('")
                    //userid,email,username,password,email_verified,firstname,lastname,name,phonenumber --9
                    .append(user.getUserId()).append("','")
                    .append(user.getEmail()).append("','")
                    .append(user.getUsername()).append("',")
                    .append(NULL).append(",")
                    .append("b'1'").append(",'")
                    .append(firstName).append("','")
                    .append(lastName).append("','")
                    .append("").append("','")
                    .append(phoneNumber).append("','")

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
                    .append("b'0'").append(",")

                    //credexpired,status,resettoken,tokenexpirydate
                    .append("b'0'").append(",")
                    .append("'active'").append(",")
                    .append(NULL).append(",")
                    .append(NULL).append(",'")

                    //createdon,createdby,modifiedon,modifiedby,deletedon,deletedby,token
                    .append(user.getCreatedAt()).append("',")
                    .append(NULL).append(",")
                    .append(NULL).append(",")
                    .append(NULL).append(",")
                    .append(NULL).append(",")
                    .append(NULL).append(",")
                    .append(NULL).append("),\n");
        }

        user_data_sql.deleteCharAt(user_data_sql.lastIndexOf(","));
        user_data_sql.append(";");

        System.out.println("============== interpreter_userauth_user.sql =============== ");
        System.out.println(user_data_sql.toString());

        fileWriter.write(user_data_sql.toString());
        fileWriter.close();
        generateVriUserSql(users, userNamesMap);


    }


    private void generateVriUserSql(List<User> users, Map<String, String> userNamesMap) throws IOException {
        final StringBuilder user_data_sql =
                new StringBuilder("USE `convovridb` ;\n\n");
        user_data_sql.append("INSERT IGNORE INTO user(")
                .append("id,firstname,lastname,email,token,status,created_on,created_by,modified_on,modified_by,deleted_on,deleted_by) ")
                .append("VALUES\n");
        String NULL = null;
        FileWriter fileWriter = new FileWriter("src/main/resources/sql/interpreter_vri_user.sql");
        for (User user : users) {
            if (!"AGENT".equalsIgnoreCase(user.getRole())) {
                System.out.println("Rejected Reason : not an agent");
                System.out.println(user);
                fileWriter.write("Rejected Reason : not an agent");
                fileWriter.write(user.toString());
                continue;
            }
            String userName = userNamesMap.get(user.getUserId());
            String firstName = "";
            String lastName = "";
            if (StringUtils.isNotBlank(userName)) {
                String userNameParts[] = userName.split(" ", 2);
                firstName = userNameParts[0];
                if (userNameParts.length > 1) {
                    lastName = userNameParts[1];
                }
            }

            user_data_sql.append("('")
                    //id,firstname,lastname,email,token,status,created_on,created_by,modified_on,modified_by,deleted_on,deleted_by
                    .append(user.getUserId()).append("','")
                    .append(firstName).append("','")
                    .append(lastName).append("','")
                    .append(user.getEmail()).append("',")
                    .append(NULL).append(",")
                    .append("'active'").append(",'")
                    .append(user.getCreatedAt()).append("',")
                    .append(NULL).append(",")
                    .append(NULL).append(",")
                    .append(NULL).append(",")
                    .append(NULL).append(",")
                    .append(NULL).append("),\n");
        }
        user_data_sql.deleteCharAt(user_data_sql.lastIndexOf(","));
        user_data_sql.append(";");

        System.out.println("============== interpreter_vri_user_sql =============== ");
        System.out.println(user_data_sql.toString());

        fileWriter.write(user_data_sql.toString());
        fileWriter.close();


    }


}
