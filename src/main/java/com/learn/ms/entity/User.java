package com.learn.ms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_redis")
public class User implements Serializable {

    @Id
    private UUID uuid;

    private String name;

    private int age;

    private String gender;

    private String department;

    private LocalDate joinDate;
}
