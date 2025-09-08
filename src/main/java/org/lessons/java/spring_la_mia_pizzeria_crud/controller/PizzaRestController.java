package org.lessons.java.spring_la_mia_pizzeria_crud.controller;

import java.util.List;
import java.util.Optional;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaRestController {

    @Autowired
    private PizzaService service;

    // INDEX
    @GetMapping
    public List<Pizza> index(@RequestParam(name = "name", required = false) String name) {
        List<Pizza> pizzas;
        if (name != null) {
            pizzas = service.findByName(name);
            if (pizzas.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } else {
            pizzas = service.findAll();
        }
        return pizzas;
    }

    // SHOW
    @GetMapping("{id}")
    public ResponseEntity<Pizza> show(@PathVariable("id") Integer id) {
        Optional<Pizza> pizzaAttempt = service.findById(id);

        if (pizzaAttempt.isEmpty()) {
            return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Pizza>(pizzaAttempt.get(), HttpStatus.OK);
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Pizza> store(@Valid @RequestBody Pizza pizza) {
        return new ResponseEntity<Pizza>(service.create(pizza), HttpStatus.OK);
    }

    // UPDATE
    @PutMapping("{id}")
    public ResponseEntity<Pizza> update(@Valid @RequestBody Pizza pizza, @PathVariable("id") Integer id) {
        if (service.findById(id).isEmpty()) {
            return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
        }

        pizza.setId(id);
        return new ResponseEntity<Pizza>(service.update(pizza), HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("{id}")
    public ResponseEntity<Pizza> delete(@PathVariable("id") Integer id) {
        if (service.findById(id).isEmpty()) {
            return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
        }
        service.deleteById(id);
        return new ResponseEntity<Pizza>(HttpStatus.OK);
    }
}
