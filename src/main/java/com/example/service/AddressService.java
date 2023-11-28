package com.example.service;

import com.example.client.ViaCepClient;
import com.example.entity.Address;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.function.Function;

@Slf4j
@ApplicationScoped
public class AddressService {

    private final ViaCepClient viaCepClient;
    private final Emitter<String> emitter;

    public AddressService(@RestClient final ViaCepClient viaCepClient, @Channel("address-out") final Emitter<String> emitter) {
        this.viaCepClient = viaCepClient;
        this.emitter = emitter;
    }

    @WithTransaction
    public Uni<Address> getAddress(final String cep) {

        final Uni<Address> addressResponse = viaCepClient.fetchAddress(cep);
        return addressResponse.map(address -> {
            emitter.send(address.toString());
            return address.persist().map(Address.class::cast);
        }).flatMap(Function.identity());
    }

    @Incoming("address-in")
    public void receive(final String message) {
        log.info("Consumer received message: {}", message);
    }
}