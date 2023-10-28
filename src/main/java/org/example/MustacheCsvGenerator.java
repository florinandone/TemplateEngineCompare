package org.example;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;

public class MustacheCsvGenerator {
    public static void main(String[] args) throws IOException {
        // Load the YAML configuration from a file using FileInputStream
        InputStream inputStream = new FileInputStream("src/main/resources/config.yaml");
        Yaml yaml = new Yaml();
        Map<String, Object> config = yaml.load(inputStream);

        // Initialize the Mustache factory
        MustacheFactory mustacheFactory = new DefaultMustacheFactory();

        // Load the Mustache template
        Mustache mustache = mustacheFactory.compile("template.mustache");

        // Create a writer to capture the generated CSV content
        StringWriter writer = new StringWriter();

        // Define the output CSV file name
        String outputFileName = "mustache-output.csv";

        // Render the template and write the result to the output file
        try (FileWriter fileWriter = new FileWriter(outputFileName)) {
            mustache.execute(fileWriter, config).flush();
        }

        System.out.println("CSV content written to " + outputFileName);
    }
}
