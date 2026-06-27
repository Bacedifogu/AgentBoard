import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Note, CreateNoteRequest, UpdateNoteRequest } from '../models';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class NoteService {
  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('auth_token');
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  getAllNotes(): Observable<Note[]> {
    return this.http.get<Note[]>(`${environment.apiUrl}/api/notes`, {
      headers: this.getHeaders()
    });
  }

  getNoteById(id: number): Observable<Note> {
    return this.http.get<Note>(`${environment.apiUrl}/api/notes/${id}`, {
      headers: this.getHeaders()
    });
  }

  getMyNotes(): Observable<Note[]> {
    return this.http.get<Note[]>(`${environment.apiUrl}/api/notes/my`, {
      headers: this.getHeaders()
    });
  }

  createNote(note: CreateNoteRequest): Observable<Note> {
    return this.http.post<Note>(`${environment.apiUrl}/api/notes`, note, {
      headers: this.getHeaders()
    });
  }

  updateNote(id: number, note: UpdateNoteRequest): Observable<Note> {
    return this.http.put<Note>(`${environment.apiUrl}/api/notes/${id}`, note, {
      headers: this.getHeaders()
    });
  }

  deleteNote(id: number): Observable<void> {
    return this.http.delete<void>(`${environment.apiUrl}/api/notes/${id}`, {
      headers: this.getHeaders()
    });
  }
}
