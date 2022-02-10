---
title: Sample application: Graph Shell
---

## About

To demonstrate the work of search algorithms, I made a small console program. The program allows you to select one of three graph samples and search for a path using two algorithms. The source code of the program is located in `graph-shell` module. 

## Build & Run

To compile the program, use the command:

```shell
$ gradle assemble
```

This command will create an executable jar, so in Linux or macOS you may run the application by

```shell
$ ./build/libs/graph-shell-1.0-SNAPSHOT.jar 
```

On the Windows machine you should run the application using java:

```shell
$ java -jar ./build/libs/graph-shell-1.0-SNAPSHOT.jar 
```

## Using Graph Shell

The application has build-in three graph schemas: `simple`, `medium`, `complex`. By default the `complex` scheme is loaded. You may specify the schema in the `application.properties` file:

```properties
graph=simple
```

Alternatively you may specify the command line parameter when you run the application:

```shell
$ ./build/libs/graph-shell-1.0-SNAPSHOT.jar --graph=medium
```

After starting the program you will see the banner and the prompt indicating the current graph scheme.

```shell
$ ./build/libs/graph-shell-1.0-SNAPSHOT.jar 

   _____                          _          _____   _              _   _
  / ____|                        | |        / ____| | |            | | | |
 | |  __   _ __    __ _   _ __   | |__     | (___   | |__     ___  | | | |
 | | |_ | | '__|  / _` | | '_ \  | '_ \     \___ \  | '_ \   / _ \ | | | |
 | |__| | | |    | (_| | | |_) | | | | |    ____) | | | | | |  __/ | | | |
  \_____| |_|     \__,_| | .__/  |_| |_|   |_____/  |_| |_|  \___| |_| |_|
                         | |
                         |_|

complex:> 
```

The command help will show all available commands with short description. 

```shell
complex:> help
AVAILABLE COMMANDS

Built-In Commands
        clear: Clear the shell screen.
        exit, quit: Exit the shell.
        help: Display help about available commands.
        history: Display or save the history of previously run commands
        script: Read and execute commands from a file.
        stacktrace: Display the full stacktrace of the last error.

Commands
        distance: prints distance for the path
        fastest: finds the fastest path by using Dijkstra's Algorithm
        schema: prints schema of the graph
        shortest: finds the shortest path by using Breadth First Search Algorithm
```

Typing `help <command>` you will get more detailed information about each command.

```shell
complex:> help fastest


NAME
	fastest - finds the fastest path by using Dijkstra's Algorithm

SYNOPSYS
	fastest [--source] string  [--target] string  

OPTIONS
	--source  string
		
		[Mandatory]
		[this vertex was not found in the graph diagram]

	--target  string
		
		[Mandatory]
		[this vertex was not found in the graph diagram]
```

The program supports scripts. I provide one script `test1.script` to demonstrate the difference between two algorithms. 




