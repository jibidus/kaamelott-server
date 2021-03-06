package jibidus.kaamelott.startup;

import jibidus.kaamelott.character.CharacterRepository;
import jibidus.kaamelott.episode.EpisodeRepository;
import jibidus.kaamelott.sentence.SentenceRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableBatchProcessing
@CommonsLog
class InitialDataLoadingBatchConfiguration {

    public static final String JOB_ID = "loadInitialData";

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private SentenceRepository sentenceRepository;

    @Autowired
    @Qualifier(CharactersLoadingBatchConfiguration.STEP_ID)
    private Step charactersLoadingStep;

    @Autowired
    @Qualifier(EpisodesLoadingBatchConfiguration.STEP_ID)
    private Step episodesLoadingStep;

    @Autowired
    @Qualifier(SentencesLoadingBatchConfiguration.STEP_ID)
    private Step sentencesLoadingStep;

    @Bean
    public Job loadInitialData() {
        return jobBuilderFactory.get(JOB_ID)
                .listener(listener())
                .start(charactersLoadingStep)
                .next(episodesLoadingStep)
                .next(sentencesLoadingStep)
                .build();
    }

    public JobExecutionDecider contidion() {
        return (jobExecution, stepExecution) ->
                sentenceRepository.count() == 0 ? FlowExecutionStatus.COMPLETED : FlowExecutionStatus.FAILED;
    }

    @Bean
    public JobExecutionListener listener() {
        return new JobExecutionListenerSupport() {
            @Override
            public void afterJob(JobExecution jobExecution) {
                if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
                    log.info(characterRepository.count() + " character(s) loaded");
                    log.info(episodeRepository.count() + " episode(s) loaded");
                    log.info(sentenceRepository.count() + " sentence(s) loaded");
                    log.info("Initial data loaded");
                } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
                    List<Throwable> allFailures = jobExecution.getAllFailureExceptions();
                    log.error(allFailures.size() + " failures found when loading initial data");
                    for (Throwable e : allFailures) {
                        log.error("Cannot load initial data", e);
                    }
                }
            }
        };
    }

}
