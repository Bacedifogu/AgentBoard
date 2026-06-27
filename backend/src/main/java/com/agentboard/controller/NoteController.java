package com.agentboard.controller;

import com.agentboard.dto.NoteRequest;
import com.agentboard.dto.NoteResponse;
import com.agentboard.model.NoteStatus;
import com.agentboard.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteResponse> createNote(
            @Valid @RequestBody NoteRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        NoteResponse note = noteService.createNote(request, userDetails.getUsername());
        return ResponseEntity.ok(note);
    }

    @GetMapping
    public ResponseEntity<List<NoteResponse>> getAllNotes() {
        List<NoteResponse> notes = noteService.getAllNotes();
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable Long id) {
        NoteResponse note = noteService.getNoteById(id);
        return ResponseEntity.ok(note);
    }

    @GetMapping("/my")
    public ResponseEntity<List<NoteResponse>> getMyNotes(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        List<NoteResponse> notes = noteService.getNotesByUsername(userDetails.getUsername());
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<NoteResponse>> getNotesByStatus(@PathVariable NoteStatus status) {
        List<NoteResponse> notes = noteService.getNotesByStatus(status);
        return ResponseEntity.ok(notes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNote(
            @PathVariable Long id,
            @Valid @RequestBody NoteRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        NoteResponse note = noteService.updateNote(id, request, userDetails.getUsername());
        return ResponseEntity.ok(note);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        noteService.deleteNote(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
