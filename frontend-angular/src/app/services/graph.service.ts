import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GraphResponse } from '../models/graph';

@Injectable({
  providedIn: 'root'
})
export class GraphService {

  private apiUrl = 'http://localhost:8081/api/graph';

  constructor(private http: HttpClient) {}

  getAffaireGraph(affaireId: number): Observable<GraphResponse> {
    return this.http.get<GraphResponse>(`${this.apiUrl}/affaire/${affaireId}`);
  }
}
