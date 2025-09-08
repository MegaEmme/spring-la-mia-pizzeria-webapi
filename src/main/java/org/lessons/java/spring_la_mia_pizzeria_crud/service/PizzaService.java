package org.lessons.java.spring_la_mia_pizzeria_crud.service;

import java.util.List;
import java.util.Optional;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.model.SpecialOffer;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private SpecialOfferRepository offerRepository;

    // LISTA PIZZE
    public List<Pizza> findAll() {
        return pizzaRepository.findAll();
    }

    // FILTRO RICERCA PER NOME
    public List<Pizza> findByName(String name) {
        return pizzaRepository.findByNameContaining(name);
    }

    // PIZZA PER ID
    public Optional<Pizza> findById(Integer id) {
        return pizzaRepository.findById(id);
    }

    public Pizza getById(Integer id) {
        Optional<Pizza> pizzaAttempt = pizzaRepository.findById(id);
        return pizzaAttempt.get();
    }

    // QUI DIVIDO LA LOGICA DI CREATE E UPDATE, CHE IN BOOKCONTROLLER ERA UNICA
    public Pizza create(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    public Pizza update(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    // DELETE PURA
    public void delete(Pizza pizza) {
        for (SpecialOffer offerToDelete : pizza.getSpecialOffers()) {
            offerRepository.delete(offerToDelete);
        }
        pizzaRepository.delete(pizza);
    }

    // DELETE PER ID
    public void deleteById(Integer id) {
        Pizza pizza = getById(id);
        for (SpecialOffer offerToDelete : pizza.getSpecialOffers()) {
            offerRepository.delete(offerToDelete);
        }
        pizzaRepository.delete(pizza);
    }

    // METODI PER VERIFICA ESISTENZA PIZZA
    public Boolean existsById(Integer id) {
        return pizzaRepository.existsById(id);
    }

    public Boolean exists(Pizza pizza) {
        return existsById(pizza.getId());
    }

}
