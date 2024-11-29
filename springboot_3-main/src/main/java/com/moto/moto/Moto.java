package com.moto.moto;



import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data

@Entity 
public class Moto {

	public Moto(Long idMotot, String nomMoto, Double prixMoto, Date dateCreation, Type type) {
		super();
		this.idMotot = idMotot;
		this.nomMoto = nomMoto;
		this.prixMoto = prixMoto;
		this.dateCreation = dateCreation;
		this.type = type;
	}

	@Id 
	 @GeneratedValue(strategy = GenerationType.IDENTITY) 
	 private Long idMotot; 
	 private String nomMoto; 
	 private Double prixMoto; 
	 private Date dateCreation; 
	 @ManyToOne(cascade = CascadeType.MERGE)  // Change from PERSIST to MERGE
	 private Type type;
	 @OneToMany (mappedBy = "moto")
		private List<Image> images;


	  
	 @Override
	public String toString() {
		return "Moto [idMotot=" + idMotot + ", nomMoto=" + nomMoto + ", prixMoto=" + prixMoto + ", dateCreation="
				+ dateCreation + ", type=" + type + "]";
	}

	public Moto(Long idMotot, String nomMoto, Double prixMoto, Date dateCreation, Type type, List<Image> images) {
		super();
		this.idMotot = idMotot;
		this.nomMoto = nomMoto;
		this.prixMoto = prixMoto;
		this.dateCreation = dateCreation;
		this.type = type;
		this.images = images;
	}

	public List<Image> getImages() {
		return images;
	}
	public void addImage(Image image) {
        images.add(image);
        image.setMoto(this);}
    

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Moto() { 
	  super(); 
	 } 
	 
	 public Moto(String nomProduit, Double prixProduit, Date dateCreation) { 
	  super(); 
	  this.nomMoto = nomProduit; 
	  this.prixMoto = prixProduit; 
	  this.dateCreation = dateCreation; 
	 }

	public Long getIdMotot() {
		return idMotot;
	}

	public void setIdMotot(Long idMotot) {
		this.idMotot = idMotot;
	}

	public String getNomMoto() {
		return nomMoto;
	}

	public void setNomMoto(String nomMoto) {
		this.nomMoto = nomMoto;
	}

	public Double getPrixMoto() {
		return prixMoto;
	}

	public void setPrixMoto(Double prixMoto) {
		this.prixMoto = prixMoto;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(java.util.Date dateCreation) {
	    if (dateCreation != null) {
	        this.dateCreation = new java.sql.Date(dateCreation.getTime());
	    } else {
	        this.dateCreation = null;
	    }
	}


	
	 
}
