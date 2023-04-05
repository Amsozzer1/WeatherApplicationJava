# WeatherApplicationJava
The program uses the JSON.simple library to parse the JSON response from the WeatherAPI and create a GUI window.
The program first prompts the user to enter a city name, and then uses the Weather API to get the weather information for the entered city. 
The program then parses a CSV file containing weather condition codes and their corresponding icons, and displays the appropriate icon for the weather condition. 
The program also displays other relevant weather information such as the city name, country, date and time, temperature, feels like temperature, humidity, and wind speed.

The program uses the following libraries:

javax.swing: for creating and managing the graphical user interface
java.net.http.HttpClient: for making HTTP requests to the Weather API
org.json.simple: for parsing the JSON data returned by the Weather API
java.nio.file: for reading the weather condition codes and icons from the CSV file
The countLineJava8 method uses the Files.lines method to count the number of lines in a file, and the dayOrNight method uses the Weather API to determine whether 
it is currently day or night in the entered city. The main method is the entry point of the program and creates a graphical user interface to display the weather 
information.
