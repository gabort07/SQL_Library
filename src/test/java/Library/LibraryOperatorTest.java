package Library;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LibraryOperatorTest {

    LibraryOperator libraryOperator;

    @Before
    public void setUp() throws Exception {
        libraryOperator = new LibraryOperator();
    }


    @Test
    public void shouldReturnTheFirstFourDigit(){
        assertEquals(1002,libraryOperator.extractISBNFromID(10023456));
    }

    @Test
    public void shouldReturnOneBook() {
        assertEquals(1001,libraryOperator.retrieveISBNOfBookFromDB(100101));
    }

}