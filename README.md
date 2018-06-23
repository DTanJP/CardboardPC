# CardboardPC
A lightweight java plugin system.

Purpose:
A plugin driven application that runs the OS plugin which then handles the rest of the other plugins. Designed to give most of the control to the OS.

To use:
Upon first run, CardboardPC will set up the necessary directories and then close.
There you must place a plugin into the OS folder.
Then rerun CardboardPC again and it will start.


FAQ:

Q. Can I put more than 1 plugin in the OS folder?

A. No

Q. What file formats are the plugins?

A. jar or class files

Q. What is the purpose of the OS?

A. To pass messages/requests between applications and drivers

Q. Why is this called CardboardPC?

A. It's simple, lightweight and not a polished piece of software. It's mainly used as a concept for a plugin system.

Q. Can CardboardPC do anything else without a OS?

A. No

Q. Does this come with a OS?

A. No. Not yet...

Q. What's the difference between a driver and a application?

A. Applications only runs when the user instructs the OS to run it. Drivers are always being runned. However since most of the control is left to the OS. This is just a concept and in no way enforceable.