package com.saurabh.E_Commerce.models;

import com.saurabh.E_Commerce.models.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

@Entity
@Table(name = "search_sync_queue")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchSyncQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long searchId;

    @Column(name = "entity_type",nullable = false)
    private String entityType;

    @Column(name = "entity_id")
    private long entityId;

    private String action;

    @Enumerated(EnumType.STRING)
    private StatusEnum status=StatusEnum.PENDING;

    @Column(name = "retry_count")
    private int retryCount=0;

    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;

}
