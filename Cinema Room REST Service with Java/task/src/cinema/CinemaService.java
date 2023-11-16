package cinema;

import cinema.dto.CinemaDto;
import cinema.dto.StatisticsDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CinemaService {
    private int columns = 9;
    private int rows = 9;
    private List<Ticket> seats = new ArrayList<>();
    @JsonIgnore
    private List<PurchaseResponse> purchasedTickets = new ArrayList<>();
    @JsonIgnore
    private String PASSWORD = "super_secret";

    CinemaService(){
        for(int i = 1; i <= rows; i++){
            for(int j = 1; j <= columns; j++){
                seats.add(new Ticket(i, j));
            }
        }
    }

    public Optional<PurchaseResponse> purchase(int row, int column) {
        Ticket toPurchase = seats.stream()
                .filter(ticket -> ticket.getRow() == row && ticket.getColumn() == column)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("The number of a row or a column is out of bound"));
        if(toPurchase.isPurchased()) return Optional.empty();
        else {
            toPurchase.setPurchased(true);
            PurchaseResponse purchased = new PurchaseResponse(toPurchase);
            purchasedTickets.add(purchased);
            return Optional.of(purchased);
        }
    }

    public Ticket returnTicket(UUID token){
        PurchaseResponse purchaseToReturn = purchasedTickets.stream()
                .filter(purchased -> purchased.getToken().equals(token))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Wrong token!"));
        Ticket returnedTicket = purchaseToReturn.getTicket();
        purchasedTickets.remove(purchaseToReturn);
        returnedTicket.setPurchased(false);
        return returnedTicket;
    }

    public StatisticsDto generateStatistics(){
        int income = purchasedTickets.stream().mapToInt(purchasedTicket -> purchasedTicket.getTicket().getPrice()).sum();
        int purchased = purchasedTickets.size();
        int available = columns * rows - purchased;
        return new StatisticsDto(income, available, purchased);
    }
    //ucp
    public boolean authenticate(String password){
        return password.equals(PASSWORD);
    }

    public CinemaDto getCinemaInfo(){
        return new CinemaDto(columns, rows, seats);
    }
}
