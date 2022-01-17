import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

import java.util.List;
import java.util.ArrayList;

import java.lang.Math;

/**
*
* Country.java
*
* This country class creates an instance of a Country
* object and stores various data points about a country
* passed into the Country class constructor in form of a String
*
* Class Country contains methods: getData(String indicator)
* which returns Double data value of any given data indicator (see method comments)
* Method .toString() is overridden to return String of object Country
* that mimics the format that it was read in initially upon construction.
*
**/

public class Country {
  // instance variables of class Country
  private String countryName;
  private List<Double> countryData;
  // private double carbonO2Emissions, accessToElectricty, renewableEnergy;
  // private double protectedAreas, populationGrowth, urbanPopulationGrowth, populationTotal;

  /* class Country constructor takes in a line of String and splits it up 
  to then store them into corresponding instance variables*/
  public Country(String lineOfData) {
    this(lineOfData.split(","));
  }
  // another constructor that takes an array of strings that have already been 
  // split up to create object file. 
  public Country(String[] splitData){
    countryData = new ArrayList<Double>();
    this.countryName = splitData[0];
    this.countryData.add(Double.parseDouble(splitData[1])); //populationTotal @index 0
    this.countryData.add(Double.parseDouble(splitData[2])); //CO2Emissions @1
    this.countryData.add(Double.parseDouble(splitData[3])); //accessToElectricty @2
    this.countryData.add(Double.parseDouble(splitData[4])); //renewableEnergy @3
    this.countryData.add(Double.parseDouble(splitData[5])); //protectedAreas @4
    this.countryData.add(Double.parseDouble(splitData[6])); //populationGrowth @5
    this.countryData.add(Double.parseDouble(splitData[7])); //urbanPopulationGrowth @6
  }

  // ------------ CLASS METHODS BELOW --------------
  
  /* below is a private helper function that takes in an indicator 
  and returns the index of countryData list that holds this perticular Double */
  private int helpFindData(String indicator) {
    switch (indicator) {
      case "PopulationTotal":
        return 0;
      case "CO2Emissions":
        return 1;
      case "AccessToElectricity":
        return 2;
      case "RenewableEnergy":
        return 3;
      case "ProtectedAreas":
        return 4;
      case "PopulationGrowth":
        return 5;
      case "UrbanPopulationGrowth":
        return 6;
    }//end switch
    // indicate error if indicator index didn't get returned by this point. 
    System.err.println(indicator + " is a not a valid data type indicator! See code comment for correct tag.");
    return 0;
  }

  /* this function takes in a string: "PopulationTotal", 
  * "CO2Emissions", "AccessToElectricity", "RenewableEnergy", "ProtectedAreas", 
  * "PopulationGrowth", or "UrbanPopulationGrowth" -- type indicators
  * and depending on the string, this function returns corresponding data. 
  */
  public Double getData(String indicator){
    int dataIndex = helpFindData(indicator);
    return this.countryData.get(dataIndex);
  }

  /* this function takes in a string:"PopulationTotal","CO2Emissions","AccessToElectricity", etc
  * and a newValue for the indicated data instance, and changes the data value. */
  public void setData(String indicator, Double newValue) {
    int dataIndex = helpFindData(indicator);
    this.countryData.set(dataIndex, newValue);
  }

  /* getCountryName() returns the name of the country in String*/
  public String getCountryName(){
    return countryName;
  }
  
  public boolean equals(Country otherCountry){
    if(!this.countryName.equals(otherCountry.countryName)){
      return false;
    } // country name isnt equal

    Double bounds = 0.001;
    int index = 0; // while loop controlling variable

    while(index!=7){
      Double difference = Math.abs((this.countryData.get(index))-(otherCountry.countryData.get(index)));
      if(difference > bounds){
        return false;
      }
      index++;
    }
    return true; // all elemtns are equal
  }

  @Override
  public String toString(){
    /**this function displays the object in line form 
    as it's written in the CSV file --   **/ 
    return (countryName+","+getData("PopulationTotal")+","+getData("CO2Emissions")+","
    +getData("AccessToElectricity")+","+getData("RenewableEnergy")+","+getData("ProtectedAreas")+","
    +getData("PopulationGrowth")+","+getData("UrbanPopulationGrowth"));
  }

  public static void main(String[] args) {
    //testcode -- equals(Country otherCountry);
    Country testCountry1 = new Country("Afghanistan,100.999, 0.33,72.7,15.73,0.1,2.99,3.89");
    Country testCountry2 = new Country("Afghanistan,100.9980,0.33,72.7,15.73,0.1,2.99,3.89");

    System.out.println(testCountry1.equals(testCountry2));
    //change element 1 to 10.233
    // testCountry.setData("PopulationTotal", 10.233d);
    // System.out.println(testCountry.toString());
  }
}