package com.spring.boot.vlt.mvc.service;

import com.spring.boot.vlt.config.property.VltSettings;
import com.spring.boot.vlt.mvc.model.vl.VirtLab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.exists;
import static java.nio.file.Files.walk;

@Service
public class VltService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private VltSettings vltSettings;

    public List<VirtLab> getVirtList() {
        final String path = System.getProperty("user.dir") + File.separator + vltSettings.getPathsUploadedFiles();
        PathMatcher requestPathMatcher = FileSystems.getDefault().getPathMatcher("glob:**.desc");
        List<VirtLab> vlList = new ArrayList<>();
        Path vlabs = Paths.get(path);
        try {
            if (exists(vlabs)) {
                walk(vlabs).filter(p -> requestPathMatcher.matches(p)).forEach(
                        p -> {
                            vlList.add(new VirtLab(p.toFile()));
                        }
                );
            } else {
                logger.error(vlabs.toAbsolutePath() + " is not exist!");
            }
        } catch (IOException e) {
            logger.error("Error accessing the " + vlabs.toAbsolutePath(), e.fillInStackTrace());
        }
        return vlList;
    }

    public VirtLab addVl(VirtLab vl) {
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
        vl.save(path);
        logger.info("Virtual laboratory " + vl.getDirName() + "create!");
        return vl;
    }

    public VirtLab getPropertyVl(String nameVl) {
        final String path = System.getProperty("user.dir") + File.separator + vltSettings.getPathsUploadedFiles();
        File vlDir = new File(path, nameVl);
        return new VirtLab(new File(vlDir, "lab.desc"));
    }

    public VirtLab savePropertyVl(VirtLab vl, String dir) {
        final String path = System.getProperty("user.dir") + File.separator + vltSettings.getPathsUploadedFiles();
        vl.setDirName(dir);
        vl.save(path);
        return vl;
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
