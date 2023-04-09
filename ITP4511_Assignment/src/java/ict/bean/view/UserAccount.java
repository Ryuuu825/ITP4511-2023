package ict.bean.view;

import ict.bean.Account;
import ict.bean.User;

public class UserAccount {

    private User user;
    private Account account;

    public UserAccount() {
    }

    public UserAccount(User user, Account account) {
        this.user = user;
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserAccount{" + "user=" + user + ", account=" + account + '}';
    }
    
}
