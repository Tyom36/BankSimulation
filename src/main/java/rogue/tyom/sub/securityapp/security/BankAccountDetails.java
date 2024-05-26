package rogue.tyom.sub.securityapp.security;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rogue.tyom.sub.securityapp.models.BankAccount;

import java.util.Collection;

@RequiredArgsConstructor
public class BankAccountDetails implements UserDetails {

    private final BankAccount bankAccount;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return bankAccount.getPassword();
    }

    @Override
    public String getUsername() {
        return bankAccount.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }
}
