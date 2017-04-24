package com.spring.boot.vlt.mvc.controller.rlcp;

import com.spring.boot.vlt.mvc.model.UserContext;
import com.spring.boot.vlt.mvc.model.entity.Attempts;
import com.spring.boot.vlt.mvc.model.entity.rlcp.CheckRlcp;
import com.spring.boot.vlt.mvc.model.entity.rlcp.GenerateRlcp;
import com.spring.boot.vlt.mvc.service.LaboratoryFrameService;
import com.spring.boot.vlt.mvc.service.VltService;
import com.spring.boot.vlt.mvc.service.rlcp.RlcpDataBaseService;
import com.spring.boot.vlt.mvc.service.rlcp.RlcpMethodService;
import com.spring.boot.vlt.security.JwtAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rlcp.calculate.CalculatingResult;
import rlcp.check.ConditionForChecking;
import rlcp.check.RlcpCheckResponse;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "/api")
public class RlcpMethodController {
    @Autowired
    private RlcpMethodService rlcpMethodService;
    @Autowired
    private RlcpDataBaseService rlcpDataBaseService;
    @Autowired
    LaboratoryFrameService laboratoryFrameService;
    @Autowired
    private VltService vltService;


    @RequestMapping(value = "/{dir}/get_generate", method = RequestMethod.GET)
    public ResponseEntity<GenerateRlcp> getGenerate(JwtAuthenticationToken token, @PathVariable("dir") String dir, @RequestParam("algorithm") String algorithm) {
        UserContext userContext = (UserContext) token.getPrincipal();
        Attempts attempt = vltService.saveAttempt(userContext.getUsername(), dir);

        String url = attempt.getLab().getUrl();
        if (url != null) {
            Optional<GenerateRlcp> result = rlcpDataBaseService.saveGenerateResult(
                    attempt,
                    rlcpMethodService.getGenerate(algorithm, url)
            );
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT);
    }

    @RequestMapping(value = "/repeat", method = RequestMethod.GET)
    public ResponseEntity<GenerateRlcp> repeat(JwtAuthenticationToken token, @RequestParam("session") String session) {
        return new ResponseEntity<>(rlcpDataBaseService.saveRepeatGenerateResult(session).get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{dir}/get_calculate", method = RequestMethod.GET)
    public ResponseEntity<CalculatingResult> getCalculate(JwtAuthenticationToken token,
                                                          @PathVariable("dir") String dir,
                                                          @RequestParam("session") String session,
                                                          @RequestParam("instructions") String instructions,
                                                          @RequestParam("condition") String condition) {
        String url = vltService.getUrl(dir);
        if (url != null) {
            CalculatingResult result = rlcpMethodService.getCalculate(url, session, instructions, condition);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT);
    }

    @RequestMapping(value = "/{dir}/{frameId}/get_check", method = RequestMethod.GET)
    public ResponseEntity<CheckRlcp> getCheck(JwtAuthenticationToken token,
                                              @PathVariable("dir") String dirName,
                                              @PathVariable("frameId") String frameId,
                                              @RequestParam("session") String session,
                                              @RequestParam("instructions") String instructions) {
        UserContext userContext = (UserContext) token.getPrincipal();
        Attempts attempt = vltService.findAttemptBySession(session);

        String url = attempt.getLab().getUrl();
        if (url != null &&
                attempt.getUser().getLogin().equals(userContext.getUsername()) &&
                attempt.getLab().getDirName().equals(dirName)) {
            laboratoryFrameService.setPreCondition(dirName, frameId);
            List<ConditionForChecking> checks = laboratoryFrameService.getCheckList();

            RlcpCheckResponse rlcpResponse = rlcpMethodService.getCheck(url, session, checks, instructions);
            Optional<CheckRlcp> checkRlcp = rlcpDataBaseService.saveCheckResult(attempt, rlcpResponse.getBody().getResults().get(0));

            return new ResponseEntity<>(checkRlcp.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT);
    }

}
