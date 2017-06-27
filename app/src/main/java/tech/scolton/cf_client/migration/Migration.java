package tech.scolton.cf_client.migration;

public class Migration {

    public static final String DATA_VERSION = "1.0";

    public static int majorVersion() {
        return Integer.parseInt(DATA_VERSION.split("\\.")[0]);
    }

    public static int minorVersion() {
        return Integer.parseInt(DATA_VERSION.split("\\.")[1]);
    }

}
