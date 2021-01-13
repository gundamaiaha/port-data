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
 * Mapps to Business Categories
 *
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BusinessCategory {
	
    @CsvBindByName(column = "tag")
	public String tag;
    
    @CsvBindByName(column = "id")
	public String id;
    
    @CsvBindByName(column = "name")
	public String name;
    
    @CsvBindByName(column = "inserted_at")
	public String inserted_at;
    
    @CsvBindByName(column = "updated_at")
	public String updated_at;
}
