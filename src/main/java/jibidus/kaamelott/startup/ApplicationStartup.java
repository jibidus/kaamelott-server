package jibidus.kaamelott.startup;

import jibidus.kaamelott.character.CharacterRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@CommonsLog
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier(InitialDataLoadingBatchConfiguration.JOB_ID)
    private Job loadInitialData;

    @Autowired
    private CharacterRepository characterRepository;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (characterRepository.count() > 0) {
            return;
        }
        try {
            log.debug("Empty database. Let's try to load initial data...");
            jobLauncher.run(loadInitialData, new JobParameters());
        } catch (Exception e) {
            log.error("Cannot load initial data", e);
        }
    }
}
