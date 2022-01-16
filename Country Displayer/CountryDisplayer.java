import java.io.File;
import java.util.Scanner;
import java.lang.Exception;
import java.io.FileNotFoundException;

import java.util.Comparator;

/**
* CountryDisplayer.java
*
* This class takes an input file into its constructor and reads it
* while calling constructor of class Country to create objects that
* stores the data information in the given CSV file 
*
* Additionally, this class has methods to be able to 
**/

public class CountryDisplayer {
  // instance variables up here
  private Comparator<Country> countryComparator;
  private String primaryIndicator; // this will be needed later for me to be able to create graphtitle and more
  private boolean isGreatestToLeast; // this will also be needed later for me to create graphtitle and more
  private SortedLinkedList countryLinkedList;

  /**
  * Constructs a new CountryDisplayer containing the countries in the file at filePath.
  * If filePath is null, constructs an empty CountryDisplayer.
  *
  * @param filePath path to the file from which to load country data
  * @param indicator indicator to use for sorting the countries
  * @param isGreatestToLeast whether the countries should be sorted from greatest to least
  */
  public CountryDisplayer(String filePath, String indicator, boolean isGreatestToLeast) {
    this.primaryIndicator = indicator;
    this.isGreatestToLeast = isGreatestToLeast;
    this.countryComparator = new CountryComparator(indicator, isGreatestToLeast);
    this.countryLinkedList = new SortedLinkedList(countryComparator);

    // code similar to LineReader.java tutorial on moodle
    if (filePath.equals("")) { //if empty, create empty linkedlist.
      this.countryLinkedList.clear();
    } else {
      String inputFilePath = filePath;
      File inputFile = new File(inputFilePath);

      Scanner scanner = null; 
      try {
        scanner = new Scanner(inputFile);
      } catch (FileNotFoundException e) {
          System.err.println("ERROR: >>> " + filePath + " File not found :(!\n"+ e);
          System.exit(1);
      }

      String line = scanner.nextLine(); //first line is discarded

      // below is a loop to store the countries into a Country object list. 
      while (scanner.hasNextLine()) {
        line = scanner.nextLine();
        Country tempCountry = new Country(line);
        this.countryLinkedList.add(tempCountry);
      }
      scanner.close();
    }

  }

  //  class methods below ------------------------------------------------------

  /**
  * Prints a text version of the countries (listed in the same format as in HW4)
  */
  public void displayCountriesAsText(){
    for(int i = 0; i < countryLinkedList.size(); i ++){
      System.out.println(countryLinkedList.get(i));
    }
  }
  
  /**
  * Displays a graph with the top 10 countries (based on the sorting criteria)
  * and a second series showing the additional indicator.
  * @param secondaryIndicator indicator to show as the second series in the graph
  */
  public void displayGraph(String secondaryIndicator){
    String graphTitle;
    if(this.isGreatestToLeast){   
      graphTitle = "Top 10 "+ primaryIndicator;
    } else {
      graphTitle = "Bottom 10 "+ primaryIndicator;
    }
    BarChart countryDataChart = new BarChart(graphTitle, "Country", "Value");
    countryDataChart.displayChart();
    if (this.countryLinkedList.size() <= 10) { // then display all of them on the graph
      helpAddValuesToChart(countryDataChart, this.countryLinkedList.size(), this.primaryIndicator, secondaryIndicator);
    } else { // or else, just display the top/bottom 10
      helpAddValuesToChart(countryDataChart, 10, this.primaryIndicator, secondaryIndicator);
    }
  }

  /* helpAddValuesToChart() is a helper function for displayGraph() function -- 
  it helps avoid duplicate code while adding data values to the BarChart object.*/
  private void helpAddValuesToChart(BarChart chart, int printingIndex, String indicator1, String indicator2){
    for(int i = 0; i < printingIndex; i++) {
      chart.addValue(this.countryLinkedList.get(i).getCountryName(), this.countryLinkedList.get(i).getData(indicator1), indicator1);
      chart.addValue(this.countryLinkedList.get(i).getCountryName(), this.countryLinkedList.get(i).getData(indicator2), indicator2);
    }
  }
  
  /**
  * Changes the criteria for sorting 
  * @param indicator indicator to use for sorting the countries Valid values are: CO2Emissions, 
  *        TotalGreenhouseGasEmissions, AccessToElectricity, RenewableEnergy, ProtectedAreas, 
  *        PopulationGrowth, PopulationTotal, or UrbanPopulationGrowth
  * @param isGreatestToLeast whether the countries should be sorted from greatest to least
  */
  public void changeSortingCriteria(String indicator, boolean isGreatestToLeast){
    this.countryComparator = new CountryComparator(indicator, isGreatestToLeast);
    this.primaryIndicator = indicator;
    this.isGreatestToLeast = isGreatestToLeast;
    this.countryLinkedList.resort(countryComparator);
  }
  
  /**
  * Adds the given country to the data.
  * @param country country to add
  */
  public void addCountry(Country country){
    countryLinkedList.add(country);
  }

  /** 
  * this commandLineOutputs takes in a CountryDisplayer object. And then creates an
  * useable interface in the terminal to interact with the linkedlist created.
  **/

  public static void commandLineOutputs(CountryDisplayer countryData1){
    System.out.println("By default the LinkedList created has been "
        + "order from GreatestToLeast in terms of UrbanPopulationGrowth.");
    String userAnswer = "";
    Scanner scanner = new Scanner(System.in);

    System.out.print( "\n>> What would you like to do? (set sorting criteria, add country, display text," 
      + " display graph, or exit)." + "\n>> ");
      userAnswer = scanner.nextLine();

    while (!userAnswer.equals("exit")) {
      if(userAnswer.equals("set sorting criteria")){
        System.out.print("Enter indicator of choice: \n>> ");
        String indicator = scanner.nextLine();
        System.out.print("Enter (true/false) isGreatestToLeast: \n>> ");
        boolean isGreatestToLeast = Boolean.parseBoolean(scanner.nextLine()); // parse to fix some issue with output
        countryData1.changeSortingCriteria(indicator, isGreatestToLeast);
        System.out.println("Sorting changes have been made!");
      } 

      else if (userAnswer.equals("add country")) {
        System.out.print("To add a country, please enter the followiing information in .csv format: " 
        + "\nCountry Name, Population(total), CO2 emissions (metric tons per capita),"
        +" Access to electricity(%), Renewable energy consumption(%), Terrestrial protected areas (%), "
        +"Population growth(%), UrbanPopulationGrowth(%)\n>> ");
        String inputCountry = scanner.nextLine();
        Country newCountry = new Country(inputCountry);
        countryData1.addCountry(newCountry);
      } 

      else if (userAnswer.equals("display text")){ countryData1.displayCountriesAsText(); }
      
      else if(userAnswer.equals("display graph")){
        System.out.print("Enter secondaryIndicator of choice: \n>> ");    
        String secondaryIndicator = scanner.nextLine();
        countryData1.displayGraph(secondaryIndicator);
      } 
      
      else if (userAnswer.equals("exit")) {
        System.out.println("exiting program..");
        System.exit(0);
      }
      
      System.out.print( "\n>> What would you like to do? \n(Note: IF nothing happened, please reenter: set sorting criteria, add country, display text," 
      + " display graph, or exit)." + "\n>> ");
      userAnswer = scanner.nextLine();
    }
    System.out.println("Exited program!");
    scanner.close();
    System.exit(0);
  }

  public static void main(String[] args){
    String filePath = "";
    if(args.length == 1)  filePath = args[0]; 
  
    //create default list: sorted "UrbanPopulationGrowth", isGreatestToLeast = true
    CountryDisplayer countryData1 = new CountryDisplayer(filePath,"UrbanPopulationGrowth", true);
    CountryDisplayer.commandLineOutputs(countryData1);     
  }
}