package com.amg.gitlab.io;

import com.amg.gitlab.config.Constant;
import org.apache.commons.io.FileUtils;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class FileUtil {

    public static void initDirectory(String filePath) {
        try {
            FileUtils.forceMkdir(new File(filePath));
            FileUtils.cleanDirectory(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void modifyVersion(String oldVersion, String newVersion) {
        try {
            for (String s : Constant.PROJECT_LIST) {
                SAXReader reader = new SAXReader();
                File pomFile = new File(Constant.GITLAB_WORK_DIR + "\\\\" + s + "\\\\pom.xml");
                Document doc = reader.read(pomFile);
                Map map = new HashMap();
                String ns = doc.getRootElement().getNamespaceURI();
                map.put("des", ns);
                XPath x = doc.createXPath("//des:revision");
                x.setNamespaceURIs(map);
                List<Node> list = x.selectNodes(doc);
                Iterator<Node> iter = list.iterator();
                while (iter.hasNext()) {
                    Node node = iter.next();
                    node.setText(node.getText().replaceAll(oldVersion.split("\\_")[1].
                            replaceAll("v", ""), newVersion.split("\\_")[1].
                            replaceAll("v", "")));
                }
            XMLWriter output = new XMLWriter(new FileWriter(pomFile));
            output.write(doc);
            output.flush();
            output.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
