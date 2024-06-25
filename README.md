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
### Auto structuring 
 - Auto Structuring Increment Down Payment: 
   - If the application is under manual review and the credit score is between 600 and 700, increase the down payment and extend the loan term.
 - Auto Structuring Decrease Monthly Payment:
   - Decrease the monthly payment and extend the loan term for applications under manual review with a credit score between 600 and 700. 
 - Auto Structuring Reduce Interest Rate:
   - Lower the interest rate and adjust loan terms for applications under manual review with a credit score between 600 and 700. 
 - Auto Structuring for High Credit Score and Low DTI:
   - Adjust loan terms favorably for applications under manual review with a credit score of 700 or above and a low DTI ratio (<= 0.3).