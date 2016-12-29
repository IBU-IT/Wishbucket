package dev.ibu.wishbucket.views.models;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FBUser {
    public String id;
    public String name;
    public Date birthday;
    public int daysUntilBirthday;
    public String coverUrl;

    public FBUser(String id, String name, String birthdayStr, String coverUrl) {
        this.id = id;
        this.name = name;
        this.coverUrl = coverUrl;

        if(birthdayStr != null) {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            try {
                this.birthday = format.parse(birthdayStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(this.birthday != null)
            this.daysUntilBirthday = calculateDaysUntilBirthday(this.birthday);
    }

    public String getDaysUntilBirthday() {
        if(this.daysUntilBirthday == 1)
            return daysUntilBirthday + " day left";

        else if(this.daysUntilBirthday == 0)
            return "Today!";

        else
            return daysUntilBirthday + " days left";
    }

    public static int calculateDaysUntilBirthday(Date birthday) {
        birthday.setYear(new Date().getYear());
        long diff = birthday.getTime() - new Date().getTime();
        return (int)Math.abs(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)) + 1;
    }
}
