package Library;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class LibraryDataBase {

    private Connection myConn;
    private ArrayList<Visitor> visitorsList;
    private ArrayList<Book> bookList;
    private HashMap<Integer, ArrayList<Book>> booksMap;
    private ArrayList<Author> authorsList;
    private HashMap<Integer, Author> authorsMap;

    public LibraryDataBase() throws Exception {
        myConn = getConnection();
        System.out.println("Connection ready");
        bookList = new ArrayList<>();
        visitorsList =new ArrayList<>();
        booksMap = new HashMap<>();
        authorsList = new ArrayList<>();
        authorsMap = new HashMap<>();
    }

    public ArrayList<Visitor> getVisitorsList() {
        return visitorsList;
    }

    public ArrayList<Book> getBookList() {
        return bookList;
    }

    public HashMap<Integer, ArrayList<Book>> getBooksMap() {
        return booksMap;
    }

    public void listAllBooks() {
        for (Book actual : getBookList()) {
            System.out.println(actual.toString());
        }
    }

//    public void showBooksByISBN() {
//        booksByISBN();
//        for (Integer actual : booksMap.keySet()) {
//            System.out.println(booksMap.get(actual).get(0).toString() + " el�rhet� k�tet: " + booksMap.get(actual).size());
//        }
//    }

    public void addNewBook(int isbn) throws Exception {
        makeBooksListFromDB();
        for (int actual : booksMap.keySet()) {
            if (actual == isbn) {
//                String addBook = "INSERT INTO books ("
//                Book newBook = new Book()
            }
        }

    }
//    --- Add new book, and check it in the database -------------------------------------------------------------------



//    ------------------------------------------------------------------------------------------------------------------

//  --- Add new visitor, and check it in the database ------------------------------------------------------------------

    public void addNewVisitor(String firstName, String lastName) throws SQLException {
        int nextID = getNextVisitorID();
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
        return nextId+1;
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
        String select = "select count(visitorID) from visitor where visitorID = ?";
        PreparedStatement prepStat = myConn.prepareStatement(select);
        prepStat.setInt(1, nextID);
        ResultSet resultSet = prepStat.executeQuery();

        int id = getSpecificVisitor(nextID).getVisitorID();

        if (id == nextID) {
            Visitor visitor = getSpecificVisitor(id);
            if (visitor.getFirstName().equals(firstName) && visitor.getLastName().equals(lastName)) {
                System.out.println("Az �j l�togat� " + nextID + " azonos�t�val, sikeresen az adatb�ziban van.");
            } else {
                System.out.println(nextID + " azonos�t�hoz tartoz� l�togat�t nem siker�l hozz�adni");
            }
        }
    }

// ---------------------------------------------------------------------------------------------------------------------


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

//    public void authorsByID(){
//        for(Author actual: authorsList){
//            authorsMap.put()
//        }
//    }

//    public void booksByISBN() {
//        for (Book actual : bookList) {
//            booksMap.putIfAbsent(actual.getISBN(), new ArrayList<>());
//            booksMap.get(actual.getISBN()).add(actual);
//        }
//    }

    public ArrayList<Book> makeBooksListFromDB() throws Exception {

        ArrayList<Book> list = new ArrayList<>();
        PreparedStatement prepStatBooks = myConn.prepareStatement("select books.ISBN, bookself.bookID, books.title, authors.firstname, authors.lastname from books join authors on books.authorID = authors.authorID join bookself on books.ISBN = bookself.ISBN");
        ResultSet resultSet = prepStatBooks.executeQuery();

        while (resultSet.next()) {
            int isbn = resultSet.getInt("ISBN");
            int id = resultSet.getInt("bookID");
            String title = resultSet.getString("title");
            String firstname = resultSet.getString("firstname");
            String lastname = resultSet.getString("lastname");
            bookList.add(new Book(isbn, id, title, firstname, lastname));
        }

        return list;

    }

//        ------ small tools ----------------------------------------------------------

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

    private Book getSpecificBook(int id) throws SQLException {
        String select = "select bookself.bookID, books.ISBN, books.title, authors.firstname, authors.lastname from bookself join books on bookself.ISBN = books.ISBN join authors on books.authorID = authors.authorID where bookID = ?";
        PreparedStatement prepStat = myConn.prepareStatement(select);
        prepStat.setInt(1, id);
        ResultSet resultSet = prepStat.executeQuery();
        int isbn = resultSet.getInt("ISBN");
        int bookID = resultSet.getInt("bookID");
        String title = resultSet.getString("title");
        String firstName = resultSet.getString("firstname");
        String lastName = resultSet.getString("lastname");

        return new Book(isbn, bookID, title, firstName, lastName);
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "nemtudom11");

        return DriverManager.getConnection(url, properties);

    }

}
