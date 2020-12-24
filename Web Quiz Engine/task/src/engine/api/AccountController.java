package engine.api;

import engine.model.Account;
import engine.repository.AccountRepository;
import engine.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;


    @PostMapping(value = "api/register", produces = "application/json")
    public void registerNewUser(@RequestBody Account account) {
        accountService.addAccount(account);
    }

    @GetMapping(value = "api/accounts", produces = "application/json")
    public List<Account> getAccounts() {
        return accountService.getAccounts();
    }
}
