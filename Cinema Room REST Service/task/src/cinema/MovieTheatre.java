package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MovieTheatre {

    @JsonProperty("total_rows")
    private int totalRows = 9;
    @JsonProperty("total_columns")
    private int totalColumns = 9;
    @JsonProperty("available_seats")
    private final List<Seat> availableSeats = new ArrayList<>();

    public MovieTheatre() {
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalColumns; j++) {
                availableSeats.add(new Seat(i + 1, j + 1, i <= 3 ? 10 : 8));
            }
        }
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public void setTotalColumns(int totalColumns) {
        this.totalColumns = totalColumns;
    }

    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }

    public Seat purchaseTicket(int row, int column) {
        for (int i = 0; i < availableSeats.size(); i++) {
            Seat seat = availableSeats.get(i);
            if (seat.getRow() == row && seat.getColumn() == column) {
                availableSeats.remove(i);
                return seat;
            }
        }

        return new Seat(0, 0, 0);
    }

    public Map<String, Seat> returnTicket(Seat seatReturn) {

        availableSeats.add(seatReturn);
        return Map.of("returned_ticket", seatReturn);
    }

    public boolean isAvailable(int row, int column) {
        for (Seat availableSeat : availableSeats) {
            if (availableSeat.getRow() == row && availableSeat.getColumn() == column) {
                return true;
            }
        }

        return false;
    }

}
