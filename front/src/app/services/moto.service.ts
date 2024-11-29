import { Injectable } from '@angular/core';
import { Moto } from '../model/moto.model';
import { Type } from '../model/type.model';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Image } from '../model/image.model';



@Injectable({
  providedIn: 'root'
})
export class MotoService {
  private apiURL: string = 'http://localhost:8087/motos/api';
  private apiURLType: string = 'http://localhost:8087/motos/api/type';

  constructor(private http: HttpClient) {}

  // Liste des motos
  listeMotos(): Observable<Moto[]> {
    return this.http.get<Moto[]>(`${this.apiURL}/all`);
  }

  // Liste des types
  listeTypes(): Observable<Type[]> {
    return this.http.get<Type[]>(this.apiURLType);
  }

  // Consulter un type par ID
  consulterType(id: number): Observable<Type> {
    const url = `${this.apiURLType}/${id}`;
    return this.http.get<Type>(url);
  }

  // Ajouter une moto
  ajouterMoto(moto: Moto): Observable<Moto> {
    return this.http.post<Moto>(this.apiURL, moto);
  }

  // Supprimer une moto
  supprimerMoto(id: number): Observable<void> {
    const url = `${this.apiURL}/${id}`;
    return this.http.delete<void>(url);
  }

  // Consulter une moto par ID
  consulterMoto(id: number): Observable<Moto> {
    const url = `${this.apiURL}/getbyid/${id}`;
    return this.http.get<Moto>(url);
  }

  // Mettre à jour une moto
  updateMoto(moto: Moto): Observable<Moto> {
    return this.http.put<Moto>(`${this.apiURL}/updatemoto`, moto);
  }

  // Rechercher des motos par type
  rechercherParType(idType: string): Observable<Moto[]> {
    const url = `${this.apiURL}/motoType/${idType}`;
    return this.http.get<Moto[]>(url);
  }

  // Rechercher des motos par nom
  rechercherParNom(nom: string): Observable<Moto[]> {
    const url = `${this.apiURL}/motosByName/${nom}`;
    return this.http.get<Moto[]>(url);
  }

  // Ajouter un type
  ajouterType(type: Type): Observable<Type> {
    return this.http.post<Type>(this.apiURLType, type);
  }

  // Supprimer un type
  supprimerType(id: number): Observable<void> {
    const url = `${this.apiURLType}/${id}`;
    return this.http.delete<void>(url);
  }

  // Mettre à jour un type
  updateType(type: Type): Observable<Type> {
    const url = `${this.apiURLType}/${type.idtype}`;
    return this.http.put<Type>(url, type);
  }
  uploadImage(file: File, filename: string): Observable<Image>{
    const imageFormData = new FormData();
    imageFormData.append('image', file, filename);
    const url = `${this.apiURL + '/image/upload'}`;
    return this.http.post<Image>(url, imageFormData);
    }
    loadImage(id: number): Observable<Image> {
    const url = `${this.apiURL + '/image/get/info'}/${id}`;
    return this.http.get<Image>(url);
    }
    uploadImageFS(file: File, filename: string, idmoto : number): Observable<any>{
      const imageFormData = new FormData();
      imageFormData.append('image', file, filename);
      const url = `${this.apiURL + '/image/uploadFS'}/${idmoto}`;
      return this.http.post(url, imageFormData);
      }
      uploadImagemot(file: File, filename: string, idmoto: number): Observable<any> {
        const imageFormData = new FormData();
        imageFormData.append('image', file, filename);
        
        // Corrected the endpoint name here
        const url = `${this.apiURL + '/image/uploadImagemoto'}/${idmoto}`;
        
        return this.http.post(url, imageFormData);
      }
      supprimerImage(id : number) {
        const url = `${this.apiURL}/image/delete/${id}`;
        return this.http.delete(url);
        }
        
      
}
