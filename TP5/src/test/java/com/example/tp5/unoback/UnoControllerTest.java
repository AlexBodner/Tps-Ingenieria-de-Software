package com.example.tp5.unoback;
import com.example.tp5.unoback.model.Card;
import com.example.tp5.unoback.model.JsonCard;
import com.example.tp5.unoback.model.NumberCard;
import com.example.tp5.unoback.Service.UnoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
@AutoConfigureMockMvc
public class UnoControllerTest {
    @Autowired
    MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    UnoService unoService;
    private UUID createMatchFor(List<String> players) throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/newmatch");
        for (String player : players) {
            request.param("players", player);
        }

        String response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String uuidStr = objectMapper.readValue(response, String.class);

        return UUID.fromString(uuidStr);
    }
    @Test playWrongTestJulio() throws Throwable{
    
    }

    @Test
    public void test01CreateNewValidMatch() throws Exception {
        List<String> players = Arrays.asList("Alice", "Bob");
        UUID matchId = UUID.randomUUID();
        Mockito.when(unoService.newMatch(players)).thenReturn(matchId);

        UUID resUUID =  createMatchFor( players);

        assertNotNull(resUUID);
    }

    @Test
    public void test02CreateMatchFailing() throws Exception {
        List<String> players = List.of();

        Mockito.when(unoService.newMatch(players))
                .thenThrow(new IllegalArgumentException("Player list cannot be empty"));
        mockMvc.perform(post("/newmatch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(players)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test
    public void test03CreateMatchFailing1Player() throws Exception {
        List<String> players = List.of("Alice");

        Mockito.when(unoService.newMatch(players))
                .thenThrow(new IllegalArgumentException("Player list cannot less than 2"));
        mockMvc.perform( post( "/newmatch" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content(objectMapper.writeValueAsString(players)))
                .andDo( print() )
                .andExpect(status().isBadRequest());

    }

    @Test
    public void test04PlayValidMatch() throws Exception { //de este no estoy  seguro
        UUID matchId = UUID.randomUUID();
        String player = "Alice";
        JsonCard card = new NumberCard("RED", 1).asJson();
        Mockito.doNothing().when(unoService).playCard(matchId, player, card);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/play/" + matchId + "/" + player)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(card))
                        )
                .andExpect(status().isOk());

        Mockito.verify(unoService).playCard(eq(matchId), eq(player), eq(card));
    }
    @Test
    public void test04PlayNoMatchPassed() throws Exception { //de este no estoy  seguro
        UUID matchId = UUID.randomUUID();
        String player = "Alice";
        JsonCard card = new NumberCard("RED", 1).asJson();
        Mockito.doNothing().when(unoService).playCard(matchId, player, card);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/play/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(card))
                        )
                .andExpect(status().isBadRequest());
    }
    @Test
    public void test04PlayNoPlayerPassed() throws Exception { //de este no estoy  seguro
        UUID matchId = UUID.randomUUID();
        String player = "Alice";
        JsonCard card = new NumberCard("RED", 1).asJson();
        Mockito.doNothing().when(unoService).playCard(matchId, player, card);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/play/" + matchId + "/" )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(card))
                        )
                .andExpect(status().isBadRequest());
    }


    @Test
    public void test05PlayInvalidMatch() throws Exception { //de este no estoy  seguro
        UUID matchId = UUID.randomUUID();
        String player = "Alice";
        JsonCard card = new NumberCard("RED", 1).asJson();
        Mockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
                .when(unoService)
                .playCard(eq(matchId), eq(player), eq(card));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/play/" + matchId + "/" + player)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(card))
                )
                .andExpect(status().isBadRequest());

        Mockito.verify(unoService).playCard(eq(matchId), eq(player), eq(card));
    }
    @Test
    public void test06PlayInvalidJsonCard() throws Exception { 
        UUID matchId = UUID.randomUUID();
        String player = "Alice";
        String InvalidJsonCardcard = "{ " +
                "\"color\": \"" + "red" + "\", " +
                "\"number\": \"" + 1 + "\", " +
                "\"type\": \"" + "NumberCard" + "\", " +
                "\"shout\": \"" + "false" + "\" " +
                "";

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/play/" + matchId + "/" + player)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(card)
                )
                .andExpect(status().isBadRequest());

    }
    @Test
    public void test07DrawCard() throws Exception {
        // Arrange: Prepare test data
        UUID matchId = UUID.randomUUID();
        String player = "Jimmy";

        Mockito.doNothing().when(unoService).drawCard(eq(matchId));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/draw/" + matchId + "/" + player))
                .andDo(print())
                .andExpect(status().isOk());

        Mockito.verify(unoService).drawCard(eq(matchId));
    }
    @Test
    public void test08DrawCardInvalidMatch() throws Exception {
        // Arrange: Prepare test data
        UUID matchId = UUID.randomUUID();
        String player = "Mark Knopfler";

        Mockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
                .when(unoService)
                .drawCard(eq(matchId));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/draw/" + matchId + "/" + player))
                .andDo(print())
                .andExpect(status().isBadRequest());

        Mockito.verify(unoService).drawCard(eq(matchId));
    }
    @Test
    public void test09ActiveCard() throws Exception {
        // Arrange: Prepare test data
        UUID matchId = UUID.randomUUID();
        Card card = new NumberCard("RED", 1);
        JsonCard expectedControllerResponse = card.asJson(); // The controller transforms Card to JsonCard

        Mockito.when(unoService.getActiveCard(matchId)).thenReturn(card);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/activecard/" + matchId )
                        .accept(MediaType.APPLICATION_JSON)) // Indicate that we expect JSON back
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedControllerResponse)))
                .andExpect(status().isOk());
        Mockito.verify(unoService).getActiveCard(eq(matchId));

    }
    // Habria que hacer uno para cuando no exista MatchId o alpedo???

        @Test
    public void test10PlayerHand() throws Exception {
        // Arrange: Prepare test data
        UUID matchId = UUID.randomUUID();
        Card card = new NumberCard("RED", 1);
        JsonCard expectedControllerResponse = card.asJson(); // The controller transforms Card to JsonCard

        Mockito.when(unoService.getActiveCard(matchId)).thenReturn(card);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/playerhand/" + matchId )
                        .accept(MediaType.APPLICATION_JSON)) // Indicate that we expect JSON back
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedControllerResponse)))
                .andExpect(status().isOk());
        Mockito.verify(unoService).getActiveCard(eq(matchId));

    }
        @Test
    public void test11PlayerHandInvalidJsonCard() throws Exception { 
        UUID matchId = UUID.randomUUID();
        String player = "Alice";
        String InvalidJsonCardcard = "{ " +
                "\"color\": \"" + "red" + "\", " +
                "\"number\": \"" + 1 + "\", " +
                "\"type\": \"" + "NumberCard" + "\", " +
                "\"shout\": \"" + "false" + "\" " +
                "";

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/playerhand/" + matchId + "/" + player)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(card)
                )
                .andExpect(status().isBadRequest());

    }
}
