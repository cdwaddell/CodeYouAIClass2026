# Java Semantic Kernel AI Agent - Student Lab Instructions

## Prerequisites

Before starting this lab, ensure you have the following installed:

1. **Java Development Kit (JDK) 17 or higher**
   - Download from: https://www.oracle.com/java/technologies/downloads/
   - Verify installation: `java -version`

2. **Apache Maven**
   - Download from: https://maven.apache.org/download.cgi
   - Verify installation: `mvn -version`

3. **Visual Studio Code**
   - Install the "Extension Pack for Java" from the VS Code marketplace
   - Install the "GitHub Copilot" extension

4. **GitHub Models Access**
   - Sign in to GitHub with your account
   - Visit https://github.com/marketplace/models
   - Create a Personal Access Token (PAT) with appropriate permissions
   - Or use your GitHub Copilot subscription which includes access to GitHub Models

5. **Create .env File**
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
   I'm getting this error when running my Java Semantic Kernel application:
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

## Lab Exercise: Building an AI Agent with Java and Semantic Kernel

### Part 1: Project Setup

**Prompt 1: Create Maven Project**
```
Create a new Maven project for a Java Semantic Kernel AI agent application. The project should:
- Use Java 17
- Include dependencies for Microsoft Semantic Kernel, Azure OpenAI client library, and dotenv-java
- Have groupId: com.codeyou
- Have artifactId: java-semantickernel
- Have version: 1.0-SNAPSHOT
- Create the pom.xml file
```

**Prompt 2: Create Project Structure**
```
Create the standard Maven directory structure and a basic App.java main class in src/main/java/com/codeyou/agent/
```

---

### Part 2: Basic Application Setup (Without Function Calling)

**Prompt 3: Load Environment Variables**
```
In App.java, add code to:
- Load environment variables from a .env file using dotenv-java
- Get the GITHUB_TOKEN from environment variables
- Display an error message if the token is not found
- Include helpful user feedback with emoji
```

**Prompt 4: Initialize GitHub Models Client**
```
Add code to create an OpenAI async client using the Azure OpenAI SDK configured to use GitHub Models endpoint (https://models.github.ai/inference) with the GitHub token from environment variables
```

**Prompt 5: Create Chat Completion Service**
```
Create an OpenAI chat completion service using Semantic Kernel that uses the model "openai/gpt-4o" (available on GitHub Models)
```

**Prompt 6: Build Basic Kernel**
```
Create a Semantic Kernel instance and register the chat completion service with it
```

**Prompt 7: Test Math Query (Without Function)**
```
Add code to:
- Get the chat completion service from the kernel
- Create a ChatHistory object
- Add a user message asking "What is 25 * 4 + 10?"
- Get a response from the AI without any function calling
- Print the result
- Note: The AI will try to answer on its own without tools
```

**Test Point**: Run the application. You should see the AI attempt to answer the math question, but it may not be accurate since it doesn't have access to calculation tools.

---

### Part 3: Adding Function Calling with Plugins

**Prompt 8: Create MathPlugin**
```
Create a MathPlugin.java class that:
- Has a calculate method annotated with @DefineKernelFunction
- Takes a mathematical expression as a string parameter
- Uses JavaScript ScriptEngine to evaluate the expression
- Returns the result as a string
- Includes error handling
- Includes proper descriptions for the function and parameter
```

**Prompt 9: Register MathPlugin with Kernel**
```
Update App.java to:
- Create an instance of MathPlugin using KernelPluginFactory
- Register the plugin with the kernel builder
```

**Prompt 10: Add Function Calling Support**
```
Update the chat interaction code to:
- Create an InvocationContext with ToolCallBehavior that allows all kernel functions
- Use getChatMessageContentsAsync with the kernel and invocation context
- Use .block() to wait for the async result
```

**Test Point**: Run the application again. Now the AI should use the MathPlugin to accurately calculate "What is 25 * 4 + 10?" and return 110.

---

**Prompt 11: Test String Query (Without Function)**
```
Replace the math query with a new query: "Reverse the string 'Hello World'"
Comment out the MathPlugin registration
Run the application and observe that the AI attempts to reverse the string without tools (may be inaccurate)
```

**Test Point**: Run the application. The AI will try to reverse the string on its own, which may not be reliable.

---

**Prompt 12: Create StringPlugin**
```
Create a StringPlugin.java class that:
- Has a reverseString method annotated with @DefineKernelFunction
- Takes a string input parameter
- Returns the reversed string
- Includes proper descriptions for the function and parameter
```

**Prompt 13: Register StringPlugin with Kernel**
```
Update App.java to:
- Create an instance of StringPlugin using KernelPluginFactory
- Register the StringPlugin with the kernel builder (along with MathPlugin)
```

**Test Point**: Run the application again. Now the AI should use the StringPlugin to accurately reverse the string and return "dlroW olleH".

---

**Prompt 14: Test Time Query (Without Function)**
```
Replace the string query with a new query: "What time is it right now?"
Comment out the TimePlugin registration (keep Math and String plugins commented)
Run the application and observe that the AI cannot provide the current time
```

**Test Point**: Run the application. The AI will not know the current time since it doesn't have access to system time.

---

**Prompt 15: Create TimePlugin**
```
Create a new TimePlugin.java class in the same package that:
- Has a getCurrentTime method annotated with @DefineKernelFunction
- Returns the current date and time formatted as "yyyy-MM-dd HH:mm:ss"
- Includes proper descriptions for the function
```

**Prompt 16: Register TimePlugin with Kernel**
```
Update App.java to:
- Create an instance of TimePlugin using KernelPluginFactory
- Register all three plugins (TimePlugin, MathPlugin, and StringPlugin) with the kernel builder
```

**Prompt 17: Create Multiple Test Queries**
```
Replace the single query with an array of test queries:
- "What time is it right now?"
- "What is 25 * 4 + 10?"
- "Reverse the string 'Hello World'"

Loop through each query, add it to chat history, get the AI response with function calling, and display results with clear formatting
```

**Test Point**: Run the application. All three queries should now work accurately using their respective plugins.

---

**Prompt 18: Add Error Handling**
```
Wrap the query loop and individual queries in try-catch blocks to handle any errors gracefully and display user-friendly error messages
```

---

### Part 4: Final Testing

**Testing Instructions:**

Run the application with all three plugins enabled. You should observe:
- Time queries return accurate current time via TimePlugin
- Math calculations are precise via MathPlugin
- String reversal is correct via StringPlugin
- The AI seamlessly chooses and uses the appropriate function for each query

---

## Expected Output

When running with function calling enabled, you should see output similar to:

```
🤖 Java Semantic Kernel Agent Starting...

Running example queries:

📝 Query: What time is it right now?
──────────────────────────────────────────────────

✅ Result: The current date and time is 2026-01-12 14:30:45

📝 Query: What is 25 * 4 + 10?
──────────────────────────────────────────────────

✅ Result: 110

📝 Query: Reverse the string 'Hello World'
──────────────────────────────────────────────────

✅ Result: dlroW olleH

🎉 Agent demo complete!
```

---

## Discussion Questions

After completing the lab, consider:

1. What's the difference between responses with and without function calling?
2. Why are plugins/tools important for AI agents?
3. How does Semantic Kernel orchestrate the function calls?
4. What other types of plugins would be useful for an AI agent?

---

## Extension Challenges

If you finish early, try:

### Additional Plugins
1. Create a WeatherPlugin that returns mock weather data
2. Add a FilePlugin that can read/write text files
3. Create a DatabasePlugin that queries a simple in-memory database
4. Add conversation memory to maintain context across multiple interactions

### Cross-Cutting Concerns (Use Copilot to Help!)

**Logging & Observability**
5. Add comprehensive logging throughout the application
   - Log all AI requests and responses
   - Log function calls with parameters and results
   - Use a logging framework like SLF4J with Logback
   - Include timestamps and log levels (INFO, DEBUG, ERROR)
   - Ask Copilot: "Add logging to track AI interactions and function calls"

**Performance Monitoring**
6. Add performance metrics and timing
   - Measure response time for each AI query
   - Track function execution duration
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

**Handling Rate Limit Responses (HTTP 429)**
9. Handle rate limiting responses from the API
   - Detect and catch HTTP 429 (Too Many Requests) errors
   - Parse retry-after headers from the response
   - Implement automatic retry after the specified delay
   - Display user-friendly messages when rate limited
   - Ask Copilot: "Add handling for HTTP 429 rate limit responses with automatic retry"
