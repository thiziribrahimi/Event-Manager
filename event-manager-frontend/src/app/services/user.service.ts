import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:9000/authentication';

  constructor(private http: HttpClient) { }

  // Méthode pour se connecter
  login(credentials: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, credentials).pipe(
      map((response: any) => {
        console.log('Réponse de login:', response); // Log pour débogage
        this.saveToken(response.token);
        this.saveUserId(response.userId);
        return response;
      })
    );
  }

  // Méthode pour s'inscrire
  signup(user: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, user, { responseType: 'text' }).pipe(
      map((response: any) => {
        // Traiter la réponse comme du texte brut
        return { message: response };
      })
    );
  }

    // Méthode pour stocker le JWT
  saveToken(token: string): void {
    localStorage.setItem('token', token);
  }

  // Méthode pour récupérer le JWT
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getUserEmail(): string | null {
    const token = this.getToken();
    if (token) {
      const decodedToken = JSON.parse(atob(token.split('.')[1]));
      return decodedToken.email; // Assumer que l'email de l'utilisateur est stocké dans le champ 'email'
    }
    return null;
  }

  saveUserId(userId: string): void {
    localStorage.setItem('userId', userId); // Sauvegarder l'ID de l'utilisateur dans le stockage local
  }

  // Méthode pour récupérer le JWT
  getUserId(): string | null {
    return localStorage.getItem('userId');
  }
}
