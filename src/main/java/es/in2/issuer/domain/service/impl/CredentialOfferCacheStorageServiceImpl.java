package es.in2.issuer.domain.service.impl;

import es.in2.issuer.domain.exception.CustomCredentialOfferNotFoundException;
import es.in2.issuer.domain.model.dto.CustomCredentialOffer;
import es.in2.issuer.domain.service.CredentialOfferCacheStorageService;
import es.in2.issuer.infrastructure.repository.CacheStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static es.in2.issuer.domain.util.Utils.generateCustomNonce;

@Slf4j
@Service
@RequiredArgsConstructor
public class CredentialOfferCacheStorageServiceImpl implements CredentialOfferCacheStorageService {

    private final CacheStore<CustomCredentialOffer> cacheStore;

    @Override
    public Mono<String> saveCustomCredentialOffer(CustomCredentialOffer customCredentialOffer) {
        return generateCustomNonce().flatMap(nonce -> cacheStore.add(nonce, customCredentialOffer));
    }

    @Override
    public Mono<CustomCredentialOffer> getCustomCredentialOffer(String nonce) {
        return cacheStore.get(nonce)
                .doOnSuccess(customCredentialOffer -> {
                    if (customCredentialOffer != null) {
                        log.debug("CustomCredentialOffer found for nonce: {}", nonce);
                        cacheStore.delete(nonce);
                    }
                })
                .switchIfEmpty(Mono.error(new CustomCredentialOfferNotFoundException("CustomCredentialOffer not found for nonce: " + nonce)));
    }

}
