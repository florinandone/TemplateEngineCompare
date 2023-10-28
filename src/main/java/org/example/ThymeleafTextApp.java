package org.example;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class ThymeleafTextApp {
    public static void main(String[] args) throws IOException {
        // Load configuration from the YAML file
        Yaml yaml = new Yaml();
        try (InputStream in = ThymeleafTextApp.class.getResourceAsStream("/config.yaml")) {
            Map<String, Object> config = yaml.load(in);

            // Initialize Thymeleaf template resolver
            ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
            templateResolver.setTemplateMode(TemplateMode.TEXT);
            templateResolver.setSuffix(".txt"); // Use the plain text template

            // Create a Thymeleaf template engine
            TemplateEngine templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(templateResolver);

            // Create a context and add the configuration
            Context context = new Context();
            context.setVariables(config);

            // Process the Thymeleaf template
            String csvContent = templateEngine.process("thymeleaf_text_template", context);

            // Define the output CSV file name
            String outputFileName = Constants.OUTPUT_DIR + "thymeleaf_text_output.csv";

            // Write the CSV content to the output file in the same folder
            try (FileWriter writer = new FileWriter(outputFileName)) {
                writer.write(csvContent);
            }

            System.out.println("CSV content written to " + outputFileName);
        }
    }
}
