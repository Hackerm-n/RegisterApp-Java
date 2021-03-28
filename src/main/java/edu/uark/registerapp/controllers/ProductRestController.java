package edu.uark.registerapp.controllers;

import java.util.Optional;
import java.util.UUID;

import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.uark.registerapp.commands.products.ProductCreateCommand;
import edu.uark.registerapp.commands.products.ProductDeleteCommand;
import edu.uark.registerapp.commands.products.ProductUpdateCommand;
import edu.uark.registerapp.models.api.ApiResponse;
import edu.uark.registerapp.models.api.Product;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/product")
public class ProductRestController extends BaseRouteController{
	@RequestMapping(value = "/", method = RequestMethod.POST)

	public @ResponseBody ApiResponse createProduct(
		@RequestBody final Product product,
		final HttpServletRequest request
	) {
		final Optional<ActiveUserEntity> activeUserEntity =
				this.getCurrentUser(request);

		if(activeUserEntity.get().getClassification() < 501) {
			return new ApiResponse().setErrorMessage("You do not have management privileges");
		}

		return this.productCreateCommand
			.setApiProduct(product)
			.execute();
	}

	@RequestMapping(value = "/{productId}", method = RequestMethod.PUT)
	public @ResponseBody ApiResponse updateProduct(
		@PathVariable final UUID productId,
		@RequestBody final Product product,
		final HttpServletRequest request
	) {

		final Optional<ActiveUserEntity> activeUserEntity =
				this.getCurrentUser(request);

		if(activeUserEntity.get().getClassification() < 501) {
			return new ApiResponse().setErrorMessage("You do not have management privileges");
		}


		return this.productUpdateCommand
			.setProductId(productId)
			.setApiProduct(product)
			.execute();
	}

	@RequestMapping(value = "/{productId}", method = RequestMethod.DELETE)
	public @ResponseBody ApiResponse deleteProduct(
		@PathVariable final UUID productId,
		final HttpServletRequest request
	) {

		final Optional<ActiveUserEntity> activeUserEntity =
				this.getCurrentUser(request);

		if(activeUserEntity.get().getClassification() < 501) {
			return new ApiResponse().setErrorMessage("You do not have management privileges");
		}

		this.productDeleteCommand
			.setProductId(productId)
			.execute();

		return new ApiResponse();
	}

	// Properties
	@Autowired
	private ProductCreateCommand productCreateCommand;
	
	@Autowired
	private ProductDeleteCommand productDeleteCommand;
	
	@Autowired
	private ProductUpdateCommand productUpdateCommand;
}
