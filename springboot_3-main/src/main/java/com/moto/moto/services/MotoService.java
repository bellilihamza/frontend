package com.moto.moto.services;

import com.moto.moto.Moto;
import com.moto.moto.Type;

import org.springframework.data.domain.Page;

import java.util.List;

public interface MotoService {
    // Basic CRUD operations for Moto
    Moto saveMoto(Moto moto);
    Moto updateMoto(Moto moto);
    void deleteMoto(Moto moto);
    void deleteMotoById(Long id);
    Moto getMoto(Long id);
    List<Moto> getAllMotos();
    
    // Pagination support
    Page<Moto> getAllMotosParPage(int page, int size);

    // Query by 'nomMoto'
    List<Moto> findByNomMoto(String nom);
    List<Moto> findByNomMotoContains(String nom);

    // Custom queries
    List<Moto> findByNomAndPrixGreaterThan(String nom, Double prix);
    List<Moto> findByType(Type type);
    List<Moto> findByTypeId(Long typeId);
    
    // Sorting queries
    List<Moto> findByOrderByNomMotoAsc();
    List<Moto> sortMotosByNomAndPrix();

    // Type entity methods
    List<Type> getAllTypes(); // Pour récupérer tous les types
    Type getTypeById(Long id); // Pour récupérer un Type par son ID
}
