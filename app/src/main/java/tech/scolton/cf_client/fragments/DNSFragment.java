package tech.scolton.cf_client.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import tech.scolton.cf_client.R;
import tech.scolton.cf_client.api.CFAPIMap;
import tech.scolton.cf_client.api.Request;
import tech.scolton.cf_client.api.Response;
import tech.scolton.cf_client.scheduler.LocalTask;
import tech.scolton.cf_client.storage.UserData;

public class DNSFragment extends Fragment {

    private View main;
    private ProgressBar progress;
    private List<DNSRecord> records;
    private SwipeRefreshLayout root;

    private DNSRecordAdapter adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dns, container, false);
        this.main = root;

        this.root = (SwipeRefreshLayout) root.findViewById(R.id.dns_swipe_refresh);
        this.root.setVisibility(View.GONE);

        this.progress = (ProgressBar) root.findViewById(R.id.dns_progress);
        this.progress.setVisibility(View.VISIBLE);

        this.root = (SwipeRefreshLayout) main.findViewById(R.id.dns_swipe_refresh);
        this.root.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateDNS();
            }
        });

        populateDNS();

        return this.main;
    }

    private void populateDNS() {
        try {
            JSONObject requestData = new JSONObject();
            requestData.put("per_page", 100);
            new Request(new LocalTask<Response>() {
                @Override
                public void runLocalTask(Response response) {
                    try {
                        // TODO: make requests work with pages to get all results
                        JSONArray records = response.getResponseData().getJSONArray("result");
                        DNSFragment.this.records = new ArrayList<>();
                        for (int i = 0; i < records.length(); i++) {
                            JSONObject record = records.getJSONObject(i);
                            DNSFragment.this.records.add(new DNSRecord(record));
                        }

                        RecyclerView DNSRecordList = (RecyclerView) main.findViewById(R.id.dns_record_list);
                        if (DNSFragment.this.adapter == null)
                            DNSFragment.this.adapter = new DNSRecordAdapter();
                        DNSRecordList.setAdapter(DNSFragment.this.adapter);
                        DNSRecordList.setLayoutManager(new LinearLayoutManager(getActivity()));
                        DNSRecordList.setItemAnimator(new DefaultItemAnimator());

                        DNSFragment.this.progress.setVisibility(View.GONE);
                        DNSFragment.this.root.setVisibility(View.VISIBLE);

                        DNSFragment.this.root.setRefreshing(false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, null, CFAPIMap.LIST_DNS_RECORDS, requestData, UserData.ZONE_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class DNSRecordAdapter extends RecyclerView.Adapter<DNSRecordViewHolder> {
        @Override
        public DNSRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View DNSRecordView = inflater.inflate(R.layout.dns_record, parent, false);

            return new DNSRecordViewHolder(DNSRecordView);
        }

        @Override
        public void onBindViewHolder(DNSRecordViewHolder holder, int position) {
            DNSRecord dnsRecord = DNSFragment.this.records.get(position);

            holder.getRecordColor().setBackgroundColor(dnsRecord.getType().getColor(DNSFragment.this.getContext()));
            holder.getRecordType().setText(dnsRecord.getType().getName(getContext()));
            holder.getRecordType().setTextColor(dnsRecord.getType().getColor(getContext()));
            holder.getRecordName().setText(dnsRecord.getName());

            holder.getRecordDesc().setText(dnsRecord.getType().getDesc(getContext()));
            holder.getRecordContent().setText(dnsRecord.getContent());

            if (dnsRecord.getType().hasPriority()) {
                holder.getRecordPrioritySection().setVisibility(View.VISIBLE);
                holder.getRecordPriority().setText(String.valueOf(dnsRecord.getPriority()));
            } else {
                holder.getRecordPrioritySection().setVisibility(View.GONE);
            }

            // TODO: Convert TTL into more easily-read format
            holder.getRecordTTL().setText(String.valueOf(dnsRecord.getTtl()));

            if (dnsRecord.isProxiable()) {
                if (dnsRecord.isProxied()) {
                    Drawable cloud = ContextCompat.getDrawable(getContext(), R.drawable.ic_cloud_black_24dp);
                    DrawableCompat.setTint(cloud, ContextCompat.getColor(getContext(), R.color.colorPrimary));
                    holder.getRecordProxy().setImageDrawable(cloud);
                } else {
                    holder.getRecordProxy().setImageResource(R.drawable.ic_cloud_off_black_36dp);
                }
                holder.getRecordProxy().setVisibility(View.VISIBLE);
            } else {
                holder.getRecordProxy().setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return DNSFragment.this.records.size();
        }
    }

    public class DNSRecord {

        @Getter
        private DNSRecordType type;

        @Getter
        private String name;

        @Getter
        private String content;

        @Getter
        private boolean proxiable;

        @Getter
        private boolean proxied;

        @Getter
        private int ttl;

        @Getter
        private boolean locked;

        @Getter
        private String id;

        @Getter
        private int priority;

        DNSRecord(JSONObject record) {
            try {
                this.type = DNSRecordType.valueOf(record.getString("type"));
                this.name = record.getString("name");
                this.content = record.getString("content");
                this.proxiable = record.getBoolean("proxiable");
                this.proxied = record.getBoolean("proxied");
                this.ttl = record.getInt("ttl");
                this.locked = record.getBoolean("locked");
                this.id = record.getString("id");

                if (record.has("priority")) {
                    this.priority = record.getInt("priority");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class DNSRecordViewHolder extends RecyclerView.ViewHolder {

        @Getter
        private View recordColor;

        @Getter
        private TextView recordType;

        @Getter
        private TextView recordName;

        @Getter
        private TextView recordDesc;

        @Getter
        private TextView recordContent;

        @Getter
        private LinearLayout recordPrioritySection;

        @Getter
        private TextView recordPriority;

        @Getter
        private TextView recordTTL;

        @Getter
        private ImageButton recordProxy;

        DNSRecordViewHolder(View itemView) {
            super(itemView);

            this.recordColor = itemView.findViewById(R.id.dns_record_color);
            this.recordType = (TextView) itemView.findViewById(R.id.dns_record_type);
            this.recordName = (TextView) itemView.findViewById(R.id.dns_record_name);
            this.recordDesc = (TextView) itemView.findViewById(R.id.dns_record_desc);
            this.recordContent = (TextView) itemView.findViewById(R.id.dns_record_content);
            this.recordPrioritySection = (LinearLayout) itemView.findViewById(R.id.dns_record_priority_section);
            this.recordPriority = (TextView) itemView.findViewById(R.id.dns_record_priority);
            this.recordTTL = (TextView) itemView.findViewById(R.id.dns_record_ttl);
            this.recordProxy = (ImageButton) itemView.findViewById(R.id.dns_record_proxy);

            this.recordProxy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = getAdapterPosition();
                    proxyToggle(i);
                }
            });
        }

        private void proxyToggle(final int id) {
            DNSRecord dnsRecord = DNSFragment.this.records.get(id);

            try {
                JSONObject requestData = new JSONObject();
                requestData.put("name", dnsRecord.getName());
                requestData.put("content", dnsRecord.getContent());
                requestData.put("type", dnsRecord.getType().toString());
                requestData.put("proxied", !dnsRecord.isProxied());
                requestData.put("ttl", dnsRecord.getTtl());

                new Request(new LocalTask<Response>() {
                    @Override
                    public void runLocalTask(Response response) {
                        DNSFragment.this.records.set(id, new DNSRecord(response.getResult()));
                        DNSFragment.this.adapter.notifyItemChanged(id);
                    }
                }, null, CFAPIMap.UPDATE_DNS_RECORD, requestData, UserData.ZONE_ID, dnsRecord.getId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private enum DNSRecordType {
        A(R.string.dns_record_a, R.string.dns_record_a_desc, R.color.dns_record_a, false),
        AAAA(R.string.dns_record_aaaa, R.string.dns_record_aaaa_desc, R.color.dns_record_aaaa, false),
        CNAME(R.string.dns_record_cname, R.string.dns_record_cname_desc, R.color.dns_record_cname, false),
        MX(R.string.dns_record_mx, R.string.dns_record_mx_desc, R.color.dns_record_mx, true),
        LOC(R.string.dns_record_loc, R.string.dns_record_loc_desc, R.color.dns_record_loc, false),
        SRV(R.string.dns_record_srv, R.string.dns_record_srv_desc, R.color.dns_record_srv, false),
        SPF(R.string.dns_record_spf, R.string.dns_record_spf_desc, R.color.dns_record_spf, false),
        TXT(R.string.dns_record_txt, R.string.dns_record_txt_desc, R.color.dns_record_txt, false),
        NS(R.string.dns_record_ns, R.string.dns_record_ns_desc, R.color.dns_record_ns, false),
        CAA(R.string.dns_record_caa, R.string.dns_record_caa_desc, R.color.dns_record_caa, false);

        @StringRes
        private int name;

        @StringRes
        private int desc;

        @ColorRes
        private int color;

        private boolean priority;

        DNSRecordType(@StringRes int name, @StringRes int desc, @ColorRes int color, boolean priority) {
            this.name = name;
            this.desc = desc;
            this.color = color;
            this.priority = priority;
        }

        public String getName(Context context) {
            return context.getString(this.name);
        }

        public String getDesc(Context context) {
            return context.getString(this.desc);
        }

        public int getColor(Context context) {
            return ContextCompat.getColor(context, this.color);
        }

        public boolean hasPriority() {
            return this.priority;
        }
    }

    public enum TTL {
        AUTOMATIC(1),
        MINUTES_2(120),
        MINUTES_5(300),
        MINUTES_10(600),
        MINUTES_15(900),
        MINUTES_30(1800),
        HOUR_1(3600);

        @Getter
        private int value;

        TTL(int value) {

        }

        public static TTL fromValue(int value) {
            for(TTL ttl : TTL.values()) {
                if (ttl.getValue() == value)
                    return ttl;
            }
            return null;
        }

    }

}
