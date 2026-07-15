import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';

import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { AffairesComponent } from './pages/affaires/affaires.component';
import { CrimesComponent } from './pages/crimes/crimes.component';
import { MapComponent } from './pages/map/map.component';
import { PersonnesComponent } from './pages/personnes/personnes.component';
import { AffaireDetailsComponent } from './pages/affaire-details/affaire-details.component';

import { AuthGuard } from './guards/auth.guard';
import { ChatbotComponent } from './pages/chatbot/chatbot.component';
import { RelationsGraphComponent } from './pages/relations-graph/relations-graph.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full'
  },

  {
    path: 'login',
    component: LoginComponent
  },

  {
    path: 'register',
    component: RegisterComponent
  },

  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuard]
  },

  {
    path: 'affaires',
    component: AffairesComponent,
    canActivate: [AuthGuard]
  },

  {
    path: 'affaires/:id',
    component: AffaireDetailsComponent,
    canActivate: [AuthGuard]
  },

  {
    path: 'crimes',
    component: CrimesComponent,
    canActivate: [AuthGuard]
  },

  {
    path: 'map',
    component: MapComponent,
    canActivate: [AuthGuard]
  },

  {
    path: 'personnes',
    component: PersonnesComponent,
    canActivate: [AuthGuard]
  },
    {
  path: 'chatbot',
  component: ChatbotComponent,
  canActivate: [AuthGuard]
},
{
  path: 'graph/affaire/:id',
  component: RelationsGraphComponent,
  canActivate: [AuthGuard]
},

  {
    path: '**',
    redirectTo: 'dashboard'
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
