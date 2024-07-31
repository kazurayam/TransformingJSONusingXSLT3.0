package transformingjsonusingxslt3;

import com.kazurayam.unittest.TestOutputOrganizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Source:
 * https://www.saxonica.com/papers/xmlprague-2016mhk.pdf
 */
public class SculpturesTransformTest {


    private Logger logger = LoggerFactory.getLogger(SculpturesTransformTest.class);

    private TestOutputOrganizer too =
            new TestOutputOrganizer.Builder(SculpturesTransformTest.class)
                    .subOutputDirectory(SculpturesTransformTest.class).build();
    private Path fixtureDir;
    private Path classOutputDir;
    @BeforeClass
    public void beforeClass() throws IOException {
        fixtureDir = too.getProjectDirectory().resolve("src/test/fixture");
        assertThat(fixtureDir).exists();
        classOutputDir = too.cleanClassOutputDirectory();
    }

    @Test
    public void test_json_xml_json() throws TransformerException {
        Path xml = fixtureDir.resolve("books.xml");
        Path json = fixtureDir.resolve("sculptures.json");
        Path xslt = fixtureDir.resolve("json-xml-json.xslt");
        Path html = classOutputDir.resolve("sculptures-transformed.1.json");
        // dummy input --- the xml will not really read
        Source xmlSource = new StreamSource(xml.toFile());
        // xslt
        Source xsltSource = new StreamSource(xslt.toFile());

        // we use Saxon-PE as Transformer
        // see https://www.saxonica.com/documentation12/index.html#!using-xsl/embedding/jaxp-transformation
        System.setProperty("javax.xml.transform.TransformerFactory",
                "com.saxonica.config.ProfessionalTransformerFactory");

        TransformerFactory transformerFactory =
                TransformerFactory.newInstance();
        Transformer transformer =
                transformerFactory.newTransformer(xsltSource);
        // set parameter "input" with the json file path string
        transformer.setParameter("input", json.toFile().toURI().toString());
        // output
        Result xmlResult = new StreamResult(html.toFile());
        // now perform transformation
        transformer.transform(xmlSource, xmlResult);
        assertThat(html.toFile()).exists();
        logger.info("output: " + html.toString());
    }
}
