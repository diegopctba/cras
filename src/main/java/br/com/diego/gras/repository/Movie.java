package br.com.diego.gras.repository;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String studio;
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @Column(name = "producer")
    private List<String> producers;
    @Column(name = "release")
    private Integer year;
    private boolean winner;

}
