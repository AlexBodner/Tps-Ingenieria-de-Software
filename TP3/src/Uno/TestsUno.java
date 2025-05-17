package Uno;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestsUno {
    @Test
    public void testNuevoUnoNoTienePozo() {
        assertEquals( 0, new JuegoUNO().tamañoPozo() );
    }
    public void jugarWildcardAceptaMismoColor() {
        WildcardNoJugada wildcard = new WildcardNoJugada();
        wildcard = wildcard.asignarColor("blue"); //Este error surge de lo q hablamos, necesitamos la jerarquia de 3 clases
        Carta azul = new CartaNumerica("azul",2);
        assertTrue(  wildcard.acepta(azul) );
    }

}
