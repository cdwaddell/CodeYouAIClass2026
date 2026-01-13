# Python LangChain Agent

A command-line AI agent built with Python and LangChain.

## Setup

1. Create a virtual environment:
```bash
python -m venv venv
```

2. Activate the virtual environment:
```bash
# Windows
venv\Scripts\activate

# Linux/Mac
source venv/bin/activate
```

3. Install dependencies:
```bash
pip install -r requirements.txt
```

4. Create a `.env` file with your GitHub token:
```bash
GITHUB_TOKEN=your-github-token-here
```

Get your GitHub token from: https://github.com/settings/tokens
Or use GitHub Models: https://github.com/marketplace/models

## Run

```bash
python app.py
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

```python
def my_tool(input: str) -> str:
    """Tool description. The agent uses this to decide when to call it."""
    # Your tool logic here
    return result

# Add to tools list
Tool(
    name="my_tool",
    func=my_tool,
    description="Tool description"
)
```

### Changing the Model

Modify the `ChatOpenAI` initialization in `app.py` to use different models available on GitHub:
```python
llm = ChatOpenAI(
    model="openai/gpt-4o",  # or "openai/gpt-4o-mini", etc.
    temperature=0.7,
    base_url="https://models.github.ai/inference",
    api_key=os.getenv("GITHUB_TOKEN")
)
```

Available models: https://github.com/marketplace/models

## Dependencies

- **langchain**: Core LangChain framework
- **langchain-openai**: OpenAI integration
- **python-dotenv**: Environment variable management
