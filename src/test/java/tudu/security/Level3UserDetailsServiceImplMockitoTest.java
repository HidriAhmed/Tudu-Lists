package tudu.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import tudu.domain.Role;
import tudu.domain.RolesEnum;
import tudu.domain.User;
import tudu.service.UserService;

public class Level3UserDetailsServiceImplMockitoTest {

	@Mock
	UserService userService;
	@InjectMocks
	UserDetailsServiceImpl authenticationDAO;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	/*
	 * Le test suivant a été ecris avec EasyMock. L'écrire à nouveau en
	 * utilisant la syntaxe BDD Mockito et en utilisant les assertions de fest
	 * assert a la place des assertEquals assertNotNull
	 */
	@Test
	public void testLoadUserByUsername() {
		// UserDetailsServiceImpl authenticationDAO = new
		// UserDetailsServiceImpl();
		// UserService userService = EasyMock.createMock(UserService.class);
		// ReflectionTestUtils.setField(authenticationDAO, "userService",
		// userService);

		User user = new User();
		user.setLogin("test_user");
		user.setPassword("password");
		user.setEnabled(true);
		Role userRole = new Role();
		userRole.setRole(RolesEnum.ROLE_USER.toString());
		user.getRoles().add(userRole);
		// expect(userService.findUser("test_user")).andReturn(user);
		when(userService.findUser("test_user")).thenReturn(user);

		// replay(userService);

		UserDetails springSecurityUser = authenticationDAO
				.loadUserByUsername("test_user");

		// assertEquals(user.getLogin(), springSecurityUser.getUsername());
		// assertEquals(user.getPassword(), springSecurityUser.getPassword());
		// assertNotNull(user.getLastAccessDate());
		// assertEquals(1, springSecurityUser.getAuthorities().size());
		// assertEquals(RolesEnum.ROLE_USER.toString(), springSecurityUser
		// .getAuthorities().iterator().next().getAuthority());

		assertThat(user.getLogin()).isEqualTo(springSecurityUser.getUsername());
		assertThat(user.getPassword()).isEqualTo(
				springSecurityUser.getPassword());
		assertThat(user.getLastAccessDate()).isNotNull();
		assertThat(1).isEqualTo(springSecurityUser.getAuthorities().size());
		assertThat(RolesEnum.ROLE_USER.toString()).isEqualTo(
				springSecurityUser.getAuthorities().iterator().next()
						.getAuthority());

		verify(userService).findUser("test_user");
	}
	// Pour aller plus loin : Should you only mock types that you own ?:
	// http://stackoverflow.com/questions/1906344/should-you-only-mock-types-you-own
}
