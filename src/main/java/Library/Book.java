package Library;

public class Book {
    private int isbn;
    private int bookID;
    private String title;
    private String authorName;
    private String authorSurName;


    public Book(int isbn, int bookId, String title, String authorName, String authorSurName) {
        this.isbn = isbn;
        this.bookID = bookId;
        this.title = title;
        this.authorName = authorName;
        this.authorSurName = authorSurName;
    }

    public int getISBN() {
        return isbn;
    }

    public void setISBN(int isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorSurName() {
        return authorSurName;
    }

    public void setAuthorSurName(String authorSurName) {
        this.authorSurName = authorSurName;
    }

    public int getBookID() {
        return bookID;
    }

    @Override
    public String toString() {
        return "Book: " +
                " ISBN: " + isbn +
                " ID: " +bookID+
                ", title: '" + title + '\'' +
                ", authorName: '" + authorName + '\'' +
                ", authorSurName: '" + authorSurName + '\'';
    }
}
