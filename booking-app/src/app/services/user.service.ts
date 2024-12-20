import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { IUser } from '../interfaces/user';

@Injectable({providedIn: 'root'})
export class UserService {
  private baseUrl = `${environment.apiUrl}/users`;

  constructor(private http: HttpClient) {}

  createUser(user: IUser): Observable<IUser> {
    return this.http.post<IUser>(this.baseUrl, user);
  }

  getUser(username: string): Observable<IUser> {
    return this.http.get<IUser>(`${this.baseUrl}/${username}`);
  }
}
