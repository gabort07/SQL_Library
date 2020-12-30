package Library;
import org.junit.Before;
import org.junit.Test;


import java.sql.SQLException;

import static org.junit.Assert.*;

public class LibraryDataBaseTest {

    LibraryDataBase libraryDataBase;

    @Before
    public void setUp() throws Exception {
        libraryDataBase = new LibraryDataBase();
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