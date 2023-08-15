# Mastermind

## Hayden Chan's Personal Project

### Purpose:
<p> This program will be a recreation of the popular board game Mastermind.<p>
<p> Anyone who loves a logic challenge should play this game and test their logical minds.<p>
<p> This is one of my favourite board games growing up,
    so I'm interested in recreating it as one of my first Java Projects.<p>

#### **User Story:**
- As a user, I want to be able to select a colour, have that input turned into ColourPegs, and added to a PegsCombination
- As a user, I want to get feedback as to how correct my attempt was.
  - Black: Correct position and Correct colour
  - White: Correct colour
- As a user, I want to be able to be notified when my colour combination is exactly correct.
- As a user, I want to be able to save my mastermind game to file 
  - attempt history, answer key, attempt number, maxsize
- As a user, I want to be able to be able to load up a save file and resume progress
- As a user, I want to be able to undo a wrong peg choice
- As a user, I want to be able to restart with the same board size (restart) or with a different board size (new game)

**Instructions for Grader**
- You can generate the first required action related to adding X's to a Y by selecting one of the pegs under the pegs 
option menu, which adds a peg into the currentCombination(a list of Pegs)
- You can generate the second required action related to adding X's to a Y by selecting undo under file option menu, 
which removes a peg from the currentCombination
- You can locate my visual component by running main in the GraphicRunner class under the ui package
  - When a peg is chosen, a corresponding colour will show up
- you can save the state of my application by selecting save under the file option menu
- you can reload the state of my application by selecting load under the file option menu

**Phase 4: Task 2**
- The Answer Key has been generated
- A red peg has been added to the combination
- A blue peg has been added to the combination
- A green peg has been added to the combination
- A yellow peg has been added to the combination
- A black peg has been added to the combination
- The pins have been generated
- The Pegs and Pins have been combined
- The pegs and pins have been added to the gameBoard
- A orange peg has been added to the combination
- A black peg has been added to the combination
- A orange peg has been added to the combination
- A yellow peg has been added to the combination
- A black peg has been added to the combination
- A black peg has been added to the combination
- A black peg has been added to the combination
- A black peg has been added to the combination
- The pins have been generated
- The Pegs and Pins have been combined
- The pegs and pins have been added to the gameBoard

**Phase 4: Task 3**

If I had more time to refactor, I would redesign how I handled the answer key. Currently, it's a part of the 
PegsCombination class. Instead, I would have a new class called answerKey, use the singleton design pattern to ensure 
there's only one answer key throughout the application, and make that class extend the PegsCombination since it 
has many similar behaviours.

The reason why this refactoring would be beneficial is that not only would it simplify the PegsCombination class, 
but it would also ensure there aren't multiple answer keys instances that gets created by accident.