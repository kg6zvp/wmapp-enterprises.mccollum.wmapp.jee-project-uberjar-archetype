#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model;

import javax.ejb.Local;
import javax.ejb.Stateless;

import ${package}.jax.UserEndpoint;
import enterprises.mccollum.utils.genericentityejb.GenericPersistenceManager;

/**
 * This class grants simple database access to Icing users by simply injecting it (see {@link UserEndpoint})
 * 
 * For more on the parent class, see {@link GenericPersistenceManager}
 * 
 * @author smccollum
 */
@Local
@Stateless
public class IcingUserManager extends GenericPersistenceManager<IcingUser, Long> {
	public IcingUserManager(){
		super(IcingUser.class);
	}
}
