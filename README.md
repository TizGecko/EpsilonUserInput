# EpsilonUserInput
An implementation for Standalone GUI UserInput in Epsilon Suite programs

## Description
This third-party plugin exposes the capability to standalone Eclipse Epsilon executions to use User Input dialogs, making that way users to take runtime actions during models explorations.

For example, it is possible to ask users at runtime to take actions during an ETL transformation, or an EGL/EGX text generation.

The built-in System.user is not usable in standalone executions because it refers to standard I/O console, not available in general in an Eclipse lauched app. (Tested under MacOSX)
This plugin, instead, prompts GUI dialogs on screen, using method calls compatible with original ones.

i.e. 
```
System.user.prompt("Title", "Default option");
            ... BECOMES ...
<your_variable_name>.prompt("Title", "Default option");
```
     
It is also obviously possible to use this plugin in a non-standalone Epsilon program, making zero-time modification to obtain a standalone Epsilon code.

## Declaring plugin
To use this external plugin in Epsilon programs, you have to use the Native() function to import it.

Every Epsilon Language (except for EOL, which is in some sense never used alone) has a "pre" section. At this point you must firstly declare using Native() function this plugin, in order to use it from the very next line of code to the end.

i.e.
```
pre {
  // declare User Input
  var userinput = new Native("userinput.api.UserInput");
  
  // from now on I can use it as standard System.user
  var t: String = userinput.prompt("Test text input", "No text");
  
  ...

}
```

**REMEMBER to add Dependency into your own project**

## Credits
TizGecko (c) 2020, The University of L'Aquila
