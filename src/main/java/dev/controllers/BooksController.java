package dev.controllers;

import dev.dao.BookDAO;
import dev.dao.LibraryService;
import dev.dao.PersonDAO;
import dev.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/library")
public class BooksController {

    private final BookDAO bookDAO;
    private final LibraryService libraryService;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, LibraryService libraryService,PersonDAO personDAO){
        this.bookDAO=bookDAO;
        this.libraryService=libraryService;
        this.personDAO=personDAO;
    }

    @GetMapping("/books")
    public String show(Model model){
        model.addAttribute("books",bookDAO.show());

        return "books/show";
    }

    @GetMapping("/books/{id}")
    public String index(@PathVariable("id") int id,Model model){
        model.addAttribute("book",bookDAO.index(id));

        if(libraryService.thisBookHaveOwner(id)){
            model.addAttribute("owner",libraryService.whoOwner(id));
            model.addAttribute("boolean",true);
        }else{
            model.addAttribute("people",personDAO.show());
            model.addAttribute("boolean",false);
        }

        return "books/index";
    }

    @PatchMapping("/books/{id}")
    public String select(@PathVariable("id") int id,@ModelAttribute("book") Book book){
        libraryService.select(id,book);

        return "redirect:/library/books/{id}";
    }

    @PatchMapping("/books/{id}/absolve")
    public String absolve(@PathVariable("id") int id){
        libraryService.absolve(id);

        return "redirect:/library/books/{id}";
    }

    @GetMapping("/books/new")
    public String create(@ModelAttribute("book")Book book){

        return "books/create";
    }

    @PostMapping("/books/new")
    public String createPost(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "books/create";
        }

        bookDAO.create(book);

        return "redirect:/library/books";
    }

    @GetMapping("/books/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){

        model.addAttribute("book",bookDAO.index(id));

        return "books/edit";
    }

    @PatchMapping("/books/{id}/edit")
    public String editPatch(@PathVariable("id") int id,@ModelAttribute("book") @Valid Book book,BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "books/edit";
        }

        bookDAO.update(id, book);

        return "redirect:/library/books/{id}";
    }

    @DeleteMapping("/books/{id}")
    public String delete(@PathVariable("id") int id){
        bookDAO.delete(id);

        return "redirect:/library/books";
    }

}
