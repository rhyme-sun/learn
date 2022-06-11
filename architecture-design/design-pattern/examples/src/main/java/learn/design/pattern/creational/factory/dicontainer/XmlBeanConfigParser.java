package learn.design.pattern.creational.factory.dicontainer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@SuppressWarnings({"unchecked", "rawtypes"})
public class XmlBeanConfigParser implements BeanConfigParser {

    @Override
    public List parse(InputStream inputStream) {
        List beanDefinitions = new ArrayList<>();
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(inputStream);
            doc.getDocumentElement().normalize();

            NodeList beanList = doc.getElementsByTagName("bean");
            for (int i = 0; i < beanList.getLength(); i++) {
                Node node = beanList.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE) continue;
                Element element = (Element) node;
                String id = element.getAttribute("id");
                BeanDefinition beanDefinition = new BeanDefinition(
                        element.getAttribute("id"),
                        element.getAttribute("class")
                );
                if (element.getAttribute("scope").equals("singleton")) {
                    beanDefinition.setScope(BeanDefinition.Scope.SINGLETON);
                }
                if (element.getAttribute("lazy-init").equals("true")) {
                    beanDefinition.setLazyInit(true);
                }
                loadConstructorArgs(
                        element.getElementsByTagName("constructor-arg"),
                        beanDefinition
                );
                beanDefinitions.add(beanDefinition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanDefinitions;
    }


    private void loadConstructorArgs(NodeList nodes, BeanDefinition beanDefinition) {
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) continue;
            Element element = (Element) node;
            BeanDefinition.ConstructorArg constructorArg = null;
            String type = element.getAttribute("type");
            if (!type.isEmpty()) {
                constructorArg = new BeanDefinition.ConstructorArg();
                try {
                    constructorArg.setType(Class.forName(type));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (Objects.equals(Integer.class.getName(), type)) {
                    constructorArg.setArg(Integer.valueOf(element.getAttribute("value")));
                } else {
                    constructorArg.setArg(element.getAttribute("value"));
                }
            }
            if (!element.getAttribute("ref").isEmpty()) {
                constructorArg = new BeanDefinition.ConstructorArg();
                constructorArg.setRef(true);
                constructorArg.setArg(element.getAttribute("ref"));
            }
            beanDefinition.addConstructorArg(constructorArg);
        }
    }

    @Override
    public List parse(String configContent) {
        return null;
    }
}
