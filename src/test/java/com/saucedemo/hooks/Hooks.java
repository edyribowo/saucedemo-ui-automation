package com.saucedemo.hooks;

import com.microsoft.playwright.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.nio.file.Paths;

public class Hooks {

    private static final ThreadLocal<Playwright> playwrightThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> contextThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();

    @Before
    public void setUp() {
        Playwright playwright = Playwright.create();
        boolean headless = Boolean.parseBoolean(System.getProperty("playwright.headless", "true"));
        int slowMo = Integer.parseInt(System.getProperty("playwright.slowmo", "0"));
        String browserName = System.getProperty("browser", "chromium").toLowerCase();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions().setHeadless(headless).setSlowMo(slowMo);
        Browser browser = switch (browserName) {
            case "firefox" -> playwright.firefox().launch(launchOptions);
            case "webkit" -> playwright.webkit().launch(launchOptions);
            default -> playwright.chromium().launch(launchOptions);
        };
        BrowserContext context = browser.newContext(
                new Browser.NewContextOptions().setViewportSize(1280, 800)
        );
        Page page = context.newPage();

        playwrightThreadLocal.set(playwright);
        browserThreadLocal.set(browser);
        contextThreadLocal.set(context);
        pageThreadLocal.set(page);
    }

    @After
    public void tearDown(Scenario scenario) {
        Page page = getPage();
        if (scenario.isFailed() && page != null) {
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
            scenario.attach(screenshot, "image/png", scenario.getName());
        }

        if (getContext() != null) getContext().close();
        if (getBrowser() != null) getBrowser().close();
        if (getPlaywright() != null) getPlaywright().close();

        pageThreadLocal.remove();
        contextThreadLocal.remove();
        browserThreadLocal.remove();
        playwrightThreadLocal.remove();
    }

    public static Page getPage() {
        return pageThreadLocal.get();
    }

    public static Browser getBrowser() {
        return browserThreadLocal.get();
    }

    public static BrowserContext getContext() {
        return contextThreadLocal.get();
    }

    public static Playwright getPlaywright() {
        return playwrightThreadLocal.get();
    }
}
