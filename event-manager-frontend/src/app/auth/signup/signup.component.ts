import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-signup',
  standalone: true, // Indiquer que c'est un composant autonome
  imports: [CommonModule, FormsModule, RouterModule], // Importer les modules nécessaires
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent {
  user = {
    name: '',
    email: '',
    password: ''
  };

  constructor(private userService: UserService, private router: Router) {}

  signup() {
    this.userService.signup(this.user).subscribe(response => {
      this.router.navigate(['/login']).then(success => { // Redirection vers la page des inscriptions
        if (success) {
          console.log('Redirection réussie vers /login');
        } else {
          console.error('Erreur de redirection vers /login');
        }
      });
    }, error => {
      console.error('Erreur d\'inscription', error);
    });
  }
}
