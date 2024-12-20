import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { IWorkstation } from '../interfaces/workstation';

@Injectable({providedIn: 'root'})
export class WorkstationService {
  private baseUrl = `${environment.apiUrl}/workstations`;

  constructor(private http: HttpClient) {}

  search(type: string, city: string): Observable<IWorkstation[]> {
    let params = new HttpParams().set('type', type).set('city', city);
    return this.http.get<IWorkstation[]>(`${this.baseUrl}/search`, { params });
  }

  getById(id: number): Observable<IWorkstation> {
    return this.http.get<IWorkstation>(`${this.baseUrl}/${id}`);
  }
}
