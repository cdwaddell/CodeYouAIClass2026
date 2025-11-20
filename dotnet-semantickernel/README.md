# C# Semantic Kernel Agent

A command-line AI agent built with C# and Microsoft Semantic Kernel.

## Setup

1. Ensure you have .NET SDK installed (version 8.0 or later)

2. Restore dependencies:
```bash
dotnet restore
```

3. Set your OpenAI API key using one of these methods:

   **Option A: Environment Variable (for current session)**
   ```powershell
   $env:OPENAI_API_KEY="your-api-key-here"
   ```

   **Option B: User Secrets (recommended for development)**
   ```bash
   dotnet user-secrets set "OPENAI_API_KEY" "your-api-key-here"
   ```

## Run

```bash
dotnet run
```

## Features

This starter project demonstrates:
- **Semantic Kernel**: Microsoft's lightweight AI SDK
- **Plugins**: Modular functions (TimePlugin, MathPlugin, StringPlugin)
- **Auto Function Calling**: Kernel automatically invokes appropriate functions
- **Type Safety**: Strong typing with C# attributes
- **Error Handling**: Graceful error messages and API key validation

## Customization

### Adding New Plugins

```csharp
public class MyPlugin
{
    [KernelFunction, Description("Description of what this function does")]
    public string MyFunction(
        [Description("Description of the parameter")] string input)
    {
        // Your logic here
        return result;
    }
}

// Register in Program.cs
builder.Plugins.AddFromType<MyPlugin>();
```

### Changing the Model

Modify the kernel builder in `Program.cs`:
```csharp
builder.AddOpenAIChatCompletion(
    "gpt-3.5-turbo",  // or "gpt-4-turbo", etc.
    apiKey
);
```

### Using Azure OpenAI

Replace the OpenAI connector:
```csharp
builder.AddAzureOpenAIChatCompletion(
    deploymentName: "your-deployment-name",
    endpoint: "https://your-resource.openai.azure.com/",
    apiKey: azureApiKey
);
```

## Dependencies

- **Microsoft.SemanticKernel**: Core Semantic Kernel framework
- **Microsoft.Extensions.Configuration**: Configuration management
- **Microsoft.Extensions.Configuration.EnvironmentVariables**: Environment variable support
- **Microsoft.Extensions.Configuration.UserSecrets**: User secrets management
