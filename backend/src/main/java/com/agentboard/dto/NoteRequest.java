package com.agentboard.dto;

import com.agentboard.model.NoteStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteRequest {

    @NotBlank
    @Size(max = 200)
    private String title;

    private String content;

    private NoteStatus status;
}
