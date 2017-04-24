package com.spring.boot.vlt.mvc.service.rlcp;

import com.spring.boot.vlt.mvc.model.entity.Attempts;
import com.spring.boot.vlt.mvc.model.entity.rlcp.CheckRlcp;
import com.spring.boot.vlt.mvc.model.entity.rlcp.GenerateRlcp;
import com.spring.boot.vlt.mvc.repository.rlcp.CheckRlcpRepository;
import com.spring.boot.vlt.mvc.repository.rlcp.GenerateRlcpRepository;
//import com.spring.boot.vlt.mvc.service.AttemptsService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rlcp.check.CheckingResult;
import rlcp.generate.GeneratingResult;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RlcpDataBaseService {

    @Autowired
    GenerateRlcpRepository generateRepository;
    @Autowired
    CheckRlcpRepository checkRepository;
//    @Autowired
//    private AttemptsService attemptsService;

    public Optional<GenerateRlcp> getGenerateBySession(String session) {
        Optional<GenerateRlcp> generate = Optional.ofNullable(generateRepository.findBySession(session));
        generate.orElseThrow(() -> new HibernateException("not found generate by session: " + session));
        return generate;
    }

    public Optional<GenerateRlcp> saveGenerateResult(Attempts attempt, GeneratingResult result) {
        Date time = Calendar.getInstance().getTime();
        GenerateRlcp generate = new GenerateRlcp(attempt, result, time);

        Optional<GenerateRlcp> saveGenerate = Optional.ofNullable(generateRepository.save(generate));
        saveGenerate.orElseThrow(() -> new HibernateException("exception while saving rlcp-generate: " + result));
        return saveGenerate;
    }

    public Optional<GenerateRlcp> saveRepeatGenerateResult(String session) {
        GenerateRlcp generateRlcp = getGenerateBySession(session).get();
        Date time = Calendar.getInstance().getTime();
        Attempts attempts = new Attempts(generateRlcp.getAttempts().getUser(),
                generateRlcp.getAttempts().getLab(), "");
//                generateRlcp.getAttempts().getLab(), attemptsService.generateSession());

        GenerateRlcp generate = new GenerateRlcp(attempts, generateRlcp.getResponse(), time, generateRlcp.getId());

        Optional<GenerateRlcp> saveGenerate = Optional.ofNullable(generateRepository.save(generate));
        saveGenerate.orElseThrow(() -> new HibernateException("exception while saving rlcp-generate: " + generateRlcp));
        return saveGenerate;
    }

    public Optional<CheckRlcp> saveCheckResult(Attempts attempt, CheckingResult result) {
        Date time = Calendar.getInstance().getTime();
        CheckRlcp check = new CheckRlcp(attempt, result, time);

        Optional<CheckRlcp> saveCheck = Optional.ofNullable(checkRepository.save(check));
        saveCheck.orElseThrow(() -> new HibernateException("exception while saving rlcp-check: " + result));
        return saveCheck;
    }
}
