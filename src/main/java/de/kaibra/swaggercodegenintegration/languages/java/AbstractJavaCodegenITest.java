package de.kaibra.swaggercodegenintegration.languages.java;

import de.kaibra.swaggercodegenintegration.container.MavenContainer;
import de.kaibra.swaggercodegenintegration.utils.LastTest;
import de.kaibra.swaggercodegenintegration.utils.LastTestMethodOrder;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;

@TestMethodOrder(LastTestMethodOrder.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract public class AbstractJavaCodegenITest {

    private final static String TARGET_DIR = "target";
    private final static String GENERATED_CODE_DIR = TARGET_DIR + "/generated-code";
    private final String MVN_COMMAND;

    public final File CODEGEN_OUTPUT_DIR;

    public AbstractJavaCodegenITest(String MVN_COMMAND) {
        this(createNewCodegenOutputDir(), MVN_COMMAND);
    }

    public AbstractJavaCodegenITest() {
        this(createNewCodegenOutputDir(), "compile");
    }

    private AbstractJavaCodegenITest(File CODEGEN_OUTPUT_DIR, String MVN_COMMAND) {
        this.CODEGEN_OUTPUT_DIR = CODEGEN_OUTPUT_DIR;
        this.MVN_COMMAND = MVN_COMMAND;
    }

    @Test
    @LastTest
    void shouldCompileAllGeneratedSources() throws Throwable {
        new MavenContainer(CODEGEN_OUTPUT_DIR.getAbsolutePath(), MVN_COMMAND)
                .assertMvnCommandIsSuccessWithoutErrors();
    }

    @BeforeAll
    public void setup() {
        createGeneratedCodeDir();
    }

    @AfterAll
    public void cleanup() {
        deleteRecursivly(CODEGEN_OUTPUT_DIR);
    }

    private void deleteRecursivly(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                deleteRecursivly(file);
            }
        }
        dir.delete();
    }

    private static void createGeneratedCodeDir() {
        File targetDir = new File(TARGET_DIR);
        if (!targetDir.exists()) {
            targetDir.mkdir();
        }
        File generatedCodeDir = new File(GENERATED_CODE_DIR);
        if (!generatedCodeDir.exists()) {
            generatedCodeDir.mkdir();
        }
    }

    public static File createNewCodegenOutputDir() {
        try {
            createGeneratedCodeDir();
            File outputFolder = File.createTempFile("codegentest-", "-tmp", new File(GENERATED_CODE_DIR));
            outputFolder.delete();
            outputFolder.mkdir();
            return outputFolder;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
