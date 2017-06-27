package tech.scolton.cf_client.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashSet;

import lombok.Getter;
import tech.scolton.cf_client.R;
import tech.scolton.cf_client.api.CFAPIMap;
import tech.scolton.cf_client.api.Request;
import tech.scolton.cf_client.api.Response;
import tech.scolton.cf_client.fragments.AnalyticsFragment;
import tech.scolton.cf_client.fragments.AppsFragment;
import tech.scolton.cf_client.fragments.CachingFragment;
import tech.scolton.cf_client.fragments.CryptoFragment;
import tech.scolton.cf_client.fragments.CustomizeFragment;
import tech.scolton.cf_client.fragments.DNSFragment;
import tech.scolton.cf_client.fragments.FirewallFragment;
import tech.scolton.cf_client.fragments.NetworkFragment;
import tech.scolton.cf_client.fragments.OverviewFragment;
import tech.scolton.cf_client.fragments.PageRulesFragment;
import tech.scolton.cf_client.fragments.ScrapeShieldFragment;
import tech.scolton.cf_client.fragments.SpeedFragment;
import tech.scolton.cf_client.fragments.TrafficFragment;
import tech.scolton.cf_client.scheduler.LocalTask;
import tech.scolton.cf_client.storage.Storage;
import tech.scolton.cf_client.storage.UserData;

public class ManagementPortalActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_portal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        this.navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView navFullName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_full_name);
        navFullName.setText(UserData.NAME);

        TextView navEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_email_address);
        navEmail.setText(UserData.EMAIL);

        this.fab = (FloatingActionButton) findViewById(R.id.management_portal_fab);
        this.fab.hide();

        setZoneList();

        setCurrentZone();
    }

    private void setCurrentZone() {
        if (UserData.ZONE_ID == null) {
            if (Storage.storage.getString("ZONE_ID") != null) {
                new Request(new LocalTask<Response>() {
                    @Override
                    public void runLocalTask(Response response) {
                        if (!response.isError() && !response.isCFError()) {
                            UserData.ZONE_ID = Storage.storage.getString("ZONE_ID");
                            continueLoading();
                        }
                    }
                }, null, CFAPIMap.ZONE_DETAILS, null, Storage.storage.getString("ZONE_ID"));
            } else {
                new Request(new LocalTask<Response>() {
                    @Override
                    public void runLocalTask(Response response) {
                        JSONObject responseData = response.getResponseData();

                        try {
                            UserData.ZONE_ID = responseData.getJSONArray("result").getJSONObject(0).getString("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            UserData.ZONE_ID = null;
                        }

                        continueLoading();
                    }
                }, null, CFAPIMap.LIST_ZONES, null);
            }
        } else {
            new Request(new LocalTask<Response>() {
                @Override
                public void runLocalTask(Response response) {
                    if (!response.isError() && !response.isCFError()) {
                        continueLoading();
                    } else {
                        new Request(new LocalTask<Response>() {
                            @Override
                            public void runLocalTask(Response response) {
                                JSONObject responseData = response.getRequestData();

                                try {
                                    UserData.ZONE_ID = responseData.getJSONArray("result").getJSONObject(0).getString("id");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    UserData.ZONE_ID = null;
                                }

                                continueLoading();
                            }
                        }, null, CFAPIMap.LIST_ZONES, null);
                    }
                }
            }, null, CFAPIMap.ZONE_DETAILS, null, UserData.ZONE_ID);
        }
    }

    private void setZoneList() {
        if (UserData.ZONES == null) {
            if (Storage.storage.getStringSet("zones") == null) {
                new Request(new LocalTask<Response>() {
                    @Override
                    public void runLocalTask(Response response) {
                        try {
                            JSONArray zones = response.getResponseData().getJSONArray("result");

                            String[] zonesList = new String[zones.length()];
                            for (int i = 0; i < zones.length(); i++) {
                                JSONObject zone = zones.getJSONObject(i);
                                zonesList[i] = zone.getString("name");
                            }

                            UserData.ZONES = zonesList;
                            Storage.storage.saveStringSet("zones", new HashSet<>(Arrays.asList(zonesList)));

                            Menu navMenu = navigationView.getMenu();
                            for (String zone : UserData.ZONES) {
                                navMenu.add(zone);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, null, CFAPIMap.LIST_ZONES, null);
            } else {
                UserData.ZONES = Storage.storage.getStringSet("zones").toArray(new String[0]);

                Menu navMenu = navigationView.getMenu();
                for (String zone : UserData.ZONES) {
                    navMenu.add(zone);
                }
            }
        } else {
            Menu navMenu = navigationView.getMenu();
            for (String zone : UserData.ZONES) {
                navMenu.add(zone);
            }
        }
    }

    private void continueLoading() {
        CFFragmentStatePagerAdapter adapter = new CFFragmentStatePagerAdapter(getSupportFragmentManager());

        ViewPager tabs = (ViewPager) findViewById(R.id.management_tabs);
        tabs.setAdapter(adapter);

        tabs.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Tabs tab = Tabs.getByPosition(position);
                assert tab != null;

                if (tab.getName().equals("DNS")) {
                    ManagementPortalActivity.this.fab.show();
                } else {
                    ManagementPortalActivity.this.fab.hide();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.management_tabs_title);
        tabLayout.setupWithViewPager(tabs);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.management_portal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void switchZone(String newZoneId) {

    }

    private class CFFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

        CFFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Tabs tab = Tabs.getByPosition(position);
            assert tab != null;
            Class clazz = tab.getClazz();
            try {
                return (Fragment) clazz.newInstance();
            } catch (InstantiationException|IllegalAccessException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public int getCount() {
            return Tabs.values().length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Tabs tab = Tabs.getByPosition(position);
            assert tab != null;
            return tab.getName();
        }

    }

    private enum Tabs {

        OVERVIEW("Overview", OverviewFragment.class, 0),
        ANALYTICS("Analytics", AnalyticsFragment.class, 1),
        DNS("DNS", DNSFragment.class, 2),
        CRYPTO("Crypto", CryptoFragment.class, 3),
        FIREWALL("Firewall", FirewallFragment.class, 4),
        SPEED("Speed", SpeedFragment.class, 5),
        CACHING("Caching", CachingFragment.class, 6),
        PAGE_RULES("Page Rules", PageRulesFragment.class, 7),
        NETWORK("Network", NetworkFragment.class, 8),
        TRAFFIC("Traffic", TrafficFragment.class, 9),
        CUSTOMIZE("Customize", CustomizeFragment.class, 10),
        APPS("Apps", AppsFragment.class, 11),
        SCRAPE_SHIELD("Scrape Shield", ScrapeShieldFragment.class, 12);

        @Getter
        private String name;

        @Getter
        private Class<? extends Fragment> clazz;

        @Getter
        private int position;

        Tabs(String name, Class<? extends Fragment> clazz, int position) {
            this.name = name;
            this.clazz = clazz;
            this.position = position;
        }

        public static Tabs getByPosition(int position) {
            for (Tabs tab : values()) {
                if (tab.getPosition() == position)
                    return tab;
            }

            return null;
        }

    }
}
