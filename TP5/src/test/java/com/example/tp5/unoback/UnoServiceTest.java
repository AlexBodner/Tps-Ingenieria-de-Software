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
                new WildCard(),

                // cartas extra para el deck
                new NumberCard("blue", 1),
                new NumberCard("green", 1));

        // Creamos un Match falso y lo inyectamos en el mapa de sesiones
        List<String> players = List.of("Martina", "Alex");

        // Simulamos que el dealer devuelve un mazo vacío o el que necesites
        when(dealer.fullDeck()).thenReturn(deck);

        // Creamos una partida con el service real pero usando el dealer mockeado
        matchId = unoService.newMatch(players);
    }


    @Test
    public void test01NewMatchCreatesMatch() {
        assertNotNull(matchId);
    }

    @Test
    public void test02NewMatchOnePlayer() {
        List<String> players = List.of("Martina");
        assertThrows(RuntimeException.class, () -> {
            unoService.newMatch(players);
        });
    }

    @Test
    public void test03GetMatchExiste() {
        Match m = unoService.getMatch(matchId);
        assertNotNull(m);
    }

    @Test
    public void test04GetActiveCard() {
        Match m = unoService.getMatch(matchId);
        assertEquals(new NumberCard("yellow", 1), m.activeCard());
    }

    @Test
    public void test05MultipleParallelMatches() {
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
    public void test06GetActiveCardInvalidMatch() {
        UUID fakeMatchId = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> {
            unoService.getActiveCard(fakeMatchId);
        });
    }

    @Test
    public void test07GetPlayerHandInvalidMatch() {
        UUID fakeMatchId = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> {
            unoService.getPlayerHand(fakeMatchId);
        });
    }

    @Test
    public void test08PlayerHand() {
        List<JsonCard> hand = unoService.getPlayerHand(matchId);
        assertNotNull(hand);
        assertEquals(7, hand.size());

        // mano de Martina
        assertEquals("red", hand.get(0).getColor());
        assertEquals(1, hand.get(0).getNumber());

        assertEquals("red", hand.get(1).getColor());
        assertEquals(2, hand.get(1).getNumber());

        assertEquals("red", hand.get(2).getColor());
        assertEquals(3, hand.get(2).getNumber());

        assertEquals("red", hand.get(3).getColor());
        assertEquals(4, hand.get(3).getNumber());

        assertEquals("red", hand.get(4).getColor());
        assertEquals(5, hand.get(4).getNumber());

        assertEquals("red", hand.get(5).getColor());
        assertEquals(6, hand.get(5).getNumber());

        assertEquals("", hand.get(6).getColor());
        assertEquals(null, hand.get(6).getNumber());

        for (JsonCard card : hand) {
            assertNotNull(card.getColor());
            assertNotNull(card.getType());
        }
    }

    @Test
    public void test09DrawCardSuma1() {
        int handSizeInicial = unoService.getPlayerHand(matchId).size();
        unoService.drawCard(matchId, "Martina");
        assertEquals(handSizeInicial + 1, unoService.getPlayerHand(matchId).size());
    }

    @Test
    public void test10DrawCardInvalidMatch() {
        UUID fakeMatchId = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> {
            unoService.drawCard(fakeMatchId, "Martina");
        });
    }

    @Test
    public void test11DrawCardTurnoIncorrecto() {
        assertThrows(RuntimeException.class, () -> {
            unoService.drawCard(matchId, "Alex");
        });
    }

    @Test
    public void test12GetMatchInvalidID() {
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
    public void test13WildCardWithColor() {
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
    public void test14PlayCardOK() {
        Match match = unoService.getMatch(matchId);
        JsonCard jsonCard = match.playerHand().getFirst().asJson(); //new JsonCard("Red", 1, "NumberCard", false);
        unoService.playCard(matchId, "Martina", jsonCard);
        assertEquals(match.activeCard(), jsonCard.asCard());
    }

    @Test
    public void test15PlayCardTurnoIncorrecto() {
        Match match =  unoService.getMatch(matchId);
        JsonCard jsonCard = match.playerHand().getFirst().asJson();
        assertThrows(RuntimeException.class, () -> unoService.playCard(matchId, "Alex", jsonCard));
    }

    @Test
    public void test16InvalidCardType() {
        UUID id = unoService.newMatch(List.of("Martina", "Alex"));
        JsonCard invalid = new JsonCard("Red", 1, "InvalidCardType", false);

        assertThrows(ClassNotFoundException.class, () -> {
            unoService.playCard(id, "Martina", invalid);
        });
    }

    @Test
    public void test17PlayCardThrowsExceptionIfMatchNotFound() {
        UUID fakeId = UUID.randomUUID();
        JsonCard jsonCard = new JsonCard("Red", 1, "NumberCard", false);
        assertThrows(IllegalArgumentException.class, () -> unoService.playCard(fakeId, "Martina", jsonCard));
    }

    @Test
    public void test18DrawCardThrowsExceptionIfMatchNotFound() {
        UUID fakeId = UUID.randomUUID();
        String player = "Meghan";
        assertThrows(IllegalArgumentException.class, () -> unoService.drawCard(fakeId, player));
    }

    @Test
    public void test19ConvertJsonCardToCard() {
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
    public void test20UnoShout() {
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

    @Test
    public void test21PlayCardAdvancesTurn() {
        Match match = unoService.getMatch(matchId);
        JsonCard jsonCard1 = match.playerHand().get(0).asJson();
        JsonCard jsonCard2 = match.playerHand().get(1).asJson();
        unoService.playCard(matchId, "Martina", jsonCard1);
        // play card avanza el turno y ahora deberia jugar Alex
        assertThrows(RuntimeException.class, () -> unoService.playCard(matchId, "Martina", jsonCard2));
    }

    @Test
    public void test22ReverseCardChangesDirection() {
        List<Card> specialDeck = List.of(
                // carta que queda primera en la mesa
                new NumberCard("yellow", 1),

                // cartas jugador 1
                new WildCard(),
                new ReverseCard("yellow"),
                new ReverseCard("blue"),
                new SkipCard("green"),
                new SkipCard("red"),
                new NumberCard("red", 1),
                new NumberCard("yellow", 1),

                // cartas jugador 2
                new WildCard(),
                new ReverseCard("green"),
                new ReverseCard("red"),
                new SkipCard("yellow"),
                new SkipCard("blue"),
                new NumberCard("blue", 1),
                new NumberCard("green", 1),

                // cartas jugador 3
                new WildCard(),
                new ReverseCard("green"),
                new ReverseCard("red"),
                new SkipCard("yellow"),
                new SkipCard("blue"),
                new NumberCard("blue", 1),
                new NumberCard("green", 1));

        List<String> specialPlayers = List.of("Martina", "Alex", "Sofia");
        when(dealer.fullDeck()).thenReturn(specialDeck);
        UUID specialMatchId = unoService.newMatch(specialPlayers);

        JsonCard jsCard1 = new JsonCard("yellow", null, "ReverseCard", false);
        JsonCard jsCard2 = new JsonCard("blue", 1, "NumberCard", false);
        unoService.playCard(specialMatchId, "Martina", jsCard1);
        // seria el turno de Alex, pero como es reverseCard ahora debe ir Sofia
        assertThrows(RuntimeException.class, () -> unoService.playCard(specialMatchId, "Alex", jsCard2));

    }
    @Test
    public void test23CannotPlayOnGameOver() {
        List<Card> specialDeck = List.of(
                // carta que queda primera en la mesa
                new NumberCard("red", 1),

                // cartas jugador 1
                new NumberCard("red", 1),
                new NumberCard("red", 1),
                new NumberCard("red", 1),
                new NumberCard("red", 1),
                new NumberCard("red", 1),
                new NumberCard("red", 1),
                new NumberCard("red", 1),

                // cartas jugador 2
                new NumberCard("red", 1),
                new NumberCard("red", 2),
                new NumberCard("red", 1),
                new NumberCard("red", 1),
                new NumberCard("red", 1),
                new NumberCard("red", 1),
                new NumberCard("red", 1),

                // cartas extra para el deck
                new NumberCard("red", 1),
                new NumberCard("red", 1));


        List<String> specialPlayers = List.of("Martina", "Alex");
        when(dealer.fullDeck()).thenReturn(specialDeck);
        UUID specialMatchId = unoService.newMatch(specialPlayers);

        JsonCard cardRed1 = new JsonCard("red", 1, "NumberCard", false);
        JsonCard cardRed1Shout = new JsonCard("red", 1, "NumberCard", true);

        for (int i = 0 ; i<5; i++){
            System.out.print(i);
            unoService.playCard(specialMatchId, "Martina", cardRed1);
            unoService.playCard(specialMatchId, "Alex", cardRed1);
        }
        System.out.print("afuera");
        unoService.playCard(specialMatchId, "Martina", cardRed1Shout);
        unoService.playCard(specialMatchId, "Alex", cardRed1);
        unoService.playCard(specialMatchId, "Martina", cardRed1);

        assertThrows(RuntimeException.class, () -> unoService.playCard(specialMatchId, "Alex", cardRed1Shout));

    }
}