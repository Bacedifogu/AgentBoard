package com.agentboard.service;

import com.agentboard.dto.NoteRequest;
import com.agentboard.dto.NoteResponse;
import com.agentboard.model.Note;
import com.agentboard.model.NoteStatus;
import com.agentboard.model.User;
import com.agentboard.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserService userService;

    @Transactional
    public NoteResponse createNote(NoteRequest request, String username) {
        User author = userService.findByUsername(username);

        Note note = Note.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .status(request.getStatus() != null ? request.getStatus() : NoteStatus.TODO)
                .author(author)
                .build();

        Note saved = noteRepository.save(note);
        return toResponse(saved);
    }

    public List<NoteResponse> getAllNotes() {
        return noteRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<NoteResponse> getNotesByUsername(String username) {
        User user = userService.findByUsername(username);
        return noteRepository.findByAuthorOrderByCreatedAtDesc(user)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<NoteResponse> getNotesByStatus(NoteStatus status) {
        return noteRepository.findByStatusOrderByCreatedAtDesc(status)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public NoteResponse getNoteById(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found: " + id));
        return toResponse(note);
    }

    @Transactional
    public NoteResponse updateNote(Long id, NoteRequest request, String username) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found: " + id));

        if (!note.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("Not authorized to update this note");
        }

        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        if (request.getStatus() != null) {
            note.setStatus(request.getStatus());
        }

        Note updated = noteRepository.save(note);
        return toResponse(updated);
    }

    @Transactional
    public void deleteNote(Long id, String username) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found: " + id));

        if (!note.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("Not authorized to delete this note");
        }

        noteRepository.delete(note);
    }

    private NoteResponse toResponse(Note note) {
        return NoteResponse.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .status(note.getStatus())
                .authorUsername(note.getAuthor().getUsername())
                .createdAt(note.getCreatedAt())
                .updatedAt(note.getUpdatedAt())
                .build();
    }
}
