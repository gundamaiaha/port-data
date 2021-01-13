package com.dataport.entitysql;

import com.dataport.pojo.User;
import com.dataport.util.SqlFileGenerator;

import java.util.List;

public class UserRoleMapEntity {


    public void generateUserRoleMapSql(List<User> users) {
        final StringBuilder user_role_data_sql =
                new StringBuilder("USE `convovridb` ;\n\n");
        user_role_data_sql.append("INSERT IGNORE INTO user_role_map(")
                .append("user_id,role_id,status,created_on,created_by,modified_on,modified_by,deleted_on,deleted_by)")
                .append(" VALUES\n");
        String NULL = null;
        for (User user : users) {
            if (!"AGENT".equalsIgnoreCase(user.getRole())) {
                continue;
            }
            user_role_data_sql.append("('")
                    //user_id,role_id,status,created_on,created_by,modified_on,modified_by,deleted_on,deleted_by
                    .append(user.getUserId()).append("','")
                    .append("ROLE_INTERPRETER").append("',")
                    .append("'active'").append(",'")
                    .append(user.getCreatedAt()).append("',")
                    .append(NULL).append(",")
                    .append(NULL).append(",")
                    .append(NULL).append(",")
                    .append(NULL).append(",")
                    .append(NULL).append("),\n");
        }

        user_role_data_sql.deleteCharAt(user_role_data_sql.lastIndexOf(","));
        user_role_data_sql.append(";");

        System.out.println("============== userAuth_user_sql =============== ");
        System.out.println(user_role_data_sql.toString());

        SqlFileGenerator.generateSql("src/main/resources/sql/interpreter_vri_user_role_map.sql", user_role_data_sql.toString());

    }

}
