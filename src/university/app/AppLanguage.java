package university.app;

import university.enums.Language;
import university.users.User;

final class AppLanguage {
    private static Language selectedLanguage = Language.EN;

    private AppLanguage() {
    }

    static void apply(Language language) {
        selectedLanguage = language == null ? Language.EN : language;
    }

    static void use(User user) {
        selectedLanguage = user == null || user.getLanguage() == null ? Language.EN : user.getLanguage();
    }

    static void changeFor(User user, Language language) {
        selectedLanguage = language == null ? Language.EN : language;
        if (user != null) {
            user.changeLanguage(selectedLanguage);
        }
    }

    static Language selected() {
        return selectedLanguage;
    }
}

