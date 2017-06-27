package tech.scolton.cf_client.api;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import lombok.Getter;
import lombok.Setter;
import tech.scolton.cf_client.scheduler.LocalTask;
import tech.scolton.cf_client.scheduler.LocalTaskCancelled;

public class Request extends AsyncTask<Void, Void, Response> {

    @Getter
    @Setter
    private static String email;

    @Setter
    private static String key;

    private LocalTask<Response> after;
    private LocalTaskCancelled<Response> cancelled;

    private CFAPIMap type;
    private JSONObject data;

    private String[] URLArgs;

    // For debugging
    private String responseText = null;
    private int responseCode = -1;
    private String requestURL = null;

    public Request(@Nullable LocalTask<Response> after, @Nullable LocalTaskCancelled<Response> cancelled, CFAPIMap type, @Nullable JSONObject data, String... URLArgs) {
        this.after = after;
        this.cancelled = cancelled;
        this.type = type;
        this.data = data;
        this.URLArgs = URLArgs;

        if (this.URLArgs.length < this.type.getUrlargc()) {
            int expected = this.type.getUrlargc();
            int received = this.URLArgs.length;
            String typeString = this.type.toString();
            throw new IllegalArgumentException("Expected " + expected + " URL arguments for Request of type " + typeString + ", received " + received);
        }

        if (this.data != null && this.data.length() < this.type.getDataargc()) {
            int expected = this.type.getDataargc();
            int received = this.data.length();
            String typeString = this.type.toString();
            throw new IllegalArgumentException("Expected " + expected + " JSON data pairs for Request of type " + typeString + ", received " + received);
        }

        this.execute();
    }

    @Override
    protected Response doInBackground(Void... voids) {
        try {
            String relURLWithArgs;

            if (this.type.getMethod() == CFAPIMap.Method.GET && this.data != null && this.data.length() > 0) {
                relURLWithArgs = MessageFormat.format(this.type.getRelURL(), this.URLArgs) + "?" + JSONtoQueryString();
            } else {
                relURLWithArgs = MessageFormat.format(this.type.getRelURL(), this.URLArgs);
            }

            this.requestURL = CFAPIMap.masterPath + relURLWithArgs;
            URL APIPath = new URL(requestURL);

            HttpsURLConnection connection = (HttpsURLConnection) APIPath.openConnection();
            connection.setRequestMethod(this.type.getMethod().toString());
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setRequestProperty("X-Auth-Email", Request.email);
            connection.setRequestProperty("X-Auth-Key", Request.key);

            switch (this.type.getMethod()) {
                case PATCH:
                case POST:
                case PUT: {
                    if (data == null)
                        break;

                    connection.setDoOutput(true);
                    DataOutputStream output = new DataOutputStream(connection.getOutputStream());
                    output.write(this.data.toString().getBytes("UTF-8"));
                    output.close();

                    break;
                }
                default: {
                    break;
                }
            }

            BufferedReader in = (connection.getResponseCode() >= 400) ? new BufferedReader(new InputStreamReader(connection.getErrorStream())) : new BufferedReader(new InputStreamReader(connection.getInputStream()));

            this.responseText = in.readLine();

            this.responseCode = connection.getResponseCode();
            Response response = new Response(new JSONObject(this.responseText), connection.getResponseCode(), this.data, this.URLArgs);

            in.close();
            connection.disconnect();

            return response;
        } catch (JSONException|IOException e) {
            // TODO: create logic for exception handling
            if (e instanceof JSONException) {
                Log.w("CF_APP", "CloudFlare returned some invalid JSON...this shouldn't happen");
                this.dumpRequestDetails(Log.WARN);
            } else {
                Log.e("CF_APP", "An error occurred while making the request.");
                this.dumpRequestDetails(Log.ERROR);
                Log.e("CF_APP", e.getMessage());
            }
            e.printStackTrace();
            // TODO: Create various Response types that convey type of error to user
            return null;
        }
    }

    @Override
    protected void onPostExecute (Response r) {
        if (this.after != null)
            this.after.runLocalTask(r);
    }

    @Override
    protected void onCancelled (Response r) {
        if (this.cancelled != null)
            this.cancelled.runLocalTaskCancelled(r);
    }

    private String JSONtoQueryString() throws JSONException, UnsupportedEncodingException {
        Iterator<String> i = this.data.keys();
        String fin = "";
        while (i.hasNext()) {
            String s = i.next();
            Object o = this.data.get(s);
            if (o instanceof Map)
                continue;
            fin += URLEncoder.encode(s, "UTF-8") + "=" + URLEncoder.encode(o + "", "UTF-8");
            if (i.hasNext())
                fin += "&";
        }
        return fin;
    }

    private void dumpRequestDetails(int level) {
        Log.println(level, "CF_APP", "Response: " + (this.responseText == null ? "No response from server." : this.responseText));
        Log.println(level, "CF_APP", "Request URL: " + this.requestURL);
        Log.println(level, "CF_APP", "Request Data: " + (this.data == null ? "None." : this.data.toString()));
        Log.println(level, "CF_APP", "Response Code: " + (this.responseCode == -1 ? "No response from server." : this.responseCode));
    }

    private void dumpRequestDetails() {
        this.dumpRequestDetails(Log.DEBUG);
    }

}
