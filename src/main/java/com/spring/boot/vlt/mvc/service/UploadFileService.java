package com.spring.boot.vlt.mvc.service;

import com.spring.boot.vlt.config.property.VltSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.Enumeration;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static java.nio.file.Files.walk;

@Service
public class UploadFileService {
    private final Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private VltSettings vltSettings;

    public boolean upload(MultipartFile uploadfile, String dir) {
        final String path = System.getProperty("user.dir") + File.separator + vltSettings.getPathsUploadedFiles();
        File vlDir = new File(path, dir);
        if(vlDir.listFiles().length != 0){
            PathMatcher requestPathMatcher = FileSystems.getDefault().getPathMatcher("glob:**.desc");
            Stream.of(vlDir.listFiles()).filter(p -> !requestPathMatcher.matches(p.toPath())).forEach(f -> deleteFile(f));
        }
        String name = uploadfile.getOriginalFilename();
        name.lastIndexOf('.');
        File newLabZip = new File(vlDir, uploadfile.getOriginalFilename());
        String ex = name.substring(name.lastIndexOf('.') + 1);
        if (ex.equalsIgnoreCase("zip")) {
            if (!uploadfile.isEmpty()) {
                if (uploadedFiles(uploadfile, newLabZip)) {
                    if (unZipFile(newLabZip)) {
                        newLabZip.delete();
                        return true;
                    }
                }
            } else {
                logger.error("Empty zip file " + uploadfile.getName());
            }
        } else {
            logger.error("Invalid file extension expected 'zip', actually '" + ex + "'");
        }
        return false;
    }

    private boolean uploadedFiles(MultipartFile uploadfile, File newLab) {
        try {
            byte[] bytes = uploadfile.getBytes();
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(newLab));
            stream.write(bytes);
            stream.close();
            logger.info("File " + uploadfile.getOriginalFilename() + " uploaded");
            return true;
        } catch (Exception e) {
            logger.error("Error upload file " + uploadfile.getName(), e.fillInStackTrace());
            return false;
        }
    }

    private boolean unZipFile(File zipFile) {
        try {
            String property = "lab.desc";
            ZipFile zip = new ZipFile(zipFile);
            Enumeration entries = zip.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                if (entry.isDirectory()) {
                    new File(zipFile.getParent(), entry.getName()).mkdirs();
                } else {
                    if (entry.getName().equals(property)) {
                    } else {
                        write(zip.getInputStream(entry),
                                new BufferedOutputStream(new FileOutputStream(
                                        new File(zipFile.getParent(), entry.getName()))));
                    }
                }
                logger.info(entry.getName());
            }
            logger.info(zipFile.getName() + " unzip");

            zip.close();
            return true;
        } catch (IOException e) {
            logger.error("Error unzip file " + zipFile.getName(), e.fillInStackTrace());
        }
        return false;
    }

    private void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        out.close();
        in.close();
    }

    private void deleteFile(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                File f = new File(dir, children[i]);
                deleteFile(f);
            }
            dir.delete();
            logger.info("Delete dir " + dir.getAbsolutePath());
        } else{
            dir.delete();
            logger.info("Delete file " + dir.getAbsolutePath());
        }
    }
}
