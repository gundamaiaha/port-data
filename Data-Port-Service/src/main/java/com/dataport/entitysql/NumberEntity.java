package com.dataport.entitysql;

import com.dataport.pojo.Customer;
import com.dataport.util.SqlFileGenerator;

import java.util.List;

public class NumberEntity {


    public void generateNumbersSqlForGuest(List<Customer> customers) {

        final StringBuilder guest_number_data_sql =
                new StringBuilder("USE `convovridb` ;\n\n");
        guest_number_data_sql.append("INSERT IGNORE INTO number (")
                .append("number,number_type_id,twilio_number_sid,owned_by,owned_by_type,owned_by_desc,used_by,used_by_desc,credit_type,credit_plan_id_or_group_id," +
                        "mins_limit_type,mins_paid,mins_prepaid,mins_used,mins_owed,mins_updated_at" +
                        ",mins_reloaded_at,expire_on,status,created_on,created_by,modified_on,modified_by,deleted_on,deleted_by) ")
                .append(" VALUES\n");
        String NULL = null;
        for (Customer customer : customers) {
            if (!"guest".equalsIgnoreCase(customer.getRole())) {
                continue;
            }
            guest_number_data_sql.append("('")
                    //number,number_type_id,twilio_number_sid,owned_by,owned_by_type
                    .append(customer.getPhone_extension()).append("','")
                    .append("extn").append("',")
                    .append(NULL).append(",'")
                    .append(customer.getId()).append("','")
                    .append("person").append("',")

                    //owned_by_desc,used_by,used_by_desc,credit_type,credit_plan_id_or_group_id
                    .append(NULL).append(",'")
                    .append(customer.getId()).append("',")
                    .append(NULL).append(",'")
                    .append("Guest").append("',")
                    .append(NULL).append(",'")

                    //mins_limit_type,mins_paid,mins_prepaid,mins_used,mins_owed,mins_updated_at
                    .append(customer.getMins_type()).append("',")
                    .append(customer.getMins_paid()).append(",")
                    .append(customer.getMins_prepaid()).append(",")
                    .append(customer.getMins_used()).append(",")
                    .append(customer.getMins_owed()).append(",")
                    .append(NULL).append(",")

                    //mins_reloaded_at,expire_on,status,created_on
                    .append(NULL).append(",")
                    .append(NULL).append(",'")
                    .append("active").append("','")
                    .append(customer.getInserted_at()).append("',")

                    //created_by,modified_on,modified_by,deleted_on,deleted_by
                    .append(NULL).append(",'")
                    .append(customer.getUpdated_at()).append("',")
                    .append(NULL).append(",")
                    .append(NULL).append(",")
                    .append(NULL).append("),\n");
        }

        guest_number_data_sql.deleteCharAt(guest_number_data_sql.lastIndexOf(","));
        guest_number_data_sql.append(";");

        System.out.println("============== userAuth_user_sql =============== ");
        System.out.println(guest_number_data_sql.toString());

        SqlFileGenerator.generateSql("src/main/resources/sql/guest_vri_number.sql", guest_number_data_sql.toString());
    }

}
