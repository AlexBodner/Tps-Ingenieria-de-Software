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
        assertEquals( "J1", juego.getJugadorTurno().getNombre() );
        assertEquals( "J2", juego.getJugadorTurno().getNombre() );
        assertEquals( "J1", juego.getJugadorTurno().getNombre() );
    }

    @Test
    public void test04CuatroJugadores8CartasReverse(){
        JuegoUNO juego = juego4Jugadores8Cartas();
        assertEquals( "J1", juego.getJugadorTurno().getNombre() );
        assertEquals( "J2", juego.getJugadorTurno().getNombre() );
        assertEquals( "J1", juego.reverse().getJugadorTurno().getNombre() );
    }

    @Test
    public void test05CuatroJugadores8CartasReverseReverse(){
        JuegoUNO juego = juego4Jugadores8Cartas();
        assertEquals( "J1", juego.getJugadorTurno().getNombre() );
        assertEquals( "J2", juego.getJugadorTurno().getNombre() );
        assertEquals( "J3", juego.getJugadorTurno().getNombre() );
        assertEquals( "J2", juego.reverse().getJugadorTurno().getNombre() );
        assertEquals( "J1", juego.getJugadorTurno().getNombre() );
        assertEquals( "J4", juego.getJugadorTurno().getNombre() );
        assertEquals( "J1", juego.reverse().getJugadorTurno().getNombre() );
    }

    @Test
    public void test06CuatroJugadores8CartasUnaJugadaPozo(){
        JuegoUNO juego = juego4Jugadores8Cartas();
        assertThrows( Exception.class, (Executable) () -> juego
                .jugar(juego.getJugadorTurno(), "rojo 4")
        );
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
}
