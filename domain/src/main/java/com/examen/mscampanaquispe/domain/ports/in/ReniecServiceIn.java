package com.examen.mscampanaquispe.domain.ports.in;

import com.examen.mscampanaquispe.domain.aggregates.response.ResponseReniec;

public interface ReniecServiceIn {
    ResponseReniec getInfoIn(String numero);
}
