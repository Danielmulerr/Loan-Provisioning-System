//package com.example.loanprovisioning.config.initializer;
//
//import com.example.loanprovisioning.config.properties.ValidationRulesConfig;
//import com.example.loanprovisioning.entity.ValidationRule;
//import com.example.loanprovisioning.repository.ValidationRuleRepository;
//import jakarta.annotation.PostConstruct;
//import lombok.val;
//import org.springframework.context.ApplicationContext;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//public class LoanApplicationInitializer {
//
//    private final ValidationRulesConfig validationRuleConfig;
//    private final ValidationRuleRepository ruleRepository;
//    private final ApplicationContext applicationContext;
//
//    public LoanApplicationInitializer(ValidationRulesConfig validationRuleConfig, ValidationRuleRepository ruleRepository, ApplicationContext applicationContext) {
//        this.validationRuleConfig = validationRuleConfig;
//        this.ruleRepository = ruleRepository;
//        this.applicationContext = applicationContext;
//    }
//
//
//    @PostConstruct
//    public void init() {
//        val configRules = validationRuleConfig.validationRules();
//        List<ValidationRule> rules = configRules
//                .values()
//                .parallelStream()
//                .map(config -> {
//                    ValidationRule rule = new ValidationRule();
//                    rule.setMinCreditScore(config.minCreditScore());
//                    rule.setMaxDTIRatio(config.maxDTIRatio());
//                    rule.setAdditionalValidationsRequired(config.additionalValidationsRequired());
//                    rule.setApprovalStrategy(config.approvalStrategy());
//                    return rule;
//                }).collect(Collectors.toList());
//        ruleRepository.saveAll(rules);
//    }
//}
