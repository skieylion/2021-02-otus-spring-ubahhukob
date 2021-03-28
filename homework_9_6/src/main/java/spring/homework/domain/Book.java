package spring.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;
}
