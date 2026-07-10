import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Affaire } from '../models/affaire';

@Injectable({
  providedIn: 'root'
})
export class AffaireService {

  private apiUrl = 'http://localhost:8081/api/affaires';

  constructor(private http: HttpClient) {}

  getAllAffaires(): Observable<Affaire[]> {
    return this.http.get<Affaire[]>(this.apiUrl);
  }

  createAffaire(affaire: Affaire): Observable<Affaire> {
    return this.http.post<Affaire>(this.apiUrl, affaire);
  }

  deleteAffaire(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/${id}`, {
      responseType: 'text'
    });
  }
  updateAffaire(id: number, affaire: Affaire): Observable<Affaire> {
  return this.http.put<Affaire>(`${this.apiUrl}/${id}`, affaire);
}
}
