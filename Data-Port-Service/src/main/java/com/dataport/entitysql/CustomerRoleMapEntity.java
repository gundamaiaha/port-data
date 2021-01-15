package com.dataport.entitysql;

import com.dataport.pojo.Customer;
import com.dataport.util.SqlFileGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CustomerRoleMapEntity {


    public void generateUserRoleMapSql(List<Customer> customers) throws IOException {
        final StringBuilder user_role_data_sql =
                new StringBuilder("USE `convovridb` ;\n\n");
        user_role_data_sql.append("INSERT IGNORE INTO user_role_map(")
                .append("user_id,role_id,status,created_on,created_by,modified_on,modified_by,deleted_on,deleted_by)")
                .append(" VALUES\n");
        String NULL = null;
        FileWriter fileWriter = new FileWriter("src/main/resources/sql/customer_vri_user_role_map.sql");
        for (Customer customer : customers) {
            if ("admin".equalsIgnoreCase(customer.getRole())) {
                System.out.println("Reject Reason : Admin User");
                System.out.println(customer);
                fileWriter.write("Reject Reason : Admin User");
                fileWriter.write(customer.toString());
                continue;
            }
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

        user_role_data_sql.deleteCharAt(user_role_data_sql.lastIndexOf(","));
        user_role_data_sql.append(";");

        System.out.println("============== customer_vri_user_role_map.sql =============== ");
        System.out.println(user_role_data_sql.toString());

        fileWriter.write(user_role_data_sql.toString());
        fileWriter.close();

    }


}
