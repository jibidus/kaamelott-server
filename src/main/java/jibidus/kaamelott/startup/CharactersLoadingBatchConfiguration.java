package jibidus.kaamelott.startup;

import jibidus.kaamelott.character.Character;
import jibidus.kaamelott.character.CharacterRepository;
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
class CharactersLoadingBatchConfiguration {

    public static final String STEP_ID = "charactersLoadingStep";
    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step charactersLoadingStep() {
        return stepBuilderFactory.get(STEP_ID)
                .<Character, Character>chunk(10)
                .reader(reader())
                .writer(writer())
                .build();
    }

    private FlatFileItemReader<Character> reader() {
        FlatFileItemReader<Character> reader = new FlatFileItemReader();
        reader.setResource(new ClassPathResource("db/initial_data/characters.csv"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<Character>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"code", "name"});
                setDelimiter(";");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Character>() {{
                setTargetType(Character.class);
            }});
        }});
        return reader;
    }

    private RepositoryItemWriter<Character> writer() {
        RepositoryItemWriter<Character> writer = new RepositoryItemWriter();
        writer.setRepository(characterRepository);
        writer.setMethodName("save");
        return writer;
    }
}
