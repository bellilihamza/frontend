package com.moto.moto.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moto.moto.Image;

public interface ImageRepository extends JpaRepository<Image , Long> {
} 