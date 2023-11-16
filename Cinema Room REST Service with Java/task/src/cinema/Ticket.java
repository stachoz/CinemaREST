package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Ticket {
    private int row;
    private int column;
    private int price;
    @JsonIgnore
    private boolean purchased;
    Ticket(int row, int column){
        this.row = row;
        this.column = column;
        if(row <= 4) this.price = 10;
        else this.price = 8;
    }
}
