package Practice;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;


public class Test {

    public static void main(String[] args) throws Exception {
        
        // Take input for file path
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter file path : ");
        String path = scanner.nextLine();
        doesFileExist(path);

        System.out.print("Enter nth smallest number : ");
        int n = scanner.nextInt();
        smallestNumber(n);

        //weightWatchersTest();
    }

    /*
     Reads the contents of a file and outputs the word and definitions
     */
    public static void doesFileExist(String path) throws Exception {

        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            String line = "";

            // Read each line of the file and print out each word and its definitions
            while ((line = in.readLine()) != null) {
                String[] parts = line.split(" – ");
                System.out.println(parts[0]);

                String[] definitions = parts[1].split(", ");
                for (String def : definitions) {
                    System.out.println(def);
                }
            }
        } catch(FileNotFoundException ex){ // catch exception when file path not found
            System.out.println(ex);
        }
    }

    /*
    Generate 500 random numbers and print the nth smallest number in a programming language of your choice.
     */
    public static void smallestNumber(int n){
        List<Integer> numbers = new ArrayList<Integer>();
        Random rand = new Random();

        // Generate 500 random numbers betwween 1 and 1000
        for(int i=0; i<500; i++){
            int num = rand.nextInt(1000-1) + 1;
            numbers.add(num);
        }
        Collections.sort(numbers); // sort numbers by ascending order
        if(n>0 && n<500)
            System.out.println(n + " smallest number : " + numbers.get(n-1));
        else if(n<=0)
            System.out.println("smallest number : " + numbers.get(0));
        else if(n>=500)
            System.out.println("largest number : " + numbers.get(499));


    }

    /*
    Steps:
1. Navigate to https://www.weightwatchers.com/us/
2. Verify loaded page title matches “Weight Loss Program, Recipes & Help | Weight Watchers”
3. On the right corner of the page, click on “Find a Meeting”
4. Verify loaded page title contains “Get Schedules & Times Near You”
5. In the search field, search for meetings for zip code: 10011
6. Print the title of the first result and the distance (located on the right of location title/name)
7. Click on the first search result and then, verify displayed location name matches with the name of the first searched result that was clicked.
8. From this location page, print TODAY’s hours of operation (located towards the bottom of the page)

Write an automated test for this scenario using WebDriver.

     */

    public static void weightWatchersTest() throws InterruptedException {
        String baseURL = "http://www.weightwatchers.com/us/";
        WebDriver driver;
        System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get(baseURL);

        // Verify title of www.weightwatchers.com/us
        String homepageTitle = driver.getTitle();
        Assert.assertEquals(homepageTitle, "Weight Loss Program, Recipes & Help | Weight Watchers");

        // Navigate to "Find a Meeting' and verify page title contains “Get Schedules & Times Near You”
        driver.findElement(By.className("find-a-meeting")).click();
        String meetingpageTitle = driver.getTitle();
        Assert.assertTrue(meetingpageTitle.contains("Get Schedules & Times Near You"));

        // Search for meetings with zip code: 10011, then print title and distance of first result
        driver.findElement(By.id("meetingSearch")).sendKeys("10011" + Keys.ENTER);
        Thread.sleep(2000);
        String meetingLocation = driver.findElements(By.className("location__name")).get(0).getText();
        String meetingDistance = driver.findElements(By.className("location__distance")).get(0).getText();

        System.out.println("location = " + meetingLocation);
        System.out.println("Distance = " + meetingDistance);

        // Click on the first result and verify location name matches search result
        driver.findElements(By.className("meeting-location")).get(0).click();
        Thread.sleep(2000);
        String locationName = driver.findElement(By.className("location__name")).getText();
        Assert.assertEquals(locationName, meetingLocation);

        // Print Today's hours of operation
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEE");
        String dayOfWeek = "Sunday";//dateFormat.format(date);

        List<WebElement> operationHours = driver.findElements(By.xpath("//div[@class='hours-list-item-day' and text()='" + dayOfWeek + "']//following-sibling::div[1]//child::div"));
        System.out.println(dayOfWeek);
        for(WebElement hour : operationHours){
            System.out.println(hour.getText());
        }


        Thread.sleep(2000);
    }

}
