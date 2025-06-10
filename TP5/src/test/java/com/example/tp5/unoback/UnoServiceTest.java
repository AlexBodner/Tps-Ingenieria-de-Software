package com.example.tp4;

import com.example.tp4.service.UnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UnoServiceTest {
    @Autowired
    private UnoService unoService;

    @Test
    public void newMatchTest(){
        UUID id = unoService.newMatch(List.of("Martina", "Alex"));
        assertNotNull(id);
    }
}
