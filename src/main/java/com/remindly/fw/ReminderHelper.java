package com.remindly.fw;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class ReminderHelper extends BaseHelper {
    public ReminderHelper(AppiumDriver driver) {
        super(driver);
    }

    public void enterTitleOfReminder(String title) {
        type(By.id("reminder_title"), title);
    }

    public void tapOnDateField() {
        tap(By.id("date"));
    }

    public void swipeToMonth(String period, int number, String month) {
        pause(500);

        if (!getSelectedMonth().equals(month)) {
            for (int i = 0; i < number; i++) {
                if (period.equals("future")) {
                    swipe(0.8, 0.4);
                } else if (period.equals("past")) {
                    swipe(0.5, 0.8);
                }
            }
        }
    }

    public void swipe(double start, double stop) {
        Dimension size = driver.manage().window().getSize();

        int x = size.width / 2;

        int startY = (int) (size.height * start);
        int stopY = (int) (size.height * stop);

        new TouchAction((PerformsTouchActions) driver).longPress(PointOption.point(x,startY))
                .moveTo(PointOption.point(x, stopY))
                .release().perform();
    }

    public String getSelectedMonth() {
        return driver.findElement(By.id("date_picker_month")).getText();
    }

    public void selectDate(int index) {
        List<WebElement> days = driver.findElements(By.className("android.view.View"));
        days.get(index).click();
    }

    public void selectYear(String period2, String year) {
        tap(By.id("date_picker_year"));

        pause(500);

        if (!getSelectedYear().equals(year)) {
            if (period2.equals("future")) {
                swipeUntilNeededYear(year,0.6, 0.5);
            } else if (period2.equals("past")) {
                swipeUntilNeededYear(year,0.5, 0.6);
            }
        }
        tap(By.id("month_text_view"));
    }

    public void swipeUntilNeededYear(String year, double startPoint, double stopPoint) {
        while (!getYear().equals(year)) {
            swipeInElement(By.className("android.widget.ListView"), startPoint, stopPoint);
        }
        getYear();
    }

    public void swipeInElement(By locator, double startPoint, double stopPoint) {
        Dimension size = driver.manage().window().getSize();

        int y = (int) (size.height * startPoint);
        int y2 = (int) (size.height * stopPoint);

        WebElement element = driver.findElement(locator);
        int leftX = element.getLocation().getX();
        int rightX = leftX + element.getSize().getWidth();
        int middleX = (leftX + rightX) / 2;

        new TouchAction((PerformsTouchActions) driver).longPress(PointOption.point(middleX, y))
                .moveTo(PointOption.point(middleX, y2))
                .release().perform();
    }

    public String getYear() {
        return driver.findElement(By.id("month_text_view")).getText();
    }

    public String getSelectedYear() {
        return driver.findElement(By.id("date_picker_year")).getText();
    }

    public void tapOnOk() {
        tap(By.id("ok"));
    }

    public void tapOnTime() {
        tap(By.id("time"));
    }

    public void selectTimeOfTheDay(String type) {
        pause(500);
        if (type.equals("AM")) {
            new TouchAction((PerformsTouchActions) driver).tap(PointOption.point(280,1330)).perform();
        } else if (type.equals("PM")) {
            new TouchAction((PerformsTouchActions) driver).tap(PointOption.point(800,1330)).perform();
        }
    }

    Point[] timePoints = new Point[] {
            new Point(540, 660),
            new Point(675, 690),
            new Point(775, 786),
            new Point(811, 921),
            new Point(775, 1061),
            new Point(675, 1156),
            new Point(540, 1193),
            new Point(404, 1156),
            new Point(305, 1061),
            new Point(272, 925),
            new Point(308, 793),
            new Point(407, 694),
    };

    public void selectHour(int hour) {
        pause(300);
        int index = hour % 12;
        Point point = timePoints[index];
        new TouchAction((PerformsTouchActions) driver)
                .tap(PointOption.point(point.x, point.y))
                .perform();
    }

    public void selectMinutes(int minutes) {
        pause(300);
        int index = minutes / 5;
        Point point = timePoints[index];
        new TouchAction((PerformsTouchActions) driver)
                .tap(PointOption.point(point.x, point.y))
                .perform();
    }

    public void selectRepetitionInterval(String interval) {
        tap(By.id("RepeatNo"));
        pause(300);
        tap(By.className("android.widget.EditText"));
        type(By.className("android.widget.EditText"), interval);
        pause(250);
        tap(By.id("android:id/button1"));
    }

    public void selectTypeOfRepetition(String type) {
        pause(200);
        swipe(0.5,0.3);
        tap(By.id("RepeatType"));
        pause(200);
        tap(By.xpath(String.format("//android.widget.TextView[@text='%s']", type)));
    }

    public void tapOnSave() {
        tap(By.id("save_reminder"));
    }

    public void verifyReminderSaved(String title) {
        pause(200);
        Assert.assertTrue(isElementPresent(By.xpath(String.format("//android.widget.TextView[@text='%s']", title))));
    }
}
