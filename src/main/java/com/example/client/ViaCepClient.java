package com.example.client;

import com.example.entity.Address;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "viacep")
public interface ViaCepClient {

    @GET
    @Path("{cep}")
    Uni<Address> fetchAddress(final String cep);
}