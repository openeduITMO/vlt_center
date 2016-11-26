package com.spring.boot.vlt.mvc.model.vl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VirtLab implements Serializable{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @NotNull
    @Size(min = 1)
    private String name;
    private String dirName;
    private String width;
    private String height;
    static String propertyFileName = "lab.desc";

    public VirtLab() {}

    public VirtLab(String name) {
        this.name = name;
    }

    public VirtLab(String name, String dirName) {
        this.name = name;
        this.dirName = dirName;
    }

    public VirtLab(File file) {
        readFile(file);
        dirName = file.getParentFile().getName();

    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    private void readFile(File desc) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(desc.getAbsoluteFile()));
            try {
                String s;
                while ((s = in.readLine()) != null) {
                    if (s.matches("name=(.*)")) {
                        name = s.substring(5);
                    } else if (s.matches("dirName=(.*)")) {
                        dirName = s.substring(8);
                    } else if (s.matches("width=(.*)")) {
                        width = s.substring(6);
                    } else if (s.matches("height=(.*)")) {
                        height = s.substring(7);
                    }
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            logger.error("RuntimeException when trying to read "  + dirName + File.separator + propertyFileName);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "#Virtual Lab settings\n" +
                new SimpleDateFormat("dd.MM.yyyy hh:mm").format(new Date()) + "\n" +
                "name=" + name + "\n" +
                "dirName=" + dirName + "\n" +
                "width=" + width + "\n" +
                "height=" + height;
    }


    public boolean save(String path) {
        File desc = new File(path + File.separator + dirName, propertyFileName);
        try {
            desc.createNewFile();
            logger.info("Create description file " + dirName + File.separator + propertyFileName);
            PrintWriter out = new PrintWriter(desc.getAbsoluteFile());
            try {
                out.print(toString());
            } finally {
                out.close();
            }
        } catch (IOException e) {
            logger.error("Error create description file " + dirName + File.separator + propertyFileName, e.fillInStackTrace());
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VirtLab virtLab = (VirtLab) o;

        if (name != null ? !name.equals(virtLab.name) : virtLab.name != null) return false;
        return dirName != null ? dirName.equals(virtLab.dirName) : virtLab.dirName == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (dirName != null ? dirName.hashCode() : 0);
        return result;
    }
}
