package cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatisticsDto {
    private int income;
    private int available;
    private int purchased;
}
