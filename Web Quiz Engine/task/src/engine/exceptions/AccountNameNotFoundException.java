package engine.exceptions;

public class AccountNameNotFoundException extends RuntimeException {

    public AccountNameNotFoundException (String name) {
        super("Account with name " + name + " was not found");
    }

}
