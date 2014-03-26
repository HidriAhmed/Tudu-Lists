package tudu.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.orm.ObjectRetrievalFailureException;

import tudu.domain.User;
import tudu.service.UserAlreadyExistsException;

public class Level1UserServiceImplMockitoTest {

	User expectedUser = new User();
	@Mock
	EntityManager entityManager;
	@InjectMocks
	UserServiceImpl userService;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		expectedUser.setLogin("test_user");
		expectedUser.setFirstName("First name");
		expectedUser.setLastName("Last name");
	}

	/*
	 * Type : Test état Vérifier que l utilisateur retourné par le service
	 * correspond bien à celui attendu. Méthode : findUser Aide : utilisation
	 * when pour mocker l appel a entityManager.find et retourner le user en
	 * variable de classe
	 */
	@Test
	public void find_user_should_return_the_user() {
		// arrange
		when(entityManager.find(User.class, "test_user")).thenReturn(
				expectedUser);
		// act
		User result = userService.findUser("test_user");
		// assert
		assertThat(result).isEqualTo(expectedUser);

	}

	@Test
	/*
	 * Type : Test Comportement Vérifier que l appel a l'entity manager a bien
	 * été effectué avec le bon user Méthode : updateUser Aide : Utilisation de
	 * verify
	 */
	public void update_user_should_call_entityManager_merge() {
		// arrange

		// act
		userService.updateUser(expectedUser);

		// assert
		verify(entityManager).merge(expectedUser);
	}

	@Test
	/*
	 * Type : Test état Vérifier que l'appel a findUser("toto") entraine bien un
	 * appel a entityManager.find avec le meme login. Méthode : findUser
	 */
	public void user_should_be_retrieved() {
		// arrange
		when(entityManager.find(User.class, "toto")).thenReturn(expectedUser);

		// act
		userService.findUser("toto");

		// assert
		verify(entityManager).find(User.class, "toto");
	}

	/*
	 * Vérifier qu'une exception de type ObjectRetrievalFailureException est
	 * bien levée si l entityManager find renvoie null Méthode : findUser
	 */
	@Test(expected = ObjectRetrievalFailureException.class)
	public void error_should_be_thrown_when_a_user_is_not_found() {
		// arrange
		when(entityManager.find(User.class, "toto")).thenReturn(null);
		// act
		userService.findUser("toto");
		// assert

	}

	/*
	 * Vérifier qu'une exception de type UserAlreadyExistsException est bien
	 * levée si le login existe deja Méthode : createUser
	 */

	@Test(expected = UserAlreadyExistsException.class)
	public void exception_should_be_thrown_when_creating_an_already_existed_user()
			throws UserAlreadyExistsException {
		// arrange
		when(entityManager.find(User.class, "test_user")).thenReturn(
				expectedUser);
		// act
		userService.createUser(expectedUser);
		// assert
	}

	/*
	 * Type : Test Comportement Vérifier que l'utilisateur a bien été sauvegardé
	 * Méthode : createUser
	 */
	@Test
	public void new_user_should_be_saved() throws UserAlreadyExistsException {
		// arrange

		// act
		userService.createUser(expectedUser);

		// assert
		verify(entityManager).persist(expectedUser);
	}

}
