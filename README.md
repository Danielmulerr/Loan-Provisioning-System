# Loan Provisioning System

## Version

- Spring boot 3.3.1
- Java 17(LTS)

## How to run?

- Make sure you have mysql database server running in your machine
- Create **loan_provisioning_system** database
- Open the code base on intellj IDE
- Load it as a maven project
- Install the maven dependency(use Maven pallet on the right side intelije)
-

## Business rule: assumptions

### Rules for Drools Rule Engine

- Credit Score Rules:
    - Thresholds determine immediate approval, manual review, or auto-decline. Borderline scores trigger further
      evaluation with alternative data.
- Alternative Credit Data Rules:
- Decline if:
    - 3+ address changes in 3 years.
    - 4+ late rental payments in 24 months.
    - 1+ late cell phone payments in 12 months.
    - Use alternative data (utility, rental history) to refine decisions.
- Auto Structuring Rules:
    - Increment down payment by $500.
    - Reduce monthly payment by 5% with a DTI cap of 15%.
    - Increase loan term by 3-month increments up to 84 months.
    - Decrease monthly payment by $50 while increasing interest rate by 0.25%.

#### Loan Origination Business Rules:

Our rule engine uses Drools to automate loan qualification and structuring:

- Credit Score Rules:
    - Set thresholds for automatic approval, manual review, or decline. Alternative Credit Data:
    - Evaluate additional data like utility and rental payments to refine credit decisions.
    - Decline conditions based on address changes and late payments.
- Auto Structuring:
    - Adjust loan terms (downpayment, monthly payments, loan term, interest rate) to meet credit policies.

### Auto structuring

- Auto Structuring Increment Down Payment:
    - If the application is under manual review and the credit score is between 600 and 700, increase the down payment
      and extend the loan term.
- Auto Structuring Decrease Monthly Payment:
    - Decrease the monthly payment and extend the loan term for applications under manual review with a credit score
      between 600 and 700.
- Auto Structuring Reduce Interest Rate:
    - Lower the interest rate and adjust loan terms for applications under manual review with a credit score between 600
      and 700.
- Auto Structuring for High Credit Score and Low DTI:
    - Adjust loan terms favorably for applications under manual review with a credit score of 700 or above and a low DTI
      ratio (<= 0.3).

## **Features to note**

- Rule based loan application provisioning and restructuring
    - rule defined at **/resources/rules/loan-rules.drl**
- Made mock simulation for real bank calls using feign client
    - used for disbursement
    - used for loan repayment for user to organization
- Logging and Audit trail
    - used third party api to do the **geolocation** based on the request ip
    - used declarative feign client for the integration
    - this includes audit trail for every api call and record the action
    - Helpful to monitor and take action based on user behavior etc.
 - Multithreading when calling multiple data points(external service)
    - this will reduce latency as we can fetch data form different endpoint at the same time
 - **Used configuration** for values that are used in program
  -  
- Notification service with email
    - Send email to users(not working as we need SMTP server for this)
        - in case of loan approval notification
        - disbursement notification
        - repayment confirmation
- Clean and maintainable code, that is aligned with principles
## Documentation
- api documentation through swagger
- http://localhost:8595/swagger-ui/index.html

## Metrics
- Configured custome metric under /app/customeMetric
- Prometheus metric configured