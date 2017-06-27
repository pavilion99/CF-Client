package tech.scolton.cf_client.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lombok.Getter;
import tech.scolton.cf_client.R;
import tech.scolton.cf_client.api.Plans;

public class AnalyticsFragment extends Fragment {

    private View main;
    private AnalyticsTimeframe currentScope;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // View root = inflater.inflate(R.layout.fragment_dns);

        return null;
    }

    private enum AnalyticsTimeframe {
        MONTH(-43200, Plans.FREE),
        WEEK(-10080, Plans.FREE),
        HOURS_24(-1440, Plans.FREE),
        HOURS_12(-720, Plans.PRO),
        HOURS_6(-360, Plans.PRO),
        MINUTES_30(-30, Plans.ENTERPRISE);

        @Getter
        private int APIVal;

        @Getter
        private Plans min;

        AnalyticsTimeframe(int APIVal, Plans min) {

        }

    }

}
