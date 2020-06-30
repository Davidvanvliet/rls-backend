package nl.rls.ci.controller;

import io.swagger.annotations.ApiOperation;
import nl.rls.ci.domain.LiTechnicalAckDto;
//import nl.rls.ci.service.CiService;
import nl.rls.ci.service.CiService;
import nl.rls.ci.url.BaseURL;
import nl.rls.util.Response;
import nl.rls.util.ResponseBuilder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(BaseURL.BASE_PATH + "/trains")
public class TcmController {
    private final CiService ciService;

    public TcmController(CiService ciService) {
        this.ciService = ciService;
    }

    @ApiOperation(value = "Constructs a tcm-message from data and puts it into de CI-buffer")
    @PostMapping(value = "{trainId}/send")
    public Response<LiTechnicalAckDto> sendTcm(@PathVariable Integer trainId) {
        return ResponseBuilder.ok().data(ciService.sendMessageToCi(trainId)).build();
    }
}
