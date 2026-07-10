import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { AffairesComponent } from './pages/affaires/affaires.component';
import { CrimesComponent } from './pages/crimes/crimes.component';
import { MapComponent } from './pages/map/map.component';
import { PersonnesComponent } from './pages/personnes/personnes.component';
import { AffaireDetailsComponent } from './pages/affaire-details/affaire-details.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full'
  },
  {
    path: 'dashboard',
    component: DashboardComponent
  },
  {
    path: 'affaires',
    component: AffairesComponent
  },
  {
    path: 'crimes',
    component: CrimesComponent
  },
  {
    path: 'map',
    component: MapComponent
  },
  {
  path: 'affaires/:id',
  component: AffaireDetailsComponent
},
  {
    path: 'personnes',
    component: PersonnesComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
