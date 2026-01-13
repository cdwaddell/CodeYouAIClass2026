# Java Semantic Kernel Agent

A command-line AI agent built with Java and Microsoft Semantic Kernel.

## Prerequisites

- Java Development Kit (JDK) 17 or later
- Maven 3.6 or later

## Setup

1. Ensure Java and Maven are installed:
```bash
java -version
mvn -version
```

2. Create a `.env` file with your GitHub token:
```
GITHUB_TOKEN=your-github-token-here
```

Get your GitHub token from: https://github.com/settings/tokens
Or use GitHub Models: https://github.com/marketplace/models

3. Build the project:
```bash
mvn clean install
```

## Run

**Using Maven:**
```bash
mvn exec:java
```

**Using the compiled JAR:**
```bash
java -jar target/java-semantickernel-agent-1.0-SNAPSHOT.jar
```

## Features

This starter project demonstrates:
- **Semantic Kernel**: Microsoft's lightweight AI SDK for Java
- **GitHub Models**: Access to OpenAI models via GitHub
- **Plugins**: Modular functions (TimePlugin, MathPlugin, StringPlugin)
- **Auto Function Calling**: Kernel automatically invokes appropriate functions
- **Type Safety**: Strong typing with Java annotations
- **Error Handling**: Graceful error messages and token validation

## Project Structure

```
java-semantickernel/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── codeyou/
│                   └── agent/
│                       ├── App.java           # Main application
│                       ├── TimePlugin.java    # Time-related functions
│                       ├── MathPlugin.java    # Math operations
│                       └── StringPlugin.java  # String manipulation
├── pom.xml                                    # Maven configuration
├── .env.example                               # Example environment file
└── README.md
```

## Customization

### Adding New Plugins

Create a new plugin class:

```java
package com.codeyou.agent;

import com.microsoft.semantickernel.semanticfunctions.annotations.DefineKernelFunction;
import com.microsoft.semantickernel.semanticfunctions.annotations.KernelFunctionParameter;

public class MyPlugin {
    
    @DefineKernelFunction(
        name = "myFunction",
        description = "Description of what this function does",
        returnDescription = "Description of the return value"
    )
    public String myFunction(
            @KernelFunctionParameter(
                name = "input",
                description = "Description of the parameter"
            ) String input) {
        // Your logic here
        return result;
    }
}
```

Register it in `App.java`:
```java
.withPlugin(new MyPlugin(), "MyPlugin")
```

### Changing the Model

Modify the model ID in `App.java` to use different models available on GitHub:
```java
OpenAIAsyncClient openAIAsyncClient = new OpenAIClientBuilder()
        .endpoint("https://models.github.ai/inference")
        .credential(new KeyCredential(githubToken))
        .buildAsyncClient();

ChatCompletionService chatCompletionService = 
    com.microsoft.semantickernel.aiservices.openai.chatcompletion.OpenAIChatCompletion.builder()
        .withOpenAIAsyncClient(openAIAsyncClient)
        .withModelId("openai/gpt-4o")  // or "openai/gpt-4o-mini", etc.
        .build();
```

Available models: https://github.com/marketplace/models

## Dependencies

- **semantic-kernel-api**: Core Semantic Kernel framework
- **semantic-kernel-connectors-ai-openai**: OpenAI integration
- **slf4j-simple**: Logging framework
- **dotenv-java**: Environment variable management

## Troubleshooting

### Maven Build Issues

If you encounter dependency resolution issues, try:
```bash
mvn clean install -U
```

### Java Version Issues

Ensure you're using Java 17 or later. Check with:
```bash
java -version
```

Set JAVA_HOME if needed:
```bash
# Windows PowerShell
$env:JAVA_HOME="C:\Program Files\Java\jdk-17"

# Linux/Mac
export JAVA_HOME=/path/to/jdk-17
```
