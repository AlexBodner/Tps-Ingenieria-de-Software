package com.example.tp5.unoback.controller;

import com.example.tp5.unoback.Service.UnoService;
import com.example.tp5.unoback.model.Card;
import com.example.tp5.unoback.model.JsonCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;


@RestController
public class UnoController {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(RuntimeException.class) public ResponseEntity<String> handleRuntime(RuntimeException exception) {
        return ResponseEntity.badRequest().body( "Error: " + exception.getMessage());
    }
    @Autowired
    UnoService unoService;
    @GetMapping("/hola")
    public ResponseEntity<String> holaMundo() {
        return new ResponseEntity<>("Respuesta a Hola Mundo", HttpStatus.OK);
    }
    @PostMapping("newmatch") public ResponseEntity newMatch(@RequestParam List<String> players) {
        return ResponseEntity.ok(unoService.newMatch(players));
        //return  ResponseEntity.ok(UUID.randomUUID());
    }

    @PostMapping("play/{matchId}/{player}")
    public ResponseEntity<Void> play(@PathVariable UUID matchId, @PathVariable String player, @RequestBody JsonCard card ) {
        unoService.playCard(matchId, player, card);
        return ResponseEntity.ok().build(); // No entiendo bien lo del .build()
    }

    @PostMapping("draw/{matchId}/{player}")
    public ResponseEntity<Void> drawCard( @PathVariable UUID matchId, @PathVariable  String player ) {
        unoService.drawCard(matchId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("activecard/{matchId}")
    public ResponseEntity<JsonCard> activeCard( @PathVariable UUID matchId ) {
        Card active = unoService.getActiveCard(matchId);
        return ResponseEntity.ok(active.asJson());
    }

    @GetMapping("playerhand/{matchId}")
    public ResponseEntity<List<JsonCard>> playerHand( @PathVariable UUID matchId ) {
        List<JsonCard> hand = unoService.getPlayerHand(matchId);
        return ResponseEntity.ok(hand);
    }
}
