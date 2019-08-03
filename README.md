# CardboardPC

A lightweight java plugin system.
====================================

Purpose: A plugin driven application that runs the OS plugin which then handles the rest of the other plugins. Designed to give most of the control to the OS.
==========

To use: Upon first run, CardboardPC will set up the necessary directories and then close. Then you must place a plugin into the OS folder. Then rerun CardboardPC again and it will start.

FAQ:

Q. Can I put more than 1 plugin in the OS folder?

A. No

Q. What file formats are the plugins?

A. jar or class files

Q. What is the purpose of the OS?

A. To pass messages/requests between applications and drivers

Q. Can CardboardPC do anything else without a OS?

A. No

Q. Does this come with a OS?

A. Yes. Check out [DustOS](https://github.com/DTanJP/DustOS). A prototype OS created to work for this.

Q. What's the difference between a driver and a application?

A. Applications only runs when the user instructs the OS to run it. Drivers are always being executed. However since most of the control is left to the OS. This is just a concept and in no way enforceable. Drivers also extends the functionality of the OS.

Q. Is there a prebuilt executable file that I can download?
  
A. Yes if you don't want to build it yourself. Download it [here](https://github.com/DTanJP/CardboardPC/tree/master/bin)
