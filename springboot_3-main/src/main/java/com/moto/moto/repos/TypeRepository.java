package com.moto.moto.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.moto.moto.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
@RepositoryRestResource(path = "Type") 
@CrossOrigin("http://localhost:4200/") 
public interface TypeRepository extends JpaRepository<Type, Long> {
    Type  findByNomType(String nomType);
}
