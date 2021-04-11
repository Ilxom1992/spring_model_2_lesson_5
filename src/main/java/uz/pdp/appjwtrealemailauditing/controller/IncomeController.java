package uz.pdp.appjwtrealemailauditing.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjwtrealemailauditing.payload.ApiResponse;
import uz.pdp.appjwtrealemailauditing.payload.IncomeDto;
import uz.pdp.appjwtrealemailauditing.service.IncomeService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/income")
public class IncomeController {
 final   IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    //  CREATE
    @PostMapping
    public HttpEntity<?> addMoney(@Valid @RequestBody IncomeDto incomeDto){
        return ResponseEntity.status(200).body(new ApiResponse("Success",true,incomeService.incomeMoney(incomeDto)));
    }
    //READ
    @GetMapping
    public HttpEntity<?> get(){
        return ResponseEntity.status(200).body(new ApiResponse("Success",true,incomeService.getIncomeMoney()));
    }
    //DELETE
    @DeleteMapping(value = "/{id}")
    public HttpEntity<?> delete(@PathVariable UUID id){
        return ResponseEntity.ok(incomeService.getIncomeMoney());
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
