package uz.pdp.appjwtrealemailauditing.controller;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjwtrealemailauditing.payload.CardDto;
import uz.pdp.appjwtrealemailauditing.service.CardService;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/card")
public class CardController {
    final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    //  CREATE
    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody CardDto cardDto){
        return ResponseEntity.status(200).body(cardService.addCard(cardDto));
    }
    //READ
    @GetMapping
    public HttpEntity<?> get(){
        return ResponseEntity.status(200).body(cardService.getCard());
    }
    //DELETE
    @DeleteMapping(value = "/{id}")
    public HttpEntity<?> delete(@PathVariable UUID id){
        return ResponseEntity.ok(cardService.deleteCard(id));
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
