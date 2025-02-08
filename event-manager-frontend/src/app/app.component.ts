import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router'; // Importer RouterModule pour les routes
import { routes } from './app.routes'; // Importer les routes définies dans app.routes.ts

@Component({
  selector: 'app-root',
  standalone: true, // Indiquer que c'est un composant autonome
  imports: [CommonModule, RouterModule], // Importer les modules nécessaires sans forRoot ici
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Event Manager';
}
