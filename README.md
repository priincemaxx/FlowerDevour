# Flower Devour

Classes are found in core/src/main/java/io/github/some_example_name

To open and run the project refer to this website: https://libgdx.com/wiki/start/import-and-running

Using libresprite for asset creation (textures, sprites, etc.).

# Work rules

1. **Use C-style formatting.**
2. **Avoid reaching deep into classes.** There's almost always a method that already does what you want it to do.
Example of what to avoid:
`thing.getObject().getOtherObject().doThing();`
3. **Document your code!!!** Mandatory. You may only exclude setters, getters and constructors from this,
but adding documentation to them wouldn't hurt either if they're doing something more complex.
4. **Keep object classes as object classes.** Avoid adding excessive clutter to classes that simply represent
an object in the game. If you want to add something new, there's probably a more appropriate class to do it in
or just add a new class.
5. **Avoid code duplication.** Don't override methods if they're exactly the same. Don't add a method to all
child classes when you could have just added it to the parent class. Don't write the same line of code twice. Etc. Etc.
6. **Keep rendering and data separate.** Do not mix in code that works with rendering something and that also works
with data. The only exception being event listeners for buttons.
7. **Finish a method or class fully before pushing changes.** Do all the work that needs to be done first.

# Goals in order of priority

1. **Clean up the code.** Move animations to their own methods and classes, fix up Screens to avoid such
excessive code duplication. Add documentation to undocumented methods and classes.
2. **Implement Map fully.** Make sure the Player can advance through the Map with the Map's advance() method.
Make sure the Map doesn't reset when switching screens.
3. **Implement Rooms fully.** Make CombatRoom be initialized with a random enemy and (sometimes) a random Tool
as a reward. Make LootRoom be initialized with a random PassiveItem. Reward the Player with according item after
clearing the Room.
4. **Fix Inventory screen.** Make it functional again, idk what you guys did to it.
5. **Fix Viewport situation.** Our game is currently unplayable in any other resolution. Stop hardcoding the drawing
values and use values that are a proportion to the window size.
6. **Add EventListener-EvenEmitter system.** If we want dynamic updates for various Items and Enemies, we will
need this.
7. **Add basic methods for showing PassiveItems.** Draw them at the top of the screen in a line. Add hover over
windows for all Items.
8. **Add rarities/types for Items and Enemies.** Update GameMaster and related classes appropriately.
9. **Add a BossRoom class and a Boss type Entity.** Subsequently, add a BossRoom and BossScreen.
10. **Game expansion.** Multiple floors, multiple characters to choose from when starting game,
possible multi-Enemy encounters. Etc. Etc.
