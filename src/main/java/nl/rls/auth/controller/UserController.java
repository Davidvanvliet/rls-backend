package nl.rls.auth.controller;

import nl.rls.auth.rest.dto.UserDto;
import nl.rls.auth.service.OwnerService;
import nl.rls.ci.url.BaseURL;
import nl.rls.util.Response;
import nl.rls.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BaseURL.BASE_PATH + "/owners")
public class UserController {

    private final OwnerService ownerService;

    public UserController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/{ownerId}/users")
    @ResponseStatus(HttpStatus.OK)
    public Response<List<UserDto>> getUsersByOwnerId(@PathVariable Integer ownerId) {
        List<UserDto> users = ownerService.getUsersByOwnerId(ownerId);
        return ResponseBuilder.ok()
                .data(users)
                .build();
    }
}
