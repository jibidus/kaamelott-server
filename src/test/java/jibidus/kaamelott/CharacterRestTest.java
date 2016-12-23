package jibidus.kaamelott;

import jibidus.kaamelott.character.Character;
import jibidus.kaamelott.character.CharacterRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
public class CharacterRestTest extends AbstractRestTest {

    @Autowired
    private CharacterRepository characterRepository;

    private Character character;

    @Before
    public final void setUp() throws Exception {
        character = new Character("code", "name");
        characterRepository.save(character);
    }

    @Test
    public void getSingleCharacter() throws Exception {
        mockMvc.perform(get("/characters/" + character.getCode()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("name").value(character.getName()));
    }

}
