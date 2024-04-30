package es.in2.issuer.domain.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.in2.issuer.domain.exception.ParseErrorException;
import es.in2.issuer.domain.service.VerifiableCredentialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

import static es.in2.issuer.domain.util.Constants.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class VerifiableCredentialServiceImpl implements VerifiableCredentialService {
    private final ObjectMapper objectMapper;
    @Override
    public Mono<String> generateVcPayLoad(String vcTemplate, String subjectDid, String issuerDid, String userData, Instant expiration) {
        return Mono.fromCallable(() -> {
            JsonNode vcTemplateNode = parseJson(vcTemplate);
            String uuid = UUID.randomUUID().toString();
            Instant nowInstant = Instant.now();

            updateTemplateNode(vcTemplateNode, uuid, issuerDid, nowInstant, expiration);

            JsonNode credentialSubjectValue = objectMapper.readTree(userData);
            ((ObjectNode) credentialSubjectValue).put("id", subjectDid);
            ((ObjectNode) vcTemplateNode).set(CREDENTIAL_SUBJECT, credentialSubjectValue);

            return objectMapper.writeValueAsString(constructFinalObjectNode(vcTemplateNode, subjectDid, issuerDid, uuid, nowInstant, expiration));
        });
    }
    @Override
    public Mono<String> generateDeferredVcPayLoad(String vcTemplate) {
        return Mono.fromCallable(() -> {
            JsonNode vcTemplateNode = parseJson(vcTemplate);
            String subjectDid = vcTemplateNode.get(CREDENTIAL_SUBJECT).get(ID).asText();
            String issuerDid = vcTemplateNode.get(ISSUER).asText();
            Instant nowInstant = Instant.parse(vcTemplateNode.get(VALID_FROM).asText());
            Instant expiration = Instant.parse(vcTemplateNode.get(EXPIRATION_DATE).asText());

            updateTemplateNode(vcTemplateNode, subjectDid, issuerDid, nowInstant, expiration);
            return objectMapper.writeValueAsString(constructFinalObjectNode(vcTemplateNode, subjectDid, issuerDid, subjectDid, nowInstant, expiration));
        });
    }
    @Override
    public Mono<String> generateVc(String vcTemplate, String subjectDid, String issuerDid, String userData, Instant expiration) {
        return Mono.fromCallable(() -> {
            JsonNode vcTemplateNode = parseJson(vcTemplate);
            String uuid = UUID.randomUUID().toString();
            Instant nowInstant = Instant.now();

            updateTemplateNode(vcTemplateNode, uuid, issuerDid, nowInstant, expiration);

            JsonNode credentialSubjectValue = objectMapper.readTree(userData);
            ((ObjectNode) credentialSubjectValue).put(ID, subjectDid);
            ((ObjectNode) vcTemplateNode).set(CREDENTIAL_SUBJECT, credentialSubjectValue);

            return objectMapper.writeValueAsString(vcTemplateNode);
        });
    }

    private void updateTemplateNode(JsonNode vcTemplateNode, String uuid, String issuerDid, Instant nowInstant, Instant expiration) {
        ((ObjectNode) vcTemplateNode).put(ID, uuid);
        ((ObjectNode) vcTemplateNode).put(ISSUER, issuerDid);
        ((ObjectNode) vcTemplateNode).put(ISSUANCE_DATE, nowInstant.toString());
        ((ObjectNode) vcTemplateNode).put(VALID_FROM, nowInstant.toString());
        ((ObjectNode) vcTemplateNode).put(EXPIRATION_DATE, expiration.toString());
    }

    private ObjectNode constructFinalObjectNode(JsonNode vcTemplateNode, String subjectDid, String issuerDid, String uuid, Instant nowInstant, Instant expiration) {
        ObjectNode finalObject = objectMapper.createObjectNode();
        finalObject.put("sub", subjectDid);
        finalObject.put("nbf", nowInstant.getEpochSecond());
        finalObject.put("iss", issuerDid);
        finalObject.put("exp", expiration.getEpochSecond());
        finalObject.put("iat", nowInstant.getEpochSecond());
        finalObject.put("jti", uuid);
        finalObject.set("vc", vcTemplateNode);
        return finalObject;
    }
    private JsonNode parseJson(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new ParseErrorException(e.getMessage());
        }
    }
}
