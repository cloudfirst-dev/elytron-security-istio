package dev.cloudfirst.quarkus.elytron.security.istio.deployment;

import java.util.Optional;

import org.wildfly.security.auth.server.SecurityRealm;

import io.quarkus.arc.deployment.BeanContainerBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.elytron.security.deployment.SecurityRealmBuildItem;
import dev.cloudfirst.quarkus.elytron.security.istio.IstioSecurityRealmConfig;
import io.quarkus.runtime.RuntimeValue;

class ElytronSecurityIstioProcessor {
    IstioSecurityRealmConfig istio;
    
    @BuildStep(providesCapabilities = "dev.cloudfirst.quarkus.elytron.security.istio")
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FeatureBuildItem.SECURITY_JDBC);
    }

    /**
     * Check to see if a JdbcRealmConfig was specified and enabled and create a
     * {@linkplain org.wildfly.security.auth.realm.JdbcSecurityRealmConfig}
     * runtime value to process the user/roles properties files. This also registers the names of the user/roles properties
     * files
     * to include the build artifact.
     *
     * @param recorder - runtime security recorder
     * @param securityRealm - the producer factory for the SecurityRealmBuildItem
     * @param dataSourceInitialized - ensure that Agroal DataSource is initialized first
     * @throws Exception - on any failure
     */
    @BuildStep
    @Record(ExecutionTime.STATIC_INIT)
    void configureJdbcRealmAuthConfig(BuildProducer<SecurityRealmBuildItem> securityRealm,
            BeanContainerBuildItem beanContainerBuildItem) throws Exception {
        if (istio.enabled) {
            RuntimeValue<SecurityRealm> realm = recorder.createRealm(istio);
            securityRealm.produce(new SecurityRealmBuildItem(realm, istio.realmName, null));
        }
    }
}
