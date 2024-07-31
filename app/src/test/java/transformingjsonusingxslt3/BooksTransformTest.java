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

public class BooksTransformTest {

    private Logger logger = LoggerFactory.getLogger(BooksTransformTest.class);

    private TestOutputOrganizer too =
            new TestOutputOrganizer.Builder(BooksTransformTest.class)
                    .subOutputDirectory(BooksTransformTest.class).build();
    private Path fixtureDir;
    private Path classOutputDir;
    @BeforeClass
    public void beforeClass() throws IOException {
        fixtureDir = too.getProjectDirectory().resolve("src/test/fixture");
        assertThat(fixtureDir).exists();
        classOutputDir = too.cleanClassOutputDirectory();
    }

    @Test
    public void test_smoke() throws TransformerException {
        Path xml = fixtureDir.resolve("books.xml");
        Path xslt = fixtureDir.resolve("books.xslt");
        Path html = classOutputDir.resolve("books.html");
        // input
        Source xmlSource = new StreamSource(xml.toFile());
        // xslt
        Source xsltSource = new StreamSource(xslt.toFile());

        // we use Saxon as Transformer
        System.setProperty("javax.xml.transform.TransformerFactory",
                "net.sf.saxon.jaxp.SaxonTransformerFactory");
        TransformerFactory transformerFactory =
                TransformerFactory.newInstance();
        Transformer transformer =
                transformerFactory.newTransformer(xsltSource);

        // output
        Result xmlResult = new StreamResult(html.toFile());
        // now perform transformation
        transformer.transform(xmlSource, xmlResult);
        assertThat(html.toFile()).exists();
        logger.info("output: " + html.toString());
    }
}
