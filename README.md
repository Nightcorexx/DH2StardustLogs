# DH2StardustLogs
Quick way to figure out whether you should sell or use your stardust logs depending on the current market situation.

The executable .jar file can be found under "out\artifacts\DH2StardustLogs_jar\DH2StardustLogs.jar"

Once downloaded you can rename the .jar file to whatever you want. For this purpose now we make it general and just call it name.

The name.jar can be executed by using your command prompt (cmd) and navigating to the folder where the name.jar file is located. So for example if your name.jar file is in a folder D:\foo\boo\name.jar you can navigate there with the following commands:
  1. d:
  2. cd foo\boo
  
or with:
  1. d:
  2. cd foo
  3. cd boo
  
In case you want to go back one hierarchy type "cd.."
  
Once you reached the folder in which the name.jar file is in you enter this command:
  java -jar name.jar
  
You can skip the navigating part but then your command needs to look like following:
  java -jar D:\foo\boo\name.jar
  
And voilà. The "magic" starts.

Please note that you need to have java 14 installed for this to work. I could compile it with an older version (smth like Java 8) but I just can't be bothered right now.
