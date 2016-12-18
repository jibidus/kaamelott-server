package jibidus.kaamelott.startup;

import jibidus.kaamelott.episode.Episode;
import jibidus.kaamelott.episode.EpisodeRepository;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
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

    private FlatFileItemReader<Episode> reader() {
        FlatFileItemReader<Episode> reader = new FlatFileItemReader();
        reader.setResource(new ClassPathResource("db/initial_data/episodes.csv"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<Episode>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"book", "number", "title"});
                setDelimiter(";");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Episode>() {{
                setTargetType(Episode.class);
            }});
        }});
        return reader;
    }

    private RepositoryItemWriter<Episode> writer() {
        RepositoryItemWriter<Episode> writer = new RepositoryItemWriter();
        writer.setRepository(episodeRepository);
        writer.setMethodName("save");
        return writer;
    }

}
