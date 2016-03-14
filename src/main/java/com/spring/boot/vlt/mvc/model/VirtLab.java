package com.spring.boot.vlt.mvc.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VirtLab {
    @Autowired
    private Environment env;

    @NotNull
    @Size(min = 1, message = "Введите название ВЛ")
    private String name;
    private String dirName;
    private int width = 400;
    private int height = 800;
    static String propertyFileName = "lab.desc";

    public VirtLab() {
        name = "";
        dirName = "";
    }

    public VirtLab(String name, String dirName) {
        this.name = name;
        this.dirName = dirName;
    }

    public VirtLab(File file) {
        readFile(file);
        dirName = file.getParentFile().getName();

    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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
                        width = Integer.parseInt(s.substring(6));
                    } else if (s.matches("height=(.*)")) {
                        height = Integer.parseInt(s.substring(7));
                    }
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
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

    public String getTableRow() {
        return "<tr>" +
                "<td>" + name + "</td>" +
                "<td>...</td>" +
                "<td>" +
                "<button class='run' onclick='run()'><span>Запустить</span></button>" +
                "<button class='tune' dirName='" + dirName + "'><span>Настроить</span></button>" +
                "</td>" +
                "</tr>";
    }

    public boolean save(String path) {
        File desc = new File(new File(path, dirName), propertyFileName);
        try {
            desc.createNewFile();
            PrintWriter out = new PrintWriter(desc.getAbsoluteFile());
            try {
                out.print(toString());
            } finally {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VirtLab virtLab = (VirtLab) o;

        if (width != virtLab.width) return false;
        if (height != virtLab.height) return false;
        if (!name.equals(virtLab.name)) return false;
        return dirName.equals(virtLab.dirName);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + dirName.hashCode();
        result = 31 * result + width;
        result = 31 * result + height;
        return result;
    }
}
