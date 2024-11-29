import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../model/user.model';
import { HttpClient } from '@angular/common/http';
import { JwtHelperService } from '@auth0/angular-jwt';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  apiURLl: string = 'http://localhost:8087/users';

  token!:string;
  public loggedUser!: string;
  public isloggedIn: Boolean = false;
  public roles!: string[];
  public regitredUser : User = new User();

 
  private helper = new JwtHelperService();
  constructor(private router: Router,
    private http: HttpClient) { }
  login(user: User) {
    return this.http.post<User>(this.apiURL + '/login', user, { observe: 'response' });
    localStorage.setItem('isLoggedIn', 'true');

  }
  setRegistredUser(user : User){
    this.regitredUser=user;
    }
    getRegistredUser(){
    return this.regitredUser;
    }
  saveToken(jwt: string) {
    localStorage.setItem('jwt', jwt);
    this.token = jwt;
    this.isloggedIn = true;
    this.decodeJWT();
  }

  decodeJWT() {
    if (this.token == undefined)
      return;
    const decodedToken = this.helper.decodeToken(this.token);
    this.roles = decodedToken.roles;
    this.loggedUser = decodedToken.sub;
    console.table(this.roles);
    console.log(this.loggedUser);
  }

  loadToken() {
    this.token = localStorage.getItem('jwt')!;
    this.decodeJWT();
  }

  getToken(): string {
    return this.token;
  }

  logout() {
    this.loggedUser = undefined!;
    this.roles = undefined!;
    this.token = undefined!;
    localStorage.removeItem('isLoggedIn');
    this.isloggedIn = false;
    localStorage.removeItem('jwt');
    this.router.navigate(['/login']);
  }

  isAdmin(): boolean {
    if (!this.roles)
      return false;
    return this.roles.indexOf('ADMIN') >= 0;
  }

  setLoggedUserFromLocalStorage(login: string) {
    this.loggedUser = login;
    this.isloggedIn = true;
  }

  isTokenExpired(): boolean {
    return this.helper.isTokenExpired(this.token);
  }

  // Nouvelle méthode pour vérifier si l'utilisateur est connecté
  isLoggedIn(): boolean {
    // Vérifiez si le token existe et s'il n'est pas expiré
    if (this.token && !this.isTokenExpired()) {
      return true;
    }
    // Vérifiez l'état de connexion stocké dans localStorage
    return localStorage.getItem('isLoggedIn') === 'true';
  }
  registerUser(user :User){
    console.log("2")

    return this.http.post<User>(this.apiURL+'/register', user,
    {observe:'response'});
    }
    validateEmail(code : string){
      return this.http.get<User>(this.apiURL+'/verifyEmail/'+code);
      }
      apiURL: string = 'http://localhost:8010/users';

          
}