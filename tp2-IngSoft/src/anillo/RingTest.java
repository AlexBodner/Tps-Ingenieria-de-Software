package anillo;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RingTest {
    @Test void test00NextOnEmpty() {
        assertThrows( Exception.class, () -> new Ring().next() );
    }

    @Test void test01CurrentOnEmpty() {
        assertThrows( Exception.class, () -> new Ring().current() );
    }

    @Test void test02CurrentAfterAdd() {
        assertEquals( "Hola" , new Ring().add( "Hola" ).current() );
    }

    @Test void test03NextAfterOneAdd() {
        assertEquals( "Hola" , new Ring().add( "Hola" ).next().current() );
    }

    @Test void test04CurrentAfterTwoAdds() { // este test me obliga a que add() mueva el current a lo que se agrego. No me quedo parado donde estaba
        assertEquals( 42, new Ring().add( "Hola" )
                                    .add( new Integer( 42 ) )
                                    .current() );
    }

    @Test void test05CurrentAndNextAfterTwoAdds() {
        assertEquals( "Hola", new Ring().add( "Hola" )
                                        .add( new Integer( 42 ) )
                                        .next()
                                        .current() );
    }

    @Test void test06CyclesWithTwoAdds() {
        assertEquals( 42, new Ring().add( "Hola" )
                                    .add( new Integer( 42 ) )
                                    .next()
                                    .next()
                                    .current() );
    }

    @Test void test07InsertsAfterTwoAdds() {
        assertEquals( LocalDate.now(), new Ring().add( "Hola" )
                                                 .add( new Integer( 42 ) )
                                                 .next()
                                                 .add( LocalDate.now() )
                                                 .current() );
    }

    @Test void test08NextOnInsertsAfterTwoAdds() {
        assertEquals( "Hola", new Ring().add( "Hola" )
                                        .add( new Integer( 42 ) )
                                        .next()
                                        .add( LocalDate.now() )
                                        .next()
                                        .current() );
    }

    @Test void test09CyclesOnTreeElements() {
        assertEquals( LocalDate.now() , new Ring().add( "Hola" )
                                                  .add( new Integer( 42 ) )
                                                  .add( LocalDate.now() )
                                                  .next()
                                                  .next()
                                                  .next()
                                                  .current() );
    }

    @Test void test10EmptyaRing() {
        Ring ring = new Ring().add( "Hola" ).remove();
        assertThrows( Exception.class, () -> ring.current() );
        assertThrows( Exception.class, () -> ring.next() );
    }

    @Test void test11RemoveCurrent() {
        assertEquals( "Hola", new Ring().add( "Hola" )
                                        .add( new Integer( 42 ) )
                                        .remove()
                                        .current() );
    }

    @Test void test12RemoveNext() {
        System.out.println( new Ring().add( "Hola" )
                                     .add( new Integer( 42 ) )
                                     .add( LocalDate.now() )
                                     .next()
                                     .remove()
                                     .current() );
        assertEquals( "Hola", new Ring().add( "Hola" )
                                        .add( new Integer( 42 ) )
                                        .add( LocalDate.now() )
                                        .next()
                                        .remove().current() );
    }

    @Test void test13RemoveLast() {
        assertEquals( LocalDate.now(), new Ring().add( "Hola" )
                                                 .add( new Integer( 42 ) )
                                                 .add( LocalDate.now() )
                                                 .next()
                                                 .next()
                                                 .remove() // elimina el 42
                                                 .current() ); // me tiene que devolver la fecha. Por lo que al eliminar paso al siguiente
    }
}
