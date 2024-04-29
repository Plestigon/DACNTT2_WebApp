package tdtu.ems.main.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu.ems.main.models.EmployeeDto;
import tdtu.ems.main.models.ProjectCreateDto;
import tdtu.ems.main.models.ProjectResult;

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
    public List<ProjectResult> getProjects() {
        List<ProjectResult> res = null;
        res = _webClient.build().get()
                .uri("http://operation-management-service/api/operations/projects")
                .retrieve()
                .bodyToFlux(ProjectResult.class)
                .collectList()
                .block();
        return res;
    }

    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    @ResponseBody
    public List<EmployeeDto> getEmployees() {
        List<EmployeeDto> res = null;
        res = _webClient.build().get()
                .uri("http://employee-service/api/employees")
                .retrieve()
                .bodyToFlux(EmployeeDto.class)
                .collectList()
                .block();
        return res;
    }

    @RequestMapping(value = "/project", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> createProject(@RequestBody ProjectCreateDto project) {
        String res = null;
        try {
            res = _webClient.build().post()
                    .uri("http://operation-management-service/api/operations/projects")
                    .bodyValue(project)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @RequestMapping(value = "/project", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> deleteProject(@RequestParam("id") String id) {
        String res = null;
        try {
            res = _webClient.build().delete()
                    .uri("http://operation-management-service/api/operations/projects/" + id)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
