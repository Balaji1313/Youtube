package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import demo.utils.ExcelDataProvider;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider { // Lets us read the data
        ChromeDriver driver;

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */

        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
        }

        @AfterTest
        public void endTest() {
                // driver.close();
                // driver.quit();

        }

        @Test(enabled = true)
        public void testCase01() throws InterruptedException {
                driver.get("https://www.youtube.com/");
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                // Thread.sleep(300);
                String url = driver.getCurrentUrl();
                Assert.assertEquals(url, "https://www.youtube.com/");
                Thread.sleep(2000);
                WebElement about = driver.findElement(By.xpath("//a[text()='About']"));
                about.click();
                Thread.sleep(2000);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.urlToBe("https://about.youtube/"));
                String aboutUrl = driver.getCurrentUrl();
                Assert.assertEquals(aboutUrl, "https://about.youtube/");
                WebElement aboutText = driver.findElement(By.xpath(
                                "//h1[@class='lb-font-display-1 lb-font-weight-700 lb-font-color-text-primary lb-font--no-crop']"));
                System.out.println(aboutText.getText());
                List<WebElement> message = driver.findElements(
                                By.xpath("//p[@class='lb-font-display-3 lb-font-color-text-primary']"));
                for (WebElement webElement : message) {
                        System.out.println(webElement.getText());
                }

        }

        @Test(enabled = true)
        public void testCase02() throws InterruptedException {
                driver.get("https://www.youtube.com/");
                // WebElement movies = driver.findElement(By.xpath("//a[@title='Movies']"));
                // movies.click();
                Wrappers wrap = new Wrappers();
                wrap.exploreTab("Movies", driver);
                Thread.sleep(2000);

                // WebElement nextBtn = driver.findElement(By.xpath(
                // "//span[@id='title' and contains(text(),'Top
                // selling')]/ancestor::div[@id='dismissible']//button[@aria-label='Next']"));

                // while (nextBtn.isDisplayed()) {
                // nextBtn.click();
                // Thread.sleep(2000);
                // }
                wrap.nextBtn("Top selling", driver);

                List<WebElement> topSellingMovies = driver.findElements(By.xpath(
                                "//span[@id='title' and  contains(text(),'Top selling')]/ancestor::div[@id='dismissible']//div[@id='items']//ytd-grid-movie-renderer"));
                int sizeOfList = topSellingMovies.size();
                String categoryOfMovie = topSellingMovies.get(sizeOfList - 1).findElement(By.xpath(
                                ".//span[@class='grid-movie-renderer-metadata style-scope ytd-grid-movie-renderer']"))
                                .getText();

                String ratingOfMovie = topSellingMovies.get(sizeOfList - 1).findElement(By.xpath(
                                ".//ytd-badge-supported-renderer[@class='badges style-scope ytd-grid-movie-renderer']//div[2]"))
                                .getText();

                System.out.println(categoryOfMovie);
                System.out.println(ratingOfMovie);

                SoftAssert softAssert = new SoftAssert();
                String[] keywords = { "Comedy", "Animation", "Drama" };
                boolean containsKeyword = false;
                for (String keyword : keywords) {
                        if (categoryOfMovie.toLowerCase().contains(keyword.toLowerCase())) {
                                containsKeyword = true;
                                break;
                        }
                }
                softAssert.assertEquals(ratingOfMovie, 'A', "The movie is Not marked 'A'");
                softAssert.assertTrue(containsKeyword,
                                "The movie category does not exists 'Comedy','Animation','Drama'");
                // softAssert.assertAll();

        }

        @Test(enabled = true)
        public void testCase03() throws InterruptedException {
                driver.get("https://www.youtube.com/");
                // WebElement music = driver.findElement(By.xpath("//a[@title='Music']"));
                // music.click();
                Wrappers wrap = new Wrappers();
                wrap.exploreTab("Music", driver);
                Thread.sleep(2000);

                // WebElement nextBtn = driver.findElement(By.xpath(
                // "//span[@id='title' and contains(text(),'Biggest
                // Hits')]/ancestor::div[@id='dismissible']//button[@aria-label='Next']"));
                // while (nextBtn.isDisplayed()) {
                // nextBtn.click();
                // Thread.sleep(2000);
                // }
                wrap.nextBtn("Biggest Hits", driver);

                List<WebElement> biggestHits = driver.findElements(By.xpath(
                                "//span[@id='title' and  contains(text(),'Biggest Hits')]/ancestor::div[@id='dismissible']//div[@id='items']//ytd-compact-station-renderer"));
                int sizeOfList = biggestHits.size();
                String nameOfPlaylist = biggestHits.get(sizeOfList - 1).findElement(By.xpath(
                                ".//h3"))// p[@id='video-count-text']
                                .getText();
                String numberOfTracks = biggestHits.get(sizeOfList - 1).findElement(By.xpath(
                                ".//p[@id='video-count-text']"))
                                .getText();
                System.out.println(nameOfPlaylist);
                System.out.println(numberOfTracks);
                String numbersOnly = numberOfTracks.replaceAll("[^0-9]", "");
                int result = Integer.parseInt(numbersOnly);
                SoftAssert softAssert = new SoftAssert();
                boolean containsKeyword = false;
                if (result <= 50) {
                        containsKeyword = true;
                }

                softAssert.assertTrue(containsKeyword,
                                "The number of tracks listed is Not less than or equal to 50");
        }

        @Test(enabled = true)
        public void testCase04() throws InterruptedException {
                driver.get("https://www.youtube.com/");
                // WebElement news = driver.findElement(By.xpath("//a[@title='News']"));
                // news.click();
                Wrappers wrap = new Wrappers();
                wrap.exploreTab("News", driver);
                Thread.sleep(2000);

                List<WebElement> latestNewsPosts = driver.findElements(By.xpath(
                                "//span[@id='title' and  contains(text(),'Latest news posts')]/ancestor::div[@id='dismissible']//ytd-rich-item-renderer"));
                int count = 0;
                int result = 0;
                // String toolbar = "";
                for (int i = 0; i < 3; i++) {
                        System.out.println((i + 1) + ".Latest news title & body : ");
                        String header = latestNewsPosts.get(i).findElement(By.xpath(
                                        ".//div[@id='header']"))
                                        .getText();
                        String body = latestNewsPosts.get(i).findElement(By.xpath(
                                        ".//div[@id='body']"))
                                        .getText();
                        String toolbar = latestNewsPosts.get(i).findElement(By.xpath(
                                        ".//div[@id='toolbar']//span[@id='vote-count-middle']"))
                                        .getText();
                        if (toolbar != null && !toolbar.isEmpty()) {
                                result = Integer.parseInt(toolbar);
                                count += result;
                        }
                        // if(toolbar.contains(".")){
                        // toolbar = toolbar.replaceAll(".", "");
                        // }
                        // if(toolbar.toLowerCase().contains("k")){
                        // toolbar.replaceAll("k", "");
                        // result = Integer.parseInt(toolbar);
                        // count += result*1000;
                        // }else if (toolbar.toLowerCase().contains("m")) {
                        // toolbar.replaceAll("m", "");
                        // result = Integer.parseInt(toolbar);
                        // count += result*1000000;
                        // }else{
                        // result = Integer.parseInt(toolbar);
                        // count += result;
                        // }
                        System.out.println(toolbar);

                        System.out.println(header);
                        System.out.println(body);
                        System.out.println(toolbar);
                }
                System.out.println("Total Likes count of first 3 are : " + count);

        }

        @Test(dataProvider = "excelData")
        public void testCase05(String toBeSearched) throws InterruptedException {

                driver.get("https://www.youtube.com/");
                WebElement element = driver.findElement(By.xpath("//input[@id='search']"));
                element.sendKeys(toBeSearched);
                Thread.sleep(1000);
                WebElement searchIcon = driver.findElement(By.xpath("//button[@id='search-icon-legacy']"));
                searchIcon.click();
                Thread.sleep(3000);
                List<WebElement> views = driver.findElements(By.xpath(
                                "//ytd-video-renderer[@class='style-scope ytd-item-section-renderer']//div[@id='meta']//div[@id='metadata']"));
                int count = 0;
                int highest = 100000000;
                for (WebElement webElement : views) {
                        String s = webElement.getText();
                        String[] parts = s.split(" ");
                        String viewCount = parts[0].trim();
                        if (viewCount.toLowerCase().contains("k")) {
                                String x = viewCount.replaceAll("[^0-9]", "");
                                int y = Integer.parseInt(x);
                                count += y * 1000;
                                JavascriptExecutor js = (JavascriptExecutor) driver;
                                js.executeScript("arguments[0].scrollIntoView(true);", webElement);
                                Thread.sleep(2000);
                                System.out.println(count);
                                if (count >= highest) {
                                        break;
                                }
                        } else if (viewCount.toLowerCase().contains("m")) {
                                String x = viewCount.replaceAll("[^0-9]", "");
                                int y = Integer.parseInt(x);
                                count += y * 1000000;
                                JavascriptExecutor js = (JavascriptExecutor) driver;
                                js.executeScript("arguments[0].scrollIntoView(true);", webElement);
                                Thread.sleep(2000);
                                System.out.println(count);
                                if (count >= highest) {
                                        break;
                                }
                        } else if (viewCount.toLowerCase().contains("b")) {
                                String x = viewCount.replaceAll("[^0-9]", "");
                                int y = Integer.parseInt(x);
                                count += y * 1000000000;
                                JavascriptExecutor js = (JavascriptExecutor) driver;
                                js.executeScript("arguments[0].scrollIntoView(true);", webElement);
                                Thread.sleep(2000);
                                System.out.println(count);
                                if (count >= highest) {
                                        break;
                                }
                        } else {
                                String x = viewCount.replaceAll("[^0-9]", "");
                                int y = Integer.parseInt(x);
                                count += y;
                                JavascriptExecutor js = (JavascriptExecutor) driver;
                                js.executeScript("arguments[0].scrollIntoView(true);", webElement);
                                Thread.sleep(2000);
                                System.out.println(count);
                                if (count >= highest) {
                                        break;
                                }
                        }

                }
                Thread.sleep(3000);
        }

}