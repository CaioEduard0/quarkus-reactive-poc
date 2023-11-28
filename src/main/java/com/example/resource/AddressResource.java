package com.example.resource;

import com.example.entity.Address;
import com.example.service.AddressService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;

@Path("addresses")
@ApplicationScoped
@RequiredArgsConstructor
class AddressResource {

    private final AddressService addressService;

    @GET
    @Path("{cep}")
    public Uni<Address> getAddress(final String cep) {
        return addressService.getAddress(cep);
    }
}