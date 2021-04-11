package uz.pdp.appjwtrealemailauditing.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjwtrealemailauditing.payload.IncomeDto;
import uz.pdp.appjwtrealemailauditing.payload.OutcomeDto;
import uz.pdp.appjwtrealemailauditing.service.OutcomeService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/outcome")
public class OutcomeController {
    final OutcomeService outcomeService;

    public OutcomeController(OutcomeService outcomeService) {
        this.outcomeService = outcomeService;
    }
    //  CREATE
    @PostMapping
    public HttpEntity<?> sendMoney(@Valid @RequestBody OutcomeDto outcomeDto){
        return ResponseEntity.status(200).body(outcomeService.sendMoney(outcomeDto));
    }
    //READ
    @GetMapping
    public HttpEntity<?> getOutcome(){
        return ResponseEntity.status(200).body(outcomeService.get());
    }
    //DELETE
    @DeleteMapping(value = "/{id}")
    public HttpEntity<?> deleteOutcome(@PathVariable UUID id){
        return ResponseEntity.ok(outcomeService.delete(id));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


}
