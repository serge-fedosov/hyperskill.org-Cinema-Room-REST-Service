package cinema;

public class Seat extends SeatWithoutPrice {

    private int price;

        public Seat(int row, int column, int price) {
        super(row, column);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
