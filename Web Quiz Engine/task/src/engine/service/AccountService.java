package engine.service;

import engine.exceptions.AccountNameNotFoundException;
import engine.model.Account;
import engine.repository.AccountRepository;
import engine.security.MyAccountDetails;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    public List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();
        accountRepository.findAll().forEach(accounts::add);
        return accounts;
    }

    @Override
    public UserDetails loadUserByUsername(String accountname) throws UsernameNotFoundException {
        Account account = accountRepository.findAccountByEmail(accountname)
                .orElseThrow(() -> new AccountNameNotFoundException(accountname));

        return new MyAccountDetails(account);
    }

    public void addAccount(Account account) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = account.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        account.setPassword(encodedPassword);

        String email = account.getEmail();
        if (!email.matches("\\w+@\\w+\\.\\w+") || rawPassword.length() < 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (accountRepository.findAccountByEmail(email).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        accountRepository.save(account);
    }


}
