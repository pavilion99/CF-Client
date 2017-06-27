package tech.scolton.cf_client.storage;

import java.util.Set;

public abstract class Storage {

    public static Storage storage = null;

    public abstract String getString(String key);
    public abstract int getInt(String key);
    public abstract float getFloat(String key);
    public abstract boolean getBoolean(String key);
    public abstract Set<String> getStringSet(String key);

    public abstract boolean saveString(String key, String value);
    public abstract boolean saveInt(String key, int value);
    public abstract boolean saveBoolean(String key, boolean value);
    public abstract boolean saveFloat(String key, float value);
    public abstract boolean saveStringSet(String key, Set<String> value);

}
