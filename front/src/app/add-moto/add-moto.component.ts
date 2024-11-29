import { Component, OnInit } from '@angular/core'; // Ajout de OnInit
import { Moto } from '../model/moto.model';
import { MotoService } from '../services/moto.service';
import { Router } from '@angular/router';
import { Type } from '../model/type.model';
import { Image } from '../model/image.model';


@Component({
  selector: 'app-add-moto',
  templateUrl: './add-moto.component.html',
})
export class AddMotoComponent implements OnInit { // Implémentation de OnInit
  types!: Type[]; // Liste des types de motos
  newIdType!: number; // ID du nouveau type sélectionné
  newMoto: Moto = new Moto(); // Nouvelle moto à ajouter
  ajouterAvecSucces = false;
  uploadedImage!: File;
imagePath: any;
  constructor(private motoService: MotoService, private router: Router) {}

  ngOnInit(): void {
    // Récupération de la liste des types de motos
    this.motoService.listeTypes().subscribe(types => {
      this.types = types; // Suppression de l'accès à _embedded
     
    });
  }

  // Méthode pour ajouter une moto

  onImageUpload(event: any) {
    this.uploadedImage = event.target.files[0];
    console.log('Selected Image: ', this.uploadedImage);
    const reader = new FileReader();
    reader.readAsDataURL(this.uploadedImage);
    reader.onload = (_event) => {
      this.imagePath = reader.result;
    };
  }
  
  addMoto() {
    console.log('Starting addMoto process...');
    console.log('Selected Type ID: ', this.newIdType);
    console.log('Available Types: ', this.types);
  
    // Find and assign the selected Type
    const selectedType = this.types.find((type) => type.idtype == this.newIdType);
    if (!selectedType) {
      console.error('Type not found for ID: ', this.newIdType);
      return;
    }
  
    this.newMoto.type = selectedType;
    console.log('New Moto before saving: ', this.newMoto);
  
    // First, save the Moto
    this.motoService.ajouterMoto(this.newMoto).subscribe(
      (savedMoto) => {
        console.log('Moto saved successfully: ', savedMoto);
  
        // Now upload the image using the saved Moto ID
        this.motoService.uploadImagemot(this.uploadedImage, this.uploadedImage.name, savedMoto.idMotot).subscribe(
          (img: Image) => {
            console.log('Uploaded Image: ', img);
  
            // Update the Moto with the image
            this.newMoto.images = [img];
            this.router.navigate(['motos']);
          },
          (error) => {
            console.error('Error uploading image: ', error);
          }
        );
      },
      (error) => {
        console.error('Error saving Moto: ', error);
      }
    );
  }
}  