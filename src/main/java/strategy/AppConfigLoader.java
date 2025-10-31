package strategy;

import java.io.File;
import java.util.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import service.VersionManagement;

public class AppConfigLoader
{
    private static final String CONFIG_PATH="Rollout_config.json";
    public static void loadAndInitializeApp(VersionManagement versionManagement) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(new File(CONFIG_PATH));

            String appId = root.path("appId").asText();
            String appName = root.path("appName").asText();

            for (JsonNode versionNode : root.path("versions")) {
                String version = versionNode.path("version").asText();
                int minAndroidVersion = versionNode.path("minAndroidVersion").asInt();
                String description = versionNode.path("description").asText();
                String apkData = versionNode.path("apkData").asText();

                versionManagement.uploadNewVersion(appId, appName, version, minAndroidVersion, description, apkData.getBytes());

                JsonNode rollout = versionNode.path("rollout");
                RolloutStrategy strategy = parseRolloutStrategy(rollout);

                versionManagement.releaseVersion(appId, version, strategy);
            }

            System.out.println("[AppConfigLoader] All versions uploaded and released successfully.");

        } catch (Exception e) {
            System.err.println("[AppConfigLoader] Failed to load app config: " + e.getMessage());
        }
    }

    private static RolloutStrategy parseRolloutStrategy(JsonNode rollout) {
        String type = rollout.path("type").asText("FullRollout");

        return switch (type) {
            case "PercentageRollout" -> new PercentageRollout(rollout.path("percentage").asInt(100));
            case "BetaRollout" -> {
                List<String> models = new ArrayList<>();
                rollout.path("models").forEach(m -> models.add(m.asText()));
                yield new BetaRollout(models);
            }
            default -> new FullRollout();
        };
    }
}


