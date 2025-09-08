package org.lessons.java.spring_la_mia_pizzeria_crud.controller;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.SpecialOffer;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/special-offers")
public class SpecialOfferController {

    @Autowired
    private SpecialOfferRepository repository;

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("specialOffer") SpecialOffer formSpecialOffer,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "special-offers/create-or-edit";
        }

        repository.save(formSpecialOffer);
        return "redirect:/pizzas/" + formSpecialOffer.getPizza().getId();
    }

    // MODIFICA DELLE OFFERTE SPECIALI, MI SERVONO:
    // METODO GET CHE RESTITUISCA UNA EDIT GIA POPOLATA DA COMPILARE
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("specialOffer", repository.findById(id).get());
        model.addAttribute("edit", true);
        return "special-offers/create-or-edit";
    }

    // METODO POST CHE EFFETTUI UNA UPDATE VERA E PROPRIA
    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("specialOffer") SpecialOffer formSpecialOffer,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "special-offers/create-or-edit";
        }
        repository.save(formSpecialOffer);
        return "redirect:/pizzas/" + formSpecialOffer.getPizza().getId();
    }

    // DELETE
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        SpecialOffer specialOffer = repository.findById(id).get();
        repository.delete(specialOffer);
        return "redirect:/pizzas/" + specialOffer.getPizza().getId();
    }
}
