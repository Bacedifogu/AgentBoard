package com.agentboard.repository;

import com.agentboard.model.Note;
import com.agentboard.model.NoteStatus;
import com.agentboard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByAuthor(User author);

    List<Note> findByStatus(NoteStatus status);

    List<Note> findByAuthorOrderByCreatedAtDesc(User author);

    List<Note> findByStatusOrderByCreatedAtDesc(NoteStatus status);

    List<Note> findAllByOrderByCreatedAtDesc();
}
