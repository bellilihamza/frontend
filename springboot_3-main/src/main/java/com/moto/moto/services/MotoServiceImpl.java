package com.moto.moto.services;

import com.moto.moto.Moto;
import com.moto.moto.Type;
import com.moto.moto.repos.MotoRepository;
import com.moto.moto.repos.TypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoServiceImpl implements MotoService {

    @Autowired
    MotoRepository motoRepository;

    @Autowired
    TypeRepository typeRepository;

    @Override
    public Moto saveMoto(Moto moto) {
        // Gestion correcte du type avant de sauvegarder la moto
        if (moto.getType() != null && moto.getType().getIdtype() != null) {
            Type existingType = getTypeById(moto.getType().getIdtype());
            moto.setType(existingType);
        }
        return motoRepository.save(moto);
    }

    @Override
    public Moto updateMoto(Moto moto) {
        return saveMoto(moto);  // Réutilisation de la méthode save pour éviter les doublons
    }

    @Override
    public void deleteMoto(Moto moto) {
        motoRepository.delete(moto);
    }

    @Override
    public void deleteMotoById(Long id) {
        motoRepository.deleteById(id);
    }

    @Override
    public Moto getMoto(Long id) {
        return motoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Moto> getAllMotos() {
        return motoRepository.findAll();
    }

    @Override
    public Page<Moto> getAllMotosParPage(int page, int size) {
        return motoRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public List<Moto> findByNomMoto(String nom) {
        return motoRepository.findByNomMoto(nom);
    }

    @Override
    public List<Moto> findByNomMotoContains(String nom) {
        return motoRepository.findByNomMotoContains(nom);
    }

    @Override
    public List<Moto> findByNomAndPrixGreaterThan(String nom, Double prix) {
        return motoRepository.findByNomAndPrixGreaterThan(nom, prix);
    }

    @Override
    public List<Moto> findByType(Type type) {
        return motoRepository.findByType(type);
    }

    @Override
    public List<Moto> findByTypeId(Long typeId) {
        return motoRepository.findByTypeId(typeId);
    }

    @Override
    public List<Moto> findByOrderByNomMotoAsc() {
        return motoRepository.findByOrderByNomMotoAsc();
    }

    @Override
    public List<Moto> sortMotosByNomAndPrix() {
        return motoRepository.sortMotosByNomAndPrix();
    }

    @Override
    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }

    @Override
    public Type getTypeById(Long id) {
        return typeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Type ID"));
    }
}
