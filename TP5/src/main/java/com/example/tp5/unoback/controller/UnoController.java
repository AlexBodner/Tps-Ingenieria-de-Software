package com.example.tp5.unoback.controller;

import com.example.tp5.unoback.Service.UnoService;
import com.example.tp5.unoback.model.Card;
import com.example.tp5.unoback.model.JsonCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class UnoController {
    @Autowired
    UnoService unoService;

    @GetMapping("/hola")
    public ResponseEntity<String> holaMundo() {
        return new ResponseEntity<>("respuesta a Hola Mundo", HttpStatus.OK);
    }

    @PostMapping("newmatch")
    public ResponseEntity newMatch(@RequestParam List<String> players) {
        return ResponseEntity.ok(unoService.newMatch(players));
    }

    @PostMapping("play/{matchId}/{player}")
    public ResponseEntity play(@PathVariable UUID matchId, @PathVariable String player, @RequestBody JsonCard card ) {
        unoService.playCard(matchId, player, card);
        return ResponseEntity.ok().build(); // No entiendo bien lo del .build()
    }

    @PostMapping("draw/{matchId}/{player}")
    public ResponseEntity drawCard( @PathVariable UUID matchId, @RequestParam String player ) {
        unoService.drawCard(matchId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("activecard/{matchId}")
    public ResponseEntity activeCard( @PathVariable UUID matchId ) {
        Card active = unoService.getActiveCard(matchId);
        return ResponseEntity.ok(active.asJson());
    }
}
