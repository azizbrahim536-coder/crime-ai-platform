import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PersonneImpliquee } from '../models/personne-impliquee';

@Injectable({
  providedIn: 'root'
})
export class PersonneImpliqueeService {

  private apiUrl = 'http://localhost:8081/api/personnes';

  constructor(private http: HttpClient) {}

  getAllPersonnes(): Observable<PersonneImpliquee[]> {
    return this.http.get<PersonneImpliquee[]>(this.apiUrl);
  }

  createPersonne(personne: PersonneImpliquee): Observable<PersonneImpliquee> {
    return this.http.post<PersonneImpliquee>(this.apiUrl, personne);
  }

  updatePersonne(id: number, personne: PersonneImpliquee): Observable<PersonneImpliquee> {
    return this.http.put<PersonneImpliquee>(`${this.apiUrl}/${id}`, personne);
  }

  deletePersonne(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/${id}`, {
      responseType: 'text'
    });
  }
  getPersonnesByAffaire(affaireId: number): Observable<PersonneImpliquee[]> {
  return this.http.get<PersonneImpliquee[]>(`${this.apiUrl}/affaire/${affaireId}`);
}
}
