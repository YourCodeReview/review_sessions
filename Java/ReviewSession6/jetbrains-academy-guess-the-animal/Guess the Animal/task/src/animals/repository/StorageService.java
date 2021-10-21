package animals.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static java.lang.System.Logger.Level.*;

public enum StorageService {
    JSON(new JsonMapper()),
    YAML(new YAMLMapper()),
    XML(new XmlMapper());

    private static final System.Logger LOGGER = System.getLogger("");

    private static final String CONFIG_FILE = "application.xml";
    private static final String DEFAULT_NAME = "animals";
    private static final String DEFAULT_TYPE = "yaml";
    private static final String baseName;
    private static final StorageService defaultService;

    static {
        final Properties properties = new Properties();
        try {
            properties.loadFromXML(new FileInputStream(CONFIG_FILE));
        } catch (IOException e) {
            LOGGER.log(WARNING, e::getMessage);
        }
        baseName = properties.getProperty("baseName", DEFAULT_NAME);
        defaultService = of(properties.getProperty("type", DEFAULT_TYPE));
        LOGGER.log(DEBUG, "Storage base name is `{0}`", baseName);
    }

    private final ObjectMapper objectMapper;

    StorageService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public static StorageService of(final String type) {
        return StorageService.valueOf(type.toUpperCase());
    }

    public static StorageService getDefaultService() {
        return defaultService;
    }

    private File getFile() {
        final var language = System.getProperty("user.language", "en").toLowerCase();
        final var lnName = "en".equals(language) ? "" : "_" + language;
        return new File(baseName + lnName + "." + this.name().toLowerCase());
    }

    public void load(final KnowledgeTree tree) {
        final var file = getFile();
        LOGGER.log(TRACE, file::getAbsolutePath);

        try {
            tree.setRoot(objectMapper.readValue(file, TreeNode.class));
        } catch (IOException error) {
            LOGGER.log(WARNING, error::getMessage);
        }
        LOGGER.log(TRACE, "is loaded: {0}", !tree.isEmpty());
    }

    public void save(final KnowledgeTree tree) {
        final var file = getFile();
        LOGGER.log(TRACE, file::getAbsolutePath);
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, tree.getRoot());
        } catch (IOException error) {
            LOGGER.log(WARNING, error::getMessage);
        }
        LOGGER.log(TRACE, "saved");
    }
}
