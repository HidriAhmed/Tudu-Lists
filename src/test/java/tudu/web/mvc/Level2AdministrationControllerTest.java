package tudu.web.mvc;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import tudu.domain.Property;
import tudu.service.ConfigurationService;
import tudu.service.UserService;

public class Level2AdministrationControllerTest {

	private static final String STATIC_PATH_PROPERTY_NAME = "application.static.path";
	private static final String GOOGLE_ANALYTICS_PROPERTY_NAME = "google.analytics.key";
	private static final String HOST_PROPERTY_NAME = "smtp.host";
	private static final String PORT_PROPERTY_NAME = "smtp.port";
	private static final String USER_PROPERTY_NAME = "smtp.user";
	private static final String PASSWORD_PROPERTY_NAME = "smtp.password";
	private static final String FROM_PROPERTY_NAME = "smtp.from";

	AdministrationModel adminModelToEnableUser = new AdministrationModel();
	AdministrationModel adminModelToDisableUser = new AdministrationModel();

	@Mock
	private ConfigurationService cfgService;
	@Mock
	private UserService userService;

	@InjectMocks
	private AdministrationController adminController;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);

		adminModelToEnableUser.setAction("enableUser");
		adminModelToDisableUser.setAction("disableUser");
		adminModelToEnableUser.setLogin("test_user");
		adminModelToDisableUser.setLogin("test_user");
	}

	/*
	 * - Vérifier qu'aucune interactions n'a lieu lorsque la page demandée n'est
	 * ni "configuration" ni "users" Méthode : display
	 */
	@Test
	public void display_should_not_interact_when_page_different_than_configuration_or_users()
			throws Exception {
		// arrange

		// act
		ModelAndView result = adminController.display("test");
		// assert
		verifyNoMoreInteractions(cfgService);
	}

	/*
	 * 
	 * Vérifier dans un test que pour la page "configuration" il n'y a pas
	 * d'interaction avec userService. Méthode : display
	 */
	@Test
	public void display_should_read_configService_properties_when_page_is_configuration()
			throws Exception {
		// arrange
		when(cfgService.getProperty(STATIC_PATH_PROPERTY_NAME)).thenReturn(
				property("staticPath"));
		when(cfgService.getProperty(GOOGLE_ANALYTICS_PROPERTY_NAME))
				.thenReturn(property("googleAnalytics"));
		when(cfgService.getProperty(HOST_PROPERTY_NAME)).thenReturn(
				property("smtpHost"));
		when(cfgService.getProperty(PORT_PROPERTY_NAME)).thenReturn(
				property("smtpPort"));
		when(cfgService.getProperty(USER_PROPERTY_NAME)).thenReturn(
				property("smtpUser"));
		when(cfgService.getProperty(PASSWORD_PROPERTY_NAME)).thenReturn(
				property("smtpPassword"));
		when(cfgService.getProperty(FROM_PROPERTY_NAME)).thenReturn(
				property("smtpFrom"));
		// act
		ModelAndView result = adminController.display("configuration");
		// assert
		verifyNoMoreInteractions(userService);
	}

	/*
	 * - Vérifer que pour l'action "enableUser" le service afférent est appelé
	 * et que disableUser ne l'est pas Méthode : update
	 */
	@Test
	public void update_enable_user_on_enableUser_action() throws Exception {
		// arrange
		// act
		adminController.update(adminModelToEnableUser);
		// assert
		verify(userService).enableUser("test_user");
		verify(userService, never()).disableUser("test_user");
	}

	/*
	 * - Vérifer que pour l'action "disableUser" le service afférent est appelé
	 * et que enableUser ne l'est pas (d'une manière différente) Méthode :
	 * update
	 */
	@Test
	public void update_can_disable_user_on_disableUser_action()
			throws Exception {
		// arrange
		// act
		adminController.update(adminModelToDisableUser);
		// assert
		verify(userService, atLeastOnce()).disableUser("test_user");
		verify(userService, never()).enableUser("test_user");
	}

	/*
	 * 
	 * Vérifier que pour l'action appelle findUsersByLogin après un enableUser
	 * ou un disableUser Méthode : update
	 */
	@Test
	public void update_should_fetch_users_on_login_after_disabling_suer()
			throws Exception {
		// arrange
		// act
		adminController.update(adminModelToDisableUser);
		// assert
		verify(userService).disableUser("test_user");

		// act
		adminController.update(adminModelToEnableUser);
		// assert
		verify(userService).disableUser("test_user");
	}

	private Property property(String value) {
		Property property = new Property();
		property.setValue(value);
		return property;
	}

}
