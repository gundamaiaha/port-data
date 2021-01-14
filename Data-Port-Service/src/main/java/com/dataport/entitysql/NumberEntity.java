package com.dataport.entitysql;

import com.dataport.pojo.Customer;
import com.dataport.util.SqlFileGenerator;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class NumberEntity {


    /**
     * Pull the numbers of users who had extension
     *
     * @param customers
     */
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

            if (StringUtils.isBlank(customer.getPhone_extension())) {
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


    /**
     * Pull the numbers of users who had extension
     *
     * @param customers
     */
    public void generateNumbersSqlForMemberWithOnlyCreditPlan(List<Customer> customers) {
        final StringBuilder member_number_data_sql =
                new StringBuilder("USE `convovridb` ;\n\n");
        member_number_data_sql.append("INSERT IGNORE INTO number (")
                .append("number,number_type_id,twilio_number_sid,owned_by,owned_by_type,owned_by_desc,used_by,used_by_desc,credit_type,credit_plan_id_or_group_id," +
                        "mins_limit_type,mins_paid,mins_prepaid,mins_used,mins_owed,mins_updated_at" +
                        ",mins_reloaded_at,expire_on,status,created_on,created_by,modified_on,modified_by,deleted_on,deleted_by) ")
                .append(" VALUES\n");
        String NULL = null;
        for (Customer customer : customers) {



            if ("member".equalsIgnoreCase(customer.getRole()) && StringUtils.isNotBlank(customer.getPhone())) {
               if(StringUtils.isBlank(customer.getCredit_groups_id()) && StringUtils.isNotBlank(customer.getCredit_plan_id())){
                   member_number_data_sql.append("('")
                           //number,number_type_id,twilio_number_sid,owned_by,owned_by_type
                           .append(customer.getPhone()).append("','")
                           .append("twilio").append("',")
                           .append(NULL).append(",'")
                           .append(customer.getId()).append("','")
                           .append("person").append("',")

                           //owned_by_desc,used_by,used_by_desc,credit_type,credit_plan_id_or_group_id
                           .append(NULL).append(",'")
                           .append(customer.getId()).append("',")
                           .append(NULL).append(",'")
                           .append("Credit-Plan").append("',")
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
            }
        }
        member_number_data_sql.deleteCharAt(member_number_data_sql.lastIndexOf(","));
        member_number_data_sql.append(";");

        System.out.println("============== member_number_data_sql =============== ");
        System.out.println(member_number_data_sql.toString());

        SqlFileGenerator.generateSql("src/main/resources/sql/member_vri_number_with_credit_plan.sql", member_number_data_sql.toString());
    }


}
