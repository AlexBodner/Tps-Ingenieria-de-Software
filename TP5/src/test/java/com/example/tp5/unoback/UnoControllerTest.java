package com.example.tp4;
import com.example.tp4.model.JsonCard;
import com.example.tp4.model.NumberCard;
import com.example.tp4.service.UnoService;
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

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
        String errorMessage = "Player list cannot be empty";

        Mockito.when(unoService.newMatch(players))
                .thenThrow(new IllegalArgumentException("Player list cannot be empty"));
        mockMvc.perform( post( "/newmatch" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content(objectMapper.writeValueAsString(players)))
                .andDo( print() )
                .andExpect(status().isBadRequest());

    }

    @Test
    public void test03PlayInValidMatch() throws Exception { //de este no estoy nada seguro
        UUID matchId = UUID.randomUUID();
        String player = "Alice";
        JsonCard card = new NumberCard("RED", 1).asJson();
        Mockito.doNothing().when(unoService).playCard(matchId, player, card);

        ResponseEntity<Object> response = ResponseEntity.ok().build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/play/" + matchId + "/" + player)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(card))
                        )
                .andExpect(status().isOk());

        //Mockito.verify(unoService).play(eq(matchId), eq(player), eq(card));
    }
}
