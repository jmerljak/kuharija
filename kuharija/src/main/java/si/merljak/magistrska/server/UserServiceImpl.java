package si.merljak.magistrska.server;

import static si.merljak.magistrska.server.model.QSession.session;
import static si.merljak.magistrska.server.model.QUser.user;

import java.util.Date;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.merljak.magistrska.common.dto.QUserDto;
import si.merljak.magistrska.common.dto.SessionDto;
import si.merljak.magistrska.common.dto.UserDto;
import si.merljak.magistrska.common.rpc.UserService;
import si.merljak.magistrska.server.model.Comment;
import si.merljak.magistrska.server.model.Recipe;
import si.merljak.magistrska.server.model.Session;
import si.merljak.magistrska.server.model.User;
import si.merljak.magistrska.server.utils.PasswordEncryptionUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;

public class UserServiceImpl extends RemoteServiceServlet implements UserService {

	private static final long serialVersionUID = -1972686679692469943L;

	private static final int SESSION_KEY_LENGTH = 50;
	private static final int SESSION_DURATION_IN_MINUTES = 15;

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	@Resource
	private UserTransaction transaction; 

	@Override
	public SessionDto register(String username, String password, String name, String email) {
		log.debug("registering new user: " + username);

		JPAQuery query = new JPAQuery(em).from(user).where(user.username.eq(username));
		if (query.exists()) {
			// user already exists
			return null;
		}

		try {
			// generate salt and encrypt password
			byte[] salt = PasswordEncryptionUtils.generateSalt();
			byte[] encryptedPassword = PasswordEncryptionUtils.getEncryptedPassword(password, salt);

			// generate session id and expiration date
			String sessionId = RandomStringUtils.randomAlphanumeric(SESSION_KEY_LENGTH);
			Date expires = new DateTime().plusMinutes(SESSION_DURATION_IN_MINUTES).toDate();

			// persist user and session
			transaction.begin();
			User userEntity = new User(username, encryptedPassword, salt, name, email);
			em.persist(userEntity);
			em.persist(new Session(sessionId, userEntity, expires));
			transaction.commit();

			return new SessionDto(sessionId, expires, new UserDto(username, name, email, null));
		} catch (Exception e) {
			log.error("Could not register user!", e);
			throw new RuntimeException("Could not register user!");
		}
	}

	@Override
	public SessionDto login(String username, String attemptedPassword) {
		log.debug("logging in user: " + username);

		User userEntity = new JPAQuery(em)
								.from(user)
								.where(user.username.eq(username))
								.uniqueResult(user);

		if (userEntity == null) {
			// user does not exists
			return null;
		}

		try {
			if (PasswordEncryptionUtils.authenticate(attemptedPassword, userEntity.getPassword(), userEntity.getSalt())) {
				String sessionId = RandomStringUtils.randomAlphanumeric(SESSION_KEY_LENGTH);
				Date expires = new DateTime().plusMinutes(15).toDate();

				transaction.begin();
				em.persist(new Session(sessionId, userEntity, expires));
				transaction.commit();

				UserDto userDto = new UserDto(username, userEntity.getName(), userEntity.getEmail(), userEntity.getPreferences());
				return new SessionDto(sessionId, expires, userDto);
			} else {
				// credentials do not match
				return null;
			}
		} catch (Exception e) {
			log.error("Could not log in!", e);
			throw new RuntimeException("Could not log in!");
		}
	}

	@Override
	public UserDto checkSession(String sessionId) {
		log.debug("checking session " + sessionId);

		return new JPAQuery(em)
					.from(user)
					.innerJoin(user.sessions, session)
					.where(session.id.eq(sessionId)
					  .and(session.expires.after(new Date())))
					.uniqueResult(new QUserDto(user.username, user.name, user.email, user.preferences));
	}

	@Override
	public void logout(String sessionId) {
		log.debug("deleting session " + sessionId + " and expired ones");

		try {
			// delete session and cleanup expired ones
			transaction.begin();
			long affected = new JPADeleteClause(em, session)
							.where(session.id.eq(sessionId)
							   .or(session.expires.before(new Date())))
							.execute();
			transaction.commit();

			log.debug(affected + " rows deleted");
		} catch (Exception e) {
			log.error("Could not delete session", e);
			// ignore
		}
	}

	@Override
	public void bookmarkRecipe(String username, long recipeId, boolean add) {
		log.debug("user " + username + " bookmarking recipe " + recipeId);

		try {
			transaction.begin();
			Recipe recipeEntity = em.find(Recipe.class, recipeId);
			User userEntity = em.find(User.class, username);
			userEntity.addBookmarks(recipeEntity, add);
			em.persist(userEntity);
			transaction.commit();
		} catch (Exception e) {
			log.error("Could not " + (add ? "add" : "remove") + " bookmark!", e);
			throw new RuntimeException("Could not " + (add ? "add" : "remove") + " bookmark!");
		}
	}

	@Override
	public void commentRecipe(String username, long recipeId, String content) {
		log.debug("user " + username + " adding comment for recipe " + recipeId);

		try {
			Recipe recipeEntity = em.find(Recipe.class, recipeId);
			User userEntity = em.find(User.class, username);

			transaction.begin();
			em.persist(new Comment(userEntity, recipeEntity, content));
			transaction.commit();
		} catch (Exception e) {
			log.error("Could not save comment!", e);
			throw new RuntimeException("Could not save comment!");
		}
	}
}
