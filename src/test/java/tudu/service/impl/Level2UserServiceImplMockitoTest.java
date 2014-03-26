package tudu.service.impl;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tudu.domain.User;
import tudu.service.UserAlreadyExistsException;

@RunWith(MockitoJUnitRunner.class)
public class Level2UserServiceImplMockitoTest {

	User expectedUser = new User();
	@Mock
	EntityManager entityManager;
	@InjectMocks
	UserServiceImpl userService;

	@Before
	public void before() {
		expectedUser.setLogin("test_user");
		expectedUser.setFirstName("First name");
		expectedUser.setLastName("Last name");
	}

	/*
	 * Vérifier que l on récupère bien uneRuntimeException en sortie de
	 * updateUser si la methode Merge de l entityManager leve une
	 * RuntimeException Méthode : updateUser
	 */
	@SuppressWarnings("unchecked")
	@Test(expected = RuntimeException.class)
	public void when_an_exception_is_thrown_by_entityManager_then_rethrow() {
		// arrange
		when(entityManager.merge(expectedUser)).thenThrow(
				RuntimeException.class);

		// act
		userService.updateUser(expectedUser);

		// assert
	}

	/*
	 * LES METHODES SUIVANTES SONT UNIQUEMENT LA POUR APPRENDRE LA SYNTAXE. CES
	 * TESTS SONT EXTREMEMENTS FRAGILES, A MANIPULER AVEC PRECAUTIONS !
	 */
	/*
	 * Type : Test Comportement Vérifier que l appel a l'entity manager persist
	 * a bien été effectué 4 fois Méthode : createUser
	 */
	@Test
	public void when_creation_of_a_new_user_then_4_calls_to_entityManager_persist()
			throws UserAlreadyExistsException {
		// arrange

		// act
		for (int i = 0; i < 4; i++) {
			userService.createUser(expectedUser);
		}
		// assert
		verify(entityManager, times(4)).persist(expectedUser);
	}

	@Test
	/*
	 * Type : Test Comportement Vérifier que l appel a l'entity manager persist
	 * a bien été effectué au moins 2 fois et au plus 5 fois Méthode :
	 * createUser
	 */
	public void when_creation_of_a_new_user_then_between_2_and_5_calls_to_entityManager_persist()
			throws UserAlreadyExistsException {
		// arrange

		// act
		for (int i = 0; i < 4; i++) {
			userService.createUser(expectedUser);
		}
		// assert
		verify(entityManager, atLeast(2)).persist(expectedUser);
		verify(entityManager, atMost(5)).persist(expectedUser);
	}

	/*
	 * Type : Test Comportement Vérifier que si l'utilisateur existe, la methode
	 * persiste n'a jamais été appelée Méthode : createUser
	 */
	@Test
	public void when_the_login_already_exist_then_persist_never_called_1() {
		// arrange
		when(entityManager.find(User.class, "test_user")).thenReturn(
				expectedUser);

		// act
		try {
			userService.createUser(expectedUser);
		} catch (UserAlreadyExistsException e) {
		}

		// assert
		finally {
			verify(entityManager, never()).persist(expectedUser);
		}
	}

	/*
	 * Type : Test Comportement Vérifier que si l'utilisateur existe, la methode
	 * persiste n'a jamais été appelée - 2eme alternative avec
	 * verifyNoMoreInteractions Méthode : createUser
	 */

	@Test
	public void when_the_login_already_exist_then_persist_never_called_2() {
		// arrange
		when(entityManager.find(User.class, "test_user")).thenReturn(
				expectedUser);

		// act
		try {
			userService.createUser(expectedUser);
		} catch (UserAlreadyExistsException e) {
		}

		// assert
		finally {
			verify(entityManager).find(User.class, "test_user");
			verifyNoMoreInteractions(entityManager);
		}

	}

}