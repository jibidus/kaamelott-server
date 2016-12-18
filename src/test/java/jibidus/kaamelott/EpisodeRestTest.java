package jibidus.kaamelott;

import jibidus.kaamelott.episode.Episode;
import jibidus.kaamelott.episode.EpisodeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
public class EpisodeRestTest extends AbstractRestTest {

    @Autowired
    EpisodeRepository episodeRepository;

    Episode episode;

    @Before
    public final void setUp() throws Exception {
        episode = new Episode("VI", 8, "Lacrimosa");
        episodeRepository.save(episode);
    }

    @Test
    public void getSingleEpisode() throws Exception {
        mockMvc.perform(get("/episodes/" + episode.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("number").value(episode.getNumber()));
    }
}
