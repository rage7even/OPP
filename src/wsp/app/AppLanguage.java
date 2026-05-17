package wsp.app;

import university.core.University;
import university.enums.Language;
import university.users.User;

final class AppLanguage {
    private static Language selectedLanguage = Language.EN;

    private AppLanguage() {
    }

    static void apply(Language language) {
        selectedLanguage = language == null ? Language.EN : language;
        for (User user : University.getInstance().getUsers()) {
            user.changeLanguage(selectedLanguage);
        }
    }

    static Language selected() {
        return selectedLanguage;
    }
}
