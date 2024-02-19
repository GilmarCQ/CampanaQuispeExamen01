package com.examen.mscampanaquispe.domain.impl;

import com.examen.mscampanaquispe.domain.aggregates.response.ResponseReniec;
import com.examen.mscampanaquispe.domain.ports.in.ReniecServiceIn;
import com.examen.mscampanaquispe.domain.ports.out.RestReniecOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReniecServiceImpl implements ReniecServiceIn {

    private final RestReniecOut reniec;
    @Override
    public ResponseReniec getInfoIn(String numero) {
        return reniec.getInfoReniec(numero);
    }
}
