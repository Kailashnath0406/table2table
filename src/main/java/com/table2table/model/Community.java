package com.table2table.model;

import jakarta.persistence.*;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "communities")
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private Integer totalFlats;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    private List<User> users;

}
