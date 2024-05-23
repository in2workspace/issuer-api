package es.in2.issuer.domain.model;

import es.in2.issuer.domain.model.enums.SignatureType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SignatureConfigurationTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        SignatureType expectedType = SignatureType.COSE;
        Map<String, String> expectedParameters = new HashMap<>();
        expectedParameters.put("param1", "value1");
        expectedParameters.put("param2", "value2");
        // Act
        SignatureConfiguration signatureConfiguration = new SignatureConfiguration(expectedType, expectedParameters);
        // Assert
        assertEquals(expectedType, signatureConfiguration.type());
        assertEquals(expectedParameters, signatureConfiguration.parameters());
    }

    @Test
    void lombokGeneratedMethodsTest() {
        // Arrange
        SignatureType expectedType = SignatureType.COSE;
        Map<String, String> expectedParameters = new HashMap<>();
        expectedParameters.put("param1", "value1");
        expectedParameters.put("param2", "value2");
        // Act
        SignatureConfiguration config1 = new SignatureConfiguration(expectedType, expectedParameters);
        SignatureConfiguration config2 = new SignatureConfiguration(expectedType, expectedParameters);
        // Assert
        assertEquals(config1, config2); // Tests equals() method generated by Lombok
        assertEquals(config1.hashCode(), config2.hashCode()); // Tests hashCode() method generated by Lombok
    }

}
