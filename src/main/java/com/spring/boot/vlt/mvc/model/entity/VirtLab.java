package com.spring.boot.vlt.mvc.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "labs")
public class VirtLab implements Serializable {
    @Transient
    @JsonIgnore
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "dirName", nullable = false, unique = true)
    private String dirName;
    @Column(name = "width")
    private String width;
    @Column(name = "height")
    private String height;
    @Column(name = "url")
    private String url;
    @Column (name = "public")
    private boolean isPublic;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "labs_author")
    private User author;

    @Transient
    static String propertyFileName;

    public VirtLab() {

        propertyFileName = "lab.desc";
        isPublic = false;
    }

    public VirtLab(String name) {
        this.name = name;
        this.isPublic = false;
    }

    public VirtLab(String name, String dirName) {
        this.name = name;
        this.dirName = dirName;
        this.isPublic = false;
    }

    public VirtLab(File file) {
        readFile(file);
        dirName = file.getParentFile().getName();
        this.isPublic = false;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
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
            logger.error("RuntimeException when trying to read " + dirName + File.separator + propertyFileName);
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


    public boolean updatePropertyFile(String path) {
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
