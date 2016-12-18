package jibidus.kaamelott.startup;

import jibidus.kaamelott.character.Character;
import jibidus.kaamelott.character.CharacterRepository;
import jibidus.kaamelott.episode.Episode;
import jibidus.kaamelott.episode.EpisodeId;
import jibidus.kaamelott.episode.EpisodeRepository;
import jibidus.kaamelott.sentence.Sentence;
import jibidus.kaamelott.sentence.SentenceRepository;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.BindException;

import java.util.Properties;

@Configuration
class SentencesLoadingBatchConfiguration {

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

    private FlatFileItemReader<Sentence> reader() {
        FlatFileItemReader<Sentence> reader = new FlatFileItemReader();
        reader.setResource(new ClassPathResource("db/initial_data/sentences.csv"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<Sentence>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"text", "character_code", "episode_book", "episode_number"});
                setDelimiter(";");
            }});
            setFieldSetMapper(new FieldSetMapper<Sentence>() {

                @Override
                public Sentence mapFieldSet(FieldSet fieldSet) throws BindException {
                    Properties properties = fieldSet.getProperties();
                    Sentence sentence = new Sentence();

                    sentence.setText(properties.getProperty("text"));

                    Character character = characterRepository.findOne(properties.getProperty("character_code"));
                    sentence.setCharacter(character);

                    String book = properties.getProperty("episode_book");
                    int number = Integer.valueOf(properties.getProperty("episode_number"));
                    EpisodeId episodeId = new EpisodeId(book, number);
                    Episode episode = episodeRepository.findOne(episodeId);
                    sentence.setEpisode(episode);

                    return sentence;
                }
            });
        }});
        return reader;
    }

    private RepositoryItemWriter<Sentence> writer() {
        RepositoryItemWriter<Sentence> writer = new RepositoryItemWriter();
        writer.setRepository(sentenceRepository);
        writer.setMethodName("save");
        return writer;
    }
}
