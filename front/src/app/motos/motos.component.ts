import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Moto } from '../model/moto.model';
import { MotoService } from '../services/moto.service';
import { AuthService } from '../services/auth.service';
import { Image } from '../model/image.model';

@Component({
  selector: 'app-motos',
  templateUrl: './motos.component.html',
  
})
export class MotosComponent implements OnInit {

  motos?: Moto[];

  constructor(private motoService: MotoService, public authService: AuthService) {}

  ngOnInit(): void {
    this.chargerMotos();
  }

  // Chargement de la liste des motos
    chargerMotos() {
      this.motoService.listeMotos().subscribe((motos: Moto[]) => {
        this.motos = motos; // Assuming motos is of type Moto[]
        this.motos.forEach((moto: Moto) => {
          // Check if there are images available for the Moto
          if (moto.image) { // Assuming image is a single Image object
            moto.imageStr = 'data:' + moto.image.type + ';base64,' + moto.image.image;
          } else if (moto.images && moto.images.length > 0) { // If there are multiple images
            moto.imageStr = 'data:' + moto.images[0].type + ';base64,' + moto.images[0].image; // Take the first image for display
          }
        });
      });
    }
    

  // Suppression d'une moto
  supprimerMoto(m: Moto) {
    let conf = confirm("Etes-vous sûr ?");
    if (conf) {
      this.motoService.supprimerMoto(m.idMotot).subscribe(() => {
        console.log("Moto supprimée");
        this.chargerMotos();
      });
    }
  }
  
}
