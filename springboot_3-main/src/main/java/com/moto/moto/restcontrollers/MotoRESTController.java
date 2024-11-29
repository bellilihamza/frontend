package com.moto.moto.restcontrollers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.moto.moto.Moto;
import com.moto.moto.services.MotoService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class MotoRESTController {

    @Autowired
    MotoService motoService;

    @RequestMapping(path = "all", method = RequestMethod.GET)
    public List<Moto> getAllMotos() {
        return motoService.getAllMotos();
    }

    @RequestMapping(value = "/getbyid/{id}", method = RequestMethod.GET)
    public Moto getMotoById(@PathVariable("id") Long id) {
        return motoService.getMoto(id);
    }

    @PostMapping
    public Moto createMoto(@RequestBody Moto moto) {
        return motoService.saveMoto(moto);
    }

    @RequestMapping(path = "/updatemoto", method = RequestMethod.PUT)
    public Moto updateMoto(@RequestBody Moto moto) {
        return motoService.saveMoto(moto);
    }

    @DeleteMapping("/{id}")
    public void deleteMoto(@PathVariable("id") Long id) {
        motoService.deleteMotoById(id);
    }

    @RequestMapping(value = "/motoType/{idType}", method = RequestMethod.GET)
    public List<Moto> getMotosByTypeId(@PathVariable("idType") Long idType) {
        return motoService.findByTypeId(idType);
    }

    @RequestMapping(value = "/motosByName/{nom}", method = RequestMethod.GET)
    public List<Moto> findByNomMotoContains(@PathVariable("nom") String nom) {
        return motoService.findByNomMotoContains(nom);
    }
}
