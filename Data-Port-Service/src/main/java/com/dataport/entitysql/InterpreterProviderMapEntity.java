package com.dataport.entitysql;

import com.dataport.pojo.Agent;
import com.dataport.pojo.User;
import com.dataport.util.SqlFileGenerator;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class InterpreterProviderMapEntity {



    public void generateInterpreterProviderMapSQL(List<User> users) throws IOException {


        final StringBuilder interpreter_provider_data_sql =
                new StringBuilder("USE `convovridb` ;\n\n");
        interpreter_provider_data_sql.append("INSERT IGNORE INTO interpreter_provider_map (")
                .append("interpreter_id,provider_id,provider_user_id,provider_user_password,status,created_on,created_by,modified_on,modified_by,deleted_on,deleted_by) ")
                .append("VALUES\n");
        String NULL = null;

        for(User user: users){
            if("agent".equalsIgnoreCase(user.getRole())) {
                //interpreter_id,provider_id,provider_user_id,provider_user_password
                interpreter_provider_data_sql.append("('")
                        .append(user.getUserId())
                        .append("','")
                        .append("twiliovri").append("','")
                        .append(user.getEmail()).append("',")
                        .append(NULL).append(",'")
                        //status,created_on,created_by,modified_on,modified_by,deleted_on,deleted_by
                        .append("active").append("','")
                        .append(user.getCreatedAt()).append("',")
                        .append(NULL).append(",")
                        .append(NULL).append(",")
                        .append(NULL).append(",")
                        .append(NULL).append(",")
                        .append(NULL).append("),\n");
            }
            else{
                System.out.println("Reject Reason : Not an Agent");
                System.out.println("user = " + user);
            }
        }


        interpreter_provider_data_sql.deleteCharAt(interpreter_provider_data_sql.lastIndexOf(","));
        interpreter_provider_data_sql.append(";");

        System.out.println("============== interpreter_sql =============== ");
        System.out.println(interpreter_provider_data_sql.toString());

        SqlFileGenerator.generateSql("src/main/resources/sql/interpreter_vridb_interpreter_provider_map.sql", interpreter_provider_data_sql.toString());



    }

}
