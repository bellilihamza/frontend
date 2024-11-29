import { Component, OnInit } from '@angular/core';
import { User } from '../model/user.model';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-verif-email',
  templateUrl: './verif-email.component.html',
  styleUrls: ['./verif-email.component.css']
})
export class VerifEmailComponent implements OnInit {

  code: string = '';
  user: User = new User();
  err: string = '';

  constructor(
    private route: ActivatedRoute,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Assurez-vous que l'utilisateur est bien récupéré ou redirigez l'utilisateur si non trouvé
    if (this.authService.getRegistredUser()) {
      this.user = this.authService.getRegistredUser();
    } else {
      this.router.navigate(['/register']);
    }
  }

  onValidateEmail() {
    // Appelez la méthode validateEmail et vérifiez le code fourni
    this.authService.validateEmail(this.code).subscribe({
      next: () => {
        alert("Validation de l'email réussie. Connexion en cours...");
        this.authService.login(this.user).subscribe({
          next: (data) => {
            const jwToken = data.headers.get("Authorization")!;
            this.authService.saveToken(jwToken);
            this.router.navigate(['/']);
          },
          error: (err: any) => {
            console.error("Erreur lors de la connexion :", err);
            this.err = "Erreur de connexion, veuillez réessayer.";
          },
        });
      },
      error: (err: any) => {
        // Vérifiez les codes d'erreurs spécifiques pour afficher des messages personnalisés
        if (err.error.errorCode === 'INVALID_TOKEN') {
          this.err = "Votre code n'est pas valide !";
        } else if (err.error.errorCode === 'EXPIRED_TOKEN') {
          this.err = "Votre code a expiré !";
        } else {
          this.err = "Une erreur est survenue, veuillez réessayer.";
        }
      },
    });
  }
}
