package dinobot;

public class GameStart {
	
	public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        new TRexBot().startGame();
    }
}

