package Library;

import java.util.Scanner;

public class LibraryMarm {
    public static void main(String[] args) throws Exception {

        LibraryDataBase libraryDataBase = new LibraryDataBase();
//        booksList = libraryDataBase.getAllBooks();
        mainMenu(libraryDataBase);

    }

    public static void mainMenu(LibraryDataBase libraryDataBase) throws Exception {
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
                    libraryDataBase.addNewVisitor();
                    break;
                case 2:
                    libraryDataBase.addBook();
                    break;
                case 3:
//                libraryDataBase.removeBook();
                    break;
                case 4:
//                libraryDataBase.showBooksByISBN();
                    break;
                case 5:
                    GUI.showBookList(libraryDataBase.makeBooksListFromDB());
                    break;
                case 9:
                    exit = true;
            }
        }
    }


//    public void borrowBook(ArrayList<Book> booksList){
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Milyen könyvet szeretne kikölcsözni?");
//        boolean error = false;
//        String author = "";
//        do {
//            System.out.println("Író: ");
//            author = sc.next();
//            for ()
//            }
//        }


}
