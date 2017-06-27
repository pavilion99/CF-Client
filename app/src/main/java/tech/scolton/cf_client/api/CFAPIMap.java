package tech.scolton.cf_client.api;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public enum CFAPIMap {

    USER_DETAILS(Method.GET, "/user", 0, 0, Plans.FREE),
    UPDATE_USER(Method.PATCH, "/user", 0, 0, Plans.FREE),
    BILLING_PROFILE(Method.GET, "/user/billing/profile", 0, 0, Plans.FREE),
    BILLING_HISTORY(Method.GET, "/user/billing/history", 0, 0, Plans.FREE),
    USER_SUBSCRIPTIONS(Method.GET, "/user/subscriptions", 0, 0, Plans.NONE),
    UPDATE_SUBSCRIPTION(Method.PUT, "/user/subscriptions/{0}", 1, 4, Plans.FREE),
    DELETE_SUBSCRIPTION(Method.DELETE, "/user/subscriptions/{0}", 1, 0, Plans.NONE),

    LIST_USER_ACCESS_RULES(Method.GET, "/user/firewall/access_rules/rules", 0, 0, Plans.FREE),
    CREATE_USER_ACCESS_RULE(Method.POST, "/user/firewall/access_rules/rules", 0, 2, Plans.FREE),
    UPDATE_USER_ACCESS_RULE(Method.PATCH, "/user/firewall/access_rules/rules/{0}", 1, 0, Plans.FREE),
    DELETE_USER_ACCESS_RULE(Method.DELETE, "/user/firewall/access_rules/rules/{0}", 1, 0, Plans.FREE),

    LIST_USER_ORGANIZATIONS(Method.GET, "/user/organizations", 0, 0, Plans.ENTERPRISE),
    USER_ORGANIZATION_DETAILS(Method.GET, "/user/organizations/{0}", 1, 0, Plans.ENTERPRISE),
    LEAVE_ORGANIZATION(Method.DELETE, "/user/organizations/{0}", 1, 0, Plans.ENTERPRISE),

    LIST_USER_INVITATIONS(Method.GET, "/user/invites", 0, 0, Plans.ENTERPRISE),
    USER_INVITATION_DETAILS(Method.GET, "/user/invites/{0}", 1, 0, Plans.ENTERPRISE),
    RESPOND_TO_INVITATION(Method.PATCH, "/user/invites/{0}", 1, 1, Plans.ENTERPRISE),

    CREATE_ZONE(Method.POST, "/zones", 0, 1, Plans.FREE),
    ZONE_ACTIVATION_CHECK(Method.PUT, "/zones/{0}/activation_check", 1, 0, Plans.FREE),
    LIST_ZONES(Method.GET, "/zones/", 0, 0, Plans.FREE),
    ZONE_DETAILS(Method.GET, "/zones/{0}", 1, 0, Plans.FREE),
    EDIT_ZONE_PROPERTIES(Method.PATCH, "/zones/{0}", 1, 0, Plans.FREE),
    PURGE_ALL_FILES(Method.DELETE, "/zones/{0}/purge_cache", 1, 1, Plans.FREE),
    PURGE_INDIVIDUAL_FILES(Method.DELETE, "/zones/{0}/purge_cache", 1, 0, Plans.FREE),
    DELETE_ZONE(Method.DELETE, "/zones/{0}", 1, 0, Plans.FREE),

    AVAILABLE_RATE_PLANS(Method.GET, "/zones/{0}/available_rate_plans", 1, 0, Plans.FREE),
    ZONE_SETTINGS(Method.GET, "/zones/{0}/settings", 1, 0, Plans.FREE),
    ADVANCED_DDOS_SETTINGS(Method.GET, "/zones/{0}/settings/advanced_ddos", 1, 0, Plans.BUSINESS),
    ALWAYS_ONLINE_SETTING(Method.GET, "/zones/{0}/settings/always_online", 1, 0, Plans.FREE),
    ALWAYS_USE_HTTPS_SETTING(Method.GET, "/zones/{0}/settings/always_use_https", 1, 0, Plans.FREE),
    AUTOMATIC_HTTPS_REWRITES_SETTING(Method.GET, "/zones/{0}/settings/automatic_https_rewrites", 1, 0, Plans.FREE),
    BROWSER_CACHE_TTL_SETTING(Method.GET, "/zones/{0}/settings/browser_cache_ttl", 1, 0, Plans.FREE),
    BROWSER_CHECK_SETTING(Method.GET, "/zones/{0}/settings/browser_check", 1, 0, Plans.FREE),
    CACHE_LEVEL_SETTING(Method.GET, "/zones/{0}/settings/cache_level", 1, 0, Plans.FREE),
    CHALLENGE_TTL_SETTING(Method.GET, "/zones/{0}/settings/challenge_ttl", 1, 0, Plans.FREE),
    DEVELOPMENT_MODE_SETTING(Method.GET, "/zones/{0}/settings/development_mode", 1, 0, Plans.FREE),
    EMAIL_OBFUSCATION_SETTING(Method.GET, "/zones/{0}/settings/email_obfuscation", 1, 0, Plans.FREE),
    HOTLINK_PROTECTION_SETTING(Method.GET, "/zones/{0}/settings/hotlink_protection", 1, 0, Plans.FREE),
    IP_GEOLOCATION_SETTING(Method.GET, "/zones/{0}/settings/ip_geolocation", 1, 0, Plans.FREE),
    IPV6_SETTING(Method.GET, "/zones/{0}/settings/ipv6", 1, 0, Plans.FREE),
    MINIFY_SETTING(Method.GET, "/zones/{0}/settings/minify", 1, 0, Plans.FREE),
    MOBILE_REDIRECT_SETTING(Method.GET, "/zones/{0}/settings/mobile_redirect", 1, 0, Plans.FREE),
    MIRAGE_SETTING(Method.GET, "/zones/{0}/settings/mirage", 1, 0, Plans.FREE),
    ENABLE_ERROR_PAGES_ON_SETTING(Method.GET, "/zones/{0}/settings/origin_error_page_pass_thru", 1, 0, Plans.ENTERPRISE),
    OPPORTUNISTIC_ENCRYPTION_SETTING(Method.GET, "/zones/{0}/settings/opportunistic_encryption", 1, 0, Plans.FREE),
    POLISH_SETTING(Method.GET, "/zones/{0}/settings/polish", 1, 0, Plans.FREE),
    WEBP_SETTING(Method.GET, "/zones/{0}/settings/webp", 1, 0, Plans.FREE),
    PREFETCH_PRELOAD_SETTING(Method.GET, "/zones/{0}/settings/prefetch_preload", 1, 0, Plans.ENTERPRISE),
    RESPONSE_BUFFERING_SETTING(Method.GET, "/zones/{0}/settings/response_buffering", 1, 0, Plans.ENTERPRISE),
    ROCKET_LOADER_SETTING(Method.GET, "/zones/{0}/settings/rocket_loader", 1, 0, Plans.FREE),
    SECURITY_HEADER_SETTING(Method.GET, "/zones/{0}/setting/security_header", 1, 0, Plans.FREE),
    SECURITY_LEVEL_SETTING(Method.GET, "/zones/{0}/setting/security_level", 1, 0, Plans.FREE),
    SERVER_SIDE_EXCLUDE_SETTING(Method.GET, "/zones/{0}/setting/server_side_exclude", 1, 0, Plans.FREE),
    ENABLE_QUERY_STRING_SORT_SETTING(Method.GET, "/zones/{0}/settings/sort_query_string_for_cache", 1, 0, Plans.ENTERPRISE),
    SSL_SETTING(Method.GET, "/zones/{0}/settings/ssl", 1, 0, Plans.FREE),
    ZONE_ENABLE_TLS_1_2(Method.GET, "/zones/{0}/settings/tls_1_2_only", 1, 0, Plans.BUSINESS),
    ZONE_ENABLE_TLS_1_3(Method.GET, "/zones/{0}/settings/tls_1_3", 1, 0, Plans.FREE),
    TLS_CLIENT_AUTH_SETTING(Method.GET, "/zones/{0}/settings/tls_client_auth", 1, 0, Plans.FREE),
    TRUE_CLIENT_IP_SETTING(Method.GET, "/zones/{0}/settings/true_Client_ip_header", 1, 0, Plans.ENTERPRISE),
    WEB_APPLICATION_FIREWALL_SETTING(Method.GET, "/zones/{0}/settings/waf", 1, 0, Plans.PRO),
    HTTP2_SETTING(Method.GET, "/zones/{0}/settings/http2", 1, 0, Plans.FREE),
    PSEUDO_IPV4_SETTING(Method.GET, "/zones/{0]/settings/pseudo_ipv4", 1, 0, Plans.FREE),
    WEBSOCKETS_SETTING(Method.GET, "/zones/{0}/settings/websockets", 1, 0, Plans.FREE),

    EDIT_ZONE_SETTINGS_INFO(Method.PATCH, "/zones/{0}/settings", 1, 1, Plans.FREE),
    CHANGE_ALWAYS_ONLINE_SETTING(Method.PATCH, "/zones/{0}/settings/always_online", 1, 1, Plans.FREE),
    CHANGE_ALWAYS_USE_HTTPS_SETTING(Method.PATCH, "/zones/{0}/settings/always_use_https", 1, 1, Plans.FREE),
    CHANGE_AUTOMATIC_HTTPS_REWRITES_SETTING(Method.PATCH, "/zones/{0}/settings/automatic_https_rewrites", 1, 1, Plans.FREE),
    CHANGE_BROWSER_CACHE_TTL_SETTING(Method.PATCH, "/zones/{0}/settings/browser_cache_ttl", 1, 1, Plans.FREE),
    CHANGE_BROWSER_CHECK_SETTING(Method.PATCH, "/zones/{0}/settings/browser_check", 1, 1, Plans.FREE),
    CHANGE_CACHE_LEVEL_SETTING(Method.PATCH, "/zones/{0}/settings/cache_level", 1, 1, Plans.FREE),
    CHANGE_CHALLENGE_TTL_SETTING(Method.PATCH, "/zones/{0}/settings/challenge_ttl", 1, 1, Plans.FREE),
    CHANGE_DEVELOPMENT_MODE_SETTING(Method.PATCH, "/zones/{0}/settings/development_mode", 1, 1, Plans.FREE),
    CHANGE_EMAIL_OBFUSCATION_SETTING(Method.PATCH, "/zones/{0}/settings/email_obfuscation", 1, 1, Plans.FREE),
    CHANGE_ENABLE_ERROR_PAGES_ON_SETTING(Method.PATCH, "/zones/{0}/settings/origin_error_page_pass_thru", 1, 1, Plans.ENTERPRISE),
    CHANGE_ENABLE_QUERY_STRING_SORT_SETTING(Method.PATCH, "/zones/{0}/settings/sort_query_string_for_cache", 1, 1, Plans.ENTERPRISE),
    CHANGE_HOTLINK_PROTECTION_SETTING(Method.PATCH, "/zones/{0}/settings/hotlink_protection", 1, 1, Plans.FREE),
    CHANGE_IP_GEOLOCATION_SETTING(Method.PATCH, "/zones/{0}/settings/ip_geolocation", 1, 1, Plans.FREE),
    CHANGE_IPV6_SETTING(Method.PATCH, "/zones/{0}/settings/ipv6", 1, 1, Plans.FREE),
    CHANGE_MINIFY_SETTING(Method.PATCH, "/zones/{0}/settings/minify", 1, 1, Plans.FREE),
    CHANGE_MOBILE_REDIRECT_SETTING(Method.PATCH, "/zones/{0}/settings/mobile_redirect", 1, 1, Plans.FREE),
    CHANGE_MIRAGE_SETTING(Method.PATCH, "/zones/{0}/settings/mirage", 1, 1, Plans.PRO),
    CHANGE_OPPORTUNISTIC_ENCRYPTION_SETTING(Method.PATCH, "/zones/{0}/settings/opportunistic_encryption", 1, 1, Plans.FREE),
    CHANGE_POLISH_SETTING(Method.PATCH, "/zones/{0}/settings/polish", 1, 1, Plans.PRO),
    CHANGE_WEBP_SETTING(Method.PATCH, "/zones/{0}/settings/webp", 1, 1, Plans.PRO),
    CHANGE_PREFETCH_PRELOAD_SETTING(Method.PATCH, "/zones/{0}/settings/prefetch_preload", 1, 1, Plans.ENTERPRISE),
    CHANGE_RESPONSE_BUFFERING_SETTING(Method.PATCH, "/zones/{0}/settings/response_buffering", 1, 1, Plans.ENTERPRISE),
    CHANGE_ROCKET_LOADER_SETTING(Method.PATCH, "/zones/{0}/settings/rocket_loader", 1, 1, Plans.FREE),
    CHANGE_SECURITY_HEADER_SETTING(Method.PATCH, "/zones/{0}/settings/security_header", 1, 1, Plans.FREE),
    CHANGE_SECURITY_LEVEL_SETTING(Method.PATCH, "/zones/{0}/settings/security_level", 1, 1, Plans.FREE),
    CHANGE_SERVER_SIDE_EXCLUDE_SETTING(Method.PATCH, "/zones/{0}/settings/server_side_exclude", 1, 1, Plans.FREE),
    CHANGE_SSL_SETTING(Method.PATCH, "/zones/{0}/settings/ssl", 1, 1, Plans.FREE),
    CHANGE_TLS_CLIENT_AUTH_SETTING(Method.PATCH, "/zones/{0}/settings/tls_client_auth", 1, 1, Plans.FREE),
    CHANGE_TRUE_CLIENT_IP_SETTING(Method.PATCH, "/zones/{0}/settings/true_client_ip_header", 1, 1, Plans.ENTERPRISE),
    CHANGE_TLS_1_2_SETTING(Method.PATCH, "/zones/{0}/settings/tls_1_2_only", 1, 1, Plans.BUSINESS),
    CHANGE_TLS_1_3_SETTING(Method.PATCH, "/zones/{0}/settings/tls_1_3", 1, 1, Plans.FREE),
    CHANGE_WEB_APPLICATION_FIREWALL_SETTING(Method.PATCH, "/zones/{0}/settings/waf", 1, 1, Plans.PRO),
    CHANGE_HTTP2_SETTING(Method.PATCH, "/zones/{0}/settings/http2", 1, 1, Plans.PRO),
    CHANGE_PSEUDO_IPV4_SETTING(Method.PATCH, "/zones/{0}/settings/pseudo_ipv4", 1, 1, Plans.PRO),
    CHANGE_WEBSOCKETS_SETTING(Method.PATCH, "/zones/{0}/settings/websockets", 1, 1, Plans.FREE),

    CREATE_DNS_RECORD(Method.POST, "/zones/{0}/dns_records", 1, 3, Plans.FREE),
    LIST_DNS_RECORDS(Method.GET, "/zones/{0}/dns_records", 1, 0, Plans.FREE),
    DNS_RECORD_DETAILS(Method.GET, "/zones/{0}/dns_records/{1}", 2, 0, Plans.FREE),
    UPDATE_DNS_RECORD(Method.PUT, "/zones/{0}/dns_records/{1}", 2, 3, Plans.FREE),
    DELETE_DNS_RECORD(Method.DELETE, "/zones/{0}/dns_records/{1}", 2, 3, Plans.FREE),
    //TODO Make Bind Config Work
    IMPORT_DNS_RECORDS(Method.POST, "/zones/{0}/dns_records/import", 1, 1, Plans.FREE),

    GET_AVAILABLE_RAILGUNS(Method.GET, "/zones/{0}/railguns", 1, 0, Plans.BUSINESS),
    GET_RAILGUN_DETAILS(Method.GET, "/zones/{0}/railguns/{1}", 2, 0, Plans.BUSINESS),
    TEST_RAILGUN_CONNECTION(Method.GET, "/zones/{0}/railguns/{1}/diagnose", 2, 0, Plans.BUSINESS),
    CONNECT_OR_DISCONNECT_RAILGUN(Method.PATCH, "/zones/{0}/railguns/{1}", 2, 1, Plans.BUSINESS),

    // Zone Analytics
    ANALYTICS_DASHBOARD(Method.GET, "/zones/{0}/analytics/dashboard", 1, 0, Plans.FREE),
    ANALYTICS_BY_COLOCATIONS(Method.GET, "/zones/{0}/analytics/colos", 1, 0, Plans.ENTERPRISE),

    // DNS Analytics
    DNS_ANALYTICS_TABLE(Method.GET, "/zones/{0}/dns_analytics/report", 1, 4, Plans.NONE),
    DNS_ANALYTICS_BY_TIME(Method.GET, "/zones/{0}/report/bytime", 1, 0, Plans.NONE),

    // Railgun
    CREATE_RAILGUN(Method.POST, "/railguns", 0, 1, Plans.BUSINESS),
    LIST_RAILGUNS(Method.GET, "/railguns", 0, 0, Plans.BUSINESS),
    RAILGUN_DETAILS(Method.GET, "/railguns/{0}", 1, 0, Plans.BUSINESS),
    GET_ZONES_CONNECTED_TO_RAILGUN(Method.GET, "/railguns/{0}/zones", 1, 0, Plans.BUSINESS),
    ENABLE_OR_DISABLE_RAILGUN(Method.PATCH, "/railguns/{0}", 1, 1, Plans.BUSINESS),
    DELETE_RAILGUN(Method.DELETE, "/railguns/{0}", 1, 0, Plans.BUSINESS),

    // Custom Pages for a Zone
    AVAILBLE_CUSTOM_PAGES(Method.GET, "/zones/{0}/custom_pages", 1, 0, Plans.PRO),
    CUSTOM_PAGE_DETAILS(Method.GET, "/zones/{0}/custom_pages/{1}", 2, 0, Plans.PRO),
    UPDATE_CUSTOM_PAGE_URL(Method.PUT, "/zones/{0}/custom_pages/{1}", 2, 2, Plans.PRO),

    // Custom SSL for a Zone
    CREATE_SSL_CONFIGURATION(Method.POST, "/zones/{0}/custom_certificates", 1, 2, Plans.BUSINESS),
    LIST_SSL_CONFIGURATIONS(Method.GET, "/zones/{0}/custom_certificates", 1, 0, Plans.BUSINESS),
    SSL_CONFIGURATION_DETAILS(Method.GET, "/zones/{0}/custom_certificates/{1}", 2, 0, Plans.BUSINESS),
    UPDATE_SSL_CONFIGURATION(Method.PATCH, "/zones/{0}/custom_certificates/{1}", 2, 0, Plans.BUSINESS),
    REPRIORITIZE_SSL_CERTIFICATES(Method.PUT, "/zones/{0}/custom_certificates/prioritize", 1, 1, Plans.BUSINESS),
    DELETE_SSL_CERTIFICATE(Method.DELETE, "/zones/{0}/custom_Certificates/{1}", 2, 0, Plans.BUSINESS),

    // Custom Hostname for a Zone
    CREATE_CUSTOM_HOSTNAME(Method.POST, "/zones/{0}/custom_hostnames", 1, 2, Plans.ENTERPRISE),
    LIST_CUSTOM_HOSTNAMES(Method.GET, "/zones/{0}/custom_hostnames", 1, 0, Plans.ENTERPRISE),
    CUSTOM_HOSTNAME_CONFIGURATION_DETAILS(Method.GET, "/zones/{0}/custom_hostnames/{1}", 2, 0, Plans.ENTERPRISE),
    UPDATE_CUSTOM_HOSTNAME_CONFIGURATION(Method.PATCH, "/zones/{0}/custom_hostnames/{1}", 2, 0, Plans.ENTERPRISE),
    DELETE_CUSTOM_HOSTNAME(Method.DELETE, "/zones/{0}/custom_hostnames/{1}", 2, 0, Plans.ENTERPRISE),

    // Keyless SSL for a Zone
    CREATE_KEYLESS_SSL_CONFIGURATION(Method.POST, "/zones/{0}/keyless_certificates", 1, 3, Plans.ENTERPRISE),
   LIST_KEYLESS_SSL_CONFIGURATIONS(Method.GET, "/zones/{0}/keyless_certificates", 1, 0, Plans.ENTERPRISE),
    KEYLESS_SSL_DETAILS(Method.GET, "/zones/{0}/keyless_certificates/{1}", 2, 0, Plans.ENTERPRISE),
    UPDATE_KEYLESS_CONFIGURATION(Method.PATCH, "/zones/{0}/keyless_certificates/{1}", 2, 0, Plans.ENTERPRISE),
    DELETE_KEYLESS_CONFIGURATION(Method.DELETE, "/zones/{0}/keyless_certificates/{1}", 2, 0, Plans.ENTERPRISE),

    // Page Rules for a Zone
    CREATE_PAGE_RULE(Method.POST, "/zones/{0}/pagerules", 1, 2, Plans.FREE),
    LIST_PAGE_RULES(Method.GET, "/zones/{0}/pagerules", 1, 0, Plans.FREE),
    PAGE_RULE_DETALS(Method.GET, "/zones/{0}/pagerules/{1}", 2, 0, Plans.FREE),
    CHANGE_PAGE_RULE(Method.PATCH, "/zones/{0}/pagerules/{1}", 2, 0, Plans.FREE),
    UPDATE_PAGE_RULE(Method.PUT, "/zones/{0}/pagerules/{1}", 2, 2, Plans.FREE),
    DELETE_PAGE_RULE(Method.DELETE, "/zones/{0}/pagerules/{1}", 2, 0, Plans.FREE),

    // Rate Limits for a Zone
    LIST_RATE_LIMITS(Method.GET, "/zones/{0}/rate_limits", 1, 0, Plans.FREE),
    CREATE_RATE_LIMIT(Method.POST, "/zones/{0}/rate_limits", 1, 4, Plans.FREE),
    RATE_LIMIT_DETAILS(Method.GET, "/zones/{0}/rate_limits/{1}", 2, 0, Plans.FREE),
    UPDATE_RATE_LIMIT(Method.PUT, "/zones/{0}/rate_limits/{1}", 2, 5, Plans.FREE),
    DELETE_RATE_LIMIT(Method.DELETE, "/zones/{0}/rate_limits/{1}", 2, 0, Plans.FREE),

    // Firewall access rule for a Zone
    LIST_ACCESS_RULES(Method.GET, "/zones/{0}/firewall/access_rules/rules", 1, 0, Plans.FREE),
    CREATE_ACCESS_RULE(Method.POST, "/zones/{0}/firewall/access_rules/rules", 1, 2, Plans.FREE),
    UPDATE_ACCESS_RULE(Method.PATCH, "/zones/{0}/firewall/access_rules/rules/{1}", 2, 0, Plans.FREE),
    DELETE_ACCESS_RULE(Method.DELETE, "/zones/{0}/firewall/access_rules/rules/{1}", 2, 0, Plans.FREE),

    // WAF Rule Packages
    LIST_FIREWALL_PACKAGES(Method.GET, "/zones/{0}/firewall/waf/packages", 1, 0, Plans.PRO),
    FIREWALL_PACKAGE_INFO(Method.GET, "/zones/{0}/firewall/waf/packages/{1}", 2, 0, Plans.PRO),
    CHANGE_ANOMALY_DETECTION_WAF_PACKAGE_SETTINGS(Method.PATCH, "/zones/{0}/firewall/waf/packages/{1}", 2, 0, Plans.PRO),

    // WAF Rule Groups
    LIST_RULE_GROUPS(Method.GET, "/zones/{0}/firewall/waf/packages/{1}/groups", 2, 0, Plans.PRO),
    RULE_GROUP_INFO(Method.GET, "/zones/{0}/firewall/waf/packages/{1}/groups/{2}", 3, 0, Plans.PRO),
    UPDATE_RULE_GROUP(Method.PATCH, "/zones/{0}/firewall/waf/packages/{1}/groups/{2}", 3, 0, Plans.PRO),

    // WAF Rules
    LIST_RULES(Method.GET, "/zones/{0}/firewall/waf/packages/{1}/rules", 2, 0, Plans.PRO),
    RULE_INFO(Method.GET, "/zones/{0}/firewall/waf/packages/{1}/rules/{2}", 3, 0, Plans.PRO),
    UPDATE_RULE(Method.PATCH, "/zones/{0}/firewall/waf/packages/{1}/rules/{2}", 3, 0, Plans.PRO),

    // Analyze Certificate
    ANALYZE_CERTIFICATE(Method.POST, "/zones/{0}/ssl/analyze", 1, 0, Plans.FREE),

    // Certificate Packs
    LIST_CERTIFICATE_PACKS(Method.GET, "/zones/{0}/ssl/certificate_packs", 1, 0, Plans.FREE),
    ORDER_CERTIFICATE_PACK(Method.POST, "/zones/{0}/ssl/certificate_packs", 1, 0, Plans.FREE),
    EDIT_CERTIFICATE_PACK(Method.PATCH, "/zones/{0}/ssl/certificate_packs/{1}", 2, 0, Plans.FREE),


    // SSL Verification
    GET_SSL_VERIFICATION(Method.GET, "/zones/{0}/ssl/verification", 1, 0, Plans.FREE),

    // Zone Subscription
    GET_ZONE_SUBSCRIPTION(Method.GET, "/zones/{0}/subscription", 1, 0, Plans.FREE),
    CREATE_ZONE_SUBSCRIPTION(Method.POST, "/zones/{0}/subscription", 1, 4, Plans.FREE),
    UPDATE_ZONE_SUBSCRIPTION(Method.POST, "/zones/{0}/subscription", 1, 4, Plans.FREE),

    // Organizations
    ORGANIZATION_DETAILS(Method.GET, "/organizations/{0}", 1, 0, Plans.ENTERPRISE),
    UPDATE_ORGANIZATION(Method.PATCH, "/organizations/{0}", 1, 0, Plans.ENTERPRISE)   ,

    // Organization Members
    LIST_MEMBERS(Method.GET, "/organizations/{0}/members", 1, 0, Plans.ENTERPRISE),
    MEMBER_DETAILS(Method.GET, "/organizations/{0}/members/{1}", 2, 0, Plans.ENTERPRISE),
    UPDATE_MEMBER_ROLES(Method.PATCH, "/organizations/{0}/members/{1}", 2, 0, Plans.ENTERPRISE),
    REMOVE_MEMBER(Method.DELETE, "/organizations/{0}/members/{1}", 2, 0, Plans.ENTERPRISE),

    // Organization Invites
    CREATE_INVITATION(Method.POST, "/organizations/{0}/invites", 1, 2, Plans.ENTERPRISE),
    LIST_INVITATIONS(Method.GET, "/organizations/{0}/invites", 1, 0, Plans.ENTERPRISE),
    INVITATION_DETAILS(Method.GET, "/organizations/{0}/invites/{1}", 2, 0, Plans.ENTERPRISE),
    UPDATE_INVITATION_ROLES(Method.PATCH, "/organizations/{0}/invite/{1}", 2, 0, Plans.ENTERPRISE),
    CANCEL_INVITATION(Method.DELETE, "/organizations/{0}/invites/{1}", 2, 0, Plans.ENTERPRISE),

    // Organization Roles
    LIST_ROLES(Method.GET, "/organizations/{0}/roles", 1, 0, Plans.ENTERPRISE),
    ROLE_DETAILS(Method.GET, "/organizations/{0}/roles/{1}", 2, 0, Plans.ENTERPRISE),

    // Organization-level Firewall access rule
    LIST_ORGANIZATION_ACCESS_RULES(Method.GET, "/organizations/{0}/firewall/access_rules/rules", 1, 0, Plans.ENTERPRISE),
    CREATE_ORGANIZATION_ACCESS_RULE(Method.POST, "/organizations/{0}/firewall/access_rules/rules", 1, 2, Plans.ENTERPRISE),
    UPDATE_ORGANIZATION_ACCESS_RULE(Method.PATCH, "/organizations/{0}/firewall/access_rules/rules/{1}", 2, 0, Plans.ENTERPRISE),
    DELETE_ORGANIZATION_ACCESS_RULE(Method.DELETE, "/organizations/{0}/firewall/access_rules/rules/{1}", 2, 0, Plans.ENTERPRISE),

    // Organization Railgun
    CREATE_ORGANIZATION_RAILGUN(Method.POST, "/organizations/{0}/railguns", 1, 1, Plans.BUSINESS),
    LIST_ORGANIZATION_RAILGUNS(Method.GET, "/organizations/{0}/railguns", 1, 0, Plans.BUSINESS),
    ORGANIZATION_RAILGUN_DETAILS(Method.GET, "/organizations/{0}/railguns/{1}", 2, 0, Plans.BUSINESS),
    GET_ORGANIZATION_RAILGUNS_CONNECTED_TO_ZONE(Method.GET, "/organizations/{0}/railguns/{1}/zones", 2, 0, Plans.BUSINESS),
    ENABLE_OR_DISABLE_ORGANIZATION_RAILGUN(Method.PATCH, "/organizations/{0}/railguns/{1}", 2, 1, Plans.BUSINESS),
    DELETE_ORGANIZATION_RAILGUN(Method.DELETE, "/organizations/{0}/railguns/{1}", 2, 0, Plans.BUSINESS),

    // Cloudflare CA
    LIST_CERTIFICATES(Method.GET, "/certificates", 0, 0, Plans.FREE),
    CREATE_CERTIFICATE(Method.POST, "/certificates", 0, 0, Plans.FREE),
    CERTIFICATE_DETAILS(Method.GET, "/certificates/{0}", 1, 0, Plans.FREE),
    REVOKE_CERTIFICATE(Method.DELETE, "/certificates/{0}", 1, 0, Plans.FREE),

    // Virtual DNS (Users)
    GET_VIRTUAL_DNS_CLUSTERS_USER(Method.GET, "/user/virtual_dns", 0, 0, Plans.ENTERPRISE),
    CREATE_VIRTUAL_DNS_CLUSTER_USER(Method.POST, "/user/virtual_dns", 0, 2, Plans.ENTERPRISE),
    GET_VIRTUAL_DNS_CLUSTER_USER(Method.GET, "/user/virtual_dns/{0}", 1, 0, Plans.ENTERPRISE),
    DELETE_VIRTUAL_DNS_CLUSTER_USER(Method.DELETE, "/user/virtual_dns/{0}", 1, 0, Plans.ENTERPRISE),
    MODIFY_VIRTUAL_DNS_CLUSTER_USER(Method.PATCH, "/user/virtual_dns/{0}", 1, 0, Plans.ENTERPRISE),

    // Virtual DNS (Organizations)
    GET_VIRTUAL_DNS_CLUSTERS_ORGANIZATION(Method.GET, "/organizations/{0}/virtual_dns", 1, 0, Plans.ENTERPRISE),
    CREATE_VIRTUAL_DNS_CLUSTER_ORGANIZATION(Method.POST, "/organizations/{0}/virtual_dns", 1, 2, Plans.ENTERPRISE),
    GET_VIRTUAL_DNS_CLUSTER_ORGANIZATION(Method.GET, "/organizations/{0}/virtual_dns/{1}", 2, 0, Plans.ENTERPRISE),
    DELETE_VIRTUAL_DNS_CLUSTER_ORGANIZATION(Method.DELETE, "/organizations/{0}/virtual_dns/{1}", 2, 0, Plans.ENTERPRISE),
    MODIFY_VIRTUAL_DNS_CLUSTER_ORGANIZATION(Method.PATCH, "/organizations/{0}/virtual_dns/{1}", 2, 0, Plans.ENTERPRISE),

    // Virtual DNS Analytics (Users)
    VIRTUAL_DNS_ANALYTICS_TABLE_USER(Method.GET, "/user/virtual_dns/{0}/dns_analytics/report", 1, 4, Plans.NONE),
    VIRTUAL_DNS_ANALYTICS_BY_TIME_USER(Method.GET, "/user/virtual_dns/{0}/dns_analytics/report/bytime", 1, 0, Plans.NONE),

    // Virtual DNS Analytics (Organizations)
    VIRTUAL_DNS_ANALYTICS_TABLE_ORGANIZATION(Method.GET, "/organizations/{0}/virtual_dns/{1}/dns_analytics/report", 2, 4, Plans.ENTERPRISE),
    VIRTUAL_DNS_ANALYTICS_BY_TIME_ORGANIZATION(Method.GET, "/organizations/{0}/virtual_dns/{1}/dns_analytics/report/bytime", 2, 0, Plans.ENTERPRISE),

    // Cloudflare IPs
    CLOUDFLARE_IPS(Method.GET, "/ips", 0, 0, Plans.NONE),

    // AML
    GET_AML_SETTINGS(Method.GET, "/zones/{0}/amp/viewer", 1, 0, Plans.FREE),
    UPDATE_AML_SETTINGS(Method.PUT, "/zones/{0}/amp/viewer", 1, 0, Plans.FREE),

    // Load Balancer Monitors
    LIST_MONITORS(Method.GET, "/user/load_balancers/monitors", 0, 0, Plans.NONE),
    CREATE_MONITOR(Method.POST, "/user/load_balancers/monitors", 0, 0, Plans.NONE),
    MONITOR_DETAILS(Method.GET, "/user/load_balancers/monitors/{1}", 1, 0, Plans.NONE),
    DELETE_MONITOR(Method.DELETE, "/user/load_balancers/monitors/{1}", 1, 0, Plans.NONE),
    MODIFY_MONITOR(Method.PATCH, "/user/load_balancers/monitors/{1}", 1, 2, Plans.NONE),

    // Load Balancer Pools
    LIST_POOLS(Method.GET, "/user/load_balancers/pools", 0, 0, Plans.NONE),
    CREATE_POOL(Method.POST, "/user/load_balancers/pools", 0, 2, Plans.NONE),
    POOL_DETAILS(Method.GET, "/user/load_balancers/pools/{1}", 1, 0, Plans.NONE),
    DELETE_POOL(Method.DELETE, "/user/load_balancers/pools/{1}", 1, 0, Plans.NONE),
    MODIFY_POOL(Method.PUT, "/user/load_balancers/pools/{1}", 1, 2, Plans.NONE),

    // Organization Load Balancer Monitors
    LIST_ORGANIZATION_MONITORS(Method.GET, "/organizations/{0}/load_balancers/monitors", 1, 0, Plans.NONE),
    CREATE_ORGANIZATION_MONITOR(Method.POST, "/organizations/{0}/load_balancers/monitors", 1, 2, Plans.NONE),
    ORGANIZATION_MONITOR_DETAILS(Method.GET, "/organizations/{0}/load_balancers/monitors/{1}", 2, 0, Plans.NONE),
    DELETE_ORGANIZATION_MONITOR(Method.DELETE, "/organizations/{0}/load_balancers/monitors/{1}", 2, 0, Plans.NONE),
    MODIFY_ORGANIZATION_MONITOR(Method.PUT, "/organizations/{0}/load_balancers/monitors/{1}", 2, 2, Plans.NONE),

    // Organization Load Balancer Pools
    LIST_ORGANIZATION_POOLS(Method.GET, "/organizations/{0}/load_balancers/pools", 1, 0, Plans.NONE),
    CREATE_ORGANIZATION_POOL(Method.POST, "/organizations/{0}/load_balancers/pools", 1, 2, Plans.NONE)   ,
    ORGANIZATION_POOL_DETAILS(Method.GET, "/organizations/{0}/load_balancers/pools/{1}", 2, 0, Plans.NONE),
    DELETE_ORGANIZATION_POOL(Method.DELETE, "/organizations/{0}/load_balancers/pools/{1}", 2, 0, Plans.NONE),
    MODIFY_ORGANIZATION_POOL(Method.PUT, "/organizations/{0}/load_balancers/pools/{1}", 2, 2, Plans.NONE),

    // Load Balancers;
    LIST_LOAD_BALANCERS(Method.GET, "/zones/{0}/load_balancers", 1, 0, Plans.NONE),
    CREATE_LOAD_BALANCER(Method.POST, "/zones/{0}/load_balancers", 1, 3, Plans.NONE),
    LOAD_BALANCER_DETAILS(Method.GET, "/zones/{0}/load_balancers/{1}", 2, 0, Plans.NONE),
    DELETE_LOAD_BALANCER(Method.DELETE, "/zones/{0}/load_balancers/{1}", 2, 0, Plans.NONE),
    MODIFY_LOAD_BALANCER(Method.PUT, "/zones/{0}/load_balancers/{1}", 2, 3, Plans.NONE);

    @Getter
    private String relURL;

    @Getter
    private Method method;

    @Getter
    private int urlargc;

    @Getter
    private int dataargc;

    @Getter
    private Plans minPlan;

    public static final String masterPath = "https://api.cloudflare.com/client/v4/";
    public static CFAPIMap LOGIN = CFAPIMap.USER_DETAILS;

    CFAPIMap(Method method, String url, int urlargc, int dataargc, Plans min) {
        this.relURL = url;
        this.method = method;
        this.urlargc = urlargc;
        this.dataargc = dataargc;
    }

    public enum Method {
        POST,
        GET,
        PUT,
        DELETE,
        PATCH
    }
}
