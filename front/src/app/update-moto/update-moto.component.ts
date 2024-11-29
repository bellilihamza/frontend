import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Moto } from '../model/moto.model'; // Importer le modèle Moto
import { MotoService } from '../services/moto.service'; // Importer le service Moto
import { Type } from '../model/type.model'; // Assurez-vous d'importer le modèle Type si nécessaire
import { Image } from '../model/image.model';

@Component({
  selector: 'app-update-moto',
  templateUrl: './update-moto.component.html',
})
export class UpdateMotoComponent implements OnInit {
  currentMoto = new Moto(); // Changer de Equipe à Moto
  types!: Type[]; // Si vous avez un modèle Type pour les motos
  updatedTypeID!: number; // ID pour le type de moto
  myImage!: string;
  uploadedImage!: File;
  isImageUpdated: boolean = false;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private motoService: MotoService // Utilisez MotoService au lieu de EquipeService
  ) {}

  ngOnInit(): void {
    // Récupérer la liste des types de motos
    this.motoService.listeTypes().subscribe(types => {
      this.types = types; // Chargez les types disponibles
      console.log(types);
    });

    // Récupérer la moto actuelle basée sur l'ID
    this.motoService.consulterMoto(this.activatedRoute.snapshot.params['id'])
      .subscribe((prod: Moto) => {
        this.currentMoto = prod;
        this.updatedTypeID = prod.type.idtype;

        // Vérifiez si l'image existe avant de la charger
        if (this.currentMoto.image && this.currentMoto.image.idImage) {
          this.motoService.loadImage(this.currentMoto.image.idImage)
            .subscribe((img: Image) => {
              this.myImage = 'data:' + img.type + ';base64,' + img.image;
            });
        }
      });
  }

  onAddImageMoto() {
    if (this.uploadedImage) { // Vérifie que l'image a bien été chargée
      this.motoService.uploadImagemot(
          this.uploadedImage,
          this.uploadedImage.name,
          this.currentMoto.idMotot
        )
        .subscribe((img: Image) => {
          if (img && img.idImage) { // Vérifie que l'image a bien un id valide
            this.currentMoto.images.push(img);
          }
        });
    }
  }

  updateMoto() {
    // Met à jour le type de la moto actuelle
    this.currentMoto.type = this.types.find(cat => cat.idtype === this.updatedTypeID)!;

    // Vérifie si l'image de la moto a été mise à jour
    if (this.isImageUpdated && this.uploadedImage) { // Assure que l'image est bien mise à jour et existe
      this.motoService.uploadImage(this.uploadedImage, this.uploadedImage.name)
        .subscribe((img: Image) => {
          if (img && img.idImage) { // Assure que l'image a un id valide
            this.currentMoto.image = img; // Assigne l'image uploadée à currentMoto
          }

          // Met à jour la moto dans le service
          this.motoService.updateMoto(this.currentMoto)
            .subscribe(() => {
              this.router.navigate(['motos']); // Redirige vers la liste des motos
            });
        });
    } else {
      // Si l'image n'a pas été mise à jour, met à jour simplement la moto
      this.motoService.updateMoto(this.currentMoto)
        .subscribe(() => {
          this.router.navigate(['motos']); // Redirige vers la liste des motos
        });
    }
  }

  onImageUpload(event: any) {
    if (event.target.files && event.target.files.length) {
      this.uploadedImage = event.target.files[0];
      this.isImageUpdated = true;
      const reader = new FileReader();
      reader.readAsDataURL(this.uploadedImage);
      reader.onload = () => { this.myImage = reader.result as string; };
    }
  }

  supprimerImage(img: Image) {
    let conf = confirm("Êtes-vous sûr de vouloir supprimer cette image ?");
    if (conf) {
      this.motoService.supprimerImage(img.idImage).subscribe(() => {
        // Supprimer l'image du tableau currentMoto.images
        const index = this.currentMoto.images.indexOf(img, 0);
        if (index > -1) {
          this.currentMoto.images.splice(index, 1);
        }
      });
    }
  }
}
