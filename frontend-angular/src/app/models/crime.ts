import { Affaire } from './affaire';

export interface Crime {
  id?: number;
  typeCrime: string;
  description: string;
  dateCrime: string;
  adresse: string;
  ville: string;
  latitude?: number | null;
  longitude?: number | null;
  statut: string;
  affaire?: Affaire;
  
}
