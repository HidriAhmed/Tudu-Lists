package tudu.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import tudu.domain.Role;
import tudu.domain.RolesEnum;
import tudu.domain.User;
import tudu.service.UserService;

public class Level1UserDetailsServiceImplMockitoTest {

	User expectedUser = new User();
	Set<Role> roles = new HashSet<Role>();
	Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	org.springframework.security.core.userdetails.User userDetail;

	@Mock
	UserService userService;
	@InjectMocks
	UserDetailsServiceImpl userDetailsService;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);

		expectedUser.setLogin("my_test_user");
		expectedUser.setPassword("a_Test_Password");
		Role userRole = new Role();
		userRole.setRole(RolesEnum.ROLE_USER.toString());
		Role adminRole = new Role();
		adminRole.setRole(RolesEnum.ROLE_ADMIN.toString());

		roles.add(userRole);
		roles.add(adminRole);
		expectedUser.setRoles(roles);

		for (Role role : roles) {
			authorities.add(new GrantedAuthorityImpl(role.getRole()));
		}
		userDetail = new org.springframework.security.core.userdetails.User(
				expectedUser.getLogin(), expectedUser.getPassword(),
				expectedUser.isEnabled(), true, true, true, authorities);

	}

	/*
	 * Type : Test état Vérifier que la méthode loadByUsername renvoie le bon
	 * login/password/les bonnes autoritées correspondant a l User renvoyé par
	 * le mock de userService.findUser Méhode : loadUserByUsername
	 */
	@Test
	public void userDetails_should_correspond_to_the_user_found() {
		// assign
		when(userService.findUser("my_test_user")).thenReturn(expectedUser);

		// act
		UserDetails result = userDetailsService
				.loadUserByUsername("my_test_user");

		// assert
		assertThat(result).isEqualTo(userDetail);
	}
}
