/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBElement;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Text;

//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import org.apache.commons.lang3.StringUtils;
//import javax.xml.bind.JAXBElement;
//import javax.xml.bind.JAXBException;
//import org.docx4j.XmlUtils;
//import org.docx4j.openpackaging.exceptions.Docx4JException;
//import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
//import org.docx4j.wml.ContentAccessor;
//import org.docx4j.wml.P;
//import org.docx4j.wml.Tbl;
//import org.docx4j.wml.Text;
//import org.docx4j.wml.Tr;
public class DocTemplate {

    public void insertIntoTemplate(Personel personel, Materiel materiel) throws Docx4JException, FileNotFoundException, IOException {
        String filePath = "rsc/";
        String file = "template2.docx";
        String outputFile = "ficheEmprunt.docx";

        WordprocessingMLPackage template = getTemplate(filePath + file);

//      MainDocumentPart documentPart = template.getMainDocumentPart();
        List<Object> texts = getAllElementFromObject(
                template.getMainDocumentPart(), Text.class);
        searchAndReplace(texts, new HashMap<String, String>() {
            {
                this.put("${SJ_NOM}", personel.getName());
                this.put("${SJ_PRENOM}", personel.getFirstname());
                this.put("${SJ_GROUPE}", materiel.getGroupe());
                this.put("${SJ_SOUS_GROUPE}", materiel.getSousGroupe());
                this.put("${SJ_DESIGNATION}", materiel.getDesignation());
                this.put("${SJ_ETAT}", materiel.getEtat());
                this.put("${SJ_DATE}", materiel.getDatePret());
            }

            @Override
            public String get(Object key) {
                // TODO Auto-generated method stub
                return super.get(key);
            }
        });

        writeDocxToStream(template, filePath + outputFile);
        
        Desktop.getDesktop().print(new File(filePath+outputFile));
    }

    public static void searchAndReplace(List<Object> texts, Map<String, String> values) {

        // -- scan all expressions  
        // Will later contain all the expressions used though not used at the moment
        List<String> els = new ArrayList<String>();

        StringBuilder sb = new StringBuilder();
        int PASS = 0;
        int PREPARE = 1;
        int READ = 2;
        int mode = PASS;

        // to nullify
        List<int[]> toNullify = new ArrayList<int[]>();
        int[] currentNullifyProps = new int[4];

        // Do scan of els and immediately insert value
        for (int i = 0; i < texts.size(); i++) {
            Object text = texts.get(i);
            Text textElement = (Text) text;
            String newVal = "";
            String v = textElement.getValue();
//          System.out.println("text: "+v);
            StringBuilder textSofar = new StringBuilder();
            int extra = 0;
            char[] vchars = v.toCharArray();
            for (int col = 0; col < vchars.length; col++) {
                char c = vchars[col];
                textSofar.append(c);
                switch (c) {
                    case '$': {
                        mode = PREPARE;
                        sb.append(c);
//                  extra = 0;
                    }
                    break;
                    case '{': {
                        if (mode == PREPARE) {
                            sb.append(c);
                            mode = READ;
                            currentNullifyProps[0] = i;
                            currentNullifyProps[1] = col + extra - 1;
                            System.out.println("extra-- " + extra);
                        } else {
                            if (mode == READ) {
                                // consecutive opening curl found. just read it
                                // but supposedly throw error
                                sb = new StringBuilder();
                                mode = PASS;
                            }
                        }
                    }
                    break;
                    case '}': {
                        if (mode == READ) {
                            mode = PASS;
                            sb.append(c);
                            els.add(sb.toString());
                            newVal += textSofar.toString()
                                    + (null == values.get(sb.toString()) ? sb.toString() : values.get(sb.toString()));
                            textSofar = new StringBuilder();
                            currentNullifyProps[2] = i;
                            currentNullifyProps[3] = col + extra;
                            toNullify.add(currentNullifyProps);
                            currentNullifyProps = new int[4];
                            extra += sb.toString().length();
                            sb = new StringBuilder();
                        } else if (mode == PREPARE) {
                            mode = PASS;
                            sb = new StringBuilder();
                        }
                    }
                    default: {
                        if (mode == READ) {
                            sb.append(c);
                        } else if (mode == PREPARE) {
                            mode = PASS;
                            sb = new StringBuilder();
                        }
                    }
                }
            }
            newVal += textSofar.toString();
            textElement.setValue(newVal);
        }

        // remove original expressions
        if (toNullify.size() > 0) {
            for (int i = 0; i < texts.size(); i++) {
                if (toNullify.size() == 0) {
                    break;
                }
                currentNullifyProps = toNullify.get(0);
                Object text = texts.get(i);
                Text textElement = (Text) text;
                String v = textElement.getValue();
                StringBuilder nvalSB = new StringBuilder();
                char[] textChars = v.toCharArray();
                for (int j = 0; j < textChars.length; j++) {
                    char c = textChars[j];
                    if (null == currentNullifyProps) {
                        nvalSB.append(c);
                        continue;
                    }
                    // I know 100000 is too much!!! And so what???
                    int floor = currentNullifyProps[0] * 100000 + currentNullifyProps[1];
                    int ceil = currentNullifyProps[2] * 100000 + currentNullifyProps[3];
                    int head = i * 100000 + j;
                    if (!(head >= floor && head <= ceil)) {
                        nvalSB.append(c);
                    }

                    if (j > currentNullifyProps[3] && i >= currentNullifyProps[2]) {
                        toNullify.remove(0);
                        if (toNullify.size() == 0) {
                            currentNullifyProps = null;
                            continue;
                        }
                        currentNullifyProps = toNullify.get(0);
                    }
                }
                textElement.setValue(nvalSB.toString());
            }
        }
    }

    private WordprocessingMLPackage getTemplate(String name)
            throws Docx4JException, FileNotFoundException {
        WordprocessingMLPackage template = WordprocessingMLPackage
                .load(new FileInputStream(new File(name)));
        return template;
    }

    private static List<Object> getAllElementFromObject(Object obj,
            Class<?> toSearch) {
        List<Object> result = new ArrayList<Object>();
        if (obj instanceof JAXBElement) {
            obj = ((JAXBElement<?>) obj).getValue();
        }

        if (obj.getClass().equals(toSearch)) {
            result.add(obj);
        } else if (obj instanceof ContentAccessor) {
            List<?> children = ((ContentAccessor) obj).getContent();
            for (Object child : children) {
                result.addAll(getAllElementFromObject(child, toSearch));
            }

        }
        return result;
    }

    private void replacePlaceholder(WordprocessingMLPackage template,
            String name, String placeholder) {
        List<Object> texts = getAllElementFromObject(
                template.getMainDocumentPart(), Text.class);

        for (Object text : texts) {
            Text textElement = (Text) text;
            if (textElement.getValue().equals(placeholder)) {
                textElement.setValue(name);
            }
        }
    }

    private void writeDocxToStream(WordprocessingMLPackage template,String target) throws IOException, Docx4JException {
        File f = new File(target);
        template.save(f);
    }

    private static void docx4jReplaceSimpleTest(String input, String output) throws Exception {
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new java.io.File("rsc/" + input));

        HashMap<String, String> mappings = new HashMap();
        mappings.put("«SJ_PERSONEL»", "test");

        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
        documentPart.variableReplace(mappings);

        wordMLPackage.save(new java.io.File("rsc/" + output));
    }

}
