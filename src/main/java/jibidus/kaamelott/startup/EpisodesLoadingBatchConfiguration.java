package jibidus.kaamelott.startup;

import jibidus.kaamelott.episode.Episode;
import jibidus.kaamelott.episode.EpisodeRepository;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
class EpisodesLoadingBatchConfiguration {

    public static final String STEP_ID = "episodesLoadingStep";
    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step episodesLoadingStep() {
        return stepBuilderFactory.get(STEP_ID)
                .<Episode, Episode>chunk(10)
                .reader(reader())
                .writer(writer())
                .build();
    }

    public static final String[] CSV_COLUMNS = {"book", "number", "title"};

    private FlatFileItemReader<Episode> reader() {
        CSVItemReaderBuilder reader = new CSVItemReaderBuilder(Episode.class, CSV_COLUMNS);
        reader.setResource(new ClassPathResource("db/initial_data/episodes.csv"));
        reader.setLinesToSkip(1);
        return reader;
    }

    private RepositoryItemWriter<Episode> writer() {
        RepositoryItemWriter<Episode> writer = new RepositoryItemWriter();
        writer.setRepository(episodeRepository);
        writer.setMethodName("save");
        return writer;
    }

}
