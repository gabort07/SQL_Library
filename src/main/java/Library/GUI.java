package Library;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class GUI {

    LibraryDataBase libraryDataBase;

    public GUI(LibraryDataBase libraryDataBase) {
        this.libraryDataBase = libraryDataBase;
    }

    public void mainMenu() {
        boolean exit = false;
        System.out.println("Üdvozlöm a könytárban! Az alábbi opciókat választhatja: ");
        while (!exit) {
            Scanner sc = new Scanner(System.in);
            System.out.println();
            System.out.println("Új látogató felvétele ---> 1");
            System.out.println("Új könyv felvétele a könyvtárba --- >2");
            System.out.println("Könyv törlése a könyvtárból --->3");
            System.out.println("Könyvtár tagok listája ---> 4");
            System.out.println("Minden elérhetõ könyv listázása: ---> 5");
            System.out.println("Könyv leadása ---> 6");
            System.out.println("Kilépsé ---> 9");
            int select = sc.nextInt();

            switch (select) {
                case 1:
                    try {
                        libraryDataBase.addNewVisitor();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    addBook(askBookId());
                    break;
                case 3:
//                libraryDataBase.removeBook();
                    break;
                case 4:
//                libraryDataBase.showBooksByISBN();
                    break;
                case 5:
                    printBookList(libraryDataBase.makeBooksListFromDB());
                    break;
                case 9:
                    exit = true;
            }
        }
    }

    public void printBookList(ArrayList<Book> list) {
        for (Book actual : list) {
            System.out.println(actual.toString());
        }
    }

    public int askBookId() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Új könyv felvétele az adatbázisba.");
        System.out.print("Könyv ID: ");
        return sc.nextInt();
    }

    public void addBook(int id) {
        try {
            if (libraryDataBase.isBookInDatabase(id)) {
                System.out.println("Azonos ID- az adatbázisban.");
                System.out.println(libraryDataBase.getSpecificBook(id).toString());
            } else {
                libraryDataBase.addNewBook(libraryDataBase.askBookData(id));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}


