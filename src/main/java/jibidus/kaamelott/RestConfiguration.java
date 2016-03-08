package jibidus.kaamelott;

import jibidus.kaamelott.episode.EpisodeId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class RestConfiguration {

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {

        return new RepositoryRestConfigurerAdapter() {

            @Override
            public void configureConversionService(ConfigurableConversionService conversionService) {
                super.configureConversionService(conversionService);
                conversionService.addConverter(EpisodeId.CONVERTER);
            }

        };
    }

}
