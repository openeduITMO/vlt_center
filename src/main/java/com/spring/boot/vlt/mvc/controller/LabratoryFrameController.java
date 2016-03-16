package com.spring.boot.vlt.mvc.controller;

import com.spring.boot.vlt.mvc.model.frames.Check;
import com.spring.boot.vlt.mvc.model.frames.Frame;
import com.spring.boot.vlt.mvc.model.frames.Generate;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.net.MalformedURLException;
import java.util.*;

@Controller
public class LabratoryFrameController {
    @Autowired
    private Environment env;
    private String framesXml = "LaboratoryFrames.xml";

    @RequestMapping(value = "/getLabratoryFame", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List getLabratoryFrme(@RequestParam("name") String nameVl) {

        List<Frame> frames = new ArrayList<>();
        try {
            Document document = readLabratoryFame(nameVl);

            List<Node> frameIndex = document.selectNodes("//FrameIndex");
            frameIndex.forEach(node -> {
                Frame frame = new Frame(Integer.parseInt(node.valueOf("@FrameID")),
                        Integer.parseInt(node.valueOf("@Scheme")),
                        node.selectSingleNode("LaboratoryFrame").valueOf("@Name"),
                        node.selectSingleNode("LaboratoryFrame").selectSingleNode("Data").getText());
                frames.add(frame);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return frames;
    }

    @RequestMapping(value = "/startVl", method = RequestMethod.POST)
    public ModelAndView startVl(@RequestParam("name") String nameVl, @RequestParam("frameId") String frameId) {
        Map<String, Object> vlProperty = new HashMap<>();
        try {
            Document document = readLabratoryFame(nameVl);
            List<Node> frameIndex = document.selectNodes("//FrameIndex");
            frameIndex.stream().filter((node) -> node.valueOf("@FrameID").equals(frameId)).forEach(node -> {
                vlProperty.put("frame", new Frame(Integer.parseInt(node.valueOf("@FrameID")),
                        Integer.parseInt(node.valueOf("@Scheme")),
                        node.selectSingleNode("LaboratoryFrame").valueOf("@Name"),
                        node.selectSingleNode("LaboratoryFrame").selectSingleNode("Data").getText()));

                vlProperty.put("generate", new Generate(
                        node.selectSingleNode("LaboratoryFrame").selectSingleNode("Generator").selectSingleNode("ByDefault").selectSingleNode("Code/comment()").getText(),
                        node.selectSingleNode("LaboratoryFrame").selectSingleNode("Generator").selectSingleNode("ByDefault").selectSingleNode("Text/comment()").getText(),
                        node.selectSingleNode("LaboratoryFrame").selectSingleNode("Generator").selectSingleNode("ByDefault").selectSingleNode("Instructions/comment()").getText(),
                        node.selectSingleNode("LaboratoryFrame").selectSingleNode("Generator").selectSingleNode("Algorithm/comment()").getText()

                ));
                List<Check> checks = new ArrayList<>();
//                vlProperty.put("check", new Check(
//                        node.selectSingleNode("LaboratoryFrame").selectSingleNode("LaboratoryTestsGroups")
//                ));
                List<Node> check = node.selectSingleNode("LaboratoryFrame").selectSingleNode("LaboratoryTestsGroups").selectNodes("LaboratoryTestsGroup");
                check.forEach(c -> {
                    checks.add(new Check(
                            Integer.parseInt(c.selectSingleNode("LaboratoryTest").valueOf("@TestID")),
                            Integer.parseInt(c.selectSingleNode("LaboratoryTest").valueOf("@LimitOnTest")),
                            c.selectSingleNode("LaboratoryTest").valueOf("@TimeScale"),
                            c.selectSingleNode("LaboratoryTest").selectSingleNode("LaboratoryTestInput/comment()").getText(),
                            c.selectSingleNode("LaboratoryTest").selectSingleNode("LaboratoryTestOutput/comment()").getText()
                    ));
                });
                vlProperty.put("check", checks);
                System.out.println();
            });
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("startVl", "vlProperty", vlProperty);
    }

    private Document readLabratoryFame(String nameVl) throws MalformedURLException, DocumentException {
        final String path = env.getProperty("paths.uploadedFiles");
        SAXReader saxReader = new SAXReader();
        return saxReader.read(new File(path + File.separator + nameVl, "frames" + File.separator + framesXml));
    }
}
