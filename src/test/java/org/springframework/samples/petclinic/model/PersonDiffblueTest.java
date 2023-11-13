package org.springframework.samples.petclinic.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class PersonDiffblueTest {

	/**
	 * Methods under test:
	 *
	 * <ul>
	 * <li>default or parameterless constructor of {@link Person}
	 * <li>{@link Person#setFirstName(String)}
	 * <li>{@link Person#setLastName(String)}
	 * <li>{@link Person#getFirstName()}
	 * <li>{@link Person#getLastName()}
	 * </ul>
	 */
	@Test
	void testConstructor() {
		// Arrange and Act
		Person actualPerson = new Person();
		actualPerson.setFirstName("Jane");
		actualPerson.setLastName("Doe");
		String actualFirstName = actualPerson.getFirstName();

		// Assert that nothing has changed
		assertEquals("Doe", actualPerson.getLastName());
		assertEquals("Jane", actualFirstName);
	}

}
