package dev.controllers;

import dev.models.Book;
import dev.models.Person;
import dev.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/library")
public class PeopleController {

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService){
        this.peopleService=peopleService;
    }

    @GetMapping
    public String main(){
        return "main";
    }

    @GetMapping("/people")
    public String show(Model model){
        model.addAttribute("people",peopleService.findAll());

        return "people/show";
    }

    @GetMapping("/people/{id}")
    public String index(@PathVariable("id")int id,Model model){
        model.addAttribute("person",peopleService.findById(id));
        model.addAttribute("books",peopleService.personHaveBooks(id));

        return "people/index";
    }

    @GetMapping("/people/new")
    public String create(@ModelAttribute("person") Person person){

        return "people/create";
    }

    @PostMapping("/people/new")
    public String createPost(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "people/create";
        }
        peopleService.create(person);

        return "redirect:/library/people";
    }

    @GetMapping("/people/{id}/edit")
    public String edit (@PathVariable("id") int id, Model model){
        model.addAttribute("person",peopleService.findById(id));

        return "people/edit";
    }

    @PatchMapping("/people/{id}/edit")
    public String editPatch(@PathVariable("id") int id,@ModelAttribute @Valid Person person,BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "people/edit";
        }

        peopleService.update(id,person);

        return "redirect:/library/people/{id}";
    }

    @DeleteMapping("/people/{id}")
    public String delete(@PathVariable("id")int id){
        peopleService.delete(id);

        return "redirect:/library/people";
    }
}
