package si.merljak.magistrska.server;

import static si.merljak.magistrska.server.model.QUser.user;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.merljak.magistrska.common.dto.QUserDto;
import si.merljak.magistrska.common.dto.UserDto;
import si.merljak.magistrska.common.rpc.UserService;
import si.merljak.magistrska.server.model.User;
import si.merljak.magistrska.server.utils.PasswordEncryptionUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mysema.query.jpa.impl.JPAQuery;

public class UserServiceImpl extends RemoteServiceServlet implements UserService {

	private static final long serialVersionUID = -1972686679692469943L;

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	@Resource
	private UserTransaction utx; 

	@Override
	public void register(String username, String password, String name, String email){
		log.debug("registering new user: " + username);

		JPAQuery query = new JPAQuery(em).from(user).where(user.username.eq(username));
		if (query.exists()) {
			// user already exists, throw exception
			throw new RuntimeException("User already exists!");
			// TODO custom exception/messages?
		}

		try {
			// generate salt and encrypt password
			byte[] salt = PasswordEncryptionUtils.generateSalt();
			byte[] encryptedPassword = PasswordEncryptionUtils.getEncryptedPassword(password, salt);
			System.out.println("salt:" + salt);
			System.out.println("encryptedPassword:" + encryptedPassword);

			// persist user entity
			utx.begin();
			em.persist(new User(username, ArrayUtils.toObject(encryptedPassword), ArrayUtils.toObject(salt), name, email));
			utx.commit();
			// TODO save logged in user?
		} catch (Exception e) {
			// TODO proper handling
			log.error("Could not register user!", e);
			throw new RuntimeException("Could not register user!");
		}
	}

	@Override
	public UserDto login(String username, String password) {
		log.debug("logging in user: " + username);

		JPAQuery query = new JPAQuery(em).from(user).where(user.username.eq(username));
		if (!query.exists()) {
			// user does not exists
			return null;
		}

		try {
			byte[] salt = ArrayUtils.toPrimitive(query.uniqueResult(user.salt));
			byte[] encryptedPassword = PasswordEncryptionUtils.getEncryptedPassword(password, salt);
					
			return query
					.where(user.password.eq(ArrayUtils.toObject(encryptedPassword)))
					.uniqueResult(new QUserDto(user.username, user.name, user.email, user.metadata));
		} catch (Exception e) {
			log.error("Could not log in!", e);
			throw new RuntimeException("Could not log in!");
		}
	}

	@Override
	public void bookmarkRecipe(long recipeId, boolean add) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bookmarkTechnique(long techniqueId, boolean add) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commentRecipe(long recipeId, String content) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commentTechnique(long techniqueId, String content) {
		// TODO Auto-generated method stub
		
	}
	

}
