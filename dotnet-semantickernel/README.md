# C# Semantic Kernel Agent

A command-line AI agent built with C# and Microsoft Semantic Kernel.

## Setup

1. Ensure you have .NET SDK installed (version 8.0 or later)

2. Restore dependencies:
```bash
dotnet restore
```

3. Set your GitHub token using one of these methods:

   **Option A: Environment Variable (for current session)**
   ```powershell
   $env:GITHUB_TOKEN="your-github-token-here"
   ```

   **Option B: User Secrets (recommended for development)**
   ```bash
   dotnet user-secrets set "GITHUB_TOKEN" "your-github-token-here"
   ```

   Get your GitHub token from: https://github.com/settings/tokens
   Or use GitHub Models: https://github.com/marketplace/models

## Run

```bash
dotnet run
```

## Features

This starter project demonstrates:
- **Semantic Kernel**: Microsoft's lightweight AI SDK
- **GitHub Models**: Access to OpenAI models via GitHub
- **Plugins**: Modular functions (TimePlugin, MathPlugin, StringPlugin)
- **Auto Function Calling**: Kernel automatically invokes appropriate functions
- **Type Safety**: Strong typing with C# attributes
- **Error Handling**: Graceful error messages and token validation

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

Modify the kernel builder in `Program.cs` to use different models available on GitHub:
```csharp
builder.AddOpenAIChatCompletion(
    modelId: "openai/gpt-4o",  // or "openai/gpt-4o-mini", etc.
    apiKey: githubToken,
    endpoint: new Uri("https://models.github.ai/inference")
);
```

Available models: https://github.com/marketplace/models

## Dependencies

- **Microsoft.SemanticKernel**: Core Semantic Kernel framework
- **Microsoft.Extensions.Configuration**: Configuration management
- **Microsoft.Extensions.Configuration.EnvironmentVariables**: Environment variable support
- **Microsoft.Extensions.Configuration.UserSecrets**: User secrets management
