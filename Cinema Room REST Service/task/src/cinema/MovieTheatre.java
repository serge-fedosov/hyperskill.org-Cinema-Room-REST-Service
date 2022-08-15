package cinema;

import java.io.Serializable;

public class MovieTheatre {
    private int total_rows = 9;
    private int total_columns = 9;
    private final Seat[] available_seats = new Seat[total_rows * total_columns];

    public MovieTheatre() {
        for (int i = 0; i < total_rows; i++) {
            for (int j = 0; j < total_columns; j++) {
                available_seats[i * total_rows + j] = new Seat(i + 1, j + 1);
            }
        }
    }

    public int getTotal_rows() {
        return total_rows;
    }

    public void setTotal_rows(int total_rows) {
        this.total_rows = total_rows;
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public void setTotal_columns(int total_columns) {
        this.total_columns = total_columns;
    }

    public Seat[] getAvailable_seats() {
        return available_seats;
    }

}
