package com.example.tp5.unoback.Service;

import com.example.tp5.unoback.model.Card;
import com.example.tp5.unoback.model.JsonCard;
import com.example.tp5.unoback.model.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UnoService {
    @Autowired
    private Dealer dealer;
    private Map<UUID, Match> sessions = new HashMap<UUID, Match>();

    public UUID newMatch(List<String> players) {
        UUID newKey = UUID.randomUUID();
        sessions.put(newKey, Match.fullMatch(dealer.fullDeck(), players));
        return newKey;
    }

    public void playCard(UUID matchId, String player, JsonCard jsonCard) {
        Match match = getMatch(matchId);
        Card card = jsonCard.asCard();                   // convertimos de Json a card
        match.play(player, card);
    }

    public Match getMatch(UUID matchId) {
        Match match = sessions.get(matchId);
        if (match == null) {
            throw new IllegalArgumentException( "Match not found for given ID" );
        }
        return match;
    }

    public void drawCard(UUID matchId, String player) {
        Match match = getMatch(matchId);
        match.drawCard(player);
    }

    // devuelve la carta del tope del mazo
    public Card getActiveCard(UUID matchId) {
        Match match = getMatch(matchId);
        return match.activeCard();
    }

    // devuelve la mano del jugador que está en turno
    public List<JsonCard> getPlayerHand(UUID matchId) {
        Match match = getMatch(matchId);
         return match.playerHand()
                 .stream()
                 .map(Card::asJson)
                 .collect(Collectors.toList());
    }

    public boolean isGameOver(UUID matchId) {
        Match match = getMatch(matchId);
        return match.isOver();
    }
}
