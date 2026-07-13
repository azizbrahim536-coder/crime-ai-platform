import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface AiChatResponse {
  answer: string;
}

export interface CrimeClassificationResponse {
  typeCrime: string;
  confidence: number;
}

@Injectable({
  providedIn: 'root'
})
export class AiService {

  private apiUrl = 'http://localhost:8081/api/ai';

  constructor(private http: HttpClient) {}

  chat(question: string): Observable<AiChatResponse> {
    return this.http.post<AiChatResponse>(`${this.apiUrl}/chat`, {
      question: question
    });
  }

  classifyCrime(description: string): Observable<CrimeClassificationResponse> {
    return this.http.post<CrimeClassificationResponse>(`${this.apiUrl}/classify`, {
      description: description
    });
  }
}
