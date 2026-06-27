import { Component, signal, computed, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NoteService, AuthService } from '../../services';
import { Note, NoteStatus } from '../../models';

@Component({
  selector: 'app-board',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './board.component.html',
  styleUrl: './board.component.scss',
})
export class BoardComponent implements OnInit {
  notes = signal<Note[]>([]);
  loading = signal(false);
  showCreateForm = signal(false);

  newNoteTitle = '';
  newNoteContent = '';
  newNoteStatus: NoteStatus = NoteStatus.TODO;

  statuses = [NoteStatus.TODO, NoteStatus.IN_PROGRESS, NoteStatus.DONE];

  todoNotes = computed(() => this.notes().filter(n => n.status === NoteStatus.TODO));
  inProgressNotes = computed(() => this.notes().filter(n => n.status === NoteStatus.IN_PROGRESS));
  doneNotes = computed(() => this.notes().filter(n => n.status === NoteStatus.DONE));

  protected readonly noteStatuses = NoteStatus;

  constructor(
    private noteService: NoteService,
    protected authService: AuthService
  ) {}

  ngOnInit() {
    this.loadNotes();
  }

  loadNotes() {
    this.loading.set(true);
    this.noteService.getAllNotes().subscribe({
      next: (notes) => {
        this.notes.set(notes);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }

  createNote() {
    if (!this.newNoteTitle.trim()) return;

    this.noteService.createNote({
      title: this.newNoteTitle,
      content: this.newNoteContent,
      status: this.newNoteStatus
    }).subscribe({
      next: (note) => {
        this.notes.update(notes => [...notes, note]);
        this.cancelCreate();
      }
    });
  }

  updateNoteStatus(note: Note, newStatus: NoteStatus) {
    if (note.status === newStatus) return;

    this.noteService.updateNote(note.id!, { status: newStatus }).subscribe({
      next: (updated) => {
        this.notes.update(notes => notes.map(n => n.id === updated.id ? updated : n));
      }
    });
  }

  deleteNote(note: Note) {
    if (!confirm('Delete this note?')) return;

    this.noteService.deleteNote(note.id!).subscribe({
      next: () => {
        this.notes.update(notes => notes.filter(n => n.id !== note.id));
      }
    });
  }

  cancelCreate() {
    this.newNoteTitle = '';
    this.newNoteContent = '';
    this.newNoteStatus = NoteStatus.TODO;
    this.showCreateForm.set(false);
  }

  logout() {
    this.authService.logout();
  }

  getNotesByStatus(status: NoteStatus): Note[] {
    return this.notes().filter(n => n.status === status);
  }
}
