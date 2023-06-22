package com.example.todofamilyapi.security.services;


import com.example.todofamilyapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.Boolean.TRUE;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final var user = userService.findByEmail(email);

        final var authorityListAdmin = createAuthorityList("ROLE_USER", "ROLE_ADMIN");
        final var authorityListUser = createAuthorityList("ROLE_USER");

        return UserDetailsImpl.build(user, TRUE.equals(user.isAdmin()) ? authorityListAdmin : authorityListUser);
    }
}
