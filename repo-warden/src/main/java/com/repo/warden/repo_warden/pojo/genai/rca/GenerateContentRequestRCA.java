package com.repo.warden.repo_warden.pojo.genai.rca;

        import java.util.ArrayList;

        public class GenerateContentRequestRCA extends GenerateContentRequest {
            public GenerateContentRequestRCA(String text) {
                super();
                GenerateContentRequest.Content content = new GenerateContentRequest.Content();
                content.setRole("user");
                content.setParts(new ArrayList<>()); // Initialize the parts list
                GenerateContentRequest.Content.Part part = new GenerateContentRequest.Content.Part();
                part.setText(text);
                content.getParts().add(part);

                // Set tool values
                GenerateContentRequest.Tool tool = new GenerateContentRequest.Tool();
                GenerateContentRequest.Tool.FunctionDeclaration functionDeclaration = new GenerateContentRequest.Tool.FunctionDeclaration();
                functionDeclaration.setName("identify_relevant_changes");
                functionDeclaration.setDescription("Identify relevant PRs related to an incident.");
                GenerateContentRequest.Tool.FunctionDeclaration.Parameters parameters = new GenerateContentRequest.Tool.FunctionDeclaration.Parameters();
                parameters.setType("object");
                GenerateContentRequest.Tool.FunctionDeclaration.Parameters.Properties properties = new GenerateContentRequest.Tool.FunctionDeclaration.Parameters.Properties();
                GenerateContentRequest.Tool.FunctionDeclaration.Parameters.Properties.RelevantPrs relevantPrs = new GenerateContentRequest.Tool.FunctionDeclaration.Parameters.Properties.RelevantPrs();
                relevantPrs.setType("array");
                relevantPrs.setDescription("List of relevant PR IDs.");
                properties.setRelevant_prs(relevantPrs);
                parameters.setProperties(properties);
                parameters.setRequired(new ArrayList<>()); // Initialize the required list
                parameters.getRequired().add("relevant_prs");
                functionDeclaration.setParameters(parameters);
                tool.setFunctionDeclarations(new ArrayList<>()); // Initialize the functionDeclarations list
                tool.getFunctionDeclarations().add(functionDeclaration);
                setTools(new ArrayList<>()); // Initialize the tools list
                getTools().add(tool);
                setContents(new ArrayList<>()); // Initialize the contents list
                getContents().add(content);
            }
        }