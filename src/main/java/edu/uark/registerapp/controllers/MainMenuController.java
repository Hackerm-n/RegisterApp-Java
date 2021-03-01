package edu.uark.registerapp.controllers;

import edu.uark.registerapp.commands.products.ProductsQuery;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/mainMenu")
public class MainMenuController {

    // Properties
    @Autowired
    private ProductsQuery productsQuery;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showMainMenu() {
        ModelAndView modelAndView =
                new ModelAndView(ViewNames.MAIN_MENU.getViewName());
        try {

            modelAndView.addObject("classification", 900);

        } catch (final Exception e) {
            modelAndView.addObject(
                    ViewModelNames.ERROR_MESSAGE.getValue(),
                    e.getMessage());
            modelAndView.addObject(
                    ViewModelNames.PRODUCTS.getValue(),
                    (new Product[0]));
        }

        return modelAndView;
    }
}


