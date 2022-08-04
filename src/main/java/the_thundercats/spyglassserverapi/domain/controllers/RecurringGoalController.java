package the_thundercats.spyglassserverapi.domain.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceNotFoundException;
import the_thundercats.spyglassserverapi.domain.models.RecurringGoal;
import the_thundercats.spyglassserverapi.domain.services.RecurringGoalService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/recurring-goals")
public class RecurringGoalController {

    private RecurringGoalService recurringGoalService;

    @Autowired
    public RecurringGoalController(RecurringGoalService recurringGoalService) {
        this.recurringGoalService = recurringGoalService;
    }

    @PostMapping("{id}")
    public ResponseEntity<RecurringGoal> create(@PathVariable("id") Long userId, @RequestBody RecurringGoal goal) throws ResourceNotFoundException {
        goal = recurringGoalService.create(userId, goal);
        return new ResponseEntity<>(goal, HttpStatus.CREATED);
    }
}
