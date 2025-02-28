package br.com.diego.gras.repository;


import br.com.diego.gras.domain.vo.MovieWinner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @NativeQuery("WITH PRODUCER_WINS AS (\n" +
            "SELECT\n" +
            "        p.producer,\n" +
            "        m.release as followingWin,\n" +
            "        LAG(m.release, 1) OVER (PARTITION BY p.producer ORDER BY m.release) AS previousWin\n" +
            "    FROM\n" +
            "        movie_producers p\n" +
            "    JOIN\n" +
            "        movie m ON p.movie_id = m.id\n" +
            "    WHERE\n" +
            "        m.winner = TRUE\n" +
            ")\n" +
            "SELECT PRODUCER, (followingWin - previousWin) as intervalYears, followingWin, previousWin\n" +
            "FROM PRODUCER_WINS \n" +
            "WHERE previousWin is not null and previousWin  < followingWin\n" +
            "order by intervalYears")
    List<MovieWinner> findProducerWithIntervalWins();
}
