# JavaScript LangChain AI Agent - Student Lab Instructions

## Prerequisites

Before starting this lab, ensure you have the following installed:

1. **Node.js (Version 18 or higher)**
   - Download from: https://nodejs.org/
   - Verify installation: `node --version`
   - Verify npm: `npm --version`

2. **Visual Studio Code**
   - Install the "JavaScript and TypeScript" extension (usually pre-installed)
   - Install the "GitHub Copilot" extension

3. **GitHub Models Access**
   - Sign in to GitHub with your account
   - Visit https://github.com/marketplace/models
   - Create a Personal Access Token (PAT) with appropriate permissions
   - Or use your GitHub Copilot subscription which includes access to GitHub Models

4. **Create .env File**
   - Create a `.env` file in the project root directory
   - Add your GitHub token: `GITHUB_TOKEN=your_token_here`
   - Replace `your_token_here` with your actual GitHub Personal Access Token

---

## 💡 Working with GitHub Copilot Chat for Debugging

**When you encounter runtime errors:**

1. **Copy the error message** from your terminal or console output
2. **Open GitHub Copilot Chat** (click the chat icon in VS Code sidebar or use `Ctrl+Alt+I` / `Cmd+Alt+I`)
3. **Provide context** by typing something like:
   ```
   I'm getting this error when running my JavaScript LangChain application:
   [paste your error message here]
   
   Can you help me fix it?
   ```
4. **Review the suggestion** and apply the fix Copilot recommends
5. **Ask follow-up questions** if you don't understand the solution

**Pro Tips:**
- Include the full error stack trace for better diagnostics
- Mention what you were trying to do when the error occurred
- If the first suggestion doesn't work, tell Copilot and ask for alternatives
- Use Copilot Chat to explain error messages you don't understand

---

## Lab Exercise: Building an AI Agent with JavaScript and LangChain

### Part 1: Project Setup

**Prompt 1: Initialize Node.js Project**
```
Initialize a new Node.js project for a JavaScript LangChain AI agent application. Create a package.json file with:
- Name: javascript-langchain
- Version: 1.0.0
- Type: module (for ES6 imports)
- Description: AI Agent using LangChain and GitHub Models
- Main entry point: app.js
```

**Prompt 2: Install Required Dependencies**
```
Add npm install commands to install the following packages:
- @langchain/openai (for OpenAI integration)
- @langchain/community (for community tools like Calculator)
- @langchain/core (for core LangChain utilities)
- langchain (main LangChain library)
- dotenv (for environment variables)
```

**Prompt 3: Create Basic App Structure**
```
Create an app.js file that:
- Uses ES6 module imports
- Has an async main() function
- Calls main().catch(console.error) at the end
- Includes a starting message with emoji
```

---

### Part 2: Basic Application Setup (Without Tools)

**Prompt 4: Load Environment Variables**
```
In app.js, add code to:
- Import and configure dotenv
- Check if GITHUB_TOKEN exists in environment variables
- Display an error message with helpful instructions if the token is not found
- Exit the process if no token is found
- Include helpful user feedback with emoji
```

**Prompt 5: Initialize ChatOpenAI Model**
```
Add code to create a ChatOpenAI instance that:
- Uses the model "openai/gpt-4o"
- Sets temperature to 0 for deterministic responses
- Configures the baseURL to "https://models.github.ai/inference"
- Uses the GITHUB_TOKEN as the apiKey in the configuration
```

**Prompt 6: Test Basic Query (Without Tools)**
```
Add code to:
- Import the ChatOpenAI's invoke method
- Create a test query: "What is 25 * 4 + 10?"
- Call model.invoke() with the query
- Print the response content
- Note: The AI will try to answer on its own without tools
```

**Test Point**: Run the application with `node app.js`. You should see the AI attempt to answer the math question, but it may not be accurate since it doesn't have access to calculation tools.

---

### Part 3: Adding Tools and Agent Executor

**Prompt 7: Import Agent and Tools**
```
Update the imports to include:
- initializeAgentExecutorWithOptions from "langchain/agents"
- Calculator from "@langchain/community/tools/calculator"
- DynamicTool from "@langchain/core/tools"
```

**Prompt 8: Create Calculator Tool**
```
After initializing the model, create a tools array with:
- The Calculator tool from @langchain/community
```

**Prompt 9: Create Agent Executor**
```
Add code to:
- Create an agent executor using initializeAgentExecutorWithOptions
- Pass the tools array, model, and configuration object with agentType: "openai-functions" and verbose: true
- Use await since it's an async function
```

**Prompt 10: Update Query to Use Agent**
```
Replace the direct model.invoke() call with:
- Create a test query: "What is 25 * 4 + 10?"
- Use executor.invoke() with an object containing the input query
- Print the result.output
- Wrap in try-catch for error handling
```

**Test Point**: Run the application again. Now the AI should use the Calculator tool to accurately calculate "What is 25 * 4 + 10?" and return 110. You should see verbose output showing the tool being called.

---

**Prompt 11: Test Time Query (Without Tool)**
```
Replace the math query with a new query: "What time is it right now?"
Comment out the Calculator tool from the tools array
Run the application and observe that the AI cannot provide the current time accurately
```

**Test Point**: Run the application. The AI will not know the current time since it doesn't have access to system time.

---

**Prompt 12: Create Time Tool**
```
Add a DynamicTool to the tools array that:
- Has name: "get_current_time"
- Has a description explaining it returns the current date and time
- Has a func that is an async function returning new Date().toString()
```

**Prompt 13: Test Time Query with Tool**
```
Uncomment or add back the Calculator tool
Keep the query: "What time is it right now?"
Run the application
```

**Test Point**: Run the application. Now the AI should use the get_current_time tool to provide the current time.

---

**Prompt 14: Test String Query (Without Tool)**
```
Replace the query with: "Reverse the string 'Hello World'"
Comment out the time tool
Run the application and observe that the AI attempts to reverse the string but may not be reliable
```

**Test Point**: Run the application. The AI will try to reverse the string on its own, which may not be perfect.

---

**Prompt 15: Create String Reversal Tool**
```
Add a DynamicTool to the tools array that:
- Has name: "reverse_string"
- Has a description: "Reverses a string. Input should be a single string."
- Has a func that is an async function taking input and returning the reversed string using split("").reverse().join("")
```

**Prompt 16: Test with All Three Tools**
```
Ensure all three tools are in the tools array:
- Calculator
- get_current_time
- reverse_string
Update the query to: "Reverse the string 'Hello World'"
```

**Test Point**: Run the application. The AI should use the reverse_string tool to accurately reverse the string and return "dlroW olleH".

---

**Prompt 17: Create Multiple Test Queries**
```
Replace the single query with an array of test queries:
- "What time is it right now?"
- "What is 25 * 4 + 10?"
- "Reverse the string 'Hello World'"

Add a loop that:
- Iterates through each query
- Prints the query with formatting
- Calls executor.invoke() for each query
- Prints the result with formatting
- Includes try-catch error handling for each query
```

**Test Point**: Run the application. All three queries should now work accurately using their respective tools. You should see verbose output showing which tool is being called for each query.

---

**Prompt 18: Improve Output Formatting**
```
Update the output formatting to:
- Print a header "Running example queries:"
- For each query, print a separator line using "─".repeat(50)
- Display results with ✅ emoji for success
- Display errors with ❌ emoji for failures
- Add a completion message at the end
```

---

### Part 4: Final Testing

**Testing Instructions:**

Run the application with all three tools enabled. You should observe:
- Time queries return accurate current time via get_current_time tool
- Math calculations are precise via Calculator tool
- String reversal is correct via reverse_string tool
- The AI seamlessly chooses and uses the appropriate tool for each query
- Verbose output shows the agent's reasoning and tool selection

---

## Expected Output

When running with all tools enabled and verbose mode, you should see output similar to:

```
🤖 JavaScript LangChain Agent Starting...

Running example queries:


📝 Query: What time is it right now?
──────────────────────────────────────────────────

[Agent reasoning and tool calls displayed in verbose mode]

✅ Result: The current date and time is Sun Jan 12 2026 14:30:45 GMT-0500 (Eastern Standard Time)


📝 Query: What is 25 * 4 + 10?
──────────────────────────────────────────────────

[Agent reasoning and tool calls displayed in verbose mode]

✅ Result: 110


📝 Query: Reverse the string 'Hello World'
──────────────────────────────────────────────────

[Agent reasoning and tool calls displayed in verbose mode]

✅ Result: dlroW olleH


🎉 Agent demo complete!
```

---

## Discussion Questions

After completing the lab, consider:

1. What's the difference between responses with and without tools?
2. Why are tools important for AI agents?
3. How does LangChain orchestrate the tool calls?
4. What is the role of the agent executor in this architecture?
5. How does the "openai-functions" agent type work?

---

## Extension Challenges

If you finish early, try:

### Additional Tools
1. Create a WeatherTool that returns mock weather data for a given city
2. Add a FileTool that can read/write text files using Node.js fs module
3. Create a WebSearchTool that simulates web search results
4. Add conversation memory to maintain context across multiple interactions using BufferMemory

### Cross-Cutting Concerns (Use Copilot to Help!)

**Logging & Observability**
5. Add comprehensive logging throughout the application
   - Log all AI requests and responses
   - Log tool calls with parameters and results
   - Use a logging library like winston or pino
   - Include timestamps and log levels (info, debug, error)
   - Ask Copilot: "Add logging using winston to track AI interactions and tool calls"

**Performance Monitoring**
6. Add performance metrics and timing
   - Measure response time for each AI query
   - Track tool execution duration using console.time() and console.timeEnd()
   - Log slow queries (over a threshold)
   - Ask Copilot: "Add performance monitoring to track query response times"

**Error Handling & Resilience**
7. Implement robust error handling
   - Add retry logic with exponential backoff for API failures
   - Handle rate limiting scenarios gracefully
   - Provide detailed error messages to users
   - Ask Copilot: "Add retry logic with exponential backoff for AI API calls"

**Input Validation**
8. Add input validation and sanitization
   - Validate user queries before sending to AI
   - Sanitize inputs to prevent injection attacks
   - Set maximum query length limits
   - Ask Copilot: "Add input validation to sanitize and validate user queries"

**Streaming Responses**
9. Implement streaming for real-time responses
   - Use the streaming capability of LangChain
   - Display responses as they are generated token by token
   - Handle streaming errors gracefully
   - Ask Copilot: "Add streaming support to display AI responses in real-time"

**Handling Rate Limit Responses (HTTP 429)**
10. Handle rate limiting responses from the API
    - Detect and catch HTTP 429 (Too Many Requests) errors
    - Parse retry-after headers from the response
    - Implement automatic retry after the specified delay
    - Display user-friendly messages when rate limited
    - Ask Copilot: "Add handling for HTTP 429 rate limit responses with automatic retry"