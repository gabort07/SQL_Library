package Library;

import java.util.Scanner;

public class LibraryMarm {
    public static void main(String[] args) throws Exception {

        LibraryDataBase libraryDataBase = new LibraryDataBase();
//        booksList = libraryDataBase.getAllBooks();
        mainMenu(libraryDataBase);

    }

    public static void mainMenu(LibraryDataBase libraryDataBase) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("�dvozl�m a k�nyt�rban! Az al�bbi opci�kat v�laszthatja: ");
        System.out.println("�j l�togat� felv�tele ---> 1");
        System.out.println("�j k�nyv felv�tele a k�nyvt�rba --- >2");
        System.out.println("K�nyv t�rl�se a k�nyvt�rb�l --->3");
        System.out.println("Csoportos list�z�s ---> 4");
        System.out.println("Minden el�rhet� k�nyv list�z�sa: ---> 5");
        System.out.println("K�nyv lead�sa ---> 6");
        int select = sc.nextInt();

        switch (select){
            case 1:
                libraryDataBase.addNewVisitor();
                break;
            case 2:
                libraryDataBase.addBook();
                break;
//            case 3:
//                libraryDataBase.removeBook();
//                break;
            case 4:
//                libraryDataBase.showBooksByISBN();
                break;
            case 5:
                libraryDataBase.listAllBooks();

        }
    }


//    public void borrowBook(ArrayList<Book> booksList){
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Milyen k�nyvet szeretne kik�lcs�zni?");
//        boolean error = false;
//        String author = "";
//        do {
//            System.out.println("�r�: ");
//            author = sc.next();
//            for ()
//            }
//        }




}
