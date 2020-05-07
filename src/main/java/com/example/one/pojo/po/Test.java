package com.example.one.pojo.po;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "table_test")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
}
