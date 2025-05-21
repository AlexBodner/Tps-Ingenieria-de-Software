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
    public void test02DosJugadores4Cartas(){
        JuegoUNO juego = juego2Jugadores4Cartas();
        assertEquals( 2, juego.getJugador("J1").cantidadCartas() );
        assertEquals( 2, juego.getJugador("J2").cantidadCartas() );
    }

    @Test
    public void test03DosJugadores4CartasTurnos(){
        JuegoUNO juego = juego2Jugadores4Cartas();
        assertEquals( "J1", juego.verJugadorTurno().getNombre() );
        juego.avanzarTurno();
        assertEquals( "J2", juego.verJugadorTurno().getNombre() );
        juego.avanzarTurno();
        assertEquals( "J1", juego.verJugadorTurno().getNombre() );
    }

    @Test
    public void test04CuatroJugadores8CartasReverse(){
        JuegoUNO juego = juego4Jugadores8Cartas();
        assertEquals( "J1", juego.verJugadorTurno().getNombre() );
        juego.avanzarTurno();
        assertEquals( "J2", juego.verJugadorTurno().getNombre() );
        juego.avanzarTurno();
        assertEquals( "J1", juego.reverse().verJugadorTurno().getNombre() );
    }

    @Test
    public void test05CuatroJugadores8CartasReverseReverse(){
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
    public void test06CuatroJugadores8CartasJugadaInvalida(){
        JuegoUNO juego = juego4Jugadores8Cartas();
        assertThrows( Exception.class, (Executable) () -> juego
                .jugar(juego.verJugadorTurno(), "rojo 4")
        );
    }

    @Test
    public void test07CuatroJugadores8CartasJugadaPozo(){
        JuegoUNO juego = juego4Jugadores8Cartas();
        assertEquals("rojo 1",
                juego.jugar(juego.verJugadorTurno(), "rojo 1")
                .getPozo().toString() );
    }

    @Test
    public void test08CuatroJugadores8CartasDosJugadas(){
        JuegoUNO juego = juego4Jugadores8Cartas();
        assertEquals("azul 1", juego
                .jugar(juego.verJugadorTurno(), "rojo 1")
                .jugar(juego.verJugadorTurno(), "azul 1")
                .getPozo().toString() );
    }

    @Test
    public void test09CuatroJugadores8CartasJugadaSkip(){
        JuegoUNO juego = juego4Jugadores8CartasSkipReverseWildcard();
        assertEquals("skip rojo", juego
                .jugar(juego.verJugadorTurno(), "rojo 1")
                .jugar(juego.verJugadorTurno(), "skip rojo")
                .getPozo().toString() );
    }

    @Test
    public void test09CuatroJugadores8CartasJugadaSkipVerTurno(){
        JuegoUNO juego = juego4Jugadores8CartasSkipReverseWildcard();
        assertEquals("J4", juego
                .jugar(juego.verJugadorTurno(), "rojo 1")
                .jugar(juego.verJugadorTurno(), "skip rojo")
                .verJugadorTurno().getNombre());
    }

    @Test
    public void test10CuatroJugadores8CartasJugadaReverseVerTurno(){
        JuegoUNO juego = juego4Jugadores8CartasSkipReverseWildcard();
        assertEquals("J2", juego
                .jugar(juego.verJugadorTurno(), "rojo 1") //J1
                .jugar(juego.verJugadorTurno(), "verde 1") //J2
                .jugar(juego.verJugadorTurno(), "reverse verde") //J3
                .verJugadorTurno().getNombre());
    }

    @Test
    public void test11CuatroJugadores8CartasWildCardVerTurno(){
        JuegoUNO juego = juego4Jugadores8CartasSkipReverseWildcard();
        assertEquals("J1", juego
                .jugar(juego.verJugadorTurno(), "rojo 1") //J1
                .jugar(juego.verJugadorTurno(), "verde 1") //J2
                .jugar(juego.verJugadorTurno(), "verde 3") //J3
                .jugarWildcard(juego.verJugadorTurno(), "wildcard", "amarillo")//J4
                .verJugadorTurno().getNombre());
    }

    @Test
    public void test12CuatroJugadores8CartasWildCardVerPozo(){
        JuegoUNO juego = juego4Jugadores8CartasSkipReverseWildcard();
        assertEquals("wildcard azul", juego
                .jugar(juego.verJugadorTurno(), "rojo 1") //J1
                .jugar(juego.verJugadorTurno(), "verde 1") //J2
                .jugar(juego.verJugadorTurno(), "verde 3") //J3
                .jugarWildcard(juego.verJugadorTurno(), "wildcard", "azul")//J4
                .getPozo().toString());
    }


    @Test
    public void test13CuatroJugadores11CartasDraw2(){
        JuegoUNO juego = juego4Jugadores8CartasSkipReverseWildcard();
        assertEquals(3, juego
                .jugar(juego.verJugadorTurno(), "rojo 1") //J1
                .jugar(juego.verJugadorTurno(), "verde 1") //J2
                .jugar(juego.verJugadorTurno(), "verde 3") //J3
                .jugar(juego.verJugadorTurno(), "+2 verde")//J4
                .getJugador("J1").cantidadCartas());
    }

    private static JuegoUNO juego2Jugadores4Cartas() {
        Jugador j1 = new Jugador("J1");
        Jugador j2 = new Jugador("J2");
        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(j1);
        jugadores.add(j2);

        ArrayList<Carta> mazo = new ArrayList<>();
        mazo.add(new CartaNumerica("rojo", 1));
        mazo.add(new CartaNumerica("rojo", 3));
        mazo.add(new CartaNumerica("azul", 3));
        mazo.add(new CartaNumerica("verde", 1));

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
        mazo.add(new WildcardNoJugada());                       // J4
        mazo.add(new Draw2("verde"));                     // J4
        mazo.add(new CartaNumerica("amarillo", 4));    // Mazo
        mazo.add(new CartaNumerica("rojo", 4));    // Mazo

        ArrayList<Integer> cantidadPorJugador = new ArrayList<>();
        cantidadPorJugador.add(2);
        cantidadPorJugador.add(2);
        cantidadPorJugador.add(2);
        cantidadPorJugador.add(3);

        return new JuegoUNO(jugadores, mazo, cantidadPorJugador);
    }
}
