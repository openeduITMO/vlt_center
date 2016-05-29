package com.spring.boot.vlt.mvc.service;

import com.spring.boot.vlt.mvc.model.frames.LaboratoryFrame;
import com.spring.boot.vlt.mvc.model.staticFile.StaticFile;
import com.spring.boot.vlt.mvc.model.vl.VirtLab;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import rlcp.check.ConditionForChecking;
import rlcp.generate.GeneratingResult;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LaboratoryFrameService {
    private static final Logger logger = LogManager.getLogger(LaboratoryFrameService.class);

    @Autowired
    private Environment env;

    private enum StaticType {js, css};
    private Document document;
    private Optional<Node> node;

    private String nameVl;

    public void setNameVl(String nameVl){
        this.nameVl = nameVl;
        try {
            document = readLabratoryFame(nameVl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void setFrameId(String frameId){
        node = findFrameById(document.selectNodes("//FrameIndex"), frameId);
    }

    public List getLaboratoryFrame() {
        List<LaboratoryFrame> frames = new ArrayList<>();
        readAllFrames(frames, document.selectNodes("//FrameIndex"));
        return frames;
    }

    public VirtLab getVirtLab(){
        return new VirtLab(new File(new File(System.getProperty("user.dir")  + File.separator + env.getProperty("paths.uploadedFiles"), nameVl), "lab.desc"));
    }

    public LaboratoryFrame getFrame(){
        return readFrame(node.get());
    }

    public GeneratingResult getGeneratingResult(){
        return  readGeneratingResult(node.get());
    }

    public String readAlgorithm(){
        return readAlgorithm(node.get());
    }

    public List<ConditionForChecking> getCheckList(){
        List<ConditionForChecking> checks = new ArrayList<>();
        readAllTests(checks, (node.get().selectSingleNode("LaboratoryFrame/LaboratoryTestsGroups/LaboratoryTestsGroup").selectNodes("LaboratoryTest")));
        return checks;
    }

    public StaticFile getStatic(String type){
        if(type.equalsIgnoreCase("js")){
            return readStatic(nameVl, StaticType.js);
        } else{
            return readStatic(nameVl, StaticType.css);
        }
    }

    public String getUrl(){
        return readUrl(node.get());
    }


    private void readAllFrames(List<LaboratoryFrame> frames, List<Node> frameIndex) {
        frameIndex.forEach(node -> {
            frames.add(readFrame(node));
        });
    }

    private LaboratoryFrame readFrame(Node node) {
        return new LaboratoryFrame(Integer.parseInt(node.valueOf("@FrameID")),
                Integer.parseInt(node.valueOf("@Scheme")),
                node.selectSingleNode("LaboratoryFrame").valueOf("@Name"),
                node.selectSingleNode("LaboratoryFrame/Data").getText());
    }

    private String readUrl(Node node) {
        return node.selectSingleNode("LaboratoryFrame/LaboratoryTestsGroups").valueOf("@URL");
    }

    private GeneratingResult readGeneratingResult(Node node) {
        return new GeneratingResult(
                node.selectSingleNode("LaboratoryFrame/Generator/ByDefault/Code/comment()").getText(),
                node.selectSingleNode("LaboratoryFrame/Generator/ByDefault/Text/comment()").getText(),
                node.selectSingleNode("LaboratoryFrame/Generator/ByDefault/Instructions/comment()").getText());
    }

    private String readAlgorithm(Node node) {
        return node.selectSingleNode("LaboratoryFrame/Generator/Algorithm/comment()").getText();
    }

    private void readAllTests(List<ConditionForChecking> checks, List<Node> check) {
        check.forEach(c -> {
            checks.add(readLaboratoryTest(c));
        });
    }

    private ConditionForChecking readLaboratoryTest(Node node) {
        return new ConditionForChecking(
                Integer.parseInt(node.valueOf("@TestID")),
                Integer.parseInt(node.valueOf("@LimitOnTest")),
                node.selectSingleNode("LaboratoryTestInput/comment()").getText(),
                node.selectSingleNode("LaboratoryTestOutput/comment()").getText()
        );
    }

    private Optional<Node> findFrameById(List<Node> frameIndex, String frameId) {
        return frameIndex.stream().filter((node) -> node.valueOf("@FrameID").equals(frameId)).findFirst();
    }

    private Document readLabratoryFame(String nameVl) throws MalformedURLException, DocumentException {
        final String path = env.getProperty("paths.uploadedFiles");
        SAXReader saxReader = new SAXReader();
        return saxReader.read(new File(path + File.separator + nameVl, "frames" + File.separator + env.getProperty("framesXml")));
    }

    private StaticFile readStatic(String nameDirVl, StaticType type) {
        final String path = System.getProperty("user.dir")  + File.separator + env.getProperty("paths.uploadedFiles");
        StaticFile staticFile = new StaticFile(nameDirVl);
        File st = new File(path + File.separator + nameDirVl, "tool" + File.separator + type);
        try {
            PathMatcher requestPathMatcher = FileSystems.getDefault().getPathMatcher("glob:**." + type);
            Files.walk(st.toPath()).filter(j -> requestPathMatcher.matches(j)).forEach(j -> {
                if (j.getParent().getFileName().toString().equalsIgnoreCase("dev")) {
                    staticFile.addDev(j.getFileName().toString());
                } else {
                    if (j.getParent().getFileName().toString().equalsIgnoreCase("lib")) {
                        staticFile.addLib(j.getFileName().toString());
                    }
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return staticFile;
    }
}
