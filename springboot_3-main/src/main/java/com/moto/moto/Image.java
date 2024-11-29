package com.moto.moto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
@Builder
@Data

public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idImage;
	private String name;
	private String type;
	@Column(name = "IMAGE", length = 4048576)
	@Lob
	private byte[] image;
	
	@ManyToOne()
	@JoinColumn (name="Moto_ID")
	@JsonIgnore
	private Moto moto;

	public Long getIdImage() {
		return idImage;
	}

	public void setIdImage(Long idImage) {
		this.idImage = idImage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	

	public Moto getMoto() {
		return moto;
	}

	public void setMoto(Moto moto) {
		this.moto = moto;
	}

	public Image(Long idImage, String name, String type, byte[] image, Moto moto) {
		super();
		this.idImage = idImage;
		this.name = name;
		this.type = type;
		this.image = image;
		this.moto = moto;
	}

	public Image() {
		super();
		// TODO Auto-generated constructor stub
	}
	  public static class Builder {
	        private Long idImage;
	        private String name;
	        private String type;
	        private byte[] image;
	        private Moto moto;

	        public Builder idImage(Long idImage) {
	            this.idImage = idImage;
	            return this;
	        }

	        public Builder name(String name) {
	            this.name = name;
	            return this;
	        }

	        public Builder type(String type) {
	            this.type = type;
	            return this;
	        }

	        public Builder image(byte[] image) {
	            this.image = image;
	            return this;
	        }

	        public Builder moto(Moto moto) {
	            this.moto = moto;
	            return this;
	        }

	        public Image build() {
	            Image image = new Image();
	            image.idImage = this.idImage;
	            image.name = this.name;
	            image.type = this.type;
	            image.image = this.image;
	            image.moto = this.moto;
	            return image;
	        }
	    }

	    // Static method to access the builder
	    public static Builder builder() {
	        return new Builder();
	    }
   
}