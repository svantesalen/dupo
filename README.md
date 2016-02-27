
----------
Background
----------
Now and then I make backup directories. Sometimes they contain other (older) backups.
After some time I come to a point where I don't know what directories have the same contents;
so I made this small program.

--------------------------------------------
The program can locate duplicate directories
--------------------------------------------

- Press the Find button and select one or several directories.
- searching...
- Result is presented:

The result:
- For each directory that has a copy somewhere deep inside the trees of directories you selected,  
  there will be a list item created.
- You may browse among the list items to see the copies paths.

What is considered a copy?
Directories are copies if they contain:
- the same number of (plain) files.
- the same number of subdirectories
- the files in 1) are binary equal.
- the subdirectories are copies (see 1-3)

Improvements
------------
- I am pretty sure the search algorithm can improved.
- TEST: test cases must be added (per use case)
- TEST: test on various platforms
- language is not stored on disk
- increase JList width
- some parts are not translated to swedish

