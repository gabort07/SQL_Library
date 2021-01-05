package Library;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

public class LibraryDataBase {

    private Connection myConn;
    private ArrayList<Visitor> visitorsList;
    private HashMap<Integer, ArrayList<Book>> booksMap;
    private ArrayList<Author> authorsList;
    private HashMap<Integer, Author> authorsMap;

    public LibraryDataBase() throws Exception {
        myConn = getConnection();
        visitorsList = new ArrayList<>();
        booksMap = new HashMap<>();
        authorsList = new ArrayList<>();
        authorsMap = new HashMap<>();
    }

    public ArrayList<Visitor> getVisitorsList() {
        return visitorsList;
    }


    public HashMap<Integer, ArrayList<Book>> getBooksMap() {
        return booksMap;
    }


//    --- Book operation with database -------------------------------------------------------------------

    public void addBook() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Új könyv felvétele az adatbázisba.");
        System.out.print("Könyv ID: ");
        int id = sc.nextInt();
        if (bookAvailable(id)) {
            System.out.println("Azonos ID- az adatbázisban.");
            sc.close();
            System.out.println(getSpecificBook(id).toString());
        } else {
            addNewBook(askBookData(id));
        }
    }

    private void addNewBook(Book book) {
        int nextAuthorID = countAuthors() + 1;
        addNewAuthor(book.getAuthorName(), book.getAuthorSurName(), nextAuthorID);
        String insertBook = "insert into (ISBN, title, authorID) values (?,?,?)";
        try {
            PreparedStatement prepStat = myConn.prepareStatement(insertBook);
            prepStat.setInt(1, book.getISBN());
            prepStat.setString(2, book.getTitle());
            prepStat.setInt(3, nextAuthorID);
            prepStat.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (bookInDatabase(book.getBookID())) {
            System.out.println("Az új könyv sikeresen hozzáadva az adatbázishoz.");
        }
    }

    public void addNewAuthor(String firstName, String lastName, int id) {
        try {
            String insertAuthor = "insert into authors (firstname, lastname, authorID) values (?,?,?)";
            PreparedStatement prepStat = myConn.prepareStatement(insertAuthor);
            prepStat.setString(1, firstName);
            prepStat.setString(2, lastName);
            prepStat.setInt(3, id);
            prepStat.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Book askBookData(int id) {
        int isbn = Integer.parseInt(String.valueOf(id).substring(0, 4));
        Scanner sc = new Scanner(System.in);
        System.out.println("A könyv címe: ");
        String title = sc.next();
        System.out.println("Író vezetékneve: ");
        String familyName = sc.next();
        System.out.println("Író keresztneve: ");
        String name = sc.next();
        return new Book(isbn, id, title, name, familyName);
    }

    private boolean bookInDatabase(int bookID) {
        String select = "select ISBN from bookself where bookID = ?";
        int isbn = Integer.parseInt(String.valueOf(bookID).substring(0, 4));
        int i = 0;
        try {
            PreparedStatement prepStat = myConn.prepareStatement(select);
            prepStat.setInt(1, bookID);
            ResultSet resultSet = prepStat.executeQuery();
            i = resultSet.getInt("ISBN");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return i == isbn;
    }

    public boolean bookAvailable(int id) {
        String select = "select count(*) from bookself where bookID = ?";
        int count = 0;
        try {
            PreparedStatement prepStat = myConn.prepareStatement(select);
            prepStat.setInt(1, id);
            ResultSet resultSet = prepStat.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt("count(*)");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return count == 1;
    }

    private Book getSpecificBook(int id) throws SQLException {
        Book book = null;
        String select = "select bookself.bookID, books.ISBN, books.title, authors.firstname, authors.lastname from bookself join books on bookself.ISBN = books.ISBN join authors on books.authorID = authors.authorID where bookID = ?";
        PreparedStatement prepStat = myConn.prepareStatement(select);
        prepStat.setInt(1, id);
        ResultSet resultSet = prepStat.executeQuery();
        int isbn = 0;
        int bookID = 0;
        String title = null;
        String firstName = null;
        String lastName = null;
        try {
            while (resultSet.next()) {
                isbn = resultSet.getInt("ISBN");
                bookID = resultSet.getInt("bookID");
                title = resultSet.getString("title");
                firstName = resultSet.getString("firstname");
                lastName = resultSet.getString("lastname");
                book = new Book(isbn, bookID, title, firstName, lastName);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return book;
    }

    private int countAuthors() {
        int count = 0;
        try {
            String select = "select count(*) from authors";
            PreparedStatement prepStat = myConn.prepareStatement(select);
            ResultSet resultSet = prepStat.executeQuery();
            count = resultSet.getInt("count(*)");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return count;
    }


//  --- Visitor operations with database  ------------------------------------------------------------------

    public void addNewVisitor() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Új látogató felvétele a nyílvántartásba.");
        System.out.print("Vezetéknév: ");
        String lastName = sc.next();
        System.out.print("Név: ");
        String firstName = sc.next();
        int nextID = getNextVisitorID();
        sc.close();
        addToDataBase(nextID, firstName, lastName);
        checkVisitorStatus(nextID, firstName, lastName);
    }

    private int getNextVisitorID() throws SQLException {
        String countOfVisitors = "select count(visitorID) from visitor";
        PreparedStatement prepStat = myConn.prepareStatement(countOfVisitors);
        ResultSet resultSet = prepStat.executeQuery();
        int nextId = 0;
        while (resultSet.next()) {
            nextId = resultSet.getInt("count(visitorID)");
        }
        return nextId + 1;
    }

    private void addToDataBase(int nextID, String firstName, String lastName) throws SQLException {
        String addVisitor = "INSERT INTO visitor (visitorID, firstname, lastname) VALUES (?, ?, ?)";
        PreparedStatement prepStat = myConn.prepareStatement(addVisitor);
        prepStat.setInt(1, nextID);
        prepStat.setString(2, firstName);
        prepStat.setString(3, lastName);
        prepStat.executeUpdate();
    }

    private void checkVisitorStatus(int nextID, String firstName, String lastName) throws SQLException {
        String select = "select ? from visitor where ? = ?";
        PreparedStatement prepStat = myConn.prepareStatement(select);
        prepStat.setString(1, "visitorID");
        prepStat.setString(2, "visitorID");
        prepStat.setInt(3, nextID);
        ResultSet resultSet = prepStat.executeQuery();

        int id = getSpecificVisitor(nextID).getVisitorID();

        if (id == nextID) {
            Visitor visitor = getSpecificVisitor(id);
            if (visitor.getFirstName().equals(firstName) && visitor.getLastName().equals(lastName)) {
                System.out.println("Az új látogató " + nextID + " azonosítóval, sikeresen az adatbáziban van.");
            } else {
                System.out.println(nextID + " azonosítóhoz tartozó látogatót nem sikerül hozzáadni");
            }
        }
    }

    public ArrayList<Visitor> getAlLlVisitors() throws SQLException {
        ArrayList<Visitor> list = new ArrayList<>();
        PreparedStatement prepStat = myConn.prepareStatement("select * from visitor");
        ResultSet myResSet = prepStat.executeQuery();

        while (myResSet.next()) {
            int id = myResSet.getInt("visitorID");
            String name = myResSet.getString("firstname");
            String surname = myResSet.getString("lastname");
            visitorsList.add(new Visitor(id, name, surname));
        }
        return list;
    }

    private Visitor getSpecificVisitor(int id) throws SQLException {
        Visitor visitor = null;
        String select = "select * from visitor where visitorID = ?";
        PreparedStatement prepStat = myConn.prepareStatement(select);
        prepStat.setInt(1, id);
        ResultSet resultSet = prepStat.executeQuery();
        while (resultSet.next()) {
            String firstName = resultSet.getString("firstname");
            String lastName = resultSet.getString("lastname");
            visitor = new Visitor(id, firstName, lastName);
        }
        return visitor;
    }


// ---------------------------------------------------------------------------------------------------------------------


    public ArrayList<Book> makeBooksListFromDB() throws Exception {
        ArrayList<Book> list = new ArrayList<>();
        PreparedStatement prepStatBooks = myConn.prepareStatement("select books.ISBN, bookself.bookID, books.title, authors.firstname, authors.lastname from books join bookself on books.ISBN = bookself.ISBN join authors on books.authorID = authors.authorID");
        ResultSet resultSet = prepStatBooks.executeQuery();

        while (resultSet.next()) {
            int isbn = resultSet.getInt("ISBN");
            int id = resultSet.getInt("bookID");
            String title = resultSet.getString("title");
            String firstname = resultSet.getString("firstname");
            String lastname = resultSet.getString("lastname");
            list.add(new Book(isbn, id, title, firstname, lastname));
        }
        return list;

    }

//        ------ small tools ----------------------------------------------------------

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "nemtudom11");
        System.out.println("Connection ready");
        return DriverManager.getConnection(url, properties);

    }

}
