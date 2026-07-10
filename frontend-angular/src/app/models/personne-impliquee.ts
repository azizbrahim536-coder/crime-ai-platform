import { Affaire } from './affaire';

export interface PersonneImpliquee {
  id?: number;
  nom: string;
  prenom: string;
  dateNaissance: string;
  genre: string;
  telephone: string;
  adresse: string;
  typePersonne: string;
  notes: string;
  affaire?: Affaire;
}
