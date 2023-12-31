package page.aaws.springex.dto;

import lombok.*;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;

@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {
    private Long tno;

    @NotEmpty
    private String title;

    private LocalDate dueDt;

    private boolean finished;

    @NotEmpty
    private String writer;
}
