---
description: "Create a focused learning guide and, when useful, demo code for a Java or Spring course topic; follow up by menu number."
name: "Learn a Java or Spring Topic"
argument-hint: "Topic to learn, such as dependency injection or REST controllers"
agent: "agent"
---

Help me learn the course topic: **${input:topic:Enter a Java or Spring topic}**

## Prepare the learning material

1. Inspect the current project structure and existing code before choosing where demo code belongs.
2. Create a concise, beginner-friendly Markdown learning guide under `learning/`, using a clear topic-based filename. Explain:
   - What the topic is and why it is useful
   - The key concepts and how they fit together
   - A small, concrete example
   - Common beginner mistakes
   - A short recap
3. Add demo code only when runnable code materially helps teach the topic. Follow the project's existing conventions and dependencies. Do not add dependencies or change project configuration just to force a demo; explain if a demo is omitted because the current project cannot run it without broader setup.
4. Keep the explanation original and focused on the topic; do not reproduce course lesson text.
5. Validate any demo code using the smallest suitable existing build or test command. Do not claim a demo runs unless it was verified.
6. Finish by linking the created learning guide and any demo files, briefly state what was created, and show the numbered follow-up menu below.

## Numbered follow-up prompts

When I reply with a number, use the matching prompt below in the context of the topic and learning guide created most recently. Treat any text accompanying the number as additional instructions.

1. **Explain it simply** — Explain the topic in plain language with a fresh, small example.
2. **Quiz me** — Ask five questions one at a time; wait for my answer before asking the next, then explain any mistakes.
3. **Give me a practice exercise** — Give one appropriately scoped exercise with acceptance criteria. Do not reveal the solution unless I ask.
4. **Walk through the demo** — Explain the demo code step by step, connecting each part to the learning guide.
5. **Make flashcards** — Create 8–12 concise question-and-answer flashcards for active recall.
