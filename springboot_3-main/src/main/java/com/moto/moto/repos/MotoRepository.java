package com.moto.moto.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import com.moto.moto.Moto;
import com.moto.moto.Type;

@RepositoryRestResource(path = "rest")
public interface MotoRepository extends JpaRepository<Moto, Long> {

    // Find motos by name
    List<Moto> findByNomMoto(String nom);
    
    // Find motos where name contains a specific string
    List<Moto> findByNomMotoContains(String nom);
    
    // Custom query to find motos by name and price greater than a specific value
    @Query("select m from Moto m where m.nomMoto like %:nom and m.prixMoto > :prix")
    List<Moto> findByNomAndPrixGreaterThan(@Param("nom") String nom, @Param("prix") Double prix);
    
    // Find motos by Type (entity)
    @Query("select m from Moto m where m.type = :type")
    List<Moto> findByType(@Param("type") Type type);
    
    // Find motos by Type ID
    @Query("select m from Moto m where m.type.idtype = :id")
    List<Moto> findByTypeId(@Param("id") Long id);
    
    // Order motos by name in ascending order
    List<Moto> findByOrderByNomMotoAsc();
    
    // Custom query to sort motos by name ascending and price descending
    @Query("select m from Moto m order by m.nomMoto ASC, m.prixMoto DESC")
    List<Moto> sortMotosByNomAndPrix();
}

