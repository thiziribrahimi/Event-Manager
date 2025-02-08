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
      this.userService.saveToken(response.token); // Stocker le token après la connexion
      console.log('Token sauvegardé :', response.token);
      this.router.navigate(['/my-registrations']).then(success => { // Redirection vers la page des inscriptions
        if (success) {
          console.log('Redirection réussie vers /my-registrations');
        } else {
          console.error('Erreur de redirection vers /my-registrations');
        }
      });
    }, error => {
      console.error('Erreur de connexion', error);
    });
  }
}
