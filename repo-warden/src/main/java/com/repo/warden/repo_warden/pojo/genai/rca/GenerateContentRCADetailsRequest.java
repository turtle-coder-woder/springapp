package com.repo.warden.repo_warden.pojo.genai.rca;

import java.util.ArrayList;
public class GenerateContentRCADetailsRequest extends GenerateContentRCADetails {
    public GenerateContentRCADetailsRequest(String prDiff, String incidentSubject, String incidentDescription) {
        super();
        String text = generateText(prDiff, incidentSubject, incidentDescription);
        GenerateContentRCADetails.Content content = new GenerateContentRCADetails.Content();
        content.setRole("user");
        content.setParts(new ArrayList<>());
        GenerateContentRCADetails.Content.Part part = new GenerateContentRCADetails.Content.Part();
        part.setText(text);
        content.getParts().add(part);
        setContents(new ArrayList<>());
        getContents().add(content);

        GenerateContentRCADetails.Tool tool = new GenerateContentRCADetails.Tool();
        GenerateContentRCADetails.Tool.FunctionDeclaration functionDeclaration = new GenerateContentRCADetails.Tool.FunctionDeclaration();
        functionDeclaration.setName("generate_rca");
        functionDeclaration.setDescription("Generate a detailed Root Cause Analysis (RCA) in HTML format explaining how a PR might be related to an incident.");
        GenerateContentRCADetails.Tool.FunctionDeclaration.Parameters parameters = new GenerateContentRCADetails.Tool.FunctionDeclaration.Parameters();
        parameters.setType("object");
        GenerateContentRCADetails.Tool.FunctionDeclaration.Parameters.Properties properties = new GenerateContentRCADetails.Tool.FunctionDeclaration.Parameters.Properties();
        GenerateContentRCADetails.Tool.FunctionDeclaration.Parameters.Properties.RcaHtml rcaHtml = new GenerateContentRCADetails.Tool.FunctionDeclaration.Parameters.Properties.RcaHtml();
        rcaHtml.setType("string");
        rcaHtml.setDescription("Detailed RCA output in HTML format.");
        properties.setRca_html(rcaHtml);
        parameters.setProperties(properties);
        parameters.setRequired(new ArrayList<>());
        parameters.getRequired().add("rca_html");
        functionDeclaration.setParameters(parameters);
        tool.setFunctionDeclarations(new ArrayList<>());
        tool.getFunctionDeclarations().add(functionDeclaration);
        setTools(new ArrayList<>());
        getTools().add(tool);
    }

    private String generateText(String prDiff, String incidentSubject, String incidentDescription) {
        return new StringBuilder().append("You are an AI assistant that analyzes incidents and pull requests to generate a detailed Root Cause Analysis (RCA) in HTML format using bootstrap css.\n\nA new incident has occurred. Here are the details:\n\nIncident Subject:")
                .append(incidentSubject)
                .append("\nIncident Description: ")
                .append(incidentDescription)
                .append("\n\nHere is the PR diff:\n\n")
                .append(prDiff)
                .append("\n\n### **Expected Output:**\nGenerate a detailed Root Cause Analysis (RCA) in HTML format using bootstrap classes(with only div, not full HTML) explaining how this PR could have contributed to the incident.").toString();
    }


}
