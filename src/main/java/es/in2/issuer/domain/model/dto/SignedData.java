package es.in2.issuer.domain.model.dto;

import es.in2.issuer.domain.model.enums.SignatureType;
import lombok.Builder;

@Builder
public record SignedData(SignatureType type, String data) {
}
