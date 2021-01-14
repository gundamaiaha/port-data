package com.dataport.pojo;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreditGroupWithOwner {

    private String creditGroupId;
    private String ownerId;
    private String ownerName;
}
