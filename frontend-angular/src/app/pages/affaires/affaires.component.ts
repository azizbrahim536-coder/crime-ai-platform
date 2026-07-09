import { Component, OnInit } from '@angular/core';
import { AffaireService } from '../../services/affaire.service';
import { Affaire } from '../../models/affaire';

@Component({
  selector: 'app-affaires',
  templateUrl: './affaires.component.html',
  styleUrls: ['./affaires.component.css']
})
export class AffairesComponent implements OnInit {

  affaires: Affaire[] = [];

newAffaire: Affaire = {
  titre: '',
  description: '',
  statut: 'OUVERTE'
};

  constructor(private affaireService: AffaireService) {}

  ngOnInit(): void {
    this.loadAffaires();
  }

  loadAffaires(): void {
    this.affaireService.getAllAffaires().subscribe({
      next: (data) => {
        this.affaires = data;
      },
      error: (err) => {
        console.error('Erreur chargement affaires', err);
      }
    });
  }

  addAffaire(): void {
   if (!this.newAffaire.titre) {
  alert('Veuillez remplir le titre de l’affaire');
  return;
}

    this.affaireService.createAffaire(this.newAffaire).subscribe({
      next: () => {
        this.loadAffaires();

        this.newAffaire = {
          numeroAffaire: '',
          titre: '',
          description: '',
          statut: 'OUVERTE'
        };
      },
      error: (err) => {
        console.error('Erreur ajout affaire', err);
      }
    });
  }

  deleteAffaire(id?: number): void {
    if (!id) return;

    if (confirm('Voulez-vous vraiment supprimer cette affaire ?')) {
      this.affaireService.deleteAffaire(id).subscribe({
        next: () => {
          this.loadAffaires();
        },
        error: (err) => {
          console.error('Erreur suppression affaire', err);
        }
      });
    }
  }
}
