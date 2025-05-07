package com.seproject.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Integer taskId;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name = "creation_date")
    private LocalDateTime creationDate = LocalDateTime.now();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name="created_by", referencedColumnName = "user_id")
    private User creator;

    @Column(name = "status", nullable = false)
    private String status ="NOT STARTED";

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Column(name="description")
    private String description;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "task_teamspaces",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "teamspace_id")
    )
    private List<Teamspace> teamspaces;


    @ManyToMany
    @JoinTable(
      name = "task_assignees",
      joinColumns = @JoinColumn(name = "task_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> assignees;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
private List<TaskComment> comments;


}
