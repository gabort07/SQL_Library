package Library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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
        bookList = makeBooksListFromDB();
        visitorsList = getAlLlVisitors();
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
//  --- �j l�togat� felv�tele, �s a hozz�adas ellen�rz�se ---------------------------------

    public void addNewVisitor(String firstName, String lastName) throws SQLException {
        String countOfVisitors = "select count(visitorID) from visitor";
        PreparedStatement
        getAlLlVisitors();
        int nextID = visitorsList.size() + 1;
        addToDataBase(nextID, firstName, lastName);
        checkVisitorStatus(nextID, firstName, lastName);
    }

    private void addToDataBase(int nextID, String firstName, String lastName) throws SQLException {
        String addVisitor = "INSERT INTO visitor (visitorID, name, surname) VALUES (?, ?, ?)";
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

        int id = resultSet.getInt("count(visitorID)");

        if (id == nextID) {
            Visitor visitor = getSpecificVisitor(id);
            if (visitor.getFirstName().equals(firstName) && visitor.getLastName().equals(lastName)) {
                System.out.println("Az �j l�togat� " + nextID + " azonos�t�val, sikeresen az adatb�ziban van.");
            } else {
                System.out.println(nextID + " azonos�t�hoz tartoz� l�togat�t nem siker�l hozz�adni");
            }
        }
    }

    private Visitor getSpecificVisitor(int id) throws SQLException {
        String select = "select * from visitor where visitorID = ?";
        PreparedStatement prepStat = myConn.prepareStatement(select);
        prepStat.setInt(1, id);
        ResultSet resultSet = prepStat.executeQuery();
        String firstName = resultSet.getString("firstname");
        String lastName = resultSet.getString("lastname");

        return new Visitor(id, firstName,lastName);
    }

// ----------------------------------------------------


public ArrayList<Visitor> getAlLlVisitors()throws SQLException{
        ArrayList<Visitor> list=new ArrayList<>();
        PreparedStatement prepStat=myConn.prepareStatement("select visitorID, name, surname from visitor");
        ResultSet myResSet=prepStat.executeQuery();

        while(myResSet.next()){
        int id=myResSet.getInt("visitorID");
        String name=myResSet.getString("name");
        String surname=myResSet.getString("surname");
        visitorsList.add(new Visitor(id,name,surname));
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

public ArrayList<Book> makeBooksListFromDB()throws Exception{

        ArrayList<Book> list=new ArrayList<>();
        PreparedStatement prepStatBooks=myConn.prepareStatement("select books.ISBN, bookself.bookID, books.title, authors.name, authors.surname from books join authors on books.authorID = authors.authorID join bookself on books.ISBN = bookself.ISBN");
        ResultSet resultSet=prepStatBooks.executeQuery();

        while(resultSet.next()){
        int isbn=resultSet.getInt("ISBN");
        int id=resultSet.getInt("bookID");
        String title=resultSet.getString("title");
        String name=resultSet.getString("name");
        String surname=resultSet.getString("surname");
        bookList.add(new Book(isbn,id,title,name,surname));
        }

        return list;

        }

private Connection getConnection()throws SQLException{
        String url="jdbc:mysql://localhost:3306/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Properties properties=new Properties();
        properties.put("user","root");
        properties.put("password","nemtudom11");

        return DriverManager.getConnection(url,properties);

        }

        }
