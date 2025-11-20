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

4. Create a `.env` file with your OpenAI API key:
```bash
OPENAI_API_KEY=your-api-key-here
```

## Run

```bash
python app.py
```

## Features

This starter project demonstrates:
- **LangChain Agent**: Uses OpenAI Functions agent type
- **Multiple Tools**: Calculator, time retrieval, and string manipulation
- **Error Handling**: Graceful error messages and API key validation
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

Modify the `ChatOpenAI` initialization in `app.py`:
```python
llm = ChatOpenAI(
    model="gpt-3.5-turbo",  # or "gpt-4-turbo", etc.
    temperature=0.7
)
```

## Dependencies

- **langchain**: Core LangChain framework
- **langchain-openai**: OpenAI integration
- **python-dotenv**: Environment variable management
