package com.kakan.forum_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateReportRequestDto {

    @Min(value = 1, message = "Chưa có message!")
    Integer accountId;

    @NotBlank(message = "Chưa có reason!")
    String reason;
}
