import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common'

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule], // Importer les modules nécessaires
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  credentials = {
    email: '',
    password: ''
  };

  constructor(private userService: UserService, private router: Router) {}

  login() {
    this.userService.login(this.credentials).subscribe(response => {
      if (response.token && response.userId) {
        this.userService.saveToken(response.token); // Stocker le token après la connexion
        this.userService.saveUserId(response.userId); // Stocker l'ID de l'utilisateur après la connexion
        console.log('Token sauvegardé :', response.token);
        console.log('ID Utilisateur sauvegardé :', response.userId);
        this.router.navigate(['/my-registrations']).then(success => { // Redirection vers la page des inscriptions
          if (success) {
            console.log('Redirection réussie vers /my-registrations');
          } else {
            console.error('Erreur de redirection vers /my-registrations');
          }
        });
      } else {
        console.error('Réponse invalide du serveur:', response);
      }
    }, error => {
      console.error('Erreur de connexion', error);
    });
  }
}
