package cinema;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    private MovieTheatre seats = new MovieTheatre();

    @GetMapping("/seats")
    public MovieTheatre getSeats() {
        return seats;
    }

}
