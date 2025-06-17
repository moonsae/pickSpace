package com.space.backend.domain.studyroom.dto;

import com.space.backend.entity.enums.RoomsStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyRoomStatusRequest {
    @NotNull
    private RoomsStatus status;

}
