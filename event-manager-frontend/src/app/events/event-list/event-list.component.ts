import { Component, OnInit } from '@angular/core';
import { EventService } from '../../services/event.service';
import { UserService } from '../../services/user.service'
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-event-list',
  standalone: true, // Indiquer que c'est un composant autonome
  imports: [CommonModule, FormsModule, RouterModule], // Importer les modules nécessaires
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.scss']
})
export class EventListComponent implements OnInit {
  events: any[] = [];
  currentUserId: string | null = null;

  constructor(private eventService: EventService, private userService: UserService) {}

  ngOnInit(): void {
    this.currentUserId = this.userService.getUserId();
    this.eventService.getAllEvents().subscribe(events => {
      console.log('Événements récupérés :', events);
      this.events = events;
    });
  }

  editEvent(eventId: number): void {
    console.log('Éditer l\'événement avec ID :', eventId);
  }

  deleteEvent(eventId: number): void {
    if (eventId === undefined) {
      console.error('eventId est undefined');
      return;
    }
    this.eventService.deleteEvent(eventId).subscribe(response => {
      console.log('Suppression réussie :', response);
      this.events = this.events.filter(event => event.id !== eventId);
    }, error => {
      console.error('Erreur de suppression', error);
    });
  }

  register(eventId: number): void {
    console.log('Event ID pour inscription:', eventId); // Log pour vérifier l'ID
    if (eventId === undefined) {
      console.error('eventId est undefined');
      return;
    }
    this.eventService.registerForEvent(eventId).subscribe(response => {
      console.log('Inscription réussie :', response);
    }, error => {
      console.error('Erreur d\'inscription', error);
    });
  }

  unregister(eventId: number): void {
    this.eventService.unregisterForEvent(eventId).subscribe(response => {
      alert('Désinscription réussie!');
      // Recharger la liste des événements
      this.ngOnInit();
    }, error => {
      console.error('Erreur lors de la désinscription', error);
    });
  }

  isEventCreator(event: any): boolean {
    return event.creatorId.toString() === this.currentUserId;
  }
}
