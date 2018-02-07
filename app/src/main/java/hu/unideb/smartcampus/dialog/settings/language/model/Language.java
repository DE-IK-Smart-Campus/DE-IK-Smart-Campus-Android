package hu.unideb.smartcampus.dialog.settings.language.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Language {
    private String languageName;
    private String languageCode;
    private String languageNameInDefaultLocale;
}
