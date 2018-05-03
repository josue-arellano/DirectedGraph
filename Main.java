/*************************************************************** 
*   file: Main.java 
*   author: J. Arellano 
*   class: CS 241 – Data Structures and Algorithms II
* 
*   assignment: program 3
*   date last modified: 3/8/2018 
* 
*   purpose: This file implements the VertexInterface interface.
*            this file is used to create vertices that are used to hold
*            city data and help us find shortest paths.
* 
****************************************************************/
package directedgraph;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;
import java.io.*;

public class Main
{
    public static void main(String[] args)
    {
        DirectedGraph<Integer, String> graph = new DirectedGraph<Integer, String>();
        Map<String, Integer> map = new HashMap<String, Integer>();
        
        try
        {
            Scanner cityData = new Scanner(new File("city.dat"));
            Scanner roadData = new Scanner(new File("road.dat"));
            
            while(cityData.hasNext())
            {
                String newCity = cityData.nextLine();
                Integer mapValue = Integer.valueOf(newCity.substring(0, 2).trim());
                String cityCode = newCity.substring(4, 6);
                map.put(cityCode, mapValue);
                graph.addVertex(mapValue, newCity);
            }
            
            while(roadData.hasNext())
            {
                String newEdge = roadData.nextLine();
                Integer beginVertex = Integer.valueOf(newEdge.substring(0, 2).trim());
                Integer endVertex = Integer.valueOf(newEdge.substring(2, 7).trim());
                Integer weight = Integer.valueOf(newEdge.substring(7).trim());
                graph.addEdge(beginVertex, endVertex, weight);
            }
        } catch (FileNotFoundException e)
        {
            System.out.println("Error: " + e.getMessage());
            System.exit(0);
        }
        Scanner kb = new Scanner(System.in);
        String command = "";
        printMenu();
        boolean cont = true;
        String beginVertexCode, endVertexCode;
        Integer beginVertex, endVertex;
        do {
            while (wrongCommand(command))
            {
                System.out.print("Command? ");
                command = kb.nextLine();
            }

            char cmd = Character.toUpperCase(command.charAt(0));

            switch (cmd)
            {
                case 'Q':
                    while(command.length() != 2)
                    {
                        System.out.print("City code: ");
                        command = kb.nextLine();
                    }
                    if(map.containsKey(command))
                    {
                        Integer vrtx = map.get(command);
                        System.out.println(graph.getLabel(vrtx));
                    } else
                        System.out.println("City code not found!");
                    break;
                case 'D':
                    while(wrongCityInput(command))
                    {
                        System.out.print("City codes: ");
                        command = kb.nextLine();
                    }
                    beginVertexCode = command.substring(0,2);
                    endVertexCode = command.substring(3);
                    if(map.containsKey(beginVertexCode) && map.containsKey(endVertexCode))
                    {
                        beginVertex = map.get(beginVertexCode);
                        endVertex = map.get(endVertexCode);
                        Stack<VertexInterface<String>> path = new Stack<VertexInterface<String>>();
                        double minDistance = graph.getCheapestPath(beginVertex, endVertex, path);
                        String beginCityName = graph.getLabel(beginVertex).substring(10, 26).trim();
                        String endCityName = graph.getLabel(endVertex).substring(10, 26).trim();

                        if(minDistance == Double.POSITIVE_INFINITY)
                            System.out.println("There is no path from " 
                                             + beginCityName + " to " 
                                             + endCityName + ".");
                        else
                        {
                            System.out.print("The minimum distance between "
                                             + beginCityName + " and "
                                             + endCityName + " is " + minDistance
                                             + " through the route: ");
                            System.out.print(path.pop().getCityCode());
                            while(!path.isEmpty())
                            {
                                String currentCityCode = path.pop().getCityCode();
                                System.out.print(", " + currentCityCode);
                            }
                            System.out.println(".");
                        }
                        
                    } else
                        System.out.println("City codes not found!");
                    break;
                case 'I':
                    while (command.length() < 5)
                    {
                        System.out.print("City codes and distance: ");
                        command = kb.nextLine();
                    }
                    beginVertexCode = command.substring(0, 2);
                    endVertexCode = command.substring(3, 5);
                    command = command.substring(5);
                    Scanner cmndScanner = new Scanner(command);
                    int distance;
                    if(cmndScanner.hasNextInt())
                        distance = cmndScanner.nextInt();
                    else
                        distance = -1;
                    if (map.containsKey(beginVertexCode) 
                        && map.containsKey(endVertexCode)
                        && distance != -1)
                    {
                        beginVertex = map.get(beginVertexCode);
                        endVertex = map.get(endVertexCode);
                        boolean done = graph.addEdge(beginVertex, endVertex, distance);
                        String beginCityName = graph.getLabel(beginVertex).substring(10, 26).trim();
                        String endCityName = graph.getLabel(endVertex).substring(10, 26).trim();
                        if(done)
                            System.out.println("You have inserted a road from "
                                               + beginCityName + " to "
                                               + endCityName + " with a distance of "
                                               + distance + ".");
                        else
                            System.out.println("Unable to add edge.");
                    } else
                    {
                        System.out.println("City codes not found!");
                    }
                    break;
                case 'R':
                    while(wrongCityInput(command))
                    {
                        System.out.print("City codes: ");
                        command = kb.nextLine();
                    }
                    beginVertexCode = command.substring(0,2);
                    endVertexCode = command.substring(3);
                    beginVertex = map.get(beginVertexCode);
                    endVertex = map.get(endVertexCode);
                    if(map.containsKey(beginVertexCode) 
                       && map.containsKey(endVertexCode))
                    {
                        String beginCityName = graph.getLabel(beginVertex).substring(10, 26).trim();
                        String endCityName = graph.getLabel(endVertex).substring(10, 26).trim();
                        if(graph.removeEdge(beginVertex, endVertex))
                            System.out.println("You have removed the road between " 
                                               + beginCityName
                                               + " and " + endCityName
                                               + ".");
                        else
                            System.out.println("The road between " + beginCityName 
                                               + " and " + endCityName
                                               + " does not exist.");
                    } else
                        System.out.println("City codes not found!");
                    break;
                case 'H':
                    printMenu();
                    break;
                case 'E':
                default:
                    cont = false;
                    break;
            }
            command = "";
        } while(cont);
        System.out.println("Thank you for using my program!");
    }
    
    //method:  printMenu
    //purpose: This method prints the menu to the console.
    private static void printMenu()
    {
        System.out.println(
            " Q   Query the city information by entering the city code.\n" +
            " D   Find the minimum distance between two cities.\n" +
            " I   Insert a road by entering two city codes and distance.\n" +
            " R   Remove an existing road by entering two city codes.\n" +
            " H   Display this message.\n" +
            " E   Exit");
    }
    
    //method:  wrongCityInput
    //purpose: This method checks the input to verify if the city code is usable.
    private static boolean wrongCityInput(String input)
    {
        boolean wrong = true;
        if(input.length() == 5)
            if(input.substring(2,3).equals(" "))
                wrong = false;
        return wrong;
    }
    
    //method:  wrongCommand
    //purpose: This method checks the command to make sure that it is a valid
    //         command from the menu.
    private static boolean wrongCommand(String command)
    {
        boolean result = false;
        if(command.length() != 1)
        {
            result = true;
        }
        else
        {
            if(!command.equalsIgnoreCase("Q")
               && !command.equalsIgnoreCase("D")
               && !command.equalsIgnoreCase("I")
               && !command.equalsIgnoreCase("R")
               && !command.equalsIgnoreCase("H")
               && !command.equalsIgnoreCase("E"))
            {
                result = true;
                System.out.println("Wrong Input! Try one of these: ");
                printMenu();
            }
        }
        return result;
    }
}
