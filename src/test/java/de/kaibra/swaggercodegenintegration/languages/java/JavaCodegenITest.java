package de.kaibra.swaggercodegenintegration.languages.java;

import io.swagger.codegen.v3.service.GenerationRequest;
import io.swagger.codegen.v3.service.GeneratorService;
import io.swagger.codegen.v3.service.Options;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static de.kaibra.swaggercodegenintegration.utils.TestingUtils.loadYamlOrJson;

public class JavaCodegenITest extends AbstractJavaCodegenITest {

    @Test
    public void shouldGeneratePetstoreJavaCode() throws IOException {
        GenerationRequest request = new GenerationRequest();

        request.codegenVersion(GenerationRequest.CodegenVersion.V3)
                .lang("java")
                .spec(loadYamlOrJson("specs/petstore_v3.json"))
                .options(new Options().outputDir(CODEGEN_OUTPUT_DIR.getAbsolutePath())
                        .addAdditionalProperty("dataLibrary", "java8")
                        .addAdditionalProperty("java8", true)
                        .library("resttemplate")
                );

        List<File> files = new GeneratorService().generationRequest(request).generate();

        Optional<String> apiClientFilePath = files.stream()
                .map(File::getAbsolutePath)
                .filter(p -> p.endsWith("ApiClient.java"))
                .findFirst();

        Assert.assertTrue(apiClientFilePath.isPresent());
    }

}
