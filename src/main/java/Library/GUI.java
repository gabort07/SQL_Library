package Library;

import java.util.ArrayList;

public class GUI {
    public static void showBookList(ArrayList<Book> list ){
        for(Book actual : list){
            System.out.println(actual.toString());
        }
    }
}
