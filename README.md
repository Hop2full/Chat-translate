# Chat-translate
Minecraft plugin that translates any language into English and gives the translation in chat.


Version 1: 
To use the plugin, players can type "/translate <language> <text>" in the chat. For example, "/translate fr Bonjour" will translate "Bonjour" from French to English and display the original text and the translation in the chat. Note that you will need to replace "YOUR_API_KEY_HERE" with your own Google Cloud Translation API key in order for this code to work.

Version 2:
This code registers a chat listener that listens for any chat messages sent by players. If the message is not in English, it will be automatically translated into English using the Google Cloud Translation API, and the original message and the translation will be displayed in chat. Note that you will need to replace "YOUR_API_KEY_HERE" with your own Google Cloud Translation API key in order for this code to work.

No need of using / commands in V2.
