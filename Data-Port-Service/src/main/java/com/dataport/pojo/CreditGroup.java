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
 * Credit Group csv
 *
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreditGroup {
	
    @CsvBindByName(column = "id")
	public String id;
    
    @CsvBindByName(column = "name")
	public String name;
    
    @CsvBindByName(column = "mins_type")
	public String mins_type;
    
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
    
    @CsvBindByName(column = "credit_plan_id")
	public String credit_plan_id;
    
    @CsvBindByName(column = "inserted_at")
	public String inserted_at;
    
    @CsvBindByName(column = "updated_at")
	public String updated_at;
}
