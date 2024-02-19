package com.examen.mscampanaquispe.infraestructure.rest;


import com.examen.mscampanaquispe.domain.aggregates.response.ResponseReniec;
import com.examen.mscampanaquispe.domain.ports.out.RestReniecOut;
import com.examen.mscampanaquispe.infraestructure.rest.client.ClienteReniec;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestReniecAdapter implements RestReniecOut {

    private final ClienteReniec reniec;

    @Value("${token.api}")
    private String tokenApi;
    @Override
    public ResponseReniec getInfoReniec(String numDoc) {
        String authorization = "Bearer " + tokenApi;
        ResponseReniec responseReniec = reniec.getInfoReniec(numDoc, authorization);
        return responseReniec;
    }
}
