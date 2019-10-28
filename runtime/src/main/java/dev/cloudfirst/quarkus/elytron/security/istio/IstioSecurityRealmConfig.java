package dev.cloudfirst.quarkus.elytron.security.istio;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

@ConfigRoot(name = "security.jdbc", phase = ConfigPhase.BUILD_AND_RUN_TIME_FIXED)
public class IstioSecurityRealmConfig {
    /**
     * The realm name
     */
    @ConfigItem(defaultValue = "Quarkus")
    public String realmName;

    /**
     * If the properties store is enabled.
     */
    @ConfigItem
    public boolean enabled;

    @Override
    public String toString() {
        return "JdbcRealmConfig{" +
                ", realmName='" + realmName + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
