package org.example;
import com.hubspot.jinjava.Jinjava;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class JinjavaApp {
    public static void main(String[] args) throws IOException {
        // Load configuration from the YAML file
        Yaml yaml = new Yaml();
        try (InputStream in = JinjavaApp.class.getResourceAsStream("/config.yaml")) {
            Map<String, Object> config = yaml.load(in);

            // Create a Jinjava instance
            Jinjava jinjava = new Jinjava();

            // Create a context for the Jinjava template
            Map<String, Object> context = new HashMap<>();
            context.put("servers", config.get("servers"));
            context.put("config", config);

            // Process the Jinjava template
            String csvContent = jinjava.render(getTemplateContent("jinja_template.j2"), context);

            // Define the output CSV file name
            String outputFileName = Constants.OUTPUT_DIR + "jinjava-output.csv";

            // Write the CSV content to the output file in the same folder
            try (FileWriter writer = new FileWriter(outputFileName)) {
                writer.write(csvContent);
            }

            System.out.println("CSV content written to " + outputFileName);
        }
    }

    private static String getTemplateContent(String templateName) throws IOException {
        try (InputStream in = JinjavaApp.class.getResourceAsStream("/" + templateName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        }
    }
}
