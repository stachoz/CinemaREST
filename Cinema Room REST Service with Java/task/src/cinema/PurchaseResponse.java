package cinema;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PurchaseResponse {
    private UUID token;
    private Ticket ticket;
    PurchaseResponse(Ticket ticket){
        this.ticket = ticket;
        token = UUID.randomUUID();
    }
}
