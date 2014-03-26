package tudu.web.mvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import tudu.domain.Property;
import tudu.domain.User;
import tudu.service.ConfigurationService;
import tudu.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class Level1AdministrationControllerTest {

	private static final String STATIC_PATH_PROPERTY_NAME = "application.static.path";
	private static final String GOOGLE_ANALYTICS_PROPERTY_NAME = "google.analytics.key";
	private static final String HOST_PROPERTY_NAME = "smtp.host";
	private static final String PORT_PROPERTY_NAME = "smtp.port";
	private static final String USER_PROPERTY_NAME = "smtp.user";
	private static final String PASSWORD_PROPERTY_NAME = "smtp.password";
	private static final String FROM_PROPERTY_NAME = "smtp.from";

	AdministrationModel administrationModel = new AdministrationModel();
	List<User> users = new ArrayList<User>();

	@Mock
	private ConfigurationService cfgService;
	@Mock
	private UserService userService;

	@InjectMocks
	private AdministrationController adminController;

	@Before
	public void before() {

		administrationModel.setSearchLogin("test_user");
		User user = new User();
		user.setLogin("my_test_user");
		user.setPassword("a_Test_Password");

	}

	/*
	 * Vérifier dans un test que pour la page "configuration" les propriétés
	 * smtp (et uniquement celles là) soit donnée au model Méthode : display
	 */
	@Test
	public void display_should_put_smtp_config_properties_in_admin_model_when_page_is_configuration()
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

		AdministrationModel resultModel = (AdministrationModel) result
				.getModel().get("administrationModel");

		// assert
		assertThat(resultModel.getSmtpFrom()).isNotEmpty();
		assertThat(resultModel.getSmtpHost()).isNotEmpty();
		assertThat(resultModel.getSmtpPassword()).isNotEmpty();
		assertThat(resultModel.getSmtpPort()).isNotEmpty();
		assertThat(resultModel.getSmtpUser()).isNotEmpty();
	}

	/*
	 * Vérifier que l update ne retourne pas un modele null
	 */
	@Test
	public void update_shouldnt_return_a_null_model() throws Exception {
		// arrange
		when(userService.findUsersByLogin("test_user")).thenReturn(users);

		// act
		ModelAndView resultModel = adminController.update(administrationModel);
		// assert
		assertThat(resultModel).isNotNull();
	}

	private Property property(String value) {
		Property property = new Property();
		property.setValue(value);
		return property;
	}
}
