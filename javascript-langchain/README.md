# JavaScript LangChain Agent

A command-line AI agent built with JavaScript and LangChain.

## Setup

1. Install dependencies:
```bash
npm install
```

2. Create a `.env` file with your GitHub token:
```bash
GITHUB_TOKEN=your-github-token-here
```

Get your GitHub token from: https://github.com/settings/tokens
Or use GitHub Models: https://github.com/marketplace/models

## Run

```bash
npm start
```

## Features

This starter project demonstrates:
- **LangChain Agent**: Uses OpenAI Functions agent type
- **GitHub Models**: Access to OpenAI models via GitHub
- **Multiple Tools**: Calculator, time retrieval, and string manipulation
- **Error Handling**: Graceful error messages and token validation
- **Extensibility**: Easy to add more tools and capabilities

## Customization

### Adding New Tools

```javascript
new DynamicTool({
  name: "tool_name",
  description: "What the tool does. The agent uses this to decide when to call it.",
  func: async (input) => {
    // Your tool logic here
    return result;
  },
})
```

### Changing the Model

Modify the `ChatOpenAI` initialization in `app.js` to use different models available on GitHub:
```javascript
const model = new ChatOpenAI({
  modelName: "openai/gpt-4o", // or "openai/gpt-4o-mini", etc.
  temperature: 0.7,
  configuration: {
    baseURL: "https://models.github.ai/inference",
    apiKey: process.env.GITHUB_TOKEN,
  },
});
```

Available models: https://github.com/marketplace/models
