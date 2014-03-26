package tudu.web.mvc;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tudu.service.ConfigurationService;
import tudu.service.UserService;

public class Level3AdministrationControllerTest {

	@Mock
	private ConfigurationService cfgService;
	@Mock
	private UserService userService;

	@InjectMocks
	private AdministrationController adminController;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);

	}

	/*
	 * Vérifier La réponse par défaut du mock configService et faire un test
	 * avec la page configuration Méthode : display Aide : configuaration du
	 * mock a l'aide de RETURNS_SMART_NULLS
	 */

	/*
	 * Vérifier que le configService.updateEmailProperties est bien appelé en ne
	 * vérifiant que les valeurs user et password . Aide : Spy Méthode : update
	 */

	/*
	 * Reprendre sur quelques tests ayant des assertEquals, assertNull,
	 * assertNotNull avec le framefork fest assert
	 */

	/*
	 * Reprendre sur quelques tests ayant des when, verify, doThrow en utilisant
	 * la syntaxe bdd mockito
	 */

}
