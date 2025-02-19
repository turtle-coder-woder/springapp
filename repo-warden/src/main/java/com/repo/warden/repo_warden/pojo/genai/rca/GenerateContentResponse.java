package com.repo.warden.repo_warden.pojo.genai.rca;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GenerateContentResponse {
    private List<Candidate> candidates;
    private UsageMetadata usageMetadata;
    private String modelVersion;

    // Getters and setters

    @Getter
    @Setter
    public static class Candidate {
        private Content content;
        private String finishReason;
        private int index;
        private List<SafetyRating> safetyRatings;

        // Getters and setters
        @Getter
        @Setter
        public static class Content {
            private List<Part> parts;
            private String role;

            // Getters and setters

            @Getter
            @Setter
            public static class Part {
                private FunctionCall functionCall;

                // Getters and setters
                @Getter
                @Setter
                public static class FunctionCall {
                    private String name;
                    private Args args;

                    // Getters and setters

                    @Getter
                    @Setter
                    public static class Args {
                        private List<Integer> relevant_prs;

                        // Getters and setters
                    }
                }
            }
        }

        @Getter
        @Setter
        public static class SafetyRating {
            private String category;
            private String probability;

            // Getters and setters
        }
    }

    @Getter
    @Setter
    public static class UsageMetadata {
        private int promptTokenCount;
        private int candidatesTokenCount;
        private int totalTokenCount;

        // Getters and setters
    }
}