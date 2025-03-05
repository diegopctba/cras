package br.com.diego.gras.batch;

import br.com.diego.gras.repository.Movie;
import br.com.diego.gras.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Configuration
public class ImportMovies {

    private static final Logger logger = LoggerFactory.getLogger(ImportMovies.class);

    private static final String filePath = "Movielist.csv";

    public static final String[] COLUMN_NAMES = {"year", "title", "studios", "producers", "winner"};

     @Bean
    CommandLineRunner initDatabase(MovieRepository repository) {

        return args -> {
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
                    logger.error(ex.getMessage(), ex);
                }

            } while (movie != null);

        };
    }

    private FlatFileItemReader<Movie> readCSV() throws IOException {

        File tempFile = new File(filePath);
        if (!tempFile.exists()) {
            logger.error("file not found");
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