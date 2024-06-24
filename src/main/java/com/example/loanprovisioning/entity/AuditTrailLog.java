package com.example.loanprovisioning.entity;


import com.example.loanprovisioning.dto.ActionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Setter
@Getter
@Table(name = "AUDIT_TRAIL_LOG", indexes = {
        @Index(name = "a_t_user_id_index", columnList = "USER_ID"),
        @Index(name = "a_t_page_path_index", columnList = "PAGE_PATH"),
})
/**
 *
 */
public class AuditTrailLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUDIT_TRAIL_LOG_ID", nullable = false)
    private Long auditTrailLogId;

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "USER_ID")
//    @JsonBackReference
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "PAGE_PATH")
    private String pagePath;
    @Column(name = "ACTION_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    @Column(name = "RESOURCE")
    private String resource;

    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "CREATION_DATE", nullable = false)
    @CreationTimestamp
    private Timestamp creationDate;
}

