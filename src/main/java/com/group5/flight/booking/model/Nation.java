package com.group5.flight.booking.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "nation")
@Data
public class Nation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nation_id")
    private Long nationId;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "deleted")
    private Boolean deleted;
}
