# Chat App Part 3

## What was added in part 3?
### Five populated arrays in message.java 
  - Sent Messages -> The full message text of every message the user chose to send
  - Disregarded Messages -> The full message text on every message the user chose to discard
  - Stored Messages -> Messages red back from the JSON file into memory
  - Message Hash -> The hash string for every message processed
  - Message ID -> The unique ID for every message processed
### The stored messages sub-menu
  - Display all stored messages
  - Display longest message
  - Search by message ID
  - Search bu recipient
  - Delete by message hash
  - Display full report
### The 6 new unit tests
  - testSentMessageArray_correctlyPopulated
  - testDisplayLongestMessage_returnsCorrectMessage
  - testSearchByMessageID_returnsCorrectMessage
  - testSearchByRecipient_returnsAllMatchingMessages
  - testDeleteByHash_removeCorrectMessage
  - testDisplayReport_containsRequiredFields
## How do you run the code?
To run the code you right click anywhere on your mainapp class and click on the "run code" option and the project will start running. As the file is running you will the required information where asked in part of testing if the code returns the appropriate outcome. If the outcomes are wrong you have to go back to your code the fix the possible errors until it returns the correct outcome.
