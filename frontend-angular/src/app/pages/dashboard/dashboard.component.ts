import { Component, OnInit } from '@angular/core';
import { DashboardService } from '../../services/dashboard.service';
import { DashboardStats } from '../../models/dashboard-stats';

import Chart from 'chart.js/auto';
import { ReportService } from 'src/app/services/report.service';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  stats?: DashboardStats;
  errorMessage = '';

  crimesTypeChart?: Chart;
  crimesVilleChart?: Chart;
  crimesStatutChart?: Chart;
  affairesStatutChart?: Chart;

  constructor(
    private dashboardService: DashboardService,
  private reportService: ReportService,
  public authService: AuthService
) {}

  ngOnInit(): void {
    this.loadStats();
  }

  loadStats(): void {
    this.dashboardService.getStats().subscribe({
      next: (data) => {
        this.stats = data;

        setTimeout(() => {
          this.createCharts();
        }, 100);
      },
      error: (err) => {
        console.error('Erreur chargement dashboard', err);
        this.errorMessage = 'Erreur lors du chargement des statistiques';
      }
    });
  }

  objectKeys(obj: any): string[] {
    return obj ? Object.keys(obj) : [];
  }

  createCharts(): void {
    if (!this.stats) return;

    this.createCrimesByTypeChart();
    this.createCrimesByVilleChart();
    this.createCrimesByStatutChart();
    this.createAffairesByStatutChart();
  }

  createCrimesByTypeChart(): void {
    const labels = Object.keys(this.stats!.crimesParType);
    const values = Object.values(this.stats!.crimesParType);

    this.crimesTypeChart = new Chart('crimesTypeChart', {
      type: 'bar',
      data: {
        labels: labels,
        datasets: [{
          label: 'Crimes par type',
          data: values
        }]
      }
    });
  }

  createCrimesByVilleChart(): void {
    const labels = Object.keys(this.stats!.crimesParVille);
    const values = Object.values(this.stats!.crimesParVille);

    this.crimesVilleChart = new Chart('crimesVilleChart', {
      type: 'bar',
      data: {
        labels: labels,
        datasets: [{
          label: 'Crimes par ville',
          data: values
        }]
      }
    });
  }

  createCrimesByStatutChart(): void {
    const labels = Object.keys(this.stats!.crimesParStatut);
    const values = Object.values(this.stats!.crimesParStatut);

    this.crimesStatutChart = new Chart('crimesStatutChart', {
      type: 'pie',
      data: {
        labels: labels,
        datasets: [{
          label: 'Crimes par statut',
          data: values
        }]
      }
    });
  }

  createAffairesByStatutChart(): void {
    const labels = Object.keys(this.stats!.affairesParStatut);
    const values = Object.values(this.stats!.affairesParStatut);

    this.affairesStatutChart = new Chart('affairesStatutChart', {
      type: 'doughnut',
      data: {
        labels: labels,
        datasets: [{
          label: 'Affaires par statut',
          data: values
        }]
      }
    });
  }
  downloadExcel(): void {
  this.reportService.downloadCrimesExcel().subscribe({
    next: (blob) => {
      const fileURL = window.URL.createObjectURL(blob);
      const link = document.createElement('a');

      link.href = fileURL;
      link.download = 'crimes-statistiques.xlsx';
      link.click();

      window.URL.revokeObjectURL(fileURL);
    },
    error: (err) => {
      console.error('Erreur téléchargement Excel', err);
      alert('Erreur lors du téléchargement du fichier Excel');
    }
  });
}
}
