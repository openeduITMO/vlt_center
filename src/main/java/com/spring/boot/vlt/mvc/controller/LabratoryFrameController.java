package com.spring.boot.vlt.mvc.controller;

import com.spring.boot.vlt.mvc.model.Trial;
import com.spring.boot.vlt.mvc.model.frames.Generator;
import com.spring.boot.vlt.mvc.model.frames.LaboratoryFrame;
import com.spring.boot.vlt.mvc.model.staticFile.StaticFile;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import rlcp.check.ConditionForChecking;
import rlcp.generate.GeneratingResult;
import rlcp.generate.RlcpGenerateRequestBody;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.PathMatcher;
import java.util.*;

@RestController
public class LabratoryFrameController {
    @Autowired
    private Environment env;

    @Autowired
    private Trial trial;

    private enum StaticType {js, css}

    @RequestMapping(value = "/getLabratoryFame", method = RequestMethod.POST, produces = "application/json")
    public List getLabratoryFrme(@RequestParam("name") String nameVl) {

        List<LaboratoryFrame> frames = new ArrayList<>();
        Document document = null;
        try {
            document = readLabratoryFame(nameVl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
        readAllFrames(frames, document.selectNodes("//FrameIndex"));
        return frames;
    }

    @RequestMapping(value = "/startVl", method = RequestMethod.POST)
    public ModelAndView startVl(@RequestParam("name") String nameVl, @RequestParam("frameId") String frameId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("startVl");
        Document document = null;
        try {
            document = readLabratoryFame(nameVl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
        Optional<Node> node = findFrameById(document.selectNodes("//FrameIndex"), frameId);

        modelAndView.addObject("frame", readFrame(node.get()));

        modelAndView.addObject("generate", readGeneratingResult(node.get()));
        modelAndView.addObject("algorithm", readAlgorithm(node.get()));

        List<ConditionForChecking> checks = new ArrayList<>();
        readAllTests(checks, (node.get().selectSingleNode("LaboratoryFrame/LaboratoryTestsGroups/LaboratoryTestsGroup").selectNodes("LaboratoryTest")));
        modelAndView.addObject("check", checks);

        modelAndView.addObject("js", readStatic(nameVl, StaticType.js));
        modelAndView.addObject("css", readStatic(nameVl, StaticType.css));


        trial.setUrl(readUrl(node.get()));
        trial.setConditionsList(checks);

        return modelAndView;
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
//                new LaboratoryTest(
//                Integer.parseInt(node.valueOf("@TestID")),
//                Integer.parseInt(node.valueOf("@LimitOnTest")),
//                node.valueOf("@TimeScale"),
//                node.selectSingleNode("LaboratoryTestInput/comment()").getText(),
//                node.selectSingleNode("LaboratoryTestOutput/comment()").getText());
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
        final String path = env.getProperty("paths.uploadedFiles");
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
