package page.aaws.springex.domain;

import java.time.LocalDate;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoVo {
    private Long tno;
    private String title;
    private LocalDate dueDt;
    private String writer;
    private boolean finished;
}
