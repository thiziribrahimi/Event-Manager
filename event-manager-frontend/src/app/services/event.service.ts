import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private apiUrl = 'http://localhost:9000/events'; // URL de l'API backend

  constructor(private http: HttpClient, private userService: UserService) { }

  private getAuthHeaders(): HttpHeaders {
    const token = this.userService.getToken();
    console.log("getAuthHeaders token ==> "+ token)
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  // Méthode pour récupérer tous les événements
  getAllEvents(): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.get(this.apiUrl, { headers } );
  }

  // Méthode pour récupérer les événements à venir
  getUpcomingEvents(): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.get(`${this.apiUrl}/upcoming`, { headers } );
  }

  // Méthode pour récupérer les événements auxquels l'utilisateur est inscrit
  getRegisteredEvents(): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.get(`${this.apiUrl}/registered`, { headers });
  }

  // Méthode pour récupérer les événements créés par l'utilisateur
  getCreatedEvents(): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.get(`${this.apiUrl}/createdevents`, { headers });
  }

  // Méthode pour créer un nouvel événement
  createEvent(event: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post(this.apiUrl, event, { headers });
  }

  // Méthode pour mettre à jour un événement existant
  updateEvent(id: number, event: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.put(`${this.apiUrl}/${id}`, event, { headers });
  }

  // Méthode pour supprimer un événement
  deleteEvent(id: number): Observable<any> {
    const headers = this.getAuthHeaders();
    console.log('demande de suppression')
    return this.http.delete(`${this.apiUrl}/${id}`, { headers });
  }

  // Méthode pour s'inscrire à un événement
  registerForEvent(eventId: number): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post(`${this.apiUrl}/register`, { eventId }, { headers });
  }

  // Méthode pour se désinscrire d'un événement
  unregisterForEvent(eventId: number): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post(`${this.apiUrl}/unregister`, { eventId }, { headers });
  }

  // Méthode permettant de récupérer un Event à partir de son ID
  getEventById(id: number): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.get(`${this.apiUrl}/${id}`, { headers });
  }
}