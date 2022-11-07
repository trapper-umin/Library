package dev.controllers;

import dev.models.Book;
import dev.services.BooksService;
import dev.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/library")
public class BooksController {

    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService){
        this.booksService=booksService;
        this.peopleService = peopleService;
    }

    @GetMapping("/books")
    public String show(Model model){
        model.addAttribute("books",booksService.findAll());
        return "books/show";
    }

    @GetMapping(value = "/books",params = {"page","books_per_page"})
    public String show(@RequestParam("page") int page,@RequestParam("books_per_page") int booksPerPage,Model model){

        model.addAttribute("books",booksService.findAllWithPagination(page,booksPerPage));

        return "books/show";
    }

    @GetMapping(value = "/books",params = {"sort"})
    public String show(@RequestParam("sort") boolean sort,Model model){
        model.addAttribute("books",booksService.findAllWithSort(sort));
        return "books/show";
    }

    @GetMapping(value = "books",params = {"page","books_per_page","sort"})
    public String show(@RequestParam("page") int page,@RequestParam("books_per_page") int booksPerPage,
                       @RequestParam("sort") boolean sort, Model model){
        model.addAttribute("books",booksService.findAllWithPaginationAndSort(page,booksPerPage,sort));
        return "books/show";
    }

    @GetMapping("/books/search")
    public String search(Model model){
        model.addAttribute("search",new Book());
        return "books/search";
    }

    @GetMapping(value = "books/search",params = {"search"})
    public String search(@RequestParam("search") String search, Model model){
        model.addAttribute("search",new Book());
        model.addAttribute("books",booksService.search(search));

        System.out.println("Search request: "+search);

        return "books/search";
    }

    @GetMapping("/books/{id}")
    public String index(@PathVariable("id") int id,Model model){

        model.addAttribute("book",booksService.findById(id));
        model.addAttribute("owner", booksService.whoOwner(id));
        model.addAttribute("people",peopleService.findAll());

        return "books/index";
    }

    @PatchMapping("/books/{id}/select")
    public String select(@PathVariable("id") int id,@ModelAttribute("book") Book book){
        booksService.select(id,book);

        return "redirect:/library/books/{id}";
    }

    @PatchMapping("/books/{id}/absolve")
    public String absolve(@PathVariable("id") int id){
        booksService.absolve(id);

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

        booksService.create(book);

        return "redirect:/library/books";
    }

    @GetMapping("/books/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){

        model.addAttribute("book",booksService.findById(id));

        return "books/edit";
    }

    @PatchMapping("/books/{id}/edit")
    public String editPatch(@PathVariable("id") int id,@ModelAttribute("book") @Valid Book book,BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "books/edit";
        }

        booksService.update(id,book);

        return "redirect:/library/books/{id}";
    }

    @DeleteMapping("/books/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);

        return "redirect:/library/books";
    }

}
