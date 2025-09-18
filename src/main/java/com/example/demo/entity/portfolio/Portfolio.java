package com.example.demo.entity.portfolio;

import com.example.demo.entity.BaseEntity;
import com.example.demo.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;


@Entity
@Table(name = "portfolio")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Portfolio extends BaseEntity {
    @Id
    @Column(name = "portfolio_id ", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id ", nullable = false)
    User user;

    @Column(name = "porfolio_name", nullable = false, unique = true)
    String name;

}
