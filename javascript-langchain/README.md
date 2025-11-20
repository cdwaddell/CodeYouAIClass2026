# JavaScript LangChain Agent

A command-line AI agent built with JavaScript and LangChain.

## Setup

1. Install dependencies:
```bash
npm install
```

2. Create a `.env` file with your OpenAI API key:
```bash
OPENAI_API_KEY=your-api-key-here
```

## Run

```bash
npm start
```

## Features

This starter project demonstrates:
- **LangChain Agent**: Uses OpenAI Functions agent type
- **Multiple Tools**: Calculator, time retrieval, and string manipulation
- **Error Handling**: Graceful error messages and API key validation
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

Modify the `ChatOpenAI` initialization in `app.js`:
```javascript
const model = new ChatOpenAI({
  modelName: "gpt-3.5-turbo", // or "gpt-4-turbo", etc.
  temperature: 0.7,
});
```
