package jibidus.kaamelott.startup;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

class CSVItemReaderBuilder<T> extends FlatFileItemReader<T> {

    private final DefaultLineMapper<T> lineMapper;

    public DefaultLineMapper<T> getLineMapper() {
        return lineMapper;
    }

    public CSVItemReaderBuilder(Class<T> type, String... columns) {
        lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer delimiter = new DelimitedLineTokenizer();
        delimiter.setNames(columns);
        delimiter.setDelimiter(";");
        lineMapper.setLineTokenizer(delimiter);

        BeanWrapperFieldSetMapper<T> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(type);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        setLineMapper(lineMapper);
    }

}
