#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import ${package}.model.Recipe;
import ${package}.model.RecipeManager;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class RecipeViewBean {
	@Inject
	RecipeManager recipeManager;

	/**
	 * Returns the {@link Recipe} with the id given in the url query parameter in the WebUI
	 * @return
	 */
	public Recipe getRecipe(){
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		return recipeManager.get(Long.valueOf(ec.getRequestParameterMap().get("id")));
	}
}
