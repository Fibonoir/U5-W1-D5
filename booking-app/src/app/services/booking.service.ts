import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { IBooking } from '../interfaces/booking';

@Injectable({providedIn: 'root'})
export class BookingService {
  private baseUrl = `${environment.apiUrl}/bookings`;

  constructor(private http: HttpClient) {}

  createBooking(username: string, workstationId: number, date: string): Observable<IBooking> {
    return this.http.post<IBooking>(`${this.baseUrl}/${username}`, { workstationId, date });
  }
}
