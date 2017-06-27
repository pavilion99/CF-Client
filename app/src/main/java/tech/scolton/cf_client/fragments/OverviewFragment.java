package tech.scolton.cf_client.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import tech.scolton.cf_client.R;
import tech.scolton.cf_client.api.CFAPIMap;
import tech.scolton.cf_client.api.Request;
import tech.scolton.cf_client.api.Response;
import tech.scolton.cf_client.fragments.dialogs.OverviewAPIKeyDialog;
import tech.scolton.cf_client.fragments.dialogs.OverviewQuickActionsDialogFragment;
import tech.scolton.cf_client.scheduler.LocalTask;
import tech.scolton.cf_client.storage.UserData;

public class OverviewFragment extends Fragment {

    private View main;
    private TextView overviewZoneID;
    private SwipeRefreshLayout refresh;
    private ProgressBar progress;

    public OverviewFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_overview, container, false);
        this.main = root;

        ScrollView overviewRoot = (ScrollView) root.findViewById(R.id.overview_root);
        overviewRoot.setVisibility(View.GONE);

        this.progress = (ProgressBar) root.findViewById(R.id.overview_progress);
        this.progress.setVisibility(View.VISIBLE);

        this.refresh = (SwipeRefreshLayout) root.findViewById(R.id.overview_swipe_refresh);
        this.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateOverview();
            }
        });

        populateOverview();

        return root;
    }

    private void populateOverview() {
        new Request(new LocalTask<Response>() {
            @Override
            public void runLocalTask(final Response response) {
                new Request(new LocalTask<Response>() {
                    @Override
                    public void runLocalTask(Response response2) {
                        try {
                            JSONObject zone = response.getResult();

                            String name = zone.getString("name");
                            boolean paused = zone.getBoolean("paused");
                            int developmentModeTime = zone.getInt("development_mode");

                            LinearLayout overviewHeader = (LinearLayout) main.findViewById(R.id.overview_header);
                            ImageView overviewIcon = (ImageView) main.findViewById(R.id.overview_icon);
                            TextView overviewZoneName = (TextView) main.findViewById(R.id.overview_zone_name);
                            TextView overviewStatus = (TextView) main.findViewById(R.id.overview_status);
                            TextView overviewStatusDesc = (TextView) main.findViewById(R.id.overview_status_desc);

                            Button overviewQuickActions = (Button) main.findViewById(R.id.overview_quick_actions);
                            Button overviewPausedResume = (Button) main.findViewById(R.id.overview_paused_resume);
                            Button overviewDevelopmentModeDisable = (Button) main.findViewById(R.id.overview_development_mode_disable);
                            Button overviewUnderAttackModeDisable = (Button) main.findViewById(R.id.overview_under_attack_mode_disable);

                            JSONArray zoneSettingsArray = response2.getResponseData().getJSONArray("result");
                            Map<String, Object> zoneSettings = new HashMap<>();

                            for (int i = 0; i < zoneSettingsArray.length(); i++) {
                                JSONObject setting = zoneSettingsArray.getJSONObject(i);

                                zoneSettings.put(setting.getString("id"), setting.getString("value"));
                            }

                            String securityLevelOrg = (String) zoneSettings.get("security_level");
                            String securityLevel = null;
                            switch (securityLevelOrg) {
                                case "essentially_off": {
                                    securityLevel = getString(R.string.security_level_essentially_off);
                                    break;
                                }
                                case "low": {
                                    securityLevel = getString(R.string.security_level_low);
                                    break;
                                }
                                case "medium": {
                                    securityLevel = getString(R.string.security_level_medium);
                                    break;
                                }
                                case "high": {
                                    securityLevel = getString(R.string.security_level_high);
                                    break;
                                }
                                case "under_attack": {
                                    securityLevel = getString(R.string.security_level_under_attack);
                                    break;
                                }
                            }

                            String SSL = (String) zoneSettings.get("ssl");
                            switch (SSL) {
                                case "off": {
                                    SSL = getString(R.string.ssl_off);
                                    break;
                                }
                                case "flexible": {
                                    SSL = getString(R.string.ssl_flexible);
                                    break;
                                }
                                case "full": {
                                    SSL = getString(R.string.ssl_full);
                                    break;
                                }
                                case "strict": {
                                    SSL = getString(R.string.ssl_strict);
                                    break;
                                }
                            }

                            String developmentMode = (String) zoneSettings.get("development_mode");
                            switch (developmentMode) {
                                case "on": {
                                    developmentMode = getString(R.string.development_mode_on);
                                    break;
                                }
                                case "off": {
                                    developmentMode = getString(R.string.development_mode_off);
                                    break;
                                }
                            }

                            String cacheLevel = (String) zoneSettings.get("cache_level");
                            switch (cacheLevel) {
                                case "simplified": {
                                    cacheLevel = getString(R.string.cache_level_simplified);
                                    break;
                                }
                                case "basic": {
                                    cacheLevel = getString(R.string.cache_level_basic);
                                    break;
                                }
                                case "aggressive": {
                                    cacheLevel = getString(R.string.cache_level_aggressive);
                                    break;
                                }
                            }

                            overviewQuickActions.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    overviewShowQuickActionsDialog();
                                }
                            });

                            overviewPausedResume.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    overviewResumeCloudflare();
                                }
                            });

                            overviewDevelopmentModeDisable.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    overviewDisableDevelopmentMode();
                                }
                            });

                            overviewUnderAttackModeDisable.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    overviewDisableUnderAttackMode();
                                }
                            });

                            overviewZoneName.setText(name);

                            if (paused) {
                                overviewHeader.setBackground(new ColorDrawable(Color.parseColor("#474747")));
                                overviewIcon.setImageResource(R.drawable.ic_pause_white_24dp);
                                overviewStatus.setText(R.string.overview_paused_text);
                                overviewStatusDesc.setText(R.string.overview_paused_desc);
                                overviewQuickActions.setVisibility(View.GONE);
                                overviewPausedResume.setVisibility(View.VISIBLE);
                                overviewDevelopmentModeDisable.setVisibility(View.GONE);
                                overviewUnderAttackModeDisable.setVisibility(View.GONE);
                            } else if (securityLevelOrg.equals("under_attack")) {
                                overviewHeader.setBackground(new ColorDrawable(Color.parseColor("#BD2527")));
                                overviewIcon.setImageResource(R.drawable.ic_warning_white_24dp);
                                overviewStatus.setText(R.string.overview_under_attack_text);
                                overviewStatusDesc.setText(R.string.overview_under_attack_desc);
                                overviewQuickActions.setVisibility(View.GONE);
                                overviewPausedResume.setVisibility(View.GONE);
                                overviewDevelopmentModeDisable.setVisibility(View.GONE);
                                overviewUnderAttackModeDisable.setVisibility(View.VISIBLE);
                            } else if (developmentModeTime > 0) {
                                overviewHeader.setBackground(new ColorDrawable(Color.parseColor("#474747")));
                                overviewIcon.setImageResource(R.drawable.ic_code_white_24dp);
                                overviewStatus.setText(R.string.overview_development_mode_text);
                                overviewStatusDesc.setText(R.string.overview_development_mode_desc);
                                overviewQuickActions.setVisibility(View.GONE);
                                overviewPausedResume.setVisibility(View.GONE);
                                overviewDevelopmentModeDisable.setVisibility(View.VISIBLE);
                                overviewUnderAttackModeDisable.setVisibility(View.GONE);
                            } else {
                                overviewHeader.setBackground(new ColorDrawable(Color.parseColor("#9BCA3E")));
                                overviewIcon.setImageResource(R.drawable.ic_check_circle_white_24dp);
                                overviewStatus.setText(R.string.overview_active_text);
                                overviewStatusDesc.setText(R.string.overview_active_desc);
                                overviewQuickActions.setVisibility(View.VISIBLE);
                                overviewPausedResume.setVisibility(View.GONE);
                                overviewDevelopmentModeDisable.setVisibility(View.GONE);
                                overviewUnderAttackModeDisable.setVisibility(View.GONE);
                            }



                            Button overviewSecurityLevel = (Button) main.findViewById(R.id.overview_security_level);
                            overviewSecurityLevel.setText(securityLevel);

                            Button overviewSSL = (Button) main.findViewById(R.id.overview_ssl);
                            overviewSSL.setText(SSL);

                            Button overviewCacheLevel = (Button) main.findViewById(R.id.overview_cache_level);
                            overviewCacheLevel.setText(cacheLevel);

                            Button overviewDevelopmentMode = (Button) main.findViewById(R.id.overview_development_mode);
                            overviewDevelopmentMode.setText(developmentMode);

                            overviewZoneID = (TextView) main.findViewById(R.id.overview_zone_id);
                            overviewZoneID.setText(response.getRequestURLArgs()[0]);

                            Button overviewGetAPIKey = (Button) main.findViewById(R.id.overview_get_api_key);
                            overviewGetAPIKey.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    OverviewFragment.this.overviewGetAPIKey();
                                }
                            });

                            Button overviewAPIDocumentation = (Button) main.findViewById(R.id.overview_api_documentation);
                            overviewAPIDocumentation.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    OverviewFragment.this.overviewAPIDocumentation();
                                }
                            });

                            overviewZoneID.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    overviewCopyZoneID();
                                }
                            });

                            main.findViewById(R.id.overview_root).setVisibility(View.VISIBLE);
                            progress.setVisibility(View.GONE);
                            refresh.setRefreshing(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, null, CFAPIMap.ZONE_SETTINGS, null, UserData.ZONE_ID);
            }
        }, null, CFAPIMap.ZONE_DETAILS, null, UserData.ZONE_ID);
    }

    private void overviewAPIDocumentation() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.cloudflare.com/"));
        startActivity(intent);
    }

    private void overviewGetAPIKey() {
        OverviewAPIKeyDialog dialog = new OverviewAPIKeyDialog();
        dialog.setTargetFragment(this, 2);
        dialog.show(getFragmentManager(), "overviewGetAPIKeyDialog");
    }

    private void overviewResumeCloudflare() {
        try {
            refresh.setRefreshing(true);

            JSONObject requestData = new JSONObject();
            requestData.put("paused", false);

            new Request(new LocalTask<Response>() {
                @Override
                public void runLocalTask(Response response) {
                    populateOverview();
                }
            }, null, CFAPIMap.EDIT_ZONE_PROPERTIES, requestData, UserData.ZONE_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void overviewDisableDevelopmentMode() {
        try {
            refresh.setRefreshing(true);

            JSONObject requestData = new JSONObject();
            requestData.put("value", "off");

            new Request(new LocalTask<Response>() {
                @Override
                public void runLocalTask(Response response) {
                    populateOverview();
                }
            }, null, CFAPIMap.CHANGE_DEVELOPMENT_MODE_SETTING, requestData, UserData.ZONE_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void overviewEnableDevelopmentMode() {
        try {
            refresh.setRefreshing(true);

            JSONObject requestData = new JSONObject();
            requestData.put("value", "on");

            new Request(new LocalTask<Response>() {
                @Override
                public void runLocalTask(Response response) {
                    populateOverview();
                }
            }, null, CFAPIMap.CHANGE_DEVELOPMENT_MODE_SETTING, requestData, UserData.ZONE_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void overviewEnableUnderAttackMode() {
        try {
            refresh.setRefreshing(true);

            JSONObject requestData = new JSONObject();
            requestData.put("value", "under_attack");

            new Request(new LocalTask<Response>() {
                @Override
                public void runLocalTask(Response response) {
                    populateOverview();
                }
            }, null, CFAPIMap.CHANGE_SECURITY_LEVEL_SETTING, requestData, UserData.ZONE_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void overviewCopyZoneID() {
        ClipboardManager clipboardManager = (ClipboardManager) this.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData APIKey = ClipData.newPlainText("CF Zone ID", overviewZoneID.getText());
        clipboardManager.setPrimaryClip(APIKey);

        Toast.makeText(this.getContext(), getString(R.string.zone_id_copied), Toast.LENGTH_LONG).show();
    }

    private void overviewShowQuickActionsDialog() {
        OverviewQuickActionsDialogFragment fragment = new OverviewQuickActionsDialogFragment();
        fragment.setTargetFragment(this, 1);
        fragment.show(getFragmentManager(), "overviewQuickActionsDialog");
    }

    private void overviewDisableUnderAttackMode() {
        try {
            refresh.setRefreshing(true);

            JSONObject requestData = new JSONObject();
            requestData.put("value", "essentially_off");

            new Request(new LocalTask<Response>() {
                @Override
                public void runLocalTask(Response response) {
                    populateOverview();
                }
            }, null, CFAPIMap.CHANGE_SECURITY_LEVEL_SETTING, requestData, UserData.ZONE_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onClickQuickAction(int action) {
        switch(action) {
            case 0: {
                overviewEnableDevelopmentMode();
                break;
            }
            case 1: {
                overviewEnableUnderAttackMode();
                break;
            }
        }
    }

}
