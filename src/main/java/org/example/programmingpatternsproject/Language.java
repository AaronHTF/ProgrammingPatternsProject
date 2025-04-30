package org.example.programmingpatternsproject;

import java.util.Locale;
import java.util.ResourceBundle;

public class Language {
    private static Language language;
    private Locale locale;
    private ResourceBundle resourceBundle;

    private Language() {
        locale = Locale.of("en", "US");
        resourceBundle = ResourceBundle.getBundle("Messages", locale);
    }

    public static Language getInstance() {
        if (language == null) {
            language = new Language();
        }
        return language;
    }

    public void changeToEnglish() {
        locale = Locale.of("en", "US");
        resourceBundle = ResourceBundle.getBundle("Messages", locale);
    }

    public void changeToFrench() {
        locale = Locale.of("fr", "CA");
        resourceBundle = ResourceBundle.getBundle("Messages", locale);
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }
}
