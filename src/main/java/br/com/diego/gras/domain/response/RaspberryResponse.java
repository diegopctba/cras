package br.com.diego.gras.domain.response;

import br.com.diego.gras.domain.vo.MovieWinner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RaspberryResponse {

    private List<MovieWinner> min;
    private List<MovieWinner> max;

}
