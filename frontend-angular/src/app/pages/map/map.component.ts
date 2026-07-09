import { Component, AfterViewInit, OnDestroy } from '@angular/core';
import * as L from 'leaflet';

import { CrimeService } from '../../services/crime.service';
import { Crime } from '../../models/crime';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements AfterViewInit, OnDestroy {

  private map?: L.Map;
  crimes: Crime[] = [];

  constructor(private crimeService: CrimeService) {}

  ngAfterViewInit(): void {
    this.initMap();
    this.loadCrimesOnMap();
  }

  ngOnDestroy(): void {
    if (this.map) {
      this.map.remove();
    }
  }

  private initMap(): void {
    this.map = L.map('crimeMap').setView([34.425, 8.784], 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap contributors'
    }).addTo(this.map);

    // Fix icon marker في Angular
    const defaultIcon = L.icon({
      iconUrl: 'assets/marker-icon.png',
      shadowUrl: 'assets/marker-shadow.png',
      iconSize: [25, 41],
      iconAnchor: [12, 41],
      popupAnchor: [1, -34],
      shadowSize: [41, 41]
    });

    L.Marker.prototype.options.icon = defaultIcon;
  }

  private loadCrimesOnMap(): void {
    this.crimeService.getAllCrimes().subscribe({
      next: (data) => {
        this.crimes = data;

        this.crimes.forEach((crime) => {
          if (crime.latitude && crime.longitude && this.map) {
            const marker = L.marker([crime.latitude, crime.longitude]).addTo(this.map);

            marker.bindPopup(`
              <b>${crime.typeCrime}</b><br>
              Ville: ${crime.ville}<br>
              Adresse: ${crime.adresse}<br>
              Statut: ${crime.statut}<br>
              Date: ${crime.dateCrime}
            `);
          }
        });
      },
      error: (err) => {
        console.error('Erreur chargement crimes sur carte', err);
      }
    });
  }
}
