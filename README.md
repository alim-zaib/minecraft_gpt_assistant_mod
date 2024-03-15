# Minecraft GPT Assistant

## Project Overview
This third-year project integrates OpenAI's GPT models into Minecraft to create a helpful assistant that can answer player questions and offer insights while playing the game. Designed as a testbed, it explores the potential of AI to enhance interactive gaming environments. This mod allows for the evaluation and comparison of responses from different GPT models, offering insights into AI's utility in real-time strategy and decision-making within video games.

## Features
- **Vision-Based Queries**: Analysing screenshots within the game to offer details about the surroundings recognise objects and give suggestions based on visual information.
- **Textual Support**: Using GPT models to address questions regarding game mechanics crafting recipes strategic tips and general knowledge about Minecraft.
- **Inventory Analysis**: Converting the players inventory into text for inquiries providing advice on item usage organisation and crafting possibilities.
- **General Minecraft Queries**: Using the GPT model to respond to questions about Minecraft without requiring specific context assisting players in gaining deeper insights and guidance.
- **Interactive Engagement**: Interacting with the game environment in time by proposing solutions, clues and entertainment elements that add uniqueness and enriched perspectives powered by AI during each gameplay session.

## Usage
- `/askvision [query]`: For visual inquiries about your surroundings or seeking suggestions based on screenshots.
- `/askinventory [query]`: Analyses your inventory setup and receive advice or information based on the resources you have in game.
- `/askgpt [query]`: A versatile command for general Minecraft-related questions, tapping into GPT's knowledge base for immediate, AI-driven responses.

## Testbed for GPT in Gaming

This mod acts as a research platform for qualitatively evaluating the performance of GPT models in the interactive setting of Minecraft gameplay. It provides a structured environment to assess the models' effectiveness in understanding and responding to various player queries. Methods such as player surveys will be employed to gather qualitative data on user experience and the relevance and accuracy of the AI's responses, offering insights into the practical application of AI in gaming contexts.


## Technologies and Learning

This section outlines the technologies utilised in this project and the key insights gained from each.

- **OpenAI API**: Used to implement GPT into the mod for natural language processing and machine learning in Minecraft.

- **Java**: Java served as the primary development language for the mod. The project provided an opportunity to apply Java in the context of game modding, enhancing proficiency in the language.

- **Minecraft Documentation**: Critical for gaining insights into Minecraft's internal mechanics and modding capabilities. This documentation was a crucial resource for understanding how to interact with and modify the game programmatically.

- **Forge Documentation**: Forge's modding API facilitated the implementation of this project. Familiarity with Forge's documentation was essential for navigating its modding tools and APIs.

- **Gson for JSON Manipulation**: Gson was used for constructing and parsing JSON payloads for API communication. This improved handling of JSON data structures and ensured accurate data exchange with the OpenAI API.

- **Asynchronous Programming**: The introduction of asynchronous operations was necessary to enhance the mod's performance by preventing freezes during API calls. This aspect of the project highlighted concurrency patterns in Java and their application in improving user experience.


## Acknowledgments
- OpenAI for providing the GPT models that power our assistant's intelligence.
- Minecraft Forge for the modding tools that make this integration possible.
