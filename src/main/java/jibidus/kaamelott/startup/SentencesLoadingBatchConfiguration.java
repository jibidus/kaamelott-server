package jibidus.kaamelott.startup;

import jibidus.kaamelott.character.Character;
import jibidus.kaamelott.character.CharacterRepository;
import jibidus.kaamelott.episode.Episode;
import jibidus.kaamelott.episode.EpisodeId;
import jibidus.kaamelott.episode.EpisodeRepository;
import jibidus.kaamelott.sentence.Sentence;
import jibidus.kaamelott.sentence.SentenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.Properties;

@Configuration
class SentencesLoadingBatchConfiguration {

    static final Logger LOG = LoggerFactory.getLogger(SentencesLoadingBatchConfiguration.class);

    public static final String STEP_ID = "sentencesLoadingStep";

    @Autowired
    private SentenceRepository sentenceRepository;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step sentencesLoadingStep() {
        return stepBuilderFactory.get(STEP_ID)
                .<Sentence, Sentence>chunk(10)
                .reader(reader())
                .writer(writer())
                .build();
    }

    private static final String[] CSV_COLUMNS = {"text", "character_code", "episode_book", "episode_number"};

    private FlatFileItemReader<Sentence> reader() {
        CSVItemReaderBuilder reader = new CSVItemReaderBuilder(Sentence.class, CSV_COLUMNS);
        reader.setResource(new ClassPathResource("db/initial_data/sentences.csv"));
        reader.setLinesToSkip(1);

        reader.getLineMapper()
                .setFieldSetMapper(fieldSet -> {
                    Properties properties = fieldSet.getProperties();
                    Sentence sentence = new Sentence();

                    sentence.setText(properties.getProperty("text"));

                    String characterId = properties.getProperty("character_code");
                    Character character = characterRepository.findOne(characterId);
                    if (character == null) {
                        LOG.warn("No character found with code {}", characterId);
                    }
                    sentence.setCharacter(character);

                    String book = properties.getProperty("episode_book");
                    int number = Integer.valueOf(properties.getProperty("episode_number"));
                    EpisodeId episodeId = new EpisodeId(book, number);
                    Episode episode = episodeRepository.findOne(episodeId);
                    if (episode == null) {
                        LOG.warn("No episode found with book {} and number {}", book, number);
                    }
                    sentence.setEpisode(episode);

                    return sentence;
                });

        return reader;
    }

    private RepositoryItemWriter<Sentence> writer() {
        RepositoryItemWriter<Sentence> writer = new RepositoryItemWriter();
        writer.setRepository(sentenceRepository);
        writer.setMethodName("save");
        return writer;
    }
}
