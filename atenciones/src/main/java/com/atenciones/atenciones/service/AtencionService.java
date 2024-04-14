package com.atenciones.atenciones.service;

import com.atenciones.atenciones.model.Atencion;
import java.util.List;
import java.util.Optional;

public interface AtencionService {
    List<Atencion> getAllAtencion();
    Optional<Atencion> getAtencionByid(Long id);
    Atencion createAtencion(Atencion paciente);

    Atencion updateAtencion (Long id, Atencion paciente);

    void deleteAtencion (Long id);


}
