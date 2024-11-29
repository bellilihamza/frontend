package com.moto.moto.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.moto.moto.Image;
import com.moto.moto.Moto;
import com.moto.moto.repos.MotoRepository;
import com.moto.moto.repos.ImageRepository;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    MotoService motoService;
    @Autowired
    MotoRepository motoRepository;
    
    @Override
    public Image uploadImage(MultipartFile file) throws IOException {
        return imageRepository.save(Image.builder()
            .name(file.getOriginalFilename())
            .type(file.getContentType())  // Corrected the method name here
            .image(file.getBytes())
            .build());
    }

    @Override
    public Image getImageDetails(Long id) throws IOException {
        final Optional<Image> dbImage = imageRepository.findById(id);
        if (dbImage.isPresent()) {
            return Image.builder()
                .idImage(dbImage.get().getIdImage())
                .name(dbImage.get().getName())
                .type(dbImage.get().getType())
                .image(dbImage.get().getImage())
                .build();
        } else {
            throw new IOException("Image not found");
        }
    }

    @Override
    public ResponseEntity<byte[]> getImage(Long id) throws IOException {
        final Optional<Image> dbImage = imageRepository.findById(id);
        if (dbImage.isPresent()) {
            return ResponseEntity.ok()
                .contentType(MediaType.valueOf(dbImage.get().getType()))
                .body(dbImage.get().getImage());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }


    @Override
    public Image uploadImageProd(MultipartFile file, Long idProd) throws IOException {
    	Moto moto = motoRepository.findById(idProd).orElseThrow(() -> new IOException("moto not found"));
        Image image = Image.builder()
            .name(file.getOriginalFilename())
            .type(file.getContentType())
            .image(file.getBytes())
            .moto(moto)
            .build();
        image = imageRepository.save(image);

        moto.addImage(image); // Add the image to the moto
        motoRepository.save(moto); // Save the moto with the new image

        return image;
    }

    @Override
    public List<Image> getImagesParProd(Long motoId) {
    	Moto p = motoRepository.findById(motoId).orElseThrow(() -> new IllegalArgumentException("moto not found"));
        return p.getImages();
    }
}
