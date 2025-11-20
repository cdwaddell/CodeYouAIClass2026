import { ChatOpenAI } from "@langchain/openai";
import { initializeAgentExecutorWithOptions } from "langchain/agents";
import { Calculator } from "@langchain/community/tools/calculator";
import { DynamicTool } from "@langchain/core/tools";
import dotenv from "dotenv";

// Load environment variables
dotenv.config();

async function main() {
  console.log("🤖 JavaScript LangChain Agent Starting...\n");

  // Check for API key
  if (!process.env.OPENAI_API_KEY) {
    console.error("❌ Error: OPENAI_API_KEY not found in environment variables.");
    console.log("Please create a .env file with your OpenAI API key:");
    console.log("OPENAI_API_KEY=your-api-key-here");
    process.exit(1);
  }

  // Initialize the LLM
  const model = new ChatOpenAI({
    modelName: "gpt-4",
    temperature: 0,
  });

  // Define tools for the agent
  const tools = [
    new Calculator(),
    new DynamicTool({
      name: "get_current_time",
      description: "Returns the current date and time. Use this when you need to know what time it is.",
      func: async () => {
        return new Date().toString();
      },
    }),
    new DynamicTool({
      name: "reverse_string",
      description: "Reverses a string. Input should be a single string.",
      func: async (input) => {
        return input.split("").reverse().join("");
      },
    }),
  ];

  // Create the agent
  const executor = await initializeAgentExecutorWithOptions(tools, model, {
    agentType: "openai-functions",
    verbose: true,
  });

  // Example queries
  const queries = [
    "What time is it right now?",
    "What is 25 * 4 + 10?",
    "Reverse the string 'Hello World'",
  ];

  console.log("Running example queries:\n");

  for (const query of queries) {
    console.log(`\n📝 Query: ${query}`);
    console.log("─".repeat(50));
    
    try {
      const result = await executor.invoke({ input: query });
      console.log(`\n✅ Result: ${result.output}\n`);
    } catch (error) {
      console.error(`❌ Error: ${error.message}\n`);
    }
  }

  console.log("\n🎉 Agent demo complete!");
}

main().catch(console.error);
