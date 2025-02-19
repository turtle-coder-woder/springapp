package com.repo.warden.repo_warden.pojo.genai.rca;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseDetailsRCA {
    private List<Candidate> candidates;
    private UsageMetadata usageMetadata;
    private String modelVersion;

    @Getter
    @Setter
    public static class Candidate {
        private Content content;
        private String finishReason;
        private int index;
        private List<SafetyRating> safetyRatings;

        @Getter
        @Setter
        public static class Content {
            private List<Part> parts;
            private String role;

            @Getter
            @Setter
            public static class Part {
                private FunctionCall functionCall;

                @Getter
                @Setter
                public static class FunctionCall {
                    private String name;
                    private Args args;

                    @Getter
                    @Setter
                    public static class Args {
                        private String rca_html;
                    }
                }
            }
        }

        @Getter
        @Setter
        public static class SafetyRating {
            private String category;
            private String probability;
        }
    }

    @Getter
    @Setter
    public static class UsageMetadata {
        private int promptTokenCount;
        private int candidatesTokenCount;
        private int totalTokenCount;
    }
}