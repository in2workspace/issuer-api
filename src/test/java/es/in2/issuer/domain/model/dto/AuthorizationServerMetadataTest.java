package es.in2.issuer.domain.model.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthorizationServerMetadataTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        String expectedTokenEndpoint = "https://example.com/token";

        // Act
        AuthorizationServerMetadata metadata = new AuthorizationServerMetadata(expectedTokenEndpoint);

        // Assert
        assertEquals(expectedTokenEndpoint, metadata.tokenEndpoint());
    }

    @Test
    void testSetters() {
        // Arrange
        String newTokenEndpoint = "https://newexample.com/token";

        // Act
        AuthorizationServerMetadata metadata = AuthorizationServerMetadata.builder()
                .tokenEndpoint(newTokenEndpoint)
                .build();

        // Assert
        assertEquals(newTokenEndpoint, metadata.tokenEndpoint());
    }
}