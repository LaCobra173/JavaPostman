package ru.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.example.dao.PersonDAO;
import ru.example.models.Person;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    @Autowired
    public PeopleController(PersonDAO personDAO){
        this.personDAO = personDAO;
    }
    @GetMapping()
    public List<Person> index(Model model) throws SQLException {
        List<Person> personList = personDAO.index();
        return personList;
    }
    @GetMapping("/{id}")
    public Person show(@PathVariable("id") int id, Model model) throws SQLException {
        Person person = personDAO.show(id);
        return person;
    }
    @PostMapping("/new")
    public String added(@RequestParam("name") @Valid String name, @RequestParam("age") @Valid int age) throws SQLException{
        personDAO.added(name, age);
        return "Пользователь добавлен";
    }
    @PatchMapping("/patch/{id}/{name}")
    public String patchPerson(@PathVariable("id") int id, @PathVariable("name") String name) throws SQLException{
        personDAO.patchPerson(id, name);
        return "Пользователь с id " + id + " обновлен";
    }
    @DeleteMapping("/delete/{id}")
    public String deletePerson(@PathVariable("id") int id) throws SQLException{
        personDAO.delete(id);
        return "Пользователь с id " + id + " удален";
    }
}
