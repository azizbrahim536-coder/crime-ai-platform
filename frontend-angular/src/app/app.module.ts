import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AffairesComponent } from './pages/affaires/affaires.component';
import { CrimesComponent } from './pages/crimes/crimes.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { MapComponent } from './pages/map/map.component';
import { PersonnesComponent } from './pages/personnes/personnes.component';
import { AffaireDetailsComponent } from './pages/affaire-details/affaire-details.component';

@NgModule({
  declarations: [
    AppComponent,
    AffairesComponent,
    CrimesComponent,
    DashboardComponent,
    MapComponent,
    PersonnesComponent,
    AffaireDetailsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }