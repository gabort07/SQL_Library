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
        System.out.println("�dvozl�m a k�nyt�rban! Az al�bbi opci�kat v�laszthatja: ");
        while (!exit) {
            Scanner sc = new Scanner(System.in);
            System.out.println();
            System.out.println("�j l�togat� felv�tele ---> 1");
            System.out.println("�j k�nyv felv�tele a k�nyvt�rba --- >2");
            System.out.println("K�nyv t�rl�se a k�nyvt�rb�l --->3");
            System.out.println("K�nyvt�r tagok list�ja ---> 4");
            System.out.println("Minden el�rhet� k�nyv list�z�sa: ---> 5");
            System.out.println("K�nyv lead�sa ---> 6");
            System.out.println("Kil�ps� ---> 9");
            int select = sc.nextInt();

            switch (select) {
                case 1:
                    addVisitor();
                    break;
                case 2:
                    addBook();
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

    private void addBook() {
        System.out.println("�j k�nyv felv�tele az adatb�zisba.");
        int id = askBookId();
        try {
            if (libraryOperator.isBookInDatabase(id)) {
                System.out.println("Azonos ID- az adatb�zisban.");
                System.out.println(libraryOperator.makeBookFromSpecificID(id).toString());
            } else {
                libraryOperator.addNewBook(libraryOperator.makeBookFromInputArgument(id, askBookData()));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private int askBookId() {
        Scanner sc = new Scanner(System.in);

        System.out.print("K�nyv ID: ");
        return sc.nextInt();
    }

    private String[] askBookData() {
        Scanner sc = new Scanner(System.in);
        System.out.println("A k�nyv c�me: ");
        String title = sc.next();
        System.out.println("�r� vezet�kneve: ");
        String familyName = sc.next();
        System.out.println("�r� keresztneve: ");
        String name = sc.next();

        return new String[]{title, name, familyName};
    }

    private void addVisitor(){
        Scanner sc = new Scanner(System.in);
        System.out.println("�j l�togat� felv�tele a ny�lv�ntart�sba.");
        System.out.print("Vezet�kn�v: ");
        String lastName = sc.next();
        System.out.print("N�v: ");
        String firstName = sc.next();
        libraryOperator.addNewVisitor(firstName,lastName);
    }

}


