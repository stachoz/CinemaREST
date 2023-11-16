package cinema;

import cinema.dto.CinemaDto;
import cinema.dto.PurchaseDto;
import cinema.dto.StatisticsDto;
import cinema.dto.TokenDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("")
public class CinemaController {
    private final CinemaService cinemaService;

    public CinemaController(CinemaService cinemaService){
        this.cinemaService = cinemaService;
    }

    @GetMapping("/seats")
    ResponseEntity<CinemaDto> seats(){
        return new ResponseEntity<>(cinemaService.getCinemaInfo(), HttpStatus.OK);
    }

    @PostMapping("/purchase")
    ResponseEntity<?> purchase(@RequestBody PurchaseDto purchase) {
        try {
            return cinemaService.purchase(purchase.getRow(), purchase.getColumn())
                    .map(ticket -> new ResponseEntity(ticket, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity(Map.of("error","The ticket has been already purchased!"), HttpStatus.BAD_REQUEST));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("error","The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/return")
    ResponseEntity<?> returnTicket(@RequestBody TokenDto tokenDto){
        try {
            Ticket returnedTicket = cinemaService.returnTicket(tokenDto.getToken());
            return new ResponseEntity<>(Map.of("ticket",returnedTicket), HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(Map.of("error","Wrong token!"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/stats")
    ResponseEntity<?> getStatistics(@RequestParam(defaultValue = "", required = false) String password){
        if(cinemaService.authenticate(password)){
            StatisticsDto statisticsDto = cinemaService.generateStatistics();
            return new ResponseEntity<>(statisticsDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
    }
}