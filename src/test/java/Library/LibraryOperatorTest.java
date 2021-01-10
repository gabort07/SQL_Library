package Library;
import org.junit.Before;

public class LibraryOperatorTest {

    LibraryOperator libraryOperator;

    @Before
    public void setUp() throws Exception {
        libraryOperator = new LibraryOperator();
    }

//    @Test
//    public void emptyVisitorList() throws SQLException {
//        libraryDataBase.getAlLlVisitors();
//        assertEquals(1,libraryDataBase.getVisitorsList().size());
//    }



//    @Test
//    public void oneElementInVisitorList() throws SQLException {
//        libraryDataBase.addNewVisitor("Laci", "Joe");
//        assertEquals(1,libraryDataBase.getVisitorsList().get(0).getVisitorID());
//        assertEquals(2, libraryDataBase.getVisitorsList().size());
//        assertEquals(libraryDataBase.getVisitorsList().size()+1, libraryDataBase.getVisitorsList().get(1).getVisitorID());
//    }
}