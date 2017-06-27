package tech.scolton.cf_client.api;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.Getter;

public class Response {

    @Getter
    private JSONObject responseData;

    @Getter
    private JSONObject requestData;

    @Getter
    private int responseCode;

    @Getter
    private String[] requestURLArgs;

    Response(JSONObject responseData, int code, JSONObject requestData, String... requestURLArgs) {
        this.responseData = responseData;
        this.responseCode = code;
        this.requestURLArgs = requestURLArgs;
        this.requestData = requestData;
    }

    public boolean isError() {
        return this.responseCode >= 400;
    }

    public boolean isCFError() {
        try {
            return this.responseData != null && !this.responseData.getBoolean("success");
        } catch (JSONException e) {
            return true;
        }
    }

    public JSONObject getResult() {
        try {
            return this.responseData.getJSONObject("result");
        } catch (JSONException e) {
            return null;
        }

    }

    public enum Status {

        OK(200, "Request successful."),
        NOT_MODIFIED(304, "Request successful. (Not modified)"),
        BAD_REQUEST(400, "Request was invalid. (Possibly malformed API key)"),
        UNAUTHORIZED(401, "User does not have permission. (Possibly not global API key)"),
        FORBIDDEN(403, "Request not authenticated. (Bad API key)"),
        TOO_MANY_REQUESTS(429, "Client is rate limited (1200 requests per 5 minutes)"),
        METHOD_NOT_ALLOWED(405, "Incorrect HTTP method provided"),
        UNSUPPORTED_MEDIA_TYPE(415, "Response is not valid JSON");

        @Getter
        private int code;

        @Getter
        private boolean error;

        @Getter
        private String message;

        Status(int code, String message) {
            this.code = code;

            this.error = this.code >= 400;

            this.message = message;
        }

        static Status fromInt(int code) {
            for (Status e : Status.values()) {
                if (e.getCode() == code)
                    return e;
            }
            return null;
        }
    }

    public Status getStatus() {
        return Status.fromInt(this.responseCode);
    }

}
