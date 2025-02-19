package com.repo.warden.repo_warden.pojo.genai.rca;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GenerateContentRCADetails {
    private List<Content> contents;
    private GenerationConfig generationConfig;
    private List<Tool> tools;

    public GenerateContentRCADetails() {
        this.generationConfig = new GenerationConfig();
    }

    @Getter
    @Setter
    public static class Content {
        private String role;
        private List<Part> parts;

        @Getter
        @Setter
        public static class Part {
            private String text;
        }
    }

    @Getter
    @Setter
    public static class GenerationConfig {
        private double temperature = 0.3;
        private double topP = 1;
        private int maxOutputTokens = 1000;
    }

    @Getter
    @Setter
    public static class Tool {
        private List<FunctionDeclaration> functionDeclarations;

        @Getter
        @Setter
        public static class FunctionDeclaration {
            private String name;
            private String description;
            private Parameters parameters;

            @Getter
            @Setter
            public static class Parameters {
                private String type;
                private Properties properties;
                private List<String> required;

                @Getter
                @Setter
                public static class Properties {
                    public RcaHtml rca_html;

                    @Getter
                    @Setter
                    public static class RcaHtml {
                        private String type;
                        private String description;
                    }
                }
            }
        }
    }
}