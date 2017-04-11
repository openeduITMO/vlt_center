package com.spring.boot.vlt.mvc.service;

import com.spring.boot.vlt.config.property.VltSettings;
import com.spring.boot.vlt.mvc.model.entity.User;
import com.spring.boot.vlt.mvc.model.entity.VirtLab;
import com.spring.boot.vlt.mvc.repository.VlRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.stream.Collectors;

import static java.nio.file.Files.walk;

@Service
public class VltService {
    private final Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private VltSettings vltSettings;
    @Autowired
    private UserService userService;
    @Autowired
    private VlRepository vlRepository;

    public Set<VirtLab> getVirtListByAuthor(String userLogin) {
        final String path = System.getProperty("user.dir") + File.separator + vltSettings.getPathsUploadedFiles();
        return vlRepository.findByAuthor(userLogin)
                .stream().filter(vl -> (new File(path, vl.getDirName())).exists()).collect(Collectors.toSet());
    }

    public Set<VirtLab> getAvailableVarList(String userLogin) {
        final String path = System.getProperty("user.dir") + File.separator + vltSettings.getPathsUploadedFiles();
        return userService.getUserVirtLabs(userLogin)
                .stream().filter(vl -> (new File(path, vl.getDirName())).exists()).collect(Collectors.toSet());
    }

    public Set<VirtLab> getPublicVlList(String userLogin) {
        final String path = System.getProperty("user.dir") + File.separator + vltSettings.getPathsUploadedFiles();
        return vlRepository.getPublicVlList(userLogin)
                .stream().filter(vl -> (new File(path, vl.getDirName())).exists()).collect(Collectors.toSet());
    }

    public VirtLab declarationOnVL(String dirName, String login){
        User user = userService.getUserByLogin(login);
        VirtLab virtLab = vlRepository.findByDirName(dirName);
        user.addDeclaration(virtLab);
        userService.saveUser(user);
        return virtLab;
    }

    @Transactional
    public VirtLab addVl(VirtLab vl, String userLogin) {
        final String path = System.getProperty("user.dir") + File.separator + vltSettings.getPathsUploadedFiles();
        File vlDir = new File(path, "lab" + System.currentTimeMillis());
        while (vlDir.exists()) {
            vlDir = new File(path, "lab" + System.currentTimeMillis());
        }
        if (!vlDir.exists()) {
            vlDir.mkdirs();
            logger.info("Create " + vlDir.getAbsolutePath());
        }
        vl.setDirName(vlDir.getName());
        vl.updatePropertyFile(path);
        User user = userService.getUserByLogin(userLogin);
        vl.setAuthor(user);
        vlRepository.save(vl);
        logger.info("Virtual laboratory " + vl.getDirName() + "create!");
        return vl;
    }

    public Set<VirtLab> getVirtLabsByAuthor(String userLogin){
        return vlRepository.findByAuthor(userLogin);
    }

    public VirtLab foundVlByDirUnderUser(String userLogin, String dirName){
        return getVirtLabsByAuthor(userLogin).stream().filter(vl -> dirName.equals(vl.getDirName())).findFirst().orElseThrow(() ->
                new NullPointerException("User with login = " + userLogin + " not contain vl = " + dirName));
    }

    public VirtLab getVl(String vlDir) {
        return Optional.ofNullable(vlRepository.findByDirName(vlDir)).orElseThrow(() ->
                new NullPointerException("vl with dir name = " + vlDir + " not found"));
    }

    @Transactional
    public VirtLab savePropertyVl(VirtLab vl, String dir, String userLogin) {
        VirtLab virtLabsByAuthor = foundVlByDirUnderUser(userLogin, dir);
        if (virtLabsByAuthor != null) {
            return savePropertyVl(vl, dir);
        } else {
            throw new NullPointerException("User with login = " + userLogin + " not contain vl = " + vl.toString());
        }
    }

    public VirtLab savePropertyVl(VirtLab vl, String dir) {
        final String path = System.getProperty("user.dir") + File.separator + vltSettings.getPathsUploadedFiles();
        VirtLab vlFromDB = getVl(dir);
        vlFromDB.setName(vl.getName());
        vlFromDB.setHeight(vl.getHeight());
        vlFromDB.setWidth(vl.getWidth());
        vlFromDB.setPublic(vl.isPublic());
//        chekAndSaveUrl(vlFromDB, vl.getUrl());
        vl.updatePropertyFile(path);
        return vlRepository.save(vlFromDB);
    }

    public boolean chekAndSaveUrl(VirtLab vl, String url) {
        if (vlRepository.findByUrl(url) == null) {
            vl.setUrl(url);
            vlRepository.save(vl);
            return true;
        }
        return false;
    }

    public String getUrl(String dir) {
        return getVl(dir).getUrl();
    }

    public byte[] getImg(String dir, String name, String suffix) throws IOException {
        final String path = System.getProperty("user.dir") + File.separator + vltSettings.getPathsUploadedFiles();
        final File img = new File(path + File.separator + dir + File.separator + "tool" + File.separator + "img", name + "." + suffix);
        return getStatic(img);
    }


    public byte[] getImg2(String dir, String name, String suffix) throws IOException {
        final String path = System.getProperty("user.dir") + File.separator + vltSettings.getPathsUploadedFiles();
        final File img = new File(path + File.separator + dir + File.separator + "tool" + File.separator + "img", name + "." + suffix);
        return getStatic(img);
    }

    private byte[] getStatic(File img) throws IOException {
        if (img.exists()) {
            RandomAccessFile f = new RandomAccessFile(img, "r");
            byte[] b = new byte[(int) f.length()];
            f.readFully(b);
            return b;
        } else {
            logger.error("File " + img.getAbsolutePath() + " not found!");
            return null;
        }

    }
}
