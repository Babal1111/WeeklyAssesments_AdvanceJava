package com.example.LearningManagmentSystem.Services;

import com.example.LearningManagmentSystem.Model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class BookService {

    private List<Book> BookList = new ArrayList<>();

//    public void addBook(Book book){
//        BookList.add(book);
//        // assuming book with same id is nort present
//    }
    public void addBook(Book book) {
        if (getBookById(book.getId()) == null) {
            BookList.add(book);
        }
    }
    public List<Book> getAllBooks(){
        return this.BookList;
    }
    public Book getBookById(int id){

        for(Book book:BookList){
            if(book.getId()==id){
                return book;
            }
        }
        return null;
    }
    public void deleteBookById(int id) {
        BookList.removeIf(book -> book.getId() == id);
    }



}
