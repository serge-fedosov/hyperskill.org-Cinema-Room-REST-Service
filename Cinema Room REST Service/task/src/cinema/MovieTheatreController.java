package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class MovieTheatreController {

    private MovieTheatre seats = new MovieTheatre();
    private HashMap<String, Seat> tokens = new HashMap();

    @GetMapping("/seats")
    public MovieTheatre getSeats() {
        return seats;
    }

    @PostMapping("/purchase")
    public TicketWithToken purchaseTicket(@RequestBody SeatWithoutPrice seat) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        int row = seat.getRow();
        int column = seat.getColumn();

        int totalRows = seats.getTotalRows();
        int totalColumns = seats.getTotalColumns();

        if (row < 1 || row > totalRows || column < 1 || column > totalColumns) {
            throw new TicketException("The number of a row or a column is out of bounds!");
        } else if (!seats.isAvailable(row, column)) {
            throw new TicketException("The ticket has been already purchased!");
        } else {
            UUID uuid = UUID.randomUUID();
            Seat seatSell = seats.purchaseTicket(row, column);
            tokens.put(uuid.toString(), seatSell);

            return new TicketWithToken(uuid.toString(), seatSell);
        }
    }

    @PostMapping("/return")
    public Map<String, Seat> returnTicket(@RequestBody HashMap<String, String> tokenMap) {

        String token = tokenMap.get("token");

        if (tokens.containsKey(token)) {
            Seat seatReturn = tokens.get(token);
            tokens.remove(token);
            return seats.returnTicket(seatReturn);
        } else {
            throw new TicketException("Wrong token!");
        }
    }

    @PostMapping("/stats")
    public Map<String, Integer> returnStats(@RequestParam(required = false) String password) {

        if ("super_secret".equals(password)) {
            int currentIncome = 0;
            for (var token : tokens.entrySet()) {
                currentIncome += token.getValue().getPrice();
            }
            int numberOfAvailableSeats = seats.getAvailableSeats().size();
            int numberOfPurchasedTickets = tokens.size();

            return Map.of("current_income", currentIncome,
                    "number_of_available_seats", numberOfAvailableSeats,
                    "number_of_purchased_tickets", numberOfPurchasedTickets);
        } else {
            throw new PasswordException("The password is wrong!");
        }
    }

    @ExceptionHandler(TicketException.class)
    public ResponseEntity<CustomErrorMessage> handleTicketException(TicketException e, WebRequest request) {

        CustomErrorMessage body = new CustomErrorMessage(e.getLocalizedMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<CustomErrorMessage> handlePasswordException(PasswordException e, WebRequest request) {

        CustomErrorMessage body = new CustomErrorMessage(e.getLocalizedMessage());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }
}
