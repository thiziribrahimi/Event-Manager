import { Route } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';
import { EventListComponent } from './events/event-list/event-list.component';
import { EventFormComponent } from './events/event-form/event-form.component';
import { MyRegistrationsComponent } from './events/my-registrations/my-registrations.component';

export const routes: Route[] = [
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'events', component: EventListComponent },
  { path: 'events/create', component: EventFormComponent },
  { path: 'events/edit/:id', component: EventFormComponent },
  { path: 'my-registrations', component: MyRegistrationsComponent },
  { path: '', redirectTo: '/events', pathMatch: 'full' }
];