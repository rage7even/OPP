package university.patterns;

import university.enums.Format;

public class CitationFormatterFactory {
    public CitationFormatter create(Format format) {
        if (format == Format.BIBTEX) {
            return new BibtexCitationFormatter();
        }
        return new PlainTextCitationFormatter();
    }
}
