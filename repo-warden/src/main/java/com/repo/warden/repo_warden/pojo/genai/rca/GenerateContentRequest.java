package com.repo.warden.repo_warden.pojo.genai.rca;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GenerateContentRequest {
    private List<Content> contents;
    private GenerationConfig generationConfig;
    private List<Tool> tools;

    public GenerateContentRequest() {
        this.generationConfig = new GenerationConfig();
    }
    // Getters and setters

    @Getter
    @Setter
    public static class Content {
        private String role;
        private List<Part> parts;

        // Getters and setters

        @Getter
        @Setter
        public static class Part {
            private String text;

            // Getters and setters
        }
    }

    @Getter
    @Setter
    public static class GenerationConfig {
        private double temperature = 0.3;
        private double topP = 1;
        private int maxOutputTokens = 500;

        public GenerationConfig() {
            this.temperature = 0.3;
            this.topP = 1;
            this.maxOutputTokens = 500;
        }
        // Getters and setters
    }

    @Getter
    @Setter
    public static class Tool {
        private List<FunctionDeclaration> functionDeclarations;

        // Getters and setters

        @Getter
        @Setter
        public static class FunctionDeclaration {
            private String name;
            private String description;
            private Parameters parameters;

            // Getters and setters

            @Getter
            @Setter
            public static class Parameters {
                private String type;
                private Properties properties;
                private List<String> required;

                // Getters and setters

                @Getter
                @Setter
                public static class Properties {
                    private RelevantPrs relevant_prs;

                    // Getters and setters

                    @Getter
                    @Setter
                    public static class RelevantPrs {
                        private String type;
                        private Items items;
                        private String description;

                        public RelevantPrs() {
                            this.items = new Items();
                        }

                        @Getter
                        @Setter
                        public static class Items {
                            private String type = "object";
                            private ItemProperties properties = new ItemProperties();

                            @Getter
                            @Setter
                            public static class ItemProperties {
                                private Reason reason = new Reason();
                                private Number number = new Number();

                                @Getter
                                @Setter
                                public static class Reason {
                                    private String type = "string";
                                }

                                @Getter
                                @Setter
                                public static class Number {
                                    private String type = "integer";
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}