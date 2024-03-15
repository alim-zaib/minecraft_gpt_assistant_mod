# Minecraft GPT Assistant

## Project Overview
This third-year project integrates OpenAI's GPT models into Minecraft to create a helpful assistant that can answer player questions and offer insights while playing the game. Designed as a testbed, it explores the potential of AI to enhance interactive gaming environments. This mod allows for the evaluation and comparison of responses from different GPT models, offering insights into AI's utility in real-time strategy and decision-making within video games.

## Features
- **Vision-Based Queries**: Analyse in-game screenshots to provide contextual information about the environment, identify objects, and offer advice based on what they see.
- **Textual Assistance**: Utilise natural language processing to answer queries related to game mechanics, crafting recipes, strategic advice, and general Minecraft knowledge.
- **Inventory Analysis**: Transforms the player's inventory into a textual format for comprehensive queries, facilitating suggestions on item use, management, and crafting opportunities.
- **General Minecraft Queries**: Leverages the GPT model to answer wide-ranging questions about Minecraft, providing players with a deeper understanding and guidance without specific context requirements.
- **Interactive Engagement**: Engages with the game world in real-time, offering solutions, hints, and entertainment, making each gameplay session unique and enriched with AI-powered insights.
- **Educational Insights**: Provides educational content on Minecraft's vast ecosystem, encouraging learning through play and exploration, powered by advanced AI models.

## Usage
- `/askvision [query]`: For visual inquiries about your surroundings or seeking suggestions based on screenshots.
- `/askinventory [query]`: Analyses your current inventory setup, offering advice or answers based on your in-game resources.
- `/askgpt [query]`: A versatile command for general Minecraft-related questions, tapping into GPT's knowledge base for immediate, AI-driven responses.

## Testbed for GPT Evaluation
This project serves as a platform for testing and evaluating the effectiveness and accuracy of GPT models within the interactive context of playing video games like Minecraft. It facilitates a unique opportunity to understand how different GPT models respond to a variety of queries, contributing valuable insights into AI's applicability in gaming.

## Technologies and Learning

This section outlines the technologies utilised in this project and the key insights gained from each.

- **OpenAI API**: Used to implement GPT into the mod for natural language processing and machine learning in Minecraft.

- **Java**: Java served as the primary development language for the mod. The project provided an opportunity to apply Java in the context of game modding, enhancing proficiency in the language.

- **Minecraft Documentation**: Critical for gaining insights into Minecraft's internal mechanics and modding capabilities. This documentation was a crucial resource for understanding how to interact with and modify the game programmatically.

- **Forge Documentation**: Forge's modding API facilitated the implementation of this project. Familiarity with Forge's documentation was essential for navigating its modding tools and APIs.

- **Gson for JSON Manipulation**: Gson was used for constructing and parsing JSON payloads for API communication. This improved handling of JSON data structures and ensured accurate data exchange with the OpenAI API.

- **Asynchronous Programming**: The introduction of asynchronous operations was necessary to enhance the mod's performance by preventing freezes during API calls. This aspect of the project highlighted concurrency patterns in Java and their application in improving user experience.

The project represented an exploration of AI's application within a gaming context, requiring an ongoing process of learning and adaptation to implement GPT within Minecraft effectively.


## Acknowledgments
- OpenAI for providing the GPT models that power our assistant's intelligence.
- Minecraft Forge for the modding tools that make this integration possible.
