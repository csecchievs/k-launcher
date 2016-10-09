package it.gamesandapps.k_launcher.utils;

public class LauncherFunctions {

    private JSONParser parser;

    public LauncherFunctions() {
        parser = new JSONParser();
    }

    public String googleSearchRequest(String search){
        return parser.simpleRequest(
                "https://www.googleapis.com/customsearch/v1?q=" + search + "&key=" + Utils.GOOGLE_KEY + "&cx=" + "008041924660149545357:87bzamu4bxq",
                "GET");
    }

}
