import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // Importer FormsModule
import { RouterModule } from '@angular/router'; // Importer RouterModule pour les routes
import { EventService } from '../../services/event.service';

@Component({
  selector: 'app-my-registrations',
  standalone: true, // Indiquer que c'est un composant autonome
  imports: [CommonModule, FormsModule, RouterModule], // Importer les modules nécessaires
  templateUrl: './my-registrations.component.html',
  styleUrls: ['./my-registrations.component.scss']
})
export class MyRegistrationsComponent implements OnInit {
  registeredEvents: any[] = [];

  constructor(private eventService: EventService) {}

  ngOnInit(): void {
    this.eventService.getRegisteredEvents().subscribe(events => {
      this.registeredEvents = events;
    });
  }
}