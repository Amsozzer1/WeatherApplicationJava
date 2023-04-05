package com.example;

import javax.imageio.ImageIO;
import javax.lang.model.util.ElementScanner14;
/**
 * Hello world!
 *
 */
import javax.swing.*;
import javax.swing.text.View;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Scanner;
import java.util.jar.Attributes.Name;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class App {
    public static int countLineJava8(String fileName) {
        
        Path path = Paths.get(fileName);
  
        int lines = 0;
        try {
  
            // much slower, this task better with sequence access
            //lines = Files.lines(path).parallel().count();
  
            lines = (int) Files.lines(path).count();
  
        } catch (IOException e) {
            e.printStackTrace();
        }
  
        return lines;
  
    }
    static String dayOrNight(String City) throws ParseException, IOException, InterruptedException
    {
        String dayNight = "day";
        HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create("https://api.weatherapi.com/v1/current.json?key=7604e2613018478ba63133932230204&q="+City))
		.method("GET", HttpRequest.BodyPublishers.noBody())
		.build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response.body());
        JSONObject locationData = (JSONObject) jsonObject.get("current");
        if((locationData.get("is_day").toString()).equals("0"))
        {
            dayNight = "night";
        }
        return dayNight;

    }
    public static JFrame frame;
    public static String CityName;
    public static String message="";
   public static void main(String args[]) throws InterruptedException {
    frame = new JFrame("MySpringDemp"); 

      //Creating a JSONParser object
      CityName = JOptionPane.showInputDialog(null, "Enter the name of the city"); 
      JSONParser jsonParser = new JSONParser();
      try {
        HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create("https://api.weatherapi.com/v1/current.json?key=7604e2613018478ba63133932230204&q="+CityName))
		.method("GET", HttpRequest.BodyPublishers.noBody())
		.build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        
         //Parsing the contents of the JSON file
         JSONObject jsonObject = (JSONObject) jsonParser.parse(response.body());
        
        int NumOfLines =  countLineJava8("weather_conditions.csv");
        Scanner sc= new Scanner(new File("weather_conditions.csv"));
                String[][] row = new String[(int) NumOfLines][4]; 
                for(int i=0; i<NumOfLines; i++)
                {
                    String[] tempLine = sc.nextLine().split(",");
                    row[i][0] = tempLine[0];
                    row[i][1] = tempLine[1];
                    row[i][2] = tempLine[2];
                    row[i][3] = tempLine[3];


                    //System.out.println(row[i][1]); 
                }
                
         
         JSONObject locationData = (JSONObject) jsonObject.get("location");
         //System.out.println("NAME: "+locationData.get("name"));
         JSONObject currentData = (JSONObject) jsonObject.get("current");
         //System.out.println("CONDITIONS: "+currentData.get("condition"));
         JSONObject conditionsData = (JSONObject) currentData.get("condition");
         for(int i=0; i<NumOfLines; i++)
            {
                if(row[i][0].equals( conditionsData.get("code").toString()) )
                {
                    
                    //System.out.println("Icon Code is ==> " + row[i][3] + "\n");
                    

                    ImageIcon icon = new ImageIcon("weather/weather/64x64/"+dayOrNight(CityName)+"/"+row[i][3]+".png");
                    //frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("beautiful-blue-sky-with-white-clouds.jpg")))));
                    frame.setIconImage(icon.getImage()); 
                    frame.setLocation(650,250);               
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
                    Container contentPane = frame.getContentPane();  
                    SpringLayout layout = new SpringLayout();  
                    contentPane.setLayout(layout);  
                    JLabel label = new JLabel("City Name: "); 
                    JLabel label1 = new JLabel("Country: ");  
                    JLabel label2 = new JLabel("Date and Time: "); 
                    message = "City Name: "+locationData.get("name").toString()+"\nCountry: "+ 
                    locationData.get("country").toString()+"\nDate and Time: "+locationData.get("localtime").toString();
                    String weather = (String) conditionsData.get("text");
                            double temp_c = (double) currentData.get("temp_c");
                            double temp_f = (double) currentData.get("temp_f");
                            double feelslike_c = (double) currentData.get("feelslike_c");
                            double feelslike_f = (double) currentData.get("feelslike_f");
                            Object[] options1 = { "Check Weather in C","Check Weather in F", "Quit"};
                            int result = JOptionPane.showOptionDialog(frame, message, "Choose",
                            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options1, null);
                            if (result == 0){
                                
                                JOptionPane.showMessageDialog(frame, "\nWeather Conditions: " + weather + "\n\nTemperature C: " + temp_c + "\n\nFeel like C: "+ feelslike_c, "Celcius", JOptionPane.PLAIN_MESSAGE);
                            
                            
                            }
                            else if(result == 1)
                            {
                                JOptionPane.showMessageDialog(frame, "\nWeather Conditions: " + weather + "\n\nTemperature F: " + temp_f + "\n\nFeel like F: "+ feelslike_f, "Farenhite", JOptionPane.PLAIN_MESSAGE);
                            
                            
                            }
                            else if(result ==2)
                            {
                                frame.dispose();
                            }
                    /*JTextField textField = new JTextField(locationData.get("name").toString(), 15);
                    textField.setEditable(false);
                    JTextField textField1 = new JTextField(locationData.get("country").toString(), 15);
                    textField1.setEditable(false);
                    JTextField textField2 = new JTextField(locationData.get("localtime").toString(), 15);
                    textField2.setEditable(false);
                    textField.setSize(15, 15); 
                    contentPane.add(label);
                    contentPane.add(label1);
                    contentPane.add(label2);    
                    contentPane.add(textField);
                    contentPane.add(textField1);
                    contentPane.add(textField2);

                    JButton conditiButton = new JButton("See Weather Conditions");
                    


                    contentPane.add(conditiButton);



                    //label  and textfield 
                    layout.putConstraint(SpringLayout.WEST, label,100,SpringLayout.WEST, contentPane);  
                    layout.putConstraint(SpringLayout.NORTH, label,100,SpringLayout.NORTH, contentPane);  
                    layout.putConstraint(SpringLayout.WEST, textField,200,SpringLayout.WEST, contentPane);  
                    layout.putConstraint(SpringLayout.NORTH, textField,100,SpringLayout.NORTH, contentPane);
                    
                    //label1  and textfield1
                    layout.putConstraint(SpringLayout.WEST, label1,0,SpringLayout.WEST, label);  
                    layout.putConstraint(SpringLayout.NORTH, label1,70,SpringLayout.NORTH, label); 
                    layout.putConstraint(SpringLayout.WEST, textField1, 6, SpringLayout.WEST, label1);
                    layout.putConstraint(SpringLayout.NORTH, textField1, 50, SpringLayout.SOUTH, textField);
                    layout.putConstraint(SpringLayout.WEST, textField1,200,SpringLayout.WEST, contentPane); 
                    
                    
                    //label2  and textfield2
                    layout.putConstraint(SpringLayout.SOUTH, label2,85,SpringLayout.NORTH, textField1); 
                    layout.putConstraint(SpringLayout.WEST, label2,100,SpringLayout.WEST, contentPane);
                    layout.putConstraint(SpringLayout.WEST, textField2, 100, SpringLayout.WEST, label2);
                    layout.putConstraint(SpringLayout.NORTH, textField2, 50, SpringLayout.SOUTH, textField1);
                    layout.putConstraint(SpringLayout.WEST, textField2,200,SpringLayout.WEST, contentPane); 


                    //Button
                    layout.putConstraint(SpringLayout.NORTH, conditiButton, 50, SpringLayout.SOUTH, label2);
                    layout.putConstraint(SpringLayout.WEST, conditiButton, 150, SpringLayout.WEST, contentPane);


                    //Frame
                    layout.putConstraint(SpringLayout.EAST, contentPane,100,SpringLayout.EAST, textField2);  
                    layout.putConstraint(SpringLayout.SOUTH, contentPane,100,SpringLayout.SOUTH, textField2); 
                    conditiButton.addActionListener(new ActionListener() { 
                        public void actionPerformed(ActionEvent e) { 
                          try {
                            selectionButtonPressed();
                        } catch (IOException | InterruptedException e1) {
                            
                            e1.printStackTrace();
                        } catch (ParseException e1) {
                            
                            e1.printStackTrace();
                        }
                          frame.dispose();
                        }
            
                        private void selectionButtonPressed() throws IOException, InterruptedException, ParseException {
                            frame.setVisible(false);
                            //frame = new JFrame("Weather conditions");
                            HttpRequest request = HttpRequest.newBuilder()
		                    .uri(URI.create("https://api.weatherapi.com/v1/current.json?key=7604e2613018478ba63133932230204&q="+CityName))
		                    .method("GET", HttpRequest.BodyPublishers.noBody())
		                    .build();
                            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                            JSONParser jsonParser = new JSONParser();
                            JSONObject jsonObject = (JSONObject) jsonParser.parse(response.body());
                            JSONObject currentData = (JSONObject) jsonObject.get("current");
                            JSONObject conditionsData = (JSONObject) currentData.get("condition");
                            



    
                            
                }});*/
                

                        
            }};
                //frame.setSize(new Dimension(300,300));
                

                    
                
                
            
            //System.out.println(row[2][0]);

         //System.out.println("");
         //System.out.println("Contact details: ");
         //Iterating the contents of the array
        //Iterator<String> iterator = jsonArray.iterator();
        //while(iterator.hasNext()) {
        //   System.out.println(iterator.next());
        //}
         } catch (FileNotFoundException e) {
        System.out.println("Catch 1");
         e.printStackTrace();
      } catch (IOException e) {
        System.out.println("Catch 2");

            e.printStackTrace();
      } catch (ParseException e) {
        System.out.println("Catch 3");

            e.printStackTrace();
      }
   }
}