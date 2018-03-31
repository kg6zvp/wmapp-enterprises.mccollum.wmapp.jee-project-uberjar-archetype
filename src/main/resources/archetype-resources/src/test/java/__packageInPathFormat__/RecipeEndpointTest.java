package ${package};

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.archive.importer.MavenImporter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

import ${package}.model.Recipe;
import ${package}.model.RecipeManager;
import ${package}.model.IcingUser;
import ${package}.model.IcingUserManager;

@RunWith(Arquillian.class)
public class RecipeEndpointTest {
	private static final String RECIPE_ENDPOINT_PATH = "/api/recipe";

	/*
	 * Inject the recipe manager so we can create a Recipe in the database
	 */
	@Inject
	RecipeManager recipes;
	
	/*
	 * @ArquillianResource will inject the base url of the server deployment so we don't have to
	 * write a specific IP address, etc., etc. The reason we do this as a habit is so that if we
	 * decided to test on a remote server (which we can configure easily with Arquillian), we
	 * can keep all of our test code the same and just let Arquillian tell us where our test
	 * server is.
	 */
	@ArquillianResource
	URL url;

	@Deployment
	public static WebArchive deployment() {
		return ShrinkWrap
					.create(MavenImporter.class) //use the automatic deployment creator
					.loadPomFromFile("pom.xml") //use the pom from our project as a guide
					.importBuildOutput() //get the output of what our pom.xml file + project code would create
					.as(WebArchive.class); //package as a war instead of a jar
	}

	@Before
	public void runBeforeEachTest() {
	}

	@Test
	public void testShouldRejectUnauthenticatedRequests() {
		Long recipeId = recipes.persist(new Recipe()).getId();
		Response response = ClientBuilder.newClient().target(url.toExternalForm() + RECIPE_ENDPOINT_PATH + "/" + recipeId).request().get();
		assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), //you could use the value 401 directly, but this is more readable
						response.getStatusInfo().getStatusCode());
	}

	@After
	public void runAfterEachTest() {
		//cleanup Database
		recipes.removeAll(recipes.getAll());
	}
}

