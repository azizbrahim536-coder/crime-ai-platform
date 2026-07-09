export interface DashboardStats {
  totalCrimes: number;
  totalAffaires: number;
  crimesParType: { [key: string]: number };
  crimesParVille: { [key: string]: number };
  crimesParStatut: { [key: string]: number };
  affairesParStatut: { [key: string]: number };
}
