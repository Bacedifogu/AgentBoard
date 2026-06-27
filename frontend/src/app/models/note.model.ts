import { NoteStatus } from './note-status.model';

export interface Note {
  id?: number;
  title: string;
  content: string;
  status: NoteStatus;
  authorId?: number;
  authorUsername?: string;
  createdAt?: string;
  updatedAt?: string;
}

export interface CreateNoteRequest {
  title: string;
  content: string;
  status: NoteStatus;
}

export interface UpdateNoteRequest {
  title?: string;
  content?: string;
  status?: NoteStatus;
}
