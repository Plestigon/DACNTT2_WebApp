package tdtu.ems.main.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu.ems.main.models.ProjectDto;

import java.util.List;

@Controller
@RequestMapping("/operations")
public class OperationManagementController {
    private final String OPERATIONS_PAGE_TITLE = "Operations Management";
    private WebClient.Builder _webClient;

    public OperationManagementController(WebClient.Builder webClient) {
        _webClient = webClient;
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("title", OPERATIONS_PAGE_TITLE);
        return "Operations/Operations";
    }

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    @ResponseBody
    public List<ProjectDto> getProjects() {
        List<ProjectDto> res = null;
        res = _webClient.build().get()
                .uri("http://operation-management-service/api/operations/projects")
                .retrieve()
                .bodyToFlux(ProjectDto.class)
                .collectList()
                .block();
        return res;
    }
}
