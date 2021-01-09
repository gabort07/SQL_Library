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
        System.out.println("�j k�nyv felv�tele az adatb�zisba.");
        System.out.print("K�nyv ID: ");
        return sc.nextInt();
    }

    public void addBook(int id) {
        try {
            if (libraryDataBase.isBookInDatabase(id)) {
                System.out.println("Azonos ID- az adatb�zisban.");
                System.out.println(libraryDataBase.getSpecificBook(id).toString());
            } else {
                libraryDataBase.addNewBook(libraryDataBase.askBookData(id));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}


