import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Crime } from '../models/crime';

@Injectable({
  providedIn: 'root'
})
export class CrimeService {



  private apiUrl = 'http://localhost:8081/api/crimes';

  constructor(private http: HttpClient) {}

  getAllCrimes(): Observable<Crime[]> {
    return this.http.get<Crime[]>(this.apiUrl);
  }

  createCrime(crime: Crime): Observable<Crime> {
    return this.http.post<Crime>(this.apiUrl, crime);
  }

  deleteCrime(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/${id}`, {
      responseType: 'text'
    });
  }

  searchCrimes(typeCrime: string, ville: string, statut: string): Observable<Crime[]> {
    let params = new HttpParams();

    if (typeCrime) {
      params = params.set('typeCrime', typeCrime);
    }

    if (ville) {
      params = params.set('ville', ville);
    }

    if (statut) {
      params = params.set('statut', statut);
    }

    return this.http.get<Crime[]>(`${this.apiUrl}/search`, { params });
  }
updateCrime(id: number, crime: Crime): Observable<Crime> {
  return this.http.put<Crime>(`${this.apiUrl}/${id}`, crime);
}
getCrimesByAffaire(affaireId: number): Observable<Crime[]> {
  return this.http.get<Crime[]>(`${this.apiUrl}/affaire/${affaireId}`);
}

}
