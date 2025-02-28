package br.com.diego.gras.batch;

import br.com.diego.gras.repository.Movie;
import br.com.diego.gras.repository.MovieRepository;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Configuration
public class ImportMovies {

    public static final String[] COLUMN_NAMES = {"year", "title", "studios", "producers", "winner"};

    @Value("${moviesList}")
    public String filePath;

    @Bean
    CommandLineRunner initDatabase(MovieRepository repository) {

        return args -> {
            Instant start = Instant.now();
            FlatFileItemReader<Movie> reader = readCSV();
            reader.open(new ExecutionContext());
            Movie movie = null;

            do {
                try {
                    movie = reader.read();
                    if (movie != null) {
                        repository.save(movie);
                    }
                } catch (FlatFileParseException ex) {
                    ex.printStackTrace();
                }

            } while (movie != null);

            long qtd = repository.count();
            Instant end = Instant.now();
            long time = Duration.between(start, end).toMillis();
        };
    }

    private FlatFileItemReader<Movie> readCSV() throws MalformedURLException, IOException {

        File tempFile = new File(filePath);
        if (!tempFile.exists()) {
            throw new IOException("file not found");
        }

        return new FlatFileItemReaderBuilder<Movie>()
                .linesToSkip(1)
                .name("movielistReader")
                .resource(new PathResource(filePath))
                .delimited()
                .delimiter(";")
                .names(COLUMN_NAMES)
                .fieldSetMapper(fieldSet -> Movie.builder()
                        .year(fieldSet.readInt("year"))
                        .title(fieldSet.readString("title"))
                        .studio(fieldSet.readString("studios"))
                        .producers(Arrays.stream(fieldSet.readString("producers").split("(?i),| and "))
                                .map(x -> StringUtils.capitalize(x.strip())).filter(x -> !x.isEmpty()).toList())
                        .winner(fieldSet.readString("winner").equalsIgnoreCase("yes"))
                        .build())
                .build();
    }
}