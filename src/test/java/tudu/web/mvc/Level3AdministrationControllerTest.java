package tudu.web.mvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import tudu.domain.Property;
import tudu.service.ConfigurationService;
import tudu.service.UserService;

public class Level3AdministrationControllerTest {

	private static final String STATIC_PATH_PROPERTY_NAME = "application.static.path";
	private static final String GOOGLE_ANALYTICS_PROPERTY_NAME = "google.analytics.key";
	private static final String HOST_PROPERTY_NAME = "smtp.host";
	private static final String PORT_PROPERTY_NAME = "smtp.port";
	private static final String USER_PROPERTY_NAME = "smtp.user";
	private static final String PASSWORD_PROPERTY_NAME = "smtp.password";
	private static final String FROM_PROPERTY_NAME = "smtp.from";

	private static final String USER_TEST_STRING = "test_user";
	private static final String PASSWORD_TEST_STRING = "test_password";

	@Mock(answer = Answers.RETURNS_SMART_NULLS)
	private ConfigurationService cfgService;
	@Mock
	private UserService userService;

	@InjectMocks
	private AdministrationController adminController;

	AdministrationModel administrationModel = new AdministrationModel();

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);

		administrationModel.setAction("configuration");
		administrationModel.setSmtpFrom("me@ccc.com");
		administrationModel.setSmtpHost("aaa.com");
		administrationModel.setSmtpPort("128");
		administrationModel.setSmtpUser(USER_TEST_STRING);
		administrationModel.setSmtpPassword(PASSWORD_TEST_STRING);
	}

	/*
	 * Vérifier La réponse par défaut du mock configService et faire un test
	 * avec la page configuration Méthode : display Aide : configuaration du
	 * mock a l'aide de RETURNS_SMART_NULLS
	 */

	// tested with RETURN_SMART_NULLS
	@Ignore
	@Test
	public void display_with_default_mocking_on_cfgService() throws Exception {
		// arrange

		// act
		ModelAndView result = adminController.display("configuration");
		// assert
		verify(cfgService);
	}

	/*
	 * Vérifier que le configService.updateEmailProperties est bien appelé en ne
	 * vérifiant que les valeurs user et password . Aide : Spy Méthode : update
	 */
	@Test
	public void update_updateEmailProperties_only_with_user_and_password()
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

		ArgumentCaptor<String> smtpHost = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> smtpPort = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> smtpUser = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> smtpPassword = ArgumentCaptor
				.forClass(String.class);
		ArgumentCaptor<String> smtpFrom = ArgumentCaptor.forClass(String.class);
		// act
		ModelAndView result = adminController.update(administrationModel);
		// assert
		verify(cfgService).updateEmailProperties(smtpHost.capture(),
				smtpPort.capture(), smtpUser.capture(), smtpPassword.capture(),
				smtpFrom.capture());

		assertThat(smtpUser.getValue()).isEqualTo(USER_TEST_STRING);
		assertThat(smtpPassword.getValue()).isEqualTo(PASSWORD_TEST_STRING);
	}

	/*
	 * Reprendre sur quelques tests ayant des assertEquals, assertNull,
	 * assertNotNull avec le framefork fest assert
	 */

	/*
	 * Reprendre sur quelques tests ayant des when, verify, doThrow en utilisant
	 * la syntaxe bdd mockito
	 */

	private Property property(String value) {
		Property property = new Property();
		property.setValue(value);
		return property;
	}

}
