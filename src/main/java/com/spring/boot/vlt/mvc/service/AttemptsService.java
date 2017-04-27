package com.spring.boot.vlt.mvc.service;

import com.spring.boot.vlt.mvc.model.entity.Session;
import com.spring.boot.vlt.mvc.model.entity.VirtLab;
import com.spring.boot.vlt.mvc.model.entity.rlcp.CheckRlcp;
import com.spring.boot.vlt.mvc.model.entity.rlcp.GenerateRlcp;
import com.spring.boot.vlt.mvc.model.staticFile.StaticFile;
import com.spring.boot.vlt.mvc.repository.SessionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AttemptsService {
    private final Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private SessionRepository attemptsRepository;
    @Autowired
    LaboratoryFrameService laboratoryFrameService;

    public String generateSession(){
        String session = UUID.randomUUID().toString();
        while (foundBySession(session).isPresent()) {
            session = UUID.randomUUID().toString();
        }
        return session;
    }

    public boolean saveAttempt(Session attempt) {
        Optional<Session> save = Optional.ofNullable(attemptsRepository.save(attempt));
        save.orElseThrow(() -> new HibernateException("exception while saving attempt: " + attempt.toString()));
        return save.isPresent();
    }

    public Optional<Session> foundBySession(String session) {
        return Optional.ofNullable(attemptsRepository.foundBySession(session));
    }

    public VirtLab foundVlBySession(String session){
        Optional<VirtLab> virtLab = Optional.ofNullable(attemptsRepository.foundVlBySession(session));
        return virtLab.orElseThrow(() ->
                new NullPointerException("VirtLab with session = " + session + " not found"));
    }

    public GenerateRlcp foundGenerateBySession(String session){
        Optional<GenerateRlcp> generateRlcp = Optional.ofNullable(attemptsRepository.foundGenerateBySession(session));
        return generateRlcp.orElseThrow(() ->
                new NullPointerException("GenerateRlcp with session = " + session + " not found"));
    }

    public CheckRlcp foundCheckBySession(String session){
        Optional<CheckRlcp> checkRlcp = Optional.ofNullable(attemptsRepository.foundCheckBySession(session));
        return checkRlcp.orElseThrow(() ->
                new NullPointerException("GenerateRlcp with session = " + session + " not found"));
    }

    public StaticFile getStatic(String type, String session){
        laboratoryFrameService.setPreCondition(foundVlBySession(session).getDirName(), null);
        return laboratoryFrameService.getStatic(type);
    }
}
