package es.in2.issuer.domain.model;

import es.in2.issuer.domain.model.enums.SignatureType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SignatureRequestTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        SignatureConfiguration expectedConfiguration = new SignatureConfiguration(SignatureType.COSE, null);
        String expectedData = "sampleData";
        // Act
        SignatureRequest signatureRequest = new SignatureRequest(expectedConfiguration, expectedData);
        // Assert
        assertEquals(expectedConfiguration, signatureRequest.configuration());
        assertEquals(expectedData, signatureRequest.data());
    }

    @Test
    void lombokGeneratedMethodsTest() {
        // Arrange
        SignatureConfiguration expectedConfiguration = new SignatureConfiguration(SignatureType.COSE, null);
        String expectedData = "sampleData";
        // Act
        SignatureRequest request1 = new SignatureRequest(expectedConfiguration, expectedData);
        SignatureRequest request2 = new SignatureRequest(expectedConfiguration, expectedData);

        // Assert
        assertEquals(request1, request2); // Tests equals() method generated by Lombok
        assertEquals(request1.hashCode(), request2.hashCode()); // Tests hashCode() method generated by Lombok
    }

}
