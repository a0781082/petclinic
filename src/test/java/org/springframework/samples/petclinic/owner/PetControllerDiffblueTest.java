package org.springframework.samples.petclinic.owner;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

@ContextConfiguration(classes = { PetController.class })
@ExtendWith(SpringExtension.class)
class PetControllerDiffblueTest {

	@MockBean
	private OwnerRepository ownerRepository;

	@Autowired
	private PetController petController;

	/**
	 * Method under test: {@link PetController#populatePetTypes()}
	 */
	@Test
	void testPopulatePetTypes() {
		// Arrange
		ArrayList<PetType> petTypeList = new ArrayList<>();
		when(ownerRepository.findPetTypes()).thenReturn(petTypeList);

		// Act
		Collection<PetType> actualPopulatePetTypesResult = petController.populatePetTypes();

		// Assert
		verify(ownerRepository).findPetTypes();
		assertTrue(actualPopulatePetTypesResult.isEmpty());
		assertSame(petTypeList, actualPopulatePetTypesResult);
	}

	/**
   * Method under test: {@link PetController#populatePetTypes()}
   */
  @Test
  void testPopulatePetTypes2() {
    // Arrange
    when(ownerRepository.findPetTypes()).thenThrow(new IllegalArgumentException("foo"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> petController.populatePetTypes());
    verify(ownerRepository).findPetTypes();
  }

	/**
	 * Method under test: {@link PetController#findOwner(int)}
	 */
	@Test
	void testFindOwner() {
		// Arrange
		Owner owner = new Owner();
		owner.setAddress("42 Main St");
		owner.setCity("Oxford");
		owner.setFirstName("Jane");
		owner.setId(1);
		owner.setLastName("Doe");
		owner.setTelephone("6625550144");
		when(ownerRepository.findById(Mockito.<Integer>any())).thenReturn(owner);

		// Act
		Owner actualFindOwnerResult = petController.findOwner(1);

		// Assert
		verify(ownerRepository).findById(Mockito.<Integer>any());
		assertSame(owner, actualFindOwnerResult);
	}

	/**
   * Method under test: {@link PetController#findOwner(int)}
   */
  @Test
  void testFindOwner2() {
    // Arrange
    when(ownerRepository.findById(Mockito.<Integer>any())).thenThrow(new IllegalArgumentException("foo"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> petController.findOwner(1));
    verify(ownerRepository).findById(Mockito.<Integer>any());
  }

	/**
	 * Method under test: {@link PetController#findPet(int, Integer)}
	 */
	@Test
	void testFindPet() {
		// Arrange
		Owner owner = new Owner();
		owner.setAddress("42 Main St");
		owner.setCity("Oxford");
		owner.setFirstName("Jane");
		owner.setId(1);
		owner.setLastName("Doe");
		owner.setTelephone("6625550144");
		when(ownerRepository.findById(Mockito.<Integer>any())).thenReturn(owner);

		// Act
		Pet actualFindPetResult = petController.findPet(1, 1);

		// Assert
		verify(ownerRepository).findById(Mockito.<Integer>any());
		assertNull(actualFindPetResult);
	}

	/**
	 * Method under test: {@link PetController#findPet(int, Integer)}
	 */
	@Test
	void testFindPet2() {
		// Arrange
		Owner owner = new Owner();
		owner.setAddress("42 Main St");
		owner.setCity("Oxford");
		owner.setFirstName("Jane");
		owner.setId(1);
		owner.setLastName("Doe");
		owner.setTelephone("6625550144");
		when(ownerRepository.findById(Mockito.<Integer>any())).thenReturn(owner);

		// Act
		Pet actualFindPetResult = petController.findPet(1, null);

		// Assert
		verify(ownerRepository).findById(Mockito.<Integer>any());
		assertTrue(actualFindPetResult.getVisits().isEmpty());
	}

	/**
   * Method under test: {@link PetController#findPet(int, Integer)}
   */
  @Test
  void testFindPet3() {
    // Arrange
    when(ownerRepository.findById(Mockito.<Integer>any())).thenThrow(new IllegalArgumentException("foo"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> petController.findPet(1, 1));
    verify(ownerRepository).findById(Mockito.<Integer>any());
  }

	/**
	 * Method under test: {@link PetController#initCreationForm(Owner, ModelMap)}
	 */
	@Test
	void testInitCreationForm() throws Exception {
		// Arrange
		Owner owner = new Owner();
		owner.setAddress("42 Main St");
		owner.setCity("Oxford");
		owner.setFirstName("Jane");
		owner.setId(1);
		owner.setLastName("Doe");
		owner.setTelephone("6625550144");
		when(ownerRepository.findPetTypes()).thenReturn(new ArrayList<>());
		when(ownerRepository.findById(Mockito.<Integer>any())).thenReturn(owner);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/owners/{ownerId}/pets/new", 1);

		// Act and Assert
		MockMvcBuilders.standaloneSetup(petController)
			.build()
			.perform(requestBuilder)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().size(3))
			.andExpect(MockMvcResultMatchers.model().attributeExists("owner", "pet", "types"))
			.andExpect(MockMvcResultMatchers.view().name("pets/createOrUpdatePetForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("pets/createOrUpdatePetForm"));
	}

	/**
	 * Method under test: {@link PetController#initUpdateForm(Owner, int, ModelMap)}
	 */
	@Test
	void testInitUpdateForm() throws Exception {
		// Arrange
		Owner owner = new Owner();
		owner.setAddress("42 Main St");
		owner.setCity("Oxford");
		owner.setFirstName("Jane");
		owner.setId(1);
		owner.setLastName("Doe");
		owner.setTelephone("6625550144");
		when(ownerRepository.findPetTypes()).thenReturn(new ArrayList<>());
		when(ownerRepository.findById(Mockito.<Integer>any())).thenReturn(owner);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/owners/{ownerId}/pets/{petId}/edit",
				1, 1);

		// Act and Assert
		MockMvcBuilders.standaloneSetup(petController)
			.build()
			.perform(requestBuilder)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().size(3))
			.andExpect(MockMvcResultMatchers.model().attributeExists("owner", "types"))
			.andExpect(MockMvcResultMatchers.view().name("pets/createOrUpdatePetForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("pets/createOrUpdatePetForm"));
	}

	/**
	 * Method under test:
	 * {@link PetController#processCreationForm(Owner, Pet, BindingResult, ModelMap)}
	 */
	@Test
	void testProcessCreationForm() throws Exception {
		// Arrange
		Owner owner = new Owner();
		owner.setAddress("42 Main St");
		owner.setCity("Oxford");
		owner.setFirstName("Jane");
		owner.setId(1);
		owner.setLastName("Doe");
		owner.setTelephone("6625550144");
		when(ownerRepository.findPetTypes()).thenReturn(new ArrayList<>());
		when(ownerRepository.findById(Mockito.<Integer>any())).thenReturn(owner);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/owners/{ownerId}/pets/new", 1);

		// Act and Assert
		MockMvcBuilders.standaloneSetup(petController)
			.build()
			.perform(requestBuilder)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().size(3))
			.andExpect(MockMvcResultMatchers.model().attributeExists("owner", "pet", "types"))
			.andExpect(MockMvcResultMatchers.view().name("pets/createOrUpdatePetForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("pets/createOrUpdatePetForm"));
	}

	/**
	 * Method under test:
	 * {@link PetController#processCreationForm(Owner, Pet, BindingResult, ModelMap)}
	 */
	@Test
	void testProcessCreationForm2() throws Exception {
		// Arrange
		Owner owner = new Owner();
		owner.setAddress("42 Main St");
		owner.setCity("Oxford");
		owner.setFirstName("Jane");
		owner.setId(1);
		owner.setLastName("Doe");
		owner.setTelephone("6625550144");
		when(ownerRepository.findPetTypes()).thenReturn(new ArrayList<>());
		when(ownerRepository.findById(Mockito.<Integer>any())).thenReturn(owner);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/owners/{ownerId}/pets/new", 1)
			.param("birthDate", "2020-03-01");

		// Act and Assert
		MockMvcBuilders.standaloneSetup(petController)
			.build()
			.perform(requestBuilder)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().size(3))
			.andExpect(MockMvcResultMatchers.model().attributeExists("owner", "pet", "types"))
			.andExpect(MockMvcResultMatchers.view().name("pets/createOrUpdatePetForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("pets/createOrUpdatePetForm"));
	}

	/**
	 * Method under test:
	 * {@link PetController#processCreationForm(Owner, Pet, BindingResult, ModelMap)}
	 */
	@Test
	void testProcessCreationForm3() throws Exception {
		// Arrange
		Owner owner = new Owner();
		owner.setAddress("42 Main St");
		owner.setCity("Oxford");
		owner.setFirstName("Jane");
		owner.setId(1);
		owner.setLastName("Doe");
		owner.setTelephone("6625550144");
		when(ownerRepository.findPetTypes()).thenReturn(new ArrayList<>());
		when(ownerRepository.findById(Mockito.<Integer>any())).thenReturn(owner);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/owners/{ownerId}/pets/new", 1)
			.param("name", "Bella");

		// Act and Assert
		MockMvcBuilders.standaloneSetup(petController)
			.build()
			.perform(requestBuilder)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().size(3))
			.andExpect(MockMvcResultMatchers.model().attributeExists("owner", "pet", "types"))
			.andExpect(MockMvcResultMatchers.view().name("pets/createOrUpdatePetForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("pets/createOrUpdatePetForm"));
	}

	/**
	 * Method under test:
	 * {@link PetController#processUpdateForm(Pet, BindingResult, Owner, ModelMap)}
	 */
	@Test
	void testProcessUpdateForm() throws Exception {
		// Arrange
		Owner owner = new Owner();
		owner.setAddress("42 Main St");
		owner.setCity("Oxford");
		owner.setFirstName("Jane");
		owner.setId(1);
		owner.setLastName("Doe");
		owner.setTelephone("6625550144");
		when(ownerRepository.findPetTypes()).thenReturn(new ArrayList<>());
		when(ownerRepository.findById(Mockito.<Integer>any())).thenReturn(owner);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/owners/{ownerId}/pets/42/edit",
				"Uri Variables", "Uri Variables");

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(petController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
	}

}
