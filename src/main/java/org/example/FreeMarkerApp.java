package org.example;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FreeMarkerApp {
    public static void main(String[] args) throws IOException, TemplateException {
        // Load configuration from the YAML file
        Yaml yaml = new Yaml();
        try (InputStream in = FreeMarkerApp.class.getResourceAsStream("/config.yaml")) {
            Map<String, Object> config = yaml.load(in);

            // Create a FreeMarker configuration
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
            cfg.setClassForTemplateLoading(FreeMarkerApp.class, "/templates");

            // Load the FreeMarker template
            Template template = cfg.getTemplate("free_maker_template.ftl");

            // Create a data model
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("servers", config.get("servers"));
            dataModel.put("config", config);

            // Process the FreeMarker template
            StringWriter stringWriter = new StringWriter();
            template.process(dataModel, stringWriter);

            // Define the output CSV file name
            String outputFileName = Constants.OUTPUT_DIR + "free_marker_output.csv";

            // Write the CSV content to the output file in the same folder
            try (FileWriter writer = new FileWriter(outputFileName)) {
                writer.write(stringWriter.toString());
            }

            System.out.println("CSV content written to " + outputFileName);
        }
    }
}
