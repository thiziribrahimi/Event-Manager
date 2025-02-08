import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EventService } from '../../services/event.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-event-form',
  standalone: true, // Indiquer que c'est un composant autonome
  imports: [CommonModule, FormsModule, RouterModule], // Importer les modules nécessaires
  templateUrl: './event-form.component.html',
  styleUrls: ['./event-form.component.scss']
})
export class EventFormComponent implements OnInit {
  event: any = {
    title: '',
    description: '',
    date: '',
    location: ''
  };
  isEdit = false;
  eventId: number | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private eventService: EventService
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    this.eventId = idParam ? +idParam : null;
    if (this.eventId) {
      this.isEdit = true;
      this.eventService.getEventById(this.eventId).subscribe(event => {
        this.event = event;
      });
    }
  }

  saveEvent(): void {
    if (this.isEdit && this.eventId !== null) {
      this.eventService.updateEvent(this.eventId, this.event).subscribe(() => {
        this.router.navigate(['/events']);
      }, error => {
        console.error('Erreur lors de la mise à jour de l\'événement', error);
      });
    } else {
      this.eventService.createEvent(this.event).subscribe(() => {
        this.router.navigate(['/events']);
      }, error => {
        console.error('Erreur lors de la création de l\'événement', error);
      });
    }
  }
}