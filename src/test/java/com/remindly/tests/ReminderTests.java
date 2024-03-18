package com.remindly.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ReminderTests extends TestBase {
    @BeforeMethod
    public void precondition() {
        app.getMainScreen().confirm();
    }

    @Test
    public void addReminderPositiveTest() {
        app.getMainScreen().tapOnAddReminder();
        app.getReminder().enterTitleOfReminder("Holiday");
        app.getReminder().tapOnDateField();
        app.getReminder().swipeToMonth("future", 2, "MAY");
        app.getReminder().selectDate(0);
        app.getReminder().selectYear("future", "2025");
        app.getReminder().tapOnOk();
        app.getReminder().tapOnTime();
        app.getReminder().selectTimeOfTheDay("AM");
        app.getReminder().selectHour(1);
        app.getReminder().selectMinutes(45);
        app.getReminder().tapOnOk();
        app.getReminder().selectRepetitionInterval("5");
        app.getReminder().selectTypeOfRepetition("Day");
        app.getReminder().tapOnSave();
        app.getReminder().verifyReminderSaved("Holiday");
    }
}
