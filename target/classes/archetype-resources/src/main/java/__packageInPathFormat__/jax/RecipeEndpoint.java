#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.jax;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import ${package}.model.IcingUser;
import ${package}.model.IcingUserManager;
import ${package}.model.Recipe;
import ${package}.model.RecipeManager;
import enterprises.mccollum.wmapp.ssauthclient.WMPrincipal;

@Path("recipe")
@Consumes(MediaType.APPLICATION_JSON) //tells Jax-RS to always interpret the incoming post bodies as json
@Produces(MediaType.APPLICATION_JSON) //tells Jax-RS to always turn what we return into json
public class RecipeEndpoint {
	@Inject
	IcingUserManager users;
	
	@Inject
	RecipeManager recipes;
	
	@Context
	SecurityContext seCtx;
	
	@GET
	@Path("{recipeId}")
	public Response getRecipe(@PathParam("recipeId")Long recipeId){
		WMPrincipal principal = getPrincipal();
		if(principal == null)
			return Response.status(Status.UNAUTHORIZED).build();
		return Response.ok(recipes.get(recipeId)).build();
	}
	
	/**
	 * Add a recipe to the database
	 * 
	 * @param recipe: Recipe class instance gets deserialized from json, xml or whatever automatically for us
	 * @return
	 */
	@POST //use this to indicate that the base url of this class (/api/recipe) will accept post requests with this method
	@Transactional //set this to indicate we want to do multiple database operations while in this function
	public Response addRecipe(Recipe recipe){
		WMPrincipal principal = getPrincipal();
		if(principal == null)
			return Response.status(Status.UNAUTHORIZED).build();
		recipe.setId(null); //we want our db to generate an id for us, so set it null just in case the user set it
		recipes.persist(recipe);
		IcingUser user = users.get(principal.getToken().getStudentID());
		user.getRecipes().add(recipe);
		users.save(user);
		recipe.setAuthor(null); //to avoid infinite recursion on serialization into json (Google circular references in json)
		return Response.ok(recipe).build();
	}
	
	private WMPrincipal getPrincipal(){
		if(seCtx.getUserPrincipal() != null && seCtx.getUserPrincipal() instanceof WMPrincipal)
			return (WMPrincipal)seCtx.getUserPrincipal();
		return null;
	}
}
