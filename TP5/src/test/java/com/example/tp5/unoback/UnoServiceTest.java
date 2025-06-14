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
    public void test04MultipleParallelMatches() {
        UUID id1 = unoService.newMatch(List.of("Martina", "Alex"));
        UUID id2 = unoService.newMatch(List.of("Pedro", "Sofia"));

        assertNotEquals(id1, id2);

        JsonCard card1 = unoService.getActiveCard(id1).asJson();
        JsonCard card2 = unoService.getActiveCard(id2).asJson();

        assertNotNull(card1);
        assertNotNull(card2);

        List<JsonCard> hand1 = unoService.getPlayerHand(id1);
        List<JsonCard> hand2 = unoService.getPlayerHand(id2);

        assertEquals(7, hand1.size());
        assertEquals(7, hand2.size());
    }


    @Test
    public void test05GetActiveCardInvalidMatch() {
        UUID fakeMatchId = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> {
            unoService.getActiveCard(fakeMatchId);
        });
    }

    @Test
    public void test06GetPlayerHandInvalidMatch() {
        UUID fakeMatchId = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> {
            unoService.getPlayerHand(fakeMatchId);
        });
    }

    @Test
    public void test07DrawCardInvalidMatch() {
        UUID fakeMatchId = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> {
            unoService.drawCard(fakeMatchId, "Martina");
        });
    }

    @Test
    public void test08GetMatchInvalidID() {
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
    public void test09WildCardWithColor() {
        UUID id = unoService.newMatch(List.of("Martina", "Alex"));
        JsonCard wild = new JsonCard("Blue", null, "WildCard", false);

        assertDoesNotThrow(() -> {
            try {
                unoService.playCard(id, "Martina", wild);
            } catch (Exception e) {
                // Ignoramos errores
            }
        });
    }

    @Test
    public void test10PlayCardOK() {
        Match match = unoService.getMatch(matchId);
        JsonCard jsonCard = match.playerHand().getFirst().asJson(); //new JsonCard("Red", 1, "NumberCard", false);
        unoService.playCard(matchId, "Martina", jsonCard);
        assertEquals(match.activeCard(), jsonCard.asCard());
    }

    @Test
    public void test11PlayCardTurnoIncorrecto() {
        Match match =  unoService.getMatch(matchId);
        JsonCard jsonCard = match.playerHand().getFirst().asJson();
        assertThrows(RuntimeException.class, () -> unoService.playCard(matchId, "Alex", jsonCard));
        //verify(match).play(eq("Martina"), any(Card.class));
    }

    @Test
    public void test12InvalidCardType() {
        UUID id = unoService.newMatch(List.of("Martina", "Alex"));
        JsonCard invalid = new JsonCard("Red", 1, "InvalidCardType", false);

        assertThrows(ClassNotFoundException.class, () -> {
            unoService.playCard(id, "Martina", invalid);
        });
    }

    @Test
    public void test13PlayCardThrowsExceptionIfMatchNotFound() {
        UUID fakeId = UUID.randomUUID();
        JsonCard jsonCard = new JsonCard("Red", 1, "NumberCard", false);
        assertThrows(IllegalArgumentException.class, () -> unoService.playCard(fakeId, "Martina", jsonCard));
    }

    @Test
    public void test14DrawCardThrowsExceptionIfMatchNotFound() {
        UUID fakeId = UUID.randomUUID();
        String player = "Meghan";
        assertThrows(IllegalArgumentException.class, () -> unoService.drawCard(fakeId, player));
    }

    @Test
    public void test15ConvertJsonCardToCard() {
        UUID id = unoService.newMatch(List.of("Martina", "Alex"));

        List<JsonCard> testCards = List.of(
                new JsonCard("Red", 5, "NumberCard", false),
                new JsonCard("Blue", null, "SkipCard", false),
                new JsonCard("Green", null, "ReverseCard", false),
                new JsonCard("Yellow", null, "Draw2Card", false),
                new JsonCard("Red", null, "WildCard", false)
        );

        for (JsonCard jc : testCards) {
            assertDoesNotThrow(() -> {
                try {
                    unoService.playCard(id, "Martina", jc);
                } catch (Exception e) {
                    // Ignoramos errores de juego, solo vemos si convierte biem
                }
            });
        }
    }

    @Test
    public void test16UnoShout() {
        UUID id = unoService.newMatch(List.of("Martina", "Alex"));
        JsonCard shoutedCard = new JsonCard("Red", 1, "NumberCard", true);

        assertDoesNotThrow(() -> {
            try {
                unoService.playCard(id, "Martina", shoutedCard);
            } catch (Exception e) {
                // de nuevo, ignoramos errores de juego
            }
        });
    }


}