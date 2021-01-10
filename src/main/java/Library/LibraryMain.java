package Library;

public class LibraryMain {
    public static void main(String[] args) throws Exception {

        LibraryOperator libraryOperator = new LibraryOperator();
        GUI gui = new GUI(libraryOperator);
        gui.mainMenu();

    }
}