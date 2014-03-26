package tudu.security;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import tudu.domain.User;
import tudu.service.impl.UserServiceImpl;

//Niveau2
@RunWith(MockitoJUnitRunner.class)
public class Level2UserDetailsServiceImplMockitoTest {

	@Mock
	UserServiceImpl userService;
	@InjectMocks
	UserDetailsServiceImpl userDetailsService;

	@Before
	public void before() {

	}

	/*
	 * Simuler une levée d'exceptions - tester que la methode lève bien une
	 * UsernameNotFoundException si la méthode findBy lève une
	 * ObjectRetrievalFailureException Méhode : loadUserByUsername
	 */
	@Test(expected = UsernameNotFoundException.class)
	public void loadByUsername_throw_UsernameNotFoundException() {
		// arrange
		when(userService.findUser("test_user")).thenThrow(
				new ObjectRetrievalFailureException(User.class, "test_user"));
		// act
		userDetailsService.loadUserByUsername("test_user");
		// assert
	}
}
