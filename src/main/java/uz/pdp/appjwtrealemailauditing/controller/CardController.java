package uz.pdp.appjwtrealemailauditing.controller;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjwtrealemailauditing.payload.CardDto;
import uz.pdp.appjwtrealemailauditing.payload.Response;
import uz.pdp.appjwtrealemailauditing.service.CardService;

import javax.servlet.http.HttpServletRequest;
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

    @PostMapping("/add")
    public HttpEntity<?> addCard(@RequestBody CardDto cardDto,HttpServletRequest httpServletRequest) {
        final Response response = cardService.add(cardDto,httpServletRequest);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PostMapping("/transfer")
    public HttpEntity<?> transfer(@RequestParam Double amount,
                                  @RequestParam UUID senderCardId,
                                  @RequestParam UUID recipientCardId) {
        final Response response = cardService.transfer(amount, senderCardId, recipientCardId);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PostMapping("/deposit")
    public HttpEntity<?> deposit(@RequestParam Double amount,
                                 @RequestParam UUID cardId) {
        final Response response = cardService.deposit(amount,cardId);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PostMapping("/withdrawal")
    public HttpEntity<?> withdrawal(@RequestParam Double amount,
                                    @RequestParam UUID cardId) {
        final Response response = cardService.withdrawal(amount,cardId);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
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
