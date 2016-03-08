package jibidus.kaamelott.startup;

import jibidus.kaamelott.CharacterRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    private Log log = LogFactory.getLog(ApplicationStartup.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
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
