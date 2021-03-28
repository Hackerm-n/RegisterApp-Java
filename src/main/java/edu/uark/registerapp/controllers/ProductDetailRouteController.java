package edu.uark.registerapp.controllers;

import java.util.Optional;
import java.util.UUID;

import edu.uark.registerapp.commands.products.ProductCreateCommand;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.products.ProductQuery;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Product;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/productDetail")
public class ProductDetailRouteController extends BaseRouteController{
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView start(final HttpServletRequest request) {
		ModelAndView modelAndView;

		final Optional<ActiveUserEntity> activeUserEntity =
				this.getCurrentUser(request);

		if(activeUserEntity.get().getClassification() >= 501) {
			modelAndView = new ModelAndView(ViewNames.PRODUCT_DETAIL.getViewName());
			modelAndView.addObject(
					ViewModelNames.IS_ELEVATED_USER.getValue(),
					true);
		}
		else {
			modelAndView = new ModelAndView(REDIRECT_PREPEND.concat(ViewNames.PRODUCT_LISTING.getRoute()));
		}

		modelAndView.addObject(ViewModelNames.PRODUCT.getValue(),
				(new Product()).setLookupCode(StringUtils.EMPTY).setCount(0));
		return modelAndView;
	}

	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
	public ModelAndView startWithProduct(@PathVariable final UUID productId) {
		final ModelAndView modelAndView =
			new ModelAndView(ViewNames.PRODUCT_DETAIL.getViewName());

		try {
			modelAndView.addObject(
				ViewModelNames.PRODUCT.getValue(),
				this.productQuery.setProductId(productId).execute());
		} catch (final Exception e) {
			modelAndView.addObject(
				ViewModelNames.ERROR_MESSAGE.getValue(),
				e.getMessage());
			modelAndView.addObject(
				ViewModelNames.PRODUCT.getValue(),
				(new Product())
					.setCount(0)
					.setLookupCode(StringUtils.EMPTY));
		}

		return modelAndView;
	}

	@RequestMapping(value = "/api/product/", method = RequestMethod.POST)
	public boolean saveProduct(@PathVariable final Product product) {
		try {
			productCreateCommand.setApiProduct(product).execute();
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	// Properties
	@Autowired
	private ProductQuery productQuery;

	@Autowired
	private ProductCreateCommand productCreateCommand;
}
