package com.dataport.pojo;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @CsvBindByName(column = "id")
    private String userId;

    @CsvBindByName(column = "role")
    private String role;

    @CsvBindByName(column = "email")
    private String email;

    @CsvBindByName(column = "username")
    private String username;

    @CsvBindByName(column = "tel")
    private String tel;

    @CsvBindByName(column = "sms")
    private String sms;

    @CsvBindByName(column = "password_hash")
    private String passwordHash;

    @CsvBindByName(column = "confirmation_token")
    private String confirmationToken;

    @CsvBindByName(column = "reset_password_expires_at")
    private String resetPasswordExpiresAt;

    @CsvBindByName(column = "email_confirmed")
    private String emailConfirmed;

    @CsvBindByName(column = "access_failed_count")
    private String accessFailedCount;

    @CsvBindByName(column = "lockout_enabled")
    private String lockoutEnabled;

    @CsvBindByName(column = "lockout_ends_at")
    private String lockoutEndsAt;

    @CsvBindByName(column = "disabled")
    private String disabled;

    @CsvBindByName(column = "created_at")
    private String createdAt;


    @CsvBindByName(column = "updated_at")
    private String updatedAt;



}
