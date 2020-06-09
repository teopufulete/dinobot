package dinobot;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;

public class TRexBot {
	
	private ChromeDriver driver;
	private WebElement window;
	private Boolean firstJump;
	
    private static final int DEFAULT_DISTANCE = 0;
    private static final int FLYING_OBSTACLE_WIDTH = 46;
    private static final int TREX_HEIGHT = 51;

    
    public TRexBot() {
    	initializeDriver();
    }
    
    
    
    private void initializeDriver() {
    	driver =  new ChromeDriver();
    }
    
    
    
    private void initializeGamePage() {
        driver.get("chrome://dino/");
        window = driver.findElement(By.id("main-frame-error"));
        new WebDriverWait(driver, 1000).until(ExpectedConditions.visibilityOf(window));

        //add delay to for page be loaded before starting the game */
        checkPageIsReady(1000);
    }
    
    
    
    public void startGame() {
        initializeGamePage();
        start();
    }
    
    
    
    private void start() {
    	firstJump = true;
        jump();
        
        while (!isGameEnded()) {
        	if(isObstaclePresent()) {
        		jump();
        	}
        }
        
        System.out.println("Your score is:" + getScore());
    }

    
    
    private Boolean isObstaclePresent() {
        Map<String, Long> obstacle = (Map<String, Long>) executeScript("return Runner.instance_.horizon.obstacles.filter(o => (o.xPos > 25))[0] || {}");
        Long tRexPos = (Long) executeScript("return Runner.instance_.tRex.xPos");
        Double currentSpeed = (Double) executeScript("return Runner.instance_.currentSpeed");

        Long distanceToStartJump = firstJump ? new Long(DEFAULT_DISTANCE + 180) : new Long(DEFAULT_DISTANCE + 100);

        //dynamically calculate the distance difference to 
        if(currentSpeed >= 10) {
            distanceToStartJump = Math.round(distanceToStartJump + (20 * (currentSpeed % 10))) + 40;
        }

        //speed is > 13, space bar needs to be pressed in advance
        if(currentSpeed > 13) {
            distanceToStartJump += 50;
        }

        // Check if obstacle is present 
        if (obstacle != null && obstacle.containsKey("xPos")) {
		
		//If obstacle is flying, jump only if dino height >= vertical position of the obstacle 
       	 	if (obstacle.get("width") == FLYING_OBSTACLE_WIDTH && obstacle.get("yPos") < TREX_HEIGHT) {
       	 		return false;
       	 	}
       	 	
        	
       	 	Long currentDistanceToObstacle = obstacle.get("xPos") - tRexPos;
        	
       	 	if (obstacle.get("xPos") > tRexPos && currentDistanceToObstacle <= distanceToStartJump) {
       	 		if (firstJump) {
       	 			firstJump = false;
       	 		}
       	 		
       	 		System.out.println("Identified Obstacle at "+ currentDistanceToObstacle);
       	 		return true;
       	 	}
        }
        return false;
    }
    
    

    private void jump() {
    	new Actions(driver).sendKeys(window, Keys.SPACE). build().perform();
    }
    
    
    
    public void checkPageIsReady(Integer time) {
    	try {
    		Thread.sleep(time);
        } catch (Exception e) {}
    }
 
    

    
	private String getScore() {
		return (String) executeScript("return Runner.instance_.distanceMeter.highScore.join(\"\").substring(4)");
	}



	private boolean isGameEnded() {
		return (Boolean) executeScript("return Runner.instance_.distanceMeter.highScore.join(\"\").substring(4)");
	}



	private Object executeScript(String command) {
		return ((JavascriptExecutor)driver).executeScript(command);
	}
}
