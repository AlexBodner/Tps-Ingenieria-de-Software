package Uno;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestsUno {
    @Test
    public void test01NuevoUnoNoTienePozo() {
        assertEquals( "carta vacia", new JuegoUNO().getPozo().toString() );
    }

    @Test
    public void test02CantidadCartas(){
        JuegoUNO juego = juego2Jugadores4Cartas();
        assertEquals( 2, juego.getJugador("J1").cantidadCartas() );
        assertEquals( 2, juego.getJugador("J2").cantidadCartas() );
    }

    @Test
    public void test03Turnos(){
        JuegoUNO juego = juego2Jugadores4Cartas();
        assertEquals( "J1", juego.verJugadorTurno().getNombre() );
        juego.avanzarTurno();
        assertEquals( "J2", juego.verJugadorTurno().getNombre() );
        juego.avanzarTurno();
        assertEquals( "J1", juego.verJugadorTurno().getNombre() );
    }

    @Test
    public void test04Reverse(){
        JuegoUNO juego = juego4Jugadores8Cartas();
        assertEquals( "J1", juego.verJugadorTurno().getNombre() );
        juego.avanzarTurno();
        assertEquals( "J2", juego.verJugadorTurno().getNombre() );
        juego.avanzarTurno();
        assertEquals( "J1", juego.reverse().verJugadorTurno().getNombre() );
    }

    @Test
    public void test05ReverseReverse(){
        JuegoUNO juego = juego4Jugadores8Cartas();
        assertEquals( "J1", juego.verJugadorTurno().getNombre() );
        juego.avanzarTurno();
        assertEquals( "J2", juego.verJugadorTurno().getNombre() );
        juego.avanzarTurno();
        assertEquals( "J3", juego.verJugadorTurno().getNombre() );
        juego.avanzarTurno();
        assertEquals( "J2", juego.reverse().verJugadorTurno().getNombre() );
        juego.avanzarTurno();
        assertEquals( "J1", juego.verJugadorTurno().getNombre() );
        juego.avanzarTurno();
        assertEquals( "J4", juego.verJugadorTurno().getNombre() );
        juego.avanzarTurno();
        assertEquals( "J1", juego.reverse().verJugadorTurno().getNombre() );
    }

    @Test
    public void test06JugadaPozo(){
        JuegoUNO juego = juego4Jugadores8Cartas();
        assertEquals("rojo 1",
                juego.jugar(juego.verJugadorTurno().getNombre(), new CartaNumerica("rojo", 1))
                .getPozo().toString() );
    }

    @Test
    public void test07DosJugadas(){
        JuegoUNO juego = juego4Jugadores8Cartas();
        assertEquals("azul 1", juego
                .jugar(juego.verJugadorTurno().getNombre(), new CartaNumerica("rojo", 1))
                .jugar(juego.verJugadorTurno().getNombre(), new CartaNumerica("azul", 1))
                .getPozo().toString() );
    }

    @Test
    public void test08JugadaSkip(){
        JuegoUNO juego = juego4Jugadores8CartasSkipReverseWildcard();
        assertEquals("skip rojo", juego
                .jugar(juego.verJugadorTurno().getNombre(), new CartaNumerica("rojo", 1))
                .jugar(juego.verJugadorTurno().getNombre(), new Skip("rojo"))
                .getPozo().toString() );
    }

    @Test
    public void test09SkipVerTurno(){
        JuegoUNO juego = juego4Jugadores8CartasSkipReverseWildcard();
        assertEquals("J4", juego
                .jugar(juego.verJugadorTurno().getNombre(),  new CartaNumerica("rojo", 1))
                .jugar(juego.verJugadorTurno().getNombre(), new Skip("rojo"))
                .verJugadorTurno().getNombre());
    }

    @Test
    public void test10ReverseVerTurno(){
        JuegoUNO juego = juego4Jugadores8CartasSkipReverseWildcard();
        assertEquals("J2", juego
                .jugar(juego.verJugadorTurno().getNombre(),  new CartaNumerica("rojo", 1)) //J1
                .jugar(juego.verJugadorTurno().getNombre(),  new CartaNumerica("verde", 1)) //J2
                .jugar(juego.verJugadorTurno().getNombre(), new Reverse("verde")) //J3
                .verJugadorTurno().getNombre());
    }

    @Test
    public void test11WildCardVerTurno(){
        JuegoUNO juego = juego4Jugadores8CartasSkipReverseWildcard();
        assertEquals("J1", juego
                .jugar(juego.verJugadorTurno().getNombre(), new CartaNumerica("rojo", 1)) //J1
                .jugar(juego.verJugadorTurno().getNombre(), new CartaNumerica("verde", 1)) //J2
                .jugar(juego.verJugadorTurno().getNombre(), new CartaNumerica("verde", 3)) //J3
                .jugar(juego.verJugadorTurno().getNombre(), new Wildcard().as("verde"))//J4
                .verJugadorTurno().getNombre());
    }

    @Test
    public void test12WildCardVerPozo(){
        JuegoUNO juego = juego4Jugadores8CartasSkipReverseWildcard();
        assertEquals("wildcard", juego
                .jugar(juego.verJugadorTurno().getNombre(), new CartaNumerica("rojo", 1)) //J1
                .jugar(juego.verJugadorTurno().getNombre(), new CartaNumerica("verde", 1)) //J2
                .jugar(juego.verJugadorTurno().getNombre(), new CartaNumerica("verde", 3)) //J3
                .jugar(juego.verJugadorTurno().getNombre(), new Wildcard().as("azul"))//J4
                .getPozo().toString());
    }

    @Test
    public void test13Draw2(){
        JuegoUNO juego = juego4Jugadores8CartasSkipReverseWildcard();
        assertEquals(3, juego
                .jugar(juego.verJugadorTurno().getNombre(), new CartaNumerica("rojo", 1)) //J1
                .jugar(juego.verJugadorTurno().getNombre(), new CartaNumerica("verde", 1)) //J2
                .jugar(juego.verJugadorTurno().getNombre(), new CartaNumerica("verde", 3))
                .jugar(juego.verJugadorTurno().getNombre(), new Draw2("verde"))//J4
                .getJugador("J1").cantidadCartas());
    }

    @Test
    public void test14AutoCantaUNOBien(){
        JuegoUNO juego = juego2Jugadores4Cartas();
        assertEquals( 1, juego
                .jugar("J1", new CartaNumerica("rojo", 1).jugarAsUNO())
                .getJugador("J1").cantidadCartas());
    }

    @Test
    public void test15AutoCantaUNOMal(){
        JuegoUNO juego = juego4Jugadores8CartasSkipReverseWildcard();
        assertEquals( 4, juego
                .jugar("J1", new CartaNumerica("rojo", 1))
                .jugar("J2", new Skip("rojo"))
                .jugar("J4", new Wildcard().as("rojo").jugarAsUNO())
                .getJugador("J4").cantidadCartas());
    }

    @Test
    public void test16AutoCantaUNO1CartaLevanta2(){
        JuegoUNO juego = juego4Jugadores8CartasSkipReverseWildcard();
        assertEquals(2, juego
                .jugar(juego.verJugadorTurno().getNombre(), new CartaNumerica("rojo", 1)) //J1
                .jugar(juego.verJugadorTurno().getNombre(), new CartaNumerica("verde", 1)) //J2
                .jugar(juego.verJugadorTurno().getNombre(), new CartaNumerica("verde", 3)) //J3
                .jugar(juego.verJugadorTurno().getNombre(), new Wildcard().as("azul"))            //J4
                .jugar(juego.verJugadorTurno().getNombre(), new CartaNumerica("azul", 3).jugarAsUNO()) //J1
                .getJugador("J1").cantidadCartas());
    }

    @Test
    public void test17AutoCantaUNO1CartaNoTerminaJuego(){
        JuegoUNO juego = juego4Jugadores8CartasSkipReverseWildcard();
        assertFalse(juego
                .jugar(juego.verJugadorTurno().getNombre(), new CartaNumerica("rojo", 1)) //J1
                .jugar(juego.verJugadorTurno().getNombre(), new CartaNumerica("verde", 1)) //J2
                .jugar(juego.verJugadorTurno().getNombre(), new CartaNumerica("verde", 3)) //J3
                .jugar(juego.verJugadorTurno().getNombre(), new Wildcard().as("azul"))            //J4
                .jugar(juego.verJugadorTurno().getNombre(), new CartaNumerica("azul", 3).jugarAsUNO()) //J1
                .isJuegoTerminado());
    }

    @Test
    public void test18TerminoJuegoVerdadero(){
        JuegoUNO juego = juego2Jugadores4CartasParaTerminar();
        assertTrue(juego
                .jugar("J1", new CartaNumerica("rojo", 1))
                .jugar("J2", new CartaNumerica("verde", 1))
                .jugar("J1", new CartaNumerica("verde", 3))
                .isJuegoTerminado());
    }

    @Test
    public void test19JugadaInvalidaSkip(){
        JuegoUNO juego = juego2Jugadores4CartasParaTerminar();
        assertTrue(juego
                .jugar("J1", new CartaNumerica("rojo", 1))
                .jugar("J2", new CartaNumerica("verde", 1))
                .jugar("J1", new CartaNumerica("verde", 3))
                .isJuegoTerminado());
    }

    @Test
    public void test20JugadorNoPoseeCarta(){
        JuegoUNO juego = juego2Jugadores4CartasParaTerminar();
        assertThrows( AssertionError.class, () -> juego
                .jugar("J1", new CartaNumerica("rojo", 5)));
    }

    private static JuegoUNO juego2Jugadores4CartasParaTerminar() {
        Jugador j1 = new Jugador("J1");
        Jugador j2 = new Jugador("J2");
        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(j1);
        jugadores.add(j2);

        ArrayList<Carta> mazo = new ArrayList<>();
        mazo.add(new CartaNumerica("rojo", 1)); // J1
        mazo.add(new CartaNumerica("verde", 3)); // J1
        mazo.add(new CartaNumerica("azul", 3)); // J2
        mazo.add(new CartaNumerica("verde", 1));// J2

        ArrayList<Integer> cantidadPorJugador = new ArrayList<>();
        cantidadPorJugador.add(2);
        cantidadPorJugador.add(2);

        return new JuegoUNO(jugadores, mazo, cantidadPorJugador);
    }

    private static JuegoUNO juego2Jugadores4Cartas() {
        Jugador j1 = new Jugador("J1");
        Jugador j2 = new Jugador("J2");
        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(j1);
        jugadores.add(j2);

        ArrayList<Carta> mazo = new ArrayList<>();
        mazo.add(new CartaNumerica("rojo", 1)); // J1
        mazo.add(new CartaNumerica("rojo", 3)); // J1
        mazo.add(new CartaNumerica("azul", 3)); // J2
        mazo.add(new CartaNumerica("verde", 1));// J2

        mazo.add(new CartaNumerica("rojo", 2));
        mazo.add(new CartaNumerica("rojo", 4));
        mazo.add(new CartaNumerica("azul", 1));
        mazo.add(new CartaNumerica("verde", 2));
        mazo.add(new CartaNumerica("amarillo", 1));
        mazo.add(new CartaNumerica("amarillo", 3));
        mazo.add(new CartaNumerica("azul", 4));
        mazo.add(new CartaNumerica("verde", 3));

        ArrayList<Integer> cantidadPorJugador = new ArrayList<>();
        cantidadPorJugador.add(2);
        cantidadPorJugador.add(2);

        return new JuegoUNO(jugadores, mazo, cantidadPorJugador);
    }

    private static JuegoUNO juego4Jugadores8Cartas() {
        Jugador j1 = new Jugador("J1");
        Jugador j2 = new Jugador("J2");
        Jugador j3 = new Jugador("J3");
        Jugador j4 = new Jugador("J4");

        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(j1);
        jugadores.add(j2);
        jugadores.add(j3);
        jugadores.add(j4);

        ArrayList<Carta> mazo = new ArrayList<>();
        mazo.add(new CartaNumerica("rojo", 1));
        mazo.add(new CartaNumerica("azul", 3));
        mazo.add(new CartaNumerica("azul", 1));
        mazo.add(new CartaNumerica("verde", 1));
        mazo.add(new CartaNumerica("rojo", 4));
        mazo.add(new CartaNumerica("verde", 3));
        mazo.add(new CartaNumerica("amarillo", 3));
        mazo.add(new CartaNumerica("amarillo", 1));

        ArrayList<Integer> cantidadPorJugador = new ArrayList<>();
        cantidadPorJugador.add(2);
        cantidadPorJugador.add(2);
        cantidadPorJugador.add(2);
        cantidadPorJugador.add(2);

        return new JuegoUNO(jugadores, mazo, cantidadPorJugador);
    }

    private static JuegoUNO juego4Jugadores8CartasSkipReverseWildcard() {
        Jugador j1 = new Jugador("J1");
        Jugador j2 = new Jugador("J2");
        Jugador j3 = new Jugador("J3");
        Jugador j4 = new Jugador("J4");

        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(j1);
        jugadores.add(j2);
        jugadores.add(j3);
        jugadores.add(j4);

        ArrayList<Carta> mazo = new ArrayList<>();
        mazo.add(new CartaNumerica("rojo", 1));    // J1
        mazo.add(new CartaNumerica("azul", 3));    // J1
        mazo.add(new Skip("rojo"));                        // J2
        mazo.add(new CartaNumerica("verde", 1));   // J2
        mazo.add(new Reverse("verde"));                    // J3
        mazo.add(new CartaNumerica("verde", 3));   // J3
        mazo.add(new CartaNumerica("amarillo", 3));// J4
        mazo.add(new Wildcard());                       // J4
        mazo.add(new Draw2("verde"));                     // J4
        mazo.add(new CartaNumerica("amarillo", 4));    // Mazo
        mazo.add(new CartaNumerica("rojo", 4));    // Mazo

        mazo.add(new CartaNumerica("amarillo", 1));    // Mazo
        mazo.add(new CartaNumerica("rojo", 2));    // Mazo
        mazo.add(new CartaNumerica("amarillo", 2));    // Mazo
        mazo.add(new CartaNumerica("rojo", 3));    // Mazo
        mazo.add(new CartaNumerica("azul", 1));    // Mazo
        mazo.add(new CartaNumerica("verde", 4));    // Mazo
        mazo.add(new CartaNumerica("azul", 2));    // Mazo
        mazo.add(new CartaNumerica("verde", 2));    // Mazo
        mazo.add(new CartaNumerica("azul", 3));    // Mazo
        mazo.add(new CartaNumerica("rojo", 5));    // Mazo

        ArrayList<Integer> cantidadPorJugador = new ArrayList<>();
        cantidadPorJugador.add(2);
        cantidadPorJugador.add(2);
        cantidadPorJugador.add(2);
        cantidadPorJugador.add(3);

        return new JuegoUNO(jugadores, mazo, cantidadPorJugador);
    }
}
