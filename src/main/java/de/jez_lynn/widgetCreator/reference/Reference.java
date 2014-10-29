package main.java.de.jez_lynn.widgetCreator.reference;

/**
 * Created by Michael on 27.10.2014.
 */
public final class Reference {

    public static final String TEMP = "temp";

    public static final class FTP{
        public static final String SERVER = "landeskunde.square7.ch";
        public static final int PORT = 21;
        public static final String USER = "landeskunde";
        public static final String PASS = "L@ncer1860";
    }

    public static final class WIDGET{

        public static final String topHolder =  "<div id=\"content-holder\">\n"+
                                                    "<div class=\"clear\">\n"+
                                                    "</div>\n"+
                                                    "\n"+
                                                    "<div id=\"content-box\">\n"+
                                                        "<div id=\"first-line\">\n";

        public static final String bigImage =               "<img src=\"http://u.jimdo.com/www70/o/sb3358dd722f93dcc/userlayout/img/marburg.png?t=1413404325\" id=\"marburg\" alt=\"\" name=\"marburg\" /> <a href=\"#food\"></a>\n";

        public static final String smallImage =             "<div data-content=\"%s\" class=\"grow-small pic\">\n"+
                                                                "<img src=\"%s\" alt=\"\" />\n"+
                                                            "</div>\n"+
                                                        "</div>\n";

        public static final String secondLine =         "<div class=\"clear\">\n"+
                                                            "</div>\n"+
                                                            "\n"+
                                                            "<div id=\"second-line\">\n"+
                                                                "<div data-content=\"%s\" class=\"grow-small pic\">\n"+
                                                                    "<a href=\"#%s\"><img src=\"%s\" alt=\"\" /></a>\n"+
                                                                "</div>\n"+
                                                            "\n"+
                                                                "<div data-content=\"%s\" class=\"grow-small pic\">\n"+
                                                                    "<a href=\"#%s\"><img src=\"%s\" alt=\"\" /></a>\n"+
                                                                "</div>\n"+
                                                            "</div>\n"+
                                                        "</div>\n";

        public static final String sideInformation =    "<div id=\"side-information\">\n"+
                                                            "<div class=\"text-widget\">\n"+
                                                                "<h1>\n"+
                                                                    "%s\n"+
                                                                "</h1>\n"+
                                                                "<p>\n"+
                                                                    "%s\n" +
                                                                "</p>\n"+
                                                            "</div>\n"+
                                                        "</div>\n"+
                                                        "\n"+
                                                        "<div class=\"clear\">\n"+
                                                        "</div>\n"+
                                                        "</div>";
    }
}
