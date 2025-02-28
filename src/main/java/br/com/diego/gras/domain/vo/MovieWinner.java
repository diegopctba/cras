package br.com.diego.gras.domain.vo;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieWinner {

    private String producer;
    @Column(name = "intervalYears")
    private Integer interval;
    private Integer previousWin;
    private Integer followingWin;

}
