import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URL;

public class FirstTest {
    private static String FILE_SEPARATOR = File.separator;
    private String currentDir = System.getProperty("user.dir") + FILE_SEPARATOR + "apks" + FILE_SEPARATOR + "org.wikipedia.apk";
    private AndroidDriver androidDriver;
    private String spec = "http://127.0.0.1:4723/wd/hub";

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("deviceName", "AndroidTestDevice");
        desiredCapabilities.setCapability("platformVersion", "7.0");
        desiredCapabilities.setCapability("AutomationName", "Appium");
        desiredCapabilities.setCapability("appPackage", "org.wikipedia");
        desiredCapabilities.setCapability("appActivity", ".main.MainActivity");
        desiredCapabilities.setCapability("app", currentDir);
        androidDriver = new AndroidDriver(new URL(spec), desiredCapabilities);
    }

    @After
    public void tearDown() {
        androidDriver.quit();
    }

    @Test
    public void firstTest() {
        waitFindElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Не удалось найти строку поиска",
                5);
        waitFindElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Android",
                "Не удалось найти поле ввода",
                5);
        waitFindElement(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Open-source operating system for mobile devices created by Google']"),
                "Не удалось найти элемент 'Open-source operating system for mobile devices created by Google' в поиске по Android",
                15
        );
    }

    @Test
    public void testCancelSearch() {
        waitFindElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Не удалось найти строку поиска",
                10
        );
        waitFindElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Android",
                "Не удалось найти поле ввода",
                5);
        waitFindElementAndClear(
                By.id("org.wikipedia:id/search_toolbar"),
                "Не удалось очистить поле ввода",
                5
        );
        waitFindElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Не удалось найти кнопку выхода из поиска",
                10
        );
        waitFindElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "Кнопка закрытия поиска все еще отображается",
                10
        );
    }

    @Test
    public void testCompareArticleTitle() {
        waitFindElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Не удалось найти строку поиска",
                5);
        waitFindElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Android",
                "Не удалось найти поле ввода",
                5);
        waitFindElementAndClick(
                By.xpath("//*[contains(@text, 'Android (operating system)')]"),
                "Не удалось найти строку поиска",
                10);
        WebElement titleElement = waitFindElement(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Не удалось найти заголовок страницы",
                10
        );
        String title = titleElement.getAttribute("text");
        Assert.assertEquals(
                "Название страницы не соответствует ожидаемому",
                "Android (operating system)",
                title
        );
    }

    private WebElement waitFindElement(By by, String errorMessage, long timeoutSeconds) {
        WebDriverWait webDriverWait = new WebDriverWait(androidDriver, timeoutSeconds);
        webDriverWait.withMessage(errorMessage + "\n");
        return webDriverWait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitFindElement(By by, String errorMessage) {
        return waitFindElement(by, errorMessage, 5);
    }

    private WebElement waitFindElementAndClick(By by, String errorMessage, long timeoutSeconds) {
        WebElement element = waitFindElement(by, errorMessage, timeoutSeconds);
        element.click();
        return element;
    }

    private WebElement waitFindElementAndSendKeys(By by, String keys, String errorMessage, long timeoutSeconds) {
        WebElement element = waitFindElement(by, errorMessage, timeoutSeconds);
        element.sendKeys(keys);
        return element;
    }

    private boolean waitFindElementNotPresent(By by, String errorMessage, long timeoutSeconds) {
        WebDriverWait webDriverWait = new WebDriverWait(androidDriver, timeoutSeconds);
        webDriverWait.withMessage(errorMessage + "\n");
        return webDriverWait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitFindElementAndClear(By by, String errorMessage, long timeoutSeconds) {
        WebElement element = waitFindElement(by, errorMessage, timeoutSeconds);
        element.clear();
        return element;
    }
}