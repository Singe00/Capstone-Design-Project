package com.example.CrtDgn.Interest.Domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "interest")
@Getter
@Setter
@Entity
@Builder
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interid")
    private Long id;

    @Column(name = "userid", nullable = false)
    private Long userId;

    @Column(name = "tourkey", nullable = false)
    private Long tourkey;

}
