package com.example.loanprovisioning.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.HashMap;
import java.util.List;

@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "loan")
public record ValidationRulesConfig(
        @NestedConfigurationProperty
        HashMap<String, RuleConfig> validationRules,
        @NestedConfigurationProperty
        AlternativeCreditDataRules alternativeCreditDataRules,
        @NestedConfigurationProperty
        AutoStructuringRules autoStructuringRules,
        List<String> processingPipeline
) {
}
