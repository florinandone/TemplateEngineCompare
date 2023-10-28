package org.example;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;

public class VelocityApp {
    public static void main(String[] args) throws IOException {
        // Load configuration from the YAML file
        Yaml yaml = new Yaml();
        try (InputStream in = VelocityApp.class.getResourceAsStream("/config.yaml")) {
            Map<String, Object> config = yaml.load(in);

            // Initialize the Velocity engine
            VelocityEngine ve = new VelocityEngine();
            ve.init();

            // Create a Velocity context
            VelocityContext context = new VelocityContext();
            context.put("servers", config.get("servers"));
            context.put("config", config);

            // Create a writer to capture the generated content
            StringWriter writer = new StringWriter();

            // Specify the correct template file path
            String templatePath = "src/main/resources/velocity_template.vm";  // Adjust the path as needed

            // Process the Velocity template
            ve.mergeTemplate(templatePath, "UTF-8", context, writer);

            // Define the output CSV file name
            String outputFileName = Constants.OUTPUT_DIR + "velocity-output.csv";

            // Write the CSV content to the output file in the same folder
            try (FileWriter fileWriter = new FileWriter(outputFileName)) {
                fileWriter.write(writer.toString());
            }

            System.out.println("CSV content written to " + outputFileName);
        }
    }
}
