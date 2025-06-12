package com.example.tp4;

import com.example.tp4.model.Match;
import com.example.tp4.service.Dealer;
import com.example.tp4.service.UnoService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import com.example.tp4.model.Card;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.mockito.Mockito;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
public class UnoServiceTest {

    @Autowired
    private UnoService unoService;

    @Autowired
    private Dealer dealer;

    private UUID matchId;

    @BeforeEach
    public void setup() {
        // Creamos un Match falso y lo inyectamos en el mapa de sesiones
        List<String> players = List.of("Martina", "Alex");

        // Simulamos que el dealer devuelve un mazo vacío o el que necesites
        //Mockito.when(dealer.fullDeck()).thenReturn(List.of(new NumberCard("Red", 1)));

        // Creamos una partida con el service real pero usando el dealer mockeado
        matchId = unoService.newMatch(players);
    }

    @Test
    public void test01NewMatchCreatesMatch() {
        UUID id = unoService.newMatch(List.of("Martina", "Alex"));
        assertNotNull(id);
    }
    @Test
    public void test02GetMatchExisten() {
        Match m = unoService.getMatch(matchId);
        assertNotNull(m);
    }
    @Test
    public void test03GetMatchInexisten() {
        UUID nonExistentId = UUID.randomUUID(); // Use a fresh random UUID
        // Make sure it's not the fixedMatchId in case it was used somewhere
        if (nonExistentId.equals(matchId)) {
            nonExistentId = UUID.randomUUID(); // Get a different one if collision
        }

        // Assert that calling getMatch with a non-existent ID throws ResponseStatusException
        UUID finalNonExistentId = nonExistentId;
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> unoService.getMatch(finalNonExistentId));

        // Assert the details of the thrown exception
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getReason().contains("Match not found: " + nonExistentId.toString()));
    }


    @Test
    public void test04PlayCardCorrecto() {
        Match match =  unoService.getMatch(matchId);
        JsonCard jsonCard = match.playerHand().getFirst().asJson(); //new JsonCard("Red", 5, "NumberCard", false);
        unoService.playCard(matchId, "Martina", jsonCard);
        assertEquals(match.activeCard(), jsonCard.asCard());
        //verify(match).play(eq("Martina"), any(Card.class));
    }
    @Test
    public void test05PlayCardTurnoIncorrecto() {
        Match match =  unoService.getMatch(matchId);
        JsonCard jsonCard = match.playerHand().getFirst().asJson();

        assertThrows(RuntimeException.class, () -> unoService.playCard(matchId, "Alex", jsonCard));
        //verify(match).play(eq("Martina"), any(Card.class));
    }

    @Test
    public void testPlayCardThrowsExceptionIfMatchNotFound() {
        UUID fakeId = UUID.randomUUID();
        JsonCard jsonCard = new JsonCard("Blue", 7, "NumberCard", false);

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> unoService.playCard(fakeId, "Martina", jsonCard)
        );

        assertEquals("404 NOT_FOUND \"Match not found: " + fakeId + "\"", ex.getMessage());
    }

    @Test
    public void testDrawCardDelegatesToMatch() {
        Match match = unoService.getMatch(matchId);

        unoService.drawCard(matchId);

        verify(match).draw();
    }

    @Test
    public void testDrawCardThrowsExceptionIfMatchNotFound() {
        UUID fakeId = UUID.randomUUID();

        assertThrows(ResponseStatusException.class, () -> unoService.drawCard(fakeId));
    }

}