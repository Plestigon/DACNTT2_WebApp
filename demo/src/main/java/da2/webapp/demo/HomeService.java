package da2.webapp.demo;

import org.springframework.stereotype.Service;

@Service
public class HomeService {
    public String index() {
        return "Landing_page";
    }
}
