package tudu.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.util.Calendar;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tudu.domain.TodoList;
import tudu.domain.User;

public class Level3UserServiceImplMockitoTest {

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
	 * Vérifier que la liste todo a bien pour name Welcome! Méthode :
	 * createNewTodoList
	 */
	@Test
	public void verify_createTodoLoist_has_list_with_name_Welcome() {
		// assign
		TodoList todoList = new TodoList();
		todoList.setName("Welcome!");
		todoList.setLastUpdate(Calendar.getInstance().getTime());
		// act
		userService.createNewTodoList(expectedUser);

		// assert
		verify(entityManager).persist(todoList);
	}

	/*
	 * Vérifier que la liste todo a bien pour name Welcome! - autre méthode
	 * Méthode : createNewTodoList
	 */
	@Test
	public void verify_createTodoLoist_has_list_with_name_Welcome_2() {
		// assign

		// act
		TodoList todoList = userService.createNewTodoList(expectedUser);

		// assert
		assertThat(todoList.getName()).isEqualTo("Welcome!");
	}
}
