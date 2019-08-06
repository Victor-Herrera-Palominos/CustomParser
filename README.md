# CustomParser
Simple parser made in Java

Reads files with custom instructions and outputs in text how a pen with a chosen color(Hex) moves from one set of coordinates to another.

Custom Instructions (Exclude Brackets): 

FORW/BACK [Num].  - Moves the pen forward/backward a set number of steps
LEFT/RIGHT [Num]. - Rotates which way the pen will move (on a Forward command) a set number of degrees. Default is 0 (Positive x-axis)
UP/DOWN.          - Only prints if previously set to DOWN. Default is UP. 
COLOR [#FF0000].  - Sets Color to chosen Hex value. Default color is #FF0000
REP # [INSTR]     - Repeats an instruction(Eg: FORW 1.) a set number of times
REP # "[INSTRS]"  - Repeats multiple instructions(Eg. "FORW 1. LEFT 90.") a set number of times

NOTE: A Dot(".") must be placed to dictate the end of an instruction. Rep instructions are an exception. 
Spaces between instruction name and number is also important.
