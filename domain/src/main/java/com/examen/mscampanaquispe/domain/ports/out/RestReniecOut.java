package com.examen.mscampanaquispe.domain.ports.out;

import com.examen.mscampanaquispe.domain.aggregates.response.ResponseReniec;

public interface RestReniecOut {
    ResponseReniec getInfoReniec(String numDoc);
}
