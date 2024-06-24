package com.example.loanprovisioning.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "USERS", indexes = {
        @Index(name = "u_email_index", columnList = "EMAIL"),
        @Index(name = "u_phone_index", columnList = "PHONE"),
        @Index(name = "u_first_name_index", columnList = "FIRST_NAME"),
        @Index(name = "u_uuid_index", columnList = "UUID"),
        @Index(name = "u_deleted_index", columnList = "IS_DELETED"),
        @Index(name = "u_verified_index", columnList = "ACCOUNT_VERIFIED"),
})

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", length = 20)
    private long userId;

    @Column(name = "UUID", unique = true, nullable = false)
    private String uuid = UUID.randomUUID().toString();
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;
    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;
    @Column(name = "PASSWORD", nullable = false)
    private String password;
    @Column(name = "PHONE", length = 20, nullable = false)
    private String phone;
    @Column(name = "PHOTO")
    private String photo; //todo: user avatar
    @Column(name = "LOGIN_ATTEMPTED_NUMBER", nullable = false, length = 3)
    private Integer loginAttemptedNumber = 0;
    @Column(name = "BLOCKED_UNTIL")
    private Timestamp blockedUntil;
    @CreationTimestamp
    @Column(name = "CREATION_DATE")
    private Timestamp creationDate;

    @UpdateTimestamp
    @Column(name = "LAST_UPDATE_DATE")
    private Timestamp lastUpdateDate;

    @Column(name = "ACCOUNT_VERIFIED", nullable = false)
    private boolean verified = false;

    @Column(name = "IS_DELETED", nullable = false, columnDefinition = "boolean default false")
    private boolean deleted = false;

    @Column(name = "LOGIN_OTP_REQUIRED", nullable = false)
    private boolean loginOtpRequired = false;

    @Column(name = "MANAGED_BY")
    private Long managedBy;
}
