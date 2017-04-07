package com.spring.boot.vlt.mvc.service.rlcp;

import com.spring.boot.vlt.mvc.model.entity.rlcp.CheckRlcp;
import com.spring.boot.vlt.mvc.model.entity.rlcp.GenerateRlcp;
import com.spring.boot.vlt.mvc.repository.rlcp.CheckRlcpRepository;
import com.spring.boot.vlt.mvc.repository.rlcp.GenerateRlcpRepository;
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

    public Optional<GenerateRlcp> getGenerateBySession(String session){
                Optional<GenerateRlcp> generate = Optional.ofNullable(generateRepository.findBySession(session));
        generate.orElseThrow(() -> new HibernateException("not found generate by session: " + session));
        return generate;
    }

    public Optional<GenerateRlcp> saveGenerateResult(GeneratingResult result){
        Date time = Calendar.getInstance().getTime();
        GenerateRlcp generate = new GenerateRlcp(UUID.randomUUID().toString(), result, time);

        Optional<GenerateRlcp> saveGenerate = Optional.ofNullable(generateRepository.save(generate));
        saveGenerate.orElseThrow(() -> new HibernateException("exception while saving rlcp-generate: " + result));
        return saveGenerate;
    }

    public Optional<GenerateRlcp> saveRepeatGenerateResult(String session){
        GenerateRlcp generateRlcp = getGenerateBySession(session).get();
        Date time = Calendar.getInstance().getTime();
        GenerateRlcp generate = new GenerateRlcp(UUID.randomUUID().toString(), generateRlcp.getResponse(), time, generateRlcp.getId());

        Optional<GenerateRlcp> saveGenerate = Optional.ofNullable(generateRepository.save(generate));
        saveGenerate.orElseThrow(() -> new HibernateException("exception while saving rlcp-generate: " + generateRlcp));
        return saveGenerate;
    }

    public Optional<CheckRlcp> saveCheckResult(String session, CheckingResult result){
        Date time = Calendar.getInstance().getTime();
        CheckRlcp check = new CheckRlcp(session, result, time);

        Optional<CheckRlcp> saveCheck = Optional.ofNullable(checkRepository.save(check));
        saveCheck.orElseThrow(() -> new HibernateException("exception while saving rlcp-check: " + result));
        return saveCheck;
    }
}
