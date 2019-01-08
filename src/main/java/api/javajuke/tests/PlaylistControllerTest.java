package api.javajuke.tests;

import api.javajuke.res.PlaylistController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
public class PlaylistControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private PlaylistController playlistController;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(playlistController).build();
    }

    @Test
    public void testPlaylistController() throws Exception {
            mockMvc.perform(get("/playlist/1"))
                    .andExpect(status().isOk())
                    .andExpect(content().json("{\n" +
                            "    \"id\": 1,\n" +
                            "    \"name\": \"Playlist 1\",\n" +
                            "    \"tracks\": [\n" +
                            "        {\n" +
                            "            \"id\": 1,\n" +
                            "            \"path\": \"example/path/1\",\n" +
                            "            \"title\": null,\n" +
                            "            \"artist\": null,\n" +
                            "            \"duration\": 0,\n" +
                            "            \"album\": null\n" +
                            "        },\n" +
                            "        {\n" +
                            "            \"id\": 2,\n" +
                            "            \"path\": \"example/path/2\",\n" +
                            "            \"title\": null,\n" +
                            "            \"artist\": null,\n" +
                            "            \"duration\": 0,\n" +
                            "            \"album\": null\n" +
                            "        }\n" +
                            "    ]\n" +
                            "}"));
    }
}