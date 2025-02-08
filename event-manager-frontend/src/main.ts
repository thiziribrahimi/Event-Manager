import { enableProdMode, importProvidersFrom } from '@angular/core';
import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideHttpClient, HTTP_INTERCEPTORS } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { routes } from './app/app.routes';
import { AuthInterceptor } from './app/services/auth.interceptor'; // Assurer l'importation correcte


bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(),
    importProvidersFrom(RouterModule.forRoot(routes)),
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true } // Ajouter l'intercepteur
  ]
}).catch(err => console.error(err));
