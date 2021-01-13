/**
 * 
 */
package com.dataport.pojo;

import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Mapps to user.csv which contains the Customer
 *
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	
    @CsvBindByName(column = "id")
	public String id;
    
    @CsvBindByName(column = "name")
	public String name;
    
    @CsvBindByName(column = "email")
	public String email;
    
    @CsvBindByName(column = "username")
	public String username;
    
    @CsvBindByName(column = "phone")
	public String phone;
    
    @CsvBindByName(column = "role")
	public String role;
    
    @CsvBindByName(column = "mins_paid")
	public String mins_paid;
    
    @CsvBindByName(column = "mins_prepaid")
	public String mins_prepaid;
    
    @CsvBindByName(column = "mins_used")
	public String mins_used;
    
    @CsvBindByName(column = "mins_owed")
	public String mins_owed;
    
    @CsvBindByName(column = "mins_updated_at")
	public String mins_updated_at;
    
    @CsvBindByName(column = "mins_reloaded_at")
	public String mins_reloaded_at;
    
    @CsvBindByName(column = "expire_on")
	public String expire_on;
    
    @CsvBindByName(column = "active")
	public String active;
    
    @CsvBindByName(column = "inserted_at")
	public String inserted_at;
    
    @CsvBindByName(column = "updated_at")
	public String updated_at;
    
    @CsvBindByName(column = "password_hash")
	public String password_hash;
    
    @CsvBindByName(column = "remember_created_at")
	public String remember_created_at;
    
    @CsvBindByName(column = "confirmation_token")
	public String confirmation_token;
    
    @CsvBindByName(column = "confirmed_at")
	public String confirmed_at;
    
    @CsvBindByName(column = "confirmation_sent_at")
	public String confirmation_sent_at;
    
    @CsvBindByName(column = "unlock_token")
	public String unlock_token;
    
    @CsvBindByName(column = "failed_attempts")
	public String failed_attempts;
    
    @CsvBindByName(column = "locked_at")
	public String locked_at;
    
    @CsvBindByName(column = "reset_password_token")
	public String reset_password_token;
    
    @CsvBindByName(column = "reset_password_sent_at")
	public String reset_password_sent_at;
    
    @CsvBindByName(column = "sign_in_count")
	public String sign_in_count;
    
    @CsvBindByName(column = "current_sign_in_at")
	public String current_sign_in_at;
    
    @CsvBindByName(column = "last_sign_in_at")
	public String last_sign_in_at;
    
    @CsvBindByName(column = "current_sign_in_ip")
	public String current_sign_in_ip;
    
    @CsvBindByName(column = "last_sign_in_ip")
	public String last_sign_in_ip; 
    
    @CsvBindByName(column = "phone_extension")
	public String phone_extension; 
    
    @CsvBindByName(column = "age")
	public String age; 
    
    @CsvBindByName(column = "gender")
	public String gender; 
    
    @CsvBindByName(column = "date_of_birth")
	public String date_of_birth; 
    
    @CsvBindByName(column = "photo_url")
	public String photo_url; 
    
    @CsvBindByName(column = "address_1")
	public String address_1; 
    
    @CsvBindByName(column = "address_2")
	public String address_2; 
    
    @CsvBindByName(column = "address_3")
	public String address_3; 
    
    @CsvBindByName(column = "city")
	public String city; 
    
    @CsvBindByName(column = "region")
	public String region; 
    
    @CsvBindByName(column = "postal_code")
	public String postal_code; 
    
    @CsvBindByName(column = "county")
	public String county; 
    
    @CsvBindByName(column = "country")
	public String country; 
    
    @CsvBindByName(column = "gov_id")
	public String gov_id; 
    
    @CsvBindByName(column = "cellphone")
	public String cellphone; 
    
    @CsvBindByName(column = "cellphone_operator")
	public String cellphone_operator; 
    
    @CsvBindByName(column = "mins_type")
	public String mins_type; 
    
    @CsvBindByName(column = "credit_plan_id")
	public String credit_plan_id; 
    
    @CsvBindByName(column = "register_type")
	public String register_type; 
    
    @CsvBindByName(column = "credit_groups_id")
	public String credit_groups_id; 
    
    @CsvBindByName(column = "submit_monthly_report")
	public String submit_monthly_report;
    
    @CsvBindByName(column = "paid_by_hourly")
	public String paid_by_hourly;

}
