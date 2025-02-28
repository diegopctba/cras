package br.com.diego.gras.domain.controller;

import br.com.diego.gras.domain.response.RaspberryResponse;
import br.com.diego.gras.domain.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    @Autowired
    private MovieService service;

    @GetMapping("/")
    private String getSuccess() {
        return "success... this app is online and read to work";
    }

    @GetMapping("/movies/awards")
    private ResponseEntity<RaspberryResponse> getRaspberryAwards() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getIntervalWinners());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

}
