package org.apereo.cas.support.x509.rest.config;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.rest.factory.ChainingRestHttpRequestCredentialFactory;
import org.apereo.cas.rest.factory.RestHttpRequestCredentialFactory;
import org.apereo.cas.rest.plan.RestHttpRequestCredentialFactoryConfigurer;
import org.apereo.cas.support.x509.rest.X509RestHttpRequestCredentialFactory;
import org.apereo.cas.web.extractcert.X509CertificateExtractor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dmytro Fedonin
 * @since 5.1.0
 */
@Configuration("x509RestConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class X509RestConfiguration implements RestHttpRequestCredentialFactoryConfigurer {
    
    @Autowired
    private CasConfigurationProperties casProperties;
    
    @Autowired
    @Qualifier("x509CertificateExtractor")
    private ObjectProvider<X509CertificateExtractor> x509CertificateExtractor;

    @Bean
    public RestHttpRequestCredentialFactory insecureX509CredentialFactory() {
        return new X509RestHttpRequestCredentialFactory(x509CertificateExtractor.getIfAvailable(), casProperties.getRestProperties().isX509insecure());
    }

    @Override
    public void configureCredentialFactory(final ChainingRestHttpRequestCredentialFactory factory) {
        factory.registerCredentialFactory(x509CredentialFactory());
    }
}
