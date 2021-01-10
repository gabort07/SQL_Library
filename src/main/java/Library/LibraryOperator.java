package Library;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

public class LibraryOperator {

    private final Connection myConn;
    private ArrayList<Visitor> visitorsList;

    public LibraryOperator() throws Exception {
        myConn = getConnection();
        visitorsList = new ArrayList<>();
        ArrayList<Author> authorsList = new ArrayList<>();
    }

    public ArrayList<Visitor> getVisitorsList() {
        return visitorsList;
    }


    //        ------ small tools ----------------------------------------------------------
    private void executeDBUpdate(PreparedStatement prepStat) {
        try {
            prepStat.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "nemtudom11");
        System.out.println("Connection ready");
        return DriverManager.getConnection(url, properties);

    }

//    --- Book operation with database -------------------------------------------------------------------

    public boolean isBookInDatabase(int bookID) {
        return retrieveISBNOfBookFromDB(bookID) == extractISBNFromID(bookID);
    }

    public int retrieveISBNOfBookFromDB(int bookID) {
        int i = 0;
        String sqlSelect = "select ISBN from bookself where bookID = ?";
        try {
            PreparedStatement prepStat = myConn.prepareStatement(sqlSelect);
            prepStat.setInt(1, bookID);
            ResultSet resultSet = prepStat.executeQuery();
            while (resultSet.next()) {
                i = resultSet.getInt("ISBN");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return i;
    }

    public int extractISBNFromID(int bookID) {
        return Integer.parseInt(String.valueOf(bookID).substring(0, 4));
    }

    public Book makeBookFromSpecificID(int id) throws SQLException {
        return makeBookFromResultSet(makeResultSetFromPrepStatement(prepareStatementWithBookID(id)));
    }

    private ResultSet makeResultSetFromPrepStatement(PreparedStatement prepStat) {
        ResultSet resultSet = null;
        try {
            resultSet = prepStat.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultSet;
    }

    private Book makeBookFromResultSet(ResultSet resultSet) {
        Book book = null;
        try {
            while (resultSet.next()) {
                int isbn = resultSet.getInt("ISBN");
                int bookID = resultSet.getInt("bookID");
                String title = resultSet.getString("title");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                book = new Book(isbn, bookID, title, firstName, lastName);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return book;
    }

    private PreparedStatement prepareStatementWithBookID(int bookID) {
        PreparedStatement prepStat = null;
        String sqlSelect = selectAllBookDataFromSQL() + " where bookID = ?";
        try {
            prepStat = myConn.prepareStatement(sqlSelect);
            prepStat.setInt(1, bookID);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return prepStat;
    }

    private PreparedStatement prepareStatementWithAuthorID(Book book, int authorID, String sqlSelect) {
        PreparedStatement prepStat = null;
        try {
            prepStat = myConn.prepareStatement(sqlSelect);
            prepStat.setInt(1, book.getISBN());
            prepStat.setString(2, book.getTitle());
            prepStat.setInt(3, authorID);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return prepStat;
    }

    public void addNewBook(Book book) {
        int nextAuthorID = countAuthorsInDB() + 1;
        addNewAuthor(book.getAuthorName(), book.getAuthorSurName(), nextAuthorID);
        String SQLSelect = "insert into (ISBN, title, authorID) values (?,?,?)";
        executeDBUpdate(prepareStatementWithAuthorID(book, nextAuthorID, SQLSelect));
        if (isBookInDatabase(book.getBookID())) {
            System.out.println("Az új könyv sikeresen hozzáadva az adatbázishoz.");
        }
    }

    public Book makeBookFromInputArgument(int id, String[] data) {
        int isbn =Integer.parseInt(String.valueOf(id).substring(0, 4));
        return new Book(isbn, id, data[0], data[1], data[2]);
    }

    private PreparedStatement prepareRegularBookStatement(String sqlSelect) {
        PreparedStatement prepStat = null;
        try {
            prepStat = myConn.prepareStatement(sqlSelect);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return prepStat;
    }

    private ResultSet makeResultSetOfBooksInDB(PreparedStatement prepStat) {
        ResultSet resultSet = null;
        try {
            resultSet = prepStat.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultSet;
    }

    private ArrayList<Book> extractBooksFromResultSetToList(ResultSet resultSet) {
        ArrayList<Book> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int isbn = resultSet.getInt("ISBN");
                int id = resultSet.getInt("bookID");
                String title = resultSet.getString("title");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                list.add(new Book(isbn, id, title, firstname, lastname));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    private String selectAllBookDataFromSQL() {
        return "select books.ISBN, bookself.bookID, books.title, authors.firstname, authors.lastname from books join bookself on books.ISBN = bookself.ISBN join authors on books.authorID = authors.authorID";
    }

    public ArrayList<Book> makeBooksListFromDB() {
        return extractBooksFromResultSetToList(makeResultSetOfBooksInDB(prepareRegularBookStatement(selectAllBookDataFromSQL())));
    }

//    --- Author operations ----------------------------------------------------------------------------

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

    private int countAuthorsInDB() {
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



}
