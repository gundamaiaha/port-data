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
 * Mapps to Business
 *
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Business {
	
    @CsvBindByName(column = "id")
	public String id;
    
    @CsvBindByName(column = "name")
	public String name;
    
    @CsvBindByName(column = "phone_number")
	public String phone_number;
    
    @CsvBindByName(column = "username")
	public String username;
    
    @CsvBindByName(column = "type")
	public String type;
    
    @CsvBindByName(column = "description")
	public String description;
    
    @CsvBindByName(column = "location")
	public String location;
    
    @CsvBindByName(column = "url")
	public String url;
    
    @CsvBindByName(column = "photo_url")
	public String photo_url;
    
    @CsvBindByName(column = "public")
	public String ispublic;
    
    @CsvBindByName(column = "category_id")
	public String category_id;
    
    @CsvBindByName(column = "priority")
	public String priority;
    
    @CsvBindByName(column = "inserted_at")
	public String inserted_at;
    
    @CsvBindByName(column = "updated_at")
	public String updated_at;
    
    @CsvBindByName(column = "billing_code")
	public String billing_code;
    
    @CsvBindByName(column = "parent_id")
	public String parent_id;
}
