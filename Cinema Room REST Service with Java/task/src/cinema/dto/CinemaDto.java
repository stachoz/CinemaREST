package cinema.dto;

import cinema.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CinemaDto {
    private int columns;
    private int rows;
    private List<Ticket> seats;
}
