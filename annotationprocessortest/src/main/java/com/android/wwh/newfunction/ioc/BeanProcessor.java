package com.android.wwh.newfunction.ioc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;

/**
 * Created by we-win on 2017/7/5.
 */
@SupportedAnnotationTypes("com.android.wwh.newfunction.ioc.Seriable")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class BeanProcessor extends AbstractProcessor {

    Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv)
    {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv)
    {

        Set<? extends Element> elememts = roundEnv
                .getElementsAnnotatedWith(Seriable.class);
        TypeElement classElement = null;
        List<VariableElement> fields = null;
        Map<String, List<VariableElement>> maps = new HashMap<String, List<VariableElement>>();

        for (Element ele : elememts)
        {

            if (ele.getKind() == ElementKind.CLASS)
            {
                classElement = (TypeElement) ele;
                maps.put(classElement.getQualifiedName().toString(),
                        fields = new ArrayList<VariableElement>());

            } else if (ele.getKind() == ElementKind.FIELD)
            {
                VariableElement varELe = (VariableElement) ele;

                TypeElement enclosingElement = (TypeElement) varELe
                        .getEnclosingElement();

                String key = enclosingElement.getQualifiedName().toString();
                fields = maps.get(key);
                if (fields == null)
                {
                    maps.put(key, fields = new ArrayList<VariableElement>());
                }
                fields.add(varELe);
            }
        }

        for (String key : maps.keySet())
        {
            if (maps.get(key).size() == 0)
            {
                TypeElement typeElement = elementUtils.getTypeElement(key);
                List<? extends Element> allMembers = elementUtils
                        .getAllMembers(typeElement);
                if (allMembers.size() > 0)
                {
                    maps.get(key).addAll(ElementFilter.fieldsIn(allMembers));
                }
            }
        }

        generateCodes(maps);

        return true;
    }

    private void generateCodes(Map<String, List<VariableElement>> maps)
    {
        File dir = new File("e://apt_test");
        if (!dir.exists())
            dir.mkdirs();
        for (String key : maps.keySet())
        {
            File file = new File(dir, key.replaceAll("\\.", "_") + ".txt");
            try
            {

                FileWriter fw = new FileWriter(file);
                fw.append("{").append("class:").append("\"" + key + "\"")
                        .append(",\n ");
                fw.append("fields:\n {\n");
                List<VariableElement> fields = maps.get(key);

                for (int i = 0; i < fields.size(); i++)
                {
                    VariableElement field = fields.get(i);
                    fw.append("  ").append(field.getSimpleName()).append(":")
                            .append("\"" + field.asType().toString() + "\"");
                    if (i < fields.size() - 1)
                    {
                        fw.append(",");
                        fw.append("\n");
                    }
                }
                fw.append("\n }\n");
                fw.append("}");
                fw.flush();
                fw.close();

            } catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }


}
