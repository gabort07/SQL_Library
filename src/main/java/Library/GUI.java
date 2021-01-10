package Library;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class GUI {

    LibraryOperator libraryOperator;

    public GUI(LibraryOperator libraryOperator) {
        this.libraryOperator = libraryOperator;
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
                        libraryOperator.addNewVisitor();
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
                    printBookList(libraryOperator.makeBooksListFromDB());
                    break;
                case 9:
                    exit = true;
            }
        }
    }

    private void printBookList(ArrayList<Book> list) {
        for (Book actual : list) {
            System.out.println(actual.toString());
        }
    }

    private int askBookId() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Új könyv felvétele az adatbázisba.");
        System.out.print("Könyv ID: ");
        return sc.nextInt();
    }

    private void addBook(int id) {
        try {
            if (libraryOperator.isBookInDatabase(id)) {
                System.out.println("Azonos ID- az adatbázisban.");
                System.out.println(libraryOperator.getSpecificBook(id).toString());
            } else {
                libraryOperator.addNewBook(libraryOperator.makeBookFromString(id, askBookData()));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private String[] askBookData() {
        Scanner sc = new Scanner(System.in);
        System.out.println("A könyv címe: ");
        String title = sc.next();
        System.out.println("Író vezetékneve: ");
        String familyName = sc.next();
        System.out.println("Író keresztneve: ");
        String name = sc.next();

        return new String[]{title, name, familyName};
    }


}


