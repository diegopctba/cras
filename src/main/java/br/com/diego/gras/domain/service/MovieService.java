package br.com.diego.gras.domain.service;

import br.com.diego.gras.domain.response.RaspberryResponse;
import br.com.diego.gras.domain.vo.MovieWinner;
import br.com.diego.gras.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    @Autowired()
    private MovieRepository repository;

    public RaspberryResponse getIntervalWinners() throws Exception {
        List<MovieWinner> winners = this.findProducerWithIntervalWins();
        if (winners == null || winners.isEmpty()) {
            logger.warn("winners is null or empty");
            throw new Exception("no winners found");
        }
        logger.info("Winners found: {}",winners.size());
        return new RaspberryResponse(getProducersWithMaxInterval(winners, false), getProducersWithMaxInterval(winners, true));
    }

    private List<MovieWinner> findProducerWithIntervalWins() {
        return repository.findProducerWithIntervalWins();
    }

    private List<MovieWinner> getProducersWithMaxInterval(List<MovieWinner> winners, boolean isMaxInterval) {
        int interval = winners.get(isMaxInterval ? winners.size()-1 : 0).getInterval();
        return winners.stream().filter(w -> w.getInterval().equals(interval))
                .collect(Collectors.toList());
    }
}
