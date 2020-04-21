package com.theguardiansearch.com;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A POJO class that represents a News from The Guardian Newspaper.
 * @author Lilia Ramalho Martins
 * @version 1.0
 */
public class TheGuardianArticle {
    private String date = "";
    private String title = "";
    private String url = "";
    private String sectionName = "";
    private String id = "";
    private boolean starred = false;

    /**
     * Constructor with 6 arguments including a boolean starred. It is called when an object
     * is created to keep the information from the json in the TheGuardianQuery. The date String
     * parameter receives the date in the format of the json and convert it to a format that can
     * be assigned to an object of Date type.
     * @param date String in the format yyyy-MM-dd'T'HH:mm:ssZ representing a date in the json.
     * @param title String with the News title.
     * @param url String with the News url in The Guardian site.
     * @param id String with the News id from the json.
     * @param sectionName String with the name of the Section where the News belongs.
     * @param starred boolean stating if the News is saved or not.
     */
    public TheGuardianArticle(String date, String title, String url, String id, String sectionName, boolean starred) throws ParseException {
        if (date != null && date != "") {
            if (date.charAt(date.length() - 1) == 'Z')
                date = date.substring(0, date.length() - 1);
            SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date tempDate = sdfIn.parse(date);
            SimpleDateFormat sdfOut = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.date = sdfOut.format(tempDate);
        }
        this.title = title;
        this.url = url;
        this.sectionName = sectionName;
        this.id = id;
        this.starred = starred;
    }

    /**
     * Constructor with 5 arguments including a boolean starred. It is called when an object
     * is created to load the information from the database in the StarredActivity. The date String
     * parameter receives the date in a format that can be assigned to an object of Date type,
     * thus it does not need any conversion. It does not have a starred boolean parameter
     * because it is used only by the StarredActivity, thus the starred field is true.
     * @param date String in the format yyyy-MM-dd'T'HH:mm:ss representing a date.
     * @param title String with the News title.
     * @param url String with the News url in The Guardian site.
     * @param id String with the News id from the json.
     * @param sectionName String with the name of the Section where the News belongs.
     */
    public TheGuardianArticle(String date, String title, String url, String sectionName, String id) {
        this.date = date;
        this.title = title;
        this.url = url;
        this.sectionName = sectionName;
        this.id = id;
        this.starred = true;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }

    public String getSectionName() {
        return sectionName;
    }

}
