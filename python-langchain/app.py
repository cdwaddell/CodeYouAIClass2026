import os
from dotenv import load_dotenv
from langchain_openai import ChatOpenAI
from langchain.agents import create_agent
from langchain_core.tools import Tool
from datetime import datetime

# Load environment variables
load_dotenv()


def get_current_time(input: str) -> str:
    """Returns the current date and time."""
    return datetime.now().strftime("%Y-%m-%d %H:%M:%S")


def reverse_string(input: str) -> str:
    """Reverses a string."""
    return input[::-1]


def calculator(input: str) -> str:
    """Evaluates a mathematical expression. Input should be a valid Python expression."""
    try:
        # Using eval with caution - only for demo purposes
        result = eval(input, {"__builtins__": {}}, {})
        return str(result)
    except Exception as e:
        return f"Error: {str(e)}"


def main():
    print("🤖 Python LangChain Agent Starting...\n")

    # Check for GitHub token
    if not os.getenv("GITHUB_TOKEN"):
        print("❌ Error: GITHUB_TOKEN not found in environment variables.")
        print("Please create a .env file with your GitHub token:")
        print("GITHUB_TOKEN=your-github-token-here")
        print("\nGet your token from: https://github.com/settings/tokens")
        print("Or use GitHub Models: https://github.com/marketplace/models")
        return

    # Initialize the LLM with GitHub Models
    llm = ChatOpenAI(
        model="openai/gpt-4o",
        temperature=0,
        base_url="https://models.github.ai/inference",
        api_key=os.getenv("GITHUB_TOKEN")
    )

    # Define tools for the agent
    tools = [
        Tool(
            name="Calculator",
            func=calculator,
            description="Useful for performing mathematical calculations. Input should be a valid mathematical expression like '25 * 4 + 10'."
        ),
        Tool(
            name="get_current_time",
            func=get_current_time,
            description="Returns the current date and time. Use this when you need to know what time it is."
        ),
        Tool(
            name="reverse_string",
            func=reverse_string,
            description="Reverses a string. Input should be a single string."
        ),
    ]

    # Get the prompt template from LangChain hub
    # Create the agent using create_agent
    agent_executor = create_agent(
        llm,
        tools=tools,
        debug=True
    )

    # Example queries
    queries = [
        "What time is it right now?",
        "What is 25 * 4 + 10?",
        "Reverse the string 'Hello World'",
    ]

    print("Running example queries:\n")

    for query in queries:
        print(f"\n📝 Query: {query}")
        print("─" * 50)
        
        try:
            result = agent_executor.invoke({"input": query})
            print(f"\n✅ Result: {result['output']}\n")
        except Exception as e:
            print(f"❌ Error: {str(e)}\n")

    print("\n🎉 Agent demo complete!")


if __name__ == "__main__":
    main()
