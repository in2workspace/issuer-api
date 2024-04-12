package es.in2.issuer.domain.model;

import es.in2.issuer.domain.model.Grant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class GrantTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        String expectedPreAuthorizedCode = "1234";
        boolean expectedUserPinRequired = true;
        Grant.TxCode expectedTxCode = new Grant.TxCode(4, "numeric", "description");
        // Act
        Grant grant = new Grant(expectedPreAuthorizedCode, expectedUserPinRequired, expectedTxCode);
        // Assert
        assertEquals(expectedPreAuthorizedCode, grant.preAuthorizedCode());
        assertEquals(expectedUserPinRequired, grant.userPinRequired());
    }

    @Test
    void testSetters() {
        // Arrange
        Grant grant = Grant.builder().preAuthorizedCode("5678").userPinRequired(false).build();
        // Assert
        assertEquals("5678", grant.preAuthorizedCode());
        assertFalse(grant.userPinRequired());
    }

    @Test
    void lombokGeneratedMethodsTest() {
        // Arrange
        String expectedPreAuthorizedCode = "1234";
        boolean expectedUserPinRequired = true;
        Grant.TxCode expectedTxCode = new Grant.TxCode(4, "numeric", "description");
        // Act
        Grant grant1 = new Grant(expectedPreAuthorizedCode, expectedUserPinRequired, expectedTxCode);
        Grant grant2 = new Grant(expectedPreAuthorizedCode, expectedUserPinRequired, expectedTxCode);
        // Assert
        assertEquals(grant1, grant2); // Tests equals() method generated by Lombok
        assertEquals(grant1.hashCode(), grant2.hashCode()); // Tests hashCode() method generated by Lombok
    }

}
