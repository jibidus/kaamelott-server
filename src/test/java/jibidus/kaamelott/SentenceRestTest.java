package jibidus.kaamelott;

import jibidus.kaamelott.character.Character;
import jibidus.kaamelott.character.CharacterRepository;
import jibidus.kaamelott.episode.Episode;
import jibidus.kaamelott.episode.EpisodeRepository;
import jibidus.kaamelott.sentence.Sentence;
import jibidus.kaamelott.sentence.SentenceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class SentenceRestTest extends AbstractRestTest {

    @Autowired
    SentenceRepository sentenceRepository;
    @Autowired
    CharacterRepository characterRepository;
    @Autowired
    EpisodeRepository episodeRepository;

    Sentence sentence;

    @Before
    public final void setUp() throws Exception {
        Character character = characterRepository.save(new Character("code", "name"));
        Episode episode = episodeRepository.save(new Episode("VI", 8, "Lacrimosa"));
        sentence = sentenceRepository.save(new Sentence(character, episode, "text content"));
    }

    @Test
    public void getSingleSentence() throws Exception {
        mockMvc.perform(get("/sentences/" + sentence.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("text").value(sentence.getText()));
    }

}
