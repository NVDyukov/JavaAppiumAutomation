import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class FirstTest {
    private String currentDir = System.getProperty("user.dir") + "\\apks\\org.wikipedia.apk";
    private AndroidDriver androidDriver;
    private String spec = "http://127.0.0.1:4723/wd/hub";

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("deviceName", "AndroidTestDevice");
        desiredCapabilities.setCapability("platformVersion", "6.0");
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
        System.out.println("First Test Run");
    }
}