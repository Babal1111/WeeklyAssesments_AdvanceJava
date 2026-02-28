package com.example.LearningManagmentSystem.Controller;

import com.example.LearningManagmentSystem.Exception.BookNotFoundException;
import com.example.LearningManagmentSystem.Model.Book;
import com.example.LearningManagmentSystem.Services.BookService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("book")
    public Book bookModel() {
        return new Book();
    }

    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "allBooks";
    }

    @GetMapping("/add")
    public String showAddBookPage() {
        return "addBook";
    }

    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute("book") Book book,
                          BindingResult result,
                          Model model) {

        if (result.hasErrors()) {
            return "addBook";
        }

        bookService.addBook(book);
        return "redirect:/allBooks";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable int id) {
        bookService.deleteBookById(id);
        return "redirect:/allBooks";
    }

    @GetMapping("/{id}")
    public String viewBook(@PathVariable int id, Model model) {

        Book book = bookService.getBookById(id);

        if (book == null) {
            throw new BookNotFoundException("Book not found with id: " + id);        }

        model.addAttribute("book", book);
        return "allBooks";
    }
}