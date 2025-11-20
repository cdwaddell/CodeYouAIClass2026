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

2. Create a `.env` file with your OpenAI API key:
```
OPENAI_API_KEY=your-api-key-here
```

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
- **Plugins**: Modular functions (TimePlugin, MathPlugin, StringPlugin)
- **Auto Function Calling**: Kernel automatically invokes appropriate functions
- **Type Safety**: Strong typing with Java annotations
- **Error Handling**: Graceful error messages and API key validation

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

Modify the kernel builder in `App.java`:
```java
.withModelId("gpt-3.5-turbo")  // or "gpt-4-turbo", etc.
```

### Using Azure OpenAI

Replace the OpenAI connector in the kernel builder:
```java
import com.microsoft.semantickernel.aiservices.openai.chatcompletion.AzureOpenAIChatCompletion;

// ...

.withAIService(ChatCompletionService.class, 
    AzureOpenAIChatCompletion.builder()
        .withDeploymentName("your-deployment-name")
        .withEndpoint("https://your-resource.openai.azure.com/")
        .withApiKey(azureApiKey)
        .build())
```

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
