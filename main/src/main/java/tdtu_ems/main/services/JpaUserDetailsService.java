package tdtu_ems.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu_ems.main.models.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tdtu_ems.userservice.models.User;

import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    private final WebClient webClient;

    @Autowired
    public JpaUserDetailsService(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = webClient.get()
                .uri("http://localhost:8081/user",
                        uriBuilder -> uriBuilder.queryParam("email", email).build())
                .retrieve()
                .bodyToMono(User.class)
                .block();
        if (user == null) {
            throw new UsernameNotFoundException("Email not found: " + email);
        }
        return new SecurityUser(user);
    }
}
