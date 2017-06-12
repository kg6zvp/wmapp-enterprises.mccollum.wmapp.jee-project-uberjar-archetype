#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model;

import javax.ejb.Local;
import javax.ejb.Stateless;

import ${package}.jax.RecipeEndpoint;
import enterprises.mccollum.utils.genericentityejb.GenericPersistenceManager;

/**
 * This class grants simple database access to Recipes by simply injecting it (see {@link RecipeEndpoint})
 * 
 * For more on the parent class, see {@link GenericPersistenceManager}
 * 
 * @author smccollum
 */
@Local
@Stateless
public class RecipeManager extends GenericPersistenceManager<Recipe, Long> {
	public RecipeManager(){
		super(Recipe.class);
	}
}
