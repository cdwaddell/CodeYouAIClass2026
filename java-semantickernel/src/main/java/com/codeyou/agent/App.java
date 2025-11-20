package com.codeyou.agent;

import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.orchestration.ToolCallBehavior;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;
import io.github.cdimascio.dotenv.Dotenv;

public class App {
    public static void main(String[] args) {
        System.out.println("🤖 Java Semantic Kernel Agent Starting...\n");

        // Load environment variables from .env file
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        String apiKey = dotenv.get("OPENAI_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            apiKey = System.getenv("OPENAI_API_KEY");
        }

        if (apiKey == null || apiKey.isEmpty()) {
            System.out.println("❌ Error: OPENAI_API_KEY not found in environment variables.");
            System.out.println("Please create a .env file with your OpenAI API key:");
            System.out.println("OPENAI_API_KEY=your-api-key-here");
            return;
        }

        try {
            // Create kernel with OpenAI chat completion
            Kernel kernel = Kernel.builder()
                    .withAIService(ChatCompletionService.class, 
                        com.microsoft.semantickernel.aiservices.openai.chatcompletion.OpenAIChatCompletion.builder()
                            .withApiKey(apiKey)
                            .withModelId("gpt-4")
                            .build())
                    .withPlugin(new TimePlugin(), "TimePlugin")
                    .withPlugin(new MathPlugin(), "MathPlugin")
                    .withPlugin(new StringPlugin(), "StringPlugin")
                    .build();

            // Get chat completion service
            ChatCompletionService chatService = kernel.getService(ChatCompletionService.class);

            // Create chat history
            ChatHistory chatHistory = new ChatHistory();

            // Example queries
            String[] queries = {
                "What time is it right now?",
                "What is 25 * 4 + 10?",
                "Reverse the string 'Hello World'"
            };

            System.out.println("Running example queries:\n");

            for (String query : queries) {
                System.out.println("\n📝 Query: " + query);
                System.out.println("─".repeat(50));

                chatHistory.addUserMessage(query);

                try {
                    // Create invocation context with auto function calling
                    InvocationContext invocationContext = InvocationContext.builder()
                            .withToolCallBehavior(ToolCallBehavior.allowAllKernelFunctions(true))
                            .build();

                    ChatMessageContent<?> result = chatService.getChatMessageContentsAsync(
                            chatHistory,
                            kernel,
                            invocationContext
                    ).block().get(0);

                    System.out.println("\n✅ Result: " + result.getContent() + "\n");
                    chatHistory.addAssistantMessage(result.getContent());
                } catch (Exception e) {
                    System.out.println("❌ Error: " + e.getMessage() + "\n");
                }
            }

            System.out.println("\n🎉 Agent demo complete!");
        } catch (Exception e) {
            System.err.println("❌ Fatal error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
