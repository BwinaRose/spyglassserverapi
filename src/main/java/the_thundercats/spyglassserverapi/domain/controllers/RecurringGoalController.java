package the_thundercats.spyglassserverapi.domain.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import the_thundercats.spyglassserverapi.domain.core.exceptions.ResourceNotFoundException;
import the_thundercats.spyglassserverapi.domain.models.RecurringGoal;
import the_thundercats.spyglassserverapi.domain.services.RecurringGoalService;

import java.util.List;

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
    public ResponseEntity<RecurringGoal> create(@PathVariable("id") String userId, @RequestBody RecurringGoal goal) throws ResourceNotFoundException {
        goal = recurringGoalService.create(userId, goal);
        return new ResponseEntity<>(goal, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<RecurringGoal> getById(@PathVariable("id") Long userId) throws ResourceNotFoundException {
        RecurringGoal goal = recurringGoalService.getById(userId);
        return new ResponseEntity<>(goal, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<List<RecurringGoal>> getAllFromUser(@PathVariable("id") String userId) throws ResourceNotFoundException {
        List<RecurringGoal> goals = recurringGoalService.getAllFromUser(userId);
        return new ResponseEntity<>(goals, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<RecurringGoal> update(@PathVariable("id") Long userId, @RequestBody RecurringGoal details) throws ResourceNotFoundException {
        RecurringGoal goal = recurringGoalService.update(userId, details);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long userId) throws ResourceNotFoundException {
        recurringGoalService.delete(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
