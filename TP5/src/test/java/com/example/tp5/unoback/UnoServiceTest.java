package com.example.tp5.unoback;

import com.example.tp5.unoback.model.*;
import com.example.tp5.unoback.Service.Dealer;
import com.example.tp5.unoback.Service.UnoService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

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

    @MockBean
    private Dealer dealer;

    private UUID matchId;

    @BeforeEach
    public void setup() {
        List<Card> deck = List.of(
                // carta que queda primera en la mesa
                new NumberCard("yellow", 1),

                // cartas jugador 1
                new NumberCard("red", 1),
                new NumberCard("red", 2),
                new NumberCard("red", 3),
                new NumberCard("red", 4),
                new NumberCard("red", 5),
                new NumberCard("red", 6),
                new WildCard(),

                // cartas jugador 2
                new NumberCard("blue", 1),
                new NumberCard("blue", 2),
                new NumberCard("blue", 3),
                new NumberCard("blue", 4),
                new NumberCard("blue", 5),
                new NumberCard("blue", 6),
                new WildCard());

        // Creamos un Match falso y lo inyectamos en el mapa de sesiones
        List<String> players = List.of("Martina", "Alex");

        // Simulamos que el dealer devuelve un mazo vacío o el que necesites
        when(dealer.fullDeck()).thenReturn(deck);

        // Creamos una partida con el service real pero usando el dealer mockeado
        matchId = unoService.newMatch(players);
    }

    @Test
    public void test01NewMatchCreatesMatch() {
        UUID id = unoService.newMatch(List.of("Martina", "Alex"));
        assertNotNull(id);
    }

    @Test
    public void test02GetMatchExiste() {
        Match m = unoService.getMatch(matchId);
        assertNotNull(m);
    }

    @Test
    public void test03GetActiveCard() {
        Match m = unoService.getMatch(matchId);
        assertEquals(new NumberCard("yellow", 1), m.activeCard());
    }

    @Test
    public void test04GetActiveCardInvalidMatch() {
        UUID fakeMatchId = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> {
            unoService.getActiveCard(fakeMatchId);
        });
    }

    @Test
    public void test05GetPlayerHandInvalidMatch() {
        UUID fakeMatchId = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> {
            unoService.getPlayerHand(fakeMatchId);
        });
    }

    @Test
    public void test06GetMatchInvalidID() {
        UUID nonExistentId = UUID.randomUUID(); // Use a fresh random UUID
        // Make sure it's not the fixedMatchId in case it was used somewhere
        if (nonExistentId.equals(matchId)) {
            nonExistentId = UUID.randomUUID(); // Get a different one if collision
        }

        // Assert that calling getMatch with a non-existent ID throws ResponseStatusException
        UUID finalNonExistentId = nonExistentId;
        assertThrows(IllegalArgumentException.class, () -> unoService.getMatch(finalNonExistentId));
    }

    @Test
    public void test07PlayCardOK() {
        Match match = unoService.getMatch(matchId);
        JsonCard jsonCard = match.playerHand().getFirst().asJson(); //new JsonCard("Red", 1, "NumberCard", false);
        unoService.playCard(matchId, "Martina", jsonCard);
        assertEquals(match.activeCard(), jsonCard.asCard());
    }

    @Test
    public void test08PlayCardTurnoIncorrecto() {
        Match match =  unoService.getMatch(matchId);
        JsonCard jsonCard = match.playerHand().getFirst().asJson();
        assertThrows(RuntimeException.class, () -> unoService.playCard(matchId, "Alex", jsonCard));
        //verify(match).play(eq("Martina"), any(Card.class));
    }

    @Test
    public void test09PlayCardThrowsExceptionIfMatchNotFound() {
        UUID fakeId = UUID.randomUUID();
        JsonCard jsonCard = new JsonCard("Red", 1, "NumberCard", false);
        assertThrows(IllegalArgumentException.class, () -> unoService.playCard(fakeId, "Martina", jsonCard));
    }

    @Test
    public void test10DrawCardThrowsExceptionIfMatchNotFound() {
        UUID fakeId = UUID.randomUUID();
        String player = "Meghan";
        assertThrows(IllegalArgumentException.class, () -> unoService.drawCard(fakeId, player));
    }

}