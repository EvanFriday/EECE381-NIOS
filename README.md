EECE381-NIOS
============

Map project

To set up project for committing purposes:

1. Clone https://github.com/SteveQiu/EECE381-NIOS.git
2. Create Android Project from existing code, navigating to the "src/software/IndoorMapper" location for creation

(There are possibly some files that won't be ignored properly as I haven't set up my NIOSII stuff yet)

Platform setup:
1. Create Quartus project like we did in module 1
2. After generating file in Niosystem, copy 3 SD card files in src/ directory into system/ubcmcldsystem/submodule
3. create, generate bsp and according project like in module 1
4. bsp project can be named whatever, the main project however has to be named Pathfinder

Android software Fix:
If there are errors in help.java. It is very likely that some support library is missing in the project.
Right click on the Pathfinder->android tools->add support library

Pathfinder/ is ignore in gitignore, when you have new file in Pathfinder that you want to add, add them using with force command
