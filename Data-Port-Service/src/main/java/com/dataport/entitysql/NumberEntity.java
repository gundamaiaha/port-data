package com.dataport.entitysql;

import com.dataport.pojo.CreditGroupWithOwner;
import com.dataport.pojo.Customer;
import com.dataport.util.SqlFileGenerator;
import org.apache.commons.lang3.StringUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
                System.out.println("Reject Reason : Phone Extension is Blank");
                System.out.println(customer);
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

        System.out.println("============== guest_number_data_sql =============== ");
        System.out.println(guest_number_data_sql.toString());

        SqlFileGenerator.generateSql("src/main/resources/sql/guest_vri_number.sql", guest_number_data_sql.toString());
    }


    /**
     * Pull the numbers of users who had extension
     *
     * @param customers
     */
    public void generateNumbersSqlForMemberWithOnlyCreditPlan(List<Customer> customers) throws IOException {
        final StringBuilder member_number_data_sql =
                new StringBuilder("USE `convovridb` ;\n\n");
        member_number_data_sql.append("INSERT IGNORE INTO number (")
                .append("number,number_type_id,twilio_number_sid,owned_by,owned_by_type,owned_by_desc,used_by,used_by_desc,credit_type,credit_plan_id_or_group_id," +
                        "mins_limit_type,mins_paid,mins_prepaid,mins_used,mins_owed,mins_updated_at" +
                        ",mins_reloaded_at,expire_on,status,created_on,created_by,modified_on,modified_by,deleted_on,deleted_by) ")
                .append(" VALUES\n");
        String NULL = null;
        FileWriter fileWriter= new FileWriter("src/main/resources/sql/customer_vri_number_with_credit_plan.sql");
        for (Customer customer : customers) {
            if (!"member".equalsIgnoreCase(customer.getRole())) {
                System.out.println("Reject Reason : Not a member");
                System.out.println(customer);
                fileWriter.write("Reject Reason : Not a member");
                fileWriter.write(customer.toString());
                continue;
            }
            if (StringUtils.isBlank(customer.getPhone())) {
                System.out.println("Reject Reason : Phone Number is blank");
                System.out.println(customer);
                fileWriter.write("Reject Reason : Phone Number is blank");
                fileWriter.write(customer.toString());
                continue;
            }
            if(StringUtils.isNotBlank(customer.getCredit_groups_id())){
                System.out.println("Reject Reason: Credit Group is not blank");
                System.out.println(customer);
                fileWriter.write("Reject Reason : Credit Group is not blank");
                fileWriter.write(customer.toString());
                continue;
            }
            if(StringUtils.isBlank(customer.getCredit_plan_id())){
                System.out.println("Reject Reason: Credit Plan is  blank");
                System.out.println(customer);
                fileWriter.write("Reject Reason : Credit Plan is  blank");
                fileWriter.write(customer.toString());
                continue;
            }


                  member_number_data_sql.append("('")
                            //number,number_type_id,twilio_number_sid,owned_by,owned_by_type
                            .append("+" + customer.getPhone()).append("','")
                            .append("twilio").append("',")
                            .append(NULL).append(",'")
                            .append(customer.getId()).append("','")
                            .append("person").append("',")

                            //owned_by_desc,used_by,used_by_desc,credit_type,credit_plan_id_or_group_id
                            .append(NULL).append(",'")
                            .append(customer.getId()).append("',")
                            .append(customer.getName()).append(",'")
                            .append("Credit-Plan").append("','")
                            .append(customer.getCredit_plan_id()).append("','")

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
        member_number_data_sql.deleteCharAt(member_number_data_sql.lastIndexOf(","));
        member_number_data_sql.append(";");

        System.out.println("============== customer_vri_number_with_credit_plan =============== ");
        System.out.println(member_number_data_sql.toString());

        fileWriter.write(member_number_data_sql.toString());
        fileWriter.close();

    }


    public void generateNumbersSqlForCreditGroups(List<Customer> customers, Map<String, CreditGroupWithOwner> creditGroupWithOwnerMap) throws IOException {
        final StringBuilder user_number_data_sql =
                new StringBuilder("USE `convovridb` ;\n\n");
        user_number_data_sql.append("INSERT IGNORE INTO number (")
                .append("number,number_type_id,twilio_number_sid,owned_by,owned_by_type,owned_by_desc,used_by,used_by_desc,credit_type,credit_plan_id_or_group_id," +
                        "mins_limit_type,mins_paid,mins_prepaid,mins_used,mins_owed,mins_updated_at" +
                        ",mins_reloaded_at,expire_on,status,created_on,created_by,modified_on,modified_by,deleted_on,deleted_by) ")
                .append(" VALUES\n");
        String NULL = null;
        FileWriter fileWriter= new FileWriter("src/main/resources/sql/customer_vri_number_with_credit_group.sql");

        for (Customer customer : customers) {



            //skip the customers who don't have phone number
            if (StringUtils.isBlank(customer.getPhone())) {
                System.out.println("Reject Reason : Phone Number is blank");
                System.out.println(customer);
                fileWriter.write("Reject Reason : Phone Number is blank");
                fileWriter.write(customer.toString());
                continue;
            }

            //skipping for guest users and credit group that is not matched with selected credit groups
            if ("guest".equalsIgnoreCase(customer.getRole()))  {
                System.out.println("Reject Reason : Guest User");
                System.out.println(customer);
                fileWriter.write("Reject Reason : Guest User");
                fileWriter.write(customer.toString());
                continue;
            }
           // skipping for  credit group that is not matched with selected credit groups
            if(!creditGroupWithOwnerMap.containsKey(customer.getCredit_groups_id())){
                System.out.println("Rejected Reason : Credit Group not matched with selected one.");
                System.out.println("customer = " + customer);
                fileWriter.write("Rejected Reason : Credit Group not matched with selected one.");
                fileWriter.write(customer.toString());
                continue;
            }
            final CreditGroupWithOwner creditGroupWithOwner = creditGroupWithOwnerMap.get(customer.getCredit_groups_id());

            user_number_data_sql.append("('")
                    //number,number_type_id,twilio_number_sid,owned_by,owned_by_type
                    .append("+" + customer.getPhone()).append("','")
                    .append("twilio").append("',")
                    .append(NULL).append(",'")
                    .append(creditGroupWithOwner.getOwnerId()).append("','")
                    .append("person").append("','")

                    //owned_by_desc,used_by,used_by_desc,credit_type,credit_plan_id_or_group_id
                    .append(creditGroupWithOwner.getOwnerName()).append("','")
                    .append(customer.getId()).append("','")
                    .append(customer.getName()).append("','")
                    .append("Credit-Group").append("','")
                    .append(customer.getCredit_groups_id()).append("','")

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
        user_number_data_sql.deleteCharAt(user_number_data_sql.lastIndexOf(","));
        user_number_data_sql.append(";");

        System.out.println("============== customer_vri_number_with_credit_group =============== ");
        System.out.println(user_number_data_sql.toString());

        fileWriter.write(user_number_data_sql.toString());
        fileWriter.close();



    }


}
