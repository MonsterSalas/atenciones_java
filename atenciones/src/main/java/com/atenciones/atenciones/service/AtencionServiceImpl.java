package com.atenciones.atenciones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atenciones.atenciones.model.Atencion;
import com.atenciones.atenciones.repository.AtencionRepository;

import java.util.List;
import java.util.Optional;


@Service
public class AtencionServiceImpl implements AtencionService{
    @Autowired
    private AtencionRepository atencionRepository;
    @Override
    public List<Atencion> getAllAtencion() {
        return atencionRepository.findAll();
    }

    @Override
    public Optional<Atencion> getAtencionByid(Long id) {
        return atencionRepository.findById(id);
    }

    @Override
    public Atencion createAtencion(Atencion atencion) {
        return atencionRepository.save(atencion);
    }

    @Override
    public Atencion updateAtencion(Long id, Atencion atencion) {
        if( atencionRepository.existsById(id)){
            atencion.setId(id);
            return atencionRepository.save(atencion);
        }else{
            return null;
        }
    }
    @Override
    public void deleteAtencion(Long id) {
        atencionRepository.deleteById(id);
    }

}
