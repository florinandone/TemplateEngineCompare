package org.example;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.FileWriter;
import java.io.IOException;

public class SimpleThymeleafApp {
    public static void main(String[] args) throws IOException {
        // Initialize Thymeleaf template resolver
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode("HTML");
        templateResolver.setSuffix(".html");

        // Create a Thymeleaf template engine
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        // Define the input template file name (without the extension)
        String inputFileName = "template";

        // Create a context and add variables
        Context context = new Context();
        context.setVariable("message", "Hello, Thymeleaf!");

        // Process the template
        String renderedHtml = templateEngine.process(inputFileName, context);

        // Define the output file name with "result_" as a prefix and the original extension
        String extension = ".html"; // Define the file extension
        String outputFileName = "result_" + inputFileName + extension;

        // Write the HTML content to the output file in the same folder
        try (FileWriter writer = new FileWriter(outputFileName)) {
            writer.write(renderedHtml);
        }

        System.out.println("HTML content written to " + outputFileName);
    }
}
