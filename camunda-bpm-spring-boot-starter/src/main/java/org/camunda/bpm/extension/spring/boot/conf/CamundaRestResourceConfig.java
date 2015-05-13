package org.camunda.bpm.extension.spring.boot.conf;

import org.camunda.bpm.engine.rest.impl.CamundaRestResources;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by hawky4s on 07.02.15.
 */
public class CamundaRestResourceConfig extends ResourceConfig {

    public CamundaRestResourceConfig() {
        registerClasses(CamundaRestResources.getResourceClasses());
        registerClasses(CamundaRestResources.getConfigurationClasses());
    }

}
