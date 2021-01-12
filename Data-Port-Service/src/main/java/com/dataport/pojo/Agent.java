package com.dataport.pojo;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Agent {

    @CsvBindByName(column = "user_id")
    private String userId;

    @CsvBindByName(column = "username")
    private String username;

    @CsvBindByName(column = "description")
    private String description;

    @CsvBindByName(column = "attributes")
    private String attributes;

    @CsvBindByName(column = "socket_id")
    private String socketId;

    @CsvBindByName(column = "disabled")
    private String disabled;

    @CsvBindByName(column = "created_at")
    private String createdAt;

    @CsvBindByName(column = "updated_at")
    private String updatedAt;

    @CsvBindByName(column = "vi_id")
    private String viId;


}
