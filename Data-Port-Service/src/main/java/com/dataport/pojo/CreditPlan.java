package com.dataport.pojo;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

/**
 * Credit Plan csv
 *
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreditPlan {

    @CsvBindByName(column = "id")
    public String id;

    @CsvBindByName(column = "name")
    public String name;

    @CsvBindByName(column = "period")
    public String period;

    @CsvBindByName(column = "amount")
    public String amount;

    @CsvBindByName(column = "day_of_month")
    public String day_of_month;

    @CsvBindByName(column = "date_reloaded")
    public String date_reloaded;

    @CsvBindByName(column = "expiration_date")
    public String expiration_date;

    @CsvBindByName(column = "inserted_at")
    public String inserted_at;

    @CsvBindByName(column = "updated_at")
    public String updated_at;
}
