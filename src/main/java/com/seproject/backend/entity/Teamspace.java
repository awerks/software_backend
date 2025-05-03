package com.seproject.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "teamspaces")
public class Teamspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teamspace_id")
    private Integer id;

    @Column(name= "name")
    private String name;

    @Column(name = "creation_date")
    private LocalDateTime creationDate = LocalDateTime.now();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name="creator_id", referencedColumnName = "user_id")
    private User creator;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="project_id", referencedColumnName = "project_id")
    private Project project;

    @Column(name="description")
    private String description;

    @ManyToMany(mappedBy = "teamspaces")
    @JsonIgnore
    private List<Task> tasks;
}
