export interface Affaire {
  id?: number;
  numeroAffaire?: string;
  titre: string;
  description: string;
  statut: string;
  dateCreation?: string;
}
