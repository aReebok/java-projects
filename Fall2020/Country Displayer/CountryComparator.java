import java.util.*;

/**
* CountryComparator.java
*
* This class will be primarily used to create comparator objects
* that store information on how the contries shuold be sorted
* The purpose of this class is to compare the given countries 
* and compare them to return information back to SortedLinkedList
* class for it to understnad how exactly to sort the data. 
* 
**/

public class CountryComparator implements Comparator<Country> {
  // class instance variables below ---- 
  private String indicator; 
  private boolean isGreatestToLeast;

  //class constructor
  public CountryComparator(String indicator, boolean isGreatestToLeast){
    this.indicator = indicator;
    this.isGreatestToLeast = isGreatestToLeast;
  }

  // compare method ------------------------------
  /** 
  * this method takes in 2 Country objects and compares them given the comparator
  * object. If int value 0 is returned, the 2 countries are exactly (nearly) the
  * same, If it returns -1, then given the comparator's info, the item1 should be 
  * on the left of the item2, and if int = 1, item1 should be on the right of item2.
  **/
  public int compare(Country country1, Country country2){
    double country1Data = country1.getData(indicator); 
    double country2Data = country2.getData(indicator);
  
    int returnInt = 1; 
    if (isGreatestToLeast != true){
      returnInt = -1; // helps sort leastToGreatest
      // reduces duplicate codes
    } 

    if(country1Data < country2Data){
      return 1*(returnInt);
    } else if(country1Data > country2Data) {
      return -1*(returnInt);
    } else { // is the same
      return compareNames(country1.getCountryName(), country2.getCountryName());
    }
  }

  /* belows private helper function reduces code duplicacy for method compare()
  * it takes the name of the countries and compares their names alphabetically:
  * so if countries have the exact same data but differnet names, they will be
  * sorted alphabetially.
  */

  private int compareNames(String countryName1, String countryName2){
    if (countryName1.compareTo(countryName2) < 0){
      return -1;
    } else if (countryName1.compareTo(countryName2) > 0)  {
      return 1;
    } else {
      return 0;
    }
  }

  public static void main(String[] args) {
    // class testcode -
    Comparator<Country> countryComparator = new CountryComparator("AccessToElectricity", false);

    Country c1 = new Country("Argentina,42.66,4.63,99.64,10.02,8.84,1.05,1.19"); // AccessToElectricity = 88.64
    Country c2 = new Country("Largentina,42.66,4.63,20.3,10.02,8.84,1.05,1.19"); // AccessToElectricity = 20.3
    Country c3 = new Country("Gargentina,42.66,4.63,99.64,10.02,8.84,1.05,1.19"); // AccessToElectricity = 88.64
    
    //99.999 and 99.9985
    //Value for argentinavsLargentina should be positive (Largentina has less access)
    int argentinavsLargentina = countryComparator.compare(c1, c2);
    System.out.println("Argentina VS Largentina (should be positive): " + argentinavsLargentina);

    //Value for argentinavsGargentina should be negative
    //(they have the same access, and argentina is alphabetically first)
    int argentinavsGargentina = countryComparator.compare(c1, c3);
    System.out.println("Argentina vs Gargentina (should be negative): " + argentinavsGargentina);
  }
}