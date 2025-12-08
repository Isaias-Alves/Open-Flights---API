package com.openflightsapi.model.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Conversor {

    private static final Map<String, String> MAPA_NOMES_PARA_ISO = new HashMap<>();

    static {
        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            MAPA_NOMES_PARA_ISO.put(l.getDisplayCountry(Locale.ENGLISH), iso);

            Locale portugues = new Locale("pt", "BR");
            MAPA_NOMES_PARA_ISO.put(l.getDisplayCountry(portugues), iso);
        }
    }

    public static String obterIsoPais(String nomePaisNoCsv) {
        return MAPA_NOMES_PARA_ISO.getOrDefault(nomePaisNoCsv, nomePaisNoCsv);

    }

}
