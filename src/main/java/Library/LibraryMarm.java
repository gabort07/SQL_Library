package Library;

import java.util.Scanner;

public class LibraryMarm {
    public static void main(String[] args) throws Exception {

        LibraryDataBase libraryDataBase = new LibraryDataBase();
        GUI gui = new GUI(libraryDataBase);
        gui.mainMenu();

    }
}