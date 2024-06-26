-- Insert data for USERS
INSERT INTO USERS (UUID, FIRST_NAME, LAST_NAME, EMAIL, PHONE, CREATION_DATE, LAST_UPDATE_DATE)
VALUES
('uuid-1234', 'John', 'Doe', 'john.doe@example.com', '1234567890', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('uuid-5678', 'Jane', 'Smith', 'jane.smith@example.com', '0987654321', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('uuid-9101', 'Jim', 'Beam', 'jim.beam@example.com', '1122334455', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('uuid-11221', 'Jill', 'Stark', 'jill2.stark@example.com', '2333445566', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('uuid-12321', 'Jill', 'Stark', 'jil3.stark@example.com', '2243445566', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('uuid-13421', 'Jill', 'Stark', 'jil4.stark@example.com', '2253445566', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('uuid-14521', 'Jill', 'Stark', 'jil6.stark@example.com', '2263445566', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('uuid-13621', 'Jill', 'Stark', 'ji2ll.stark@example.com', '2733445566', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO CREDIT_SCORE (USER_ID, CREDIT_SCORE, DTI_RATIO, MAX_DTI_RATIO, CHANGE_OF_ADDRESS, CHANGE_OF_ADDRESS_PERIOD, LATE_RENTAL_PAYMENTS, LATE_RENTAL_PAYMENTS_PERIOD, LATE_CELL_PHONE_PAYMENTS, LATE_CELL_PHONE_PAYMENTS_PERIOD, SCORE_DATE, CREDIT_BUREAU_SOURCE, LAST_UPDATE_DATE)
VALUES
(1, 800, 0.25, 0.36, 0, 0, 0, 0, 0, 0, CURRENT_TIMESTAMP, 'Experian', CURRENT_TIMESTAMP), -- Excellent credit score
(2, 650, 0.35, 0.36, 1, 12, 2, 12, 1, 12, CURRENT_TIMESTAMP, 'Equifax', CURRENT_TIMESTAMP), -- Average credit score with late payments
(3, 500, 0.45, 0.36, 2, 24, 5, 24, 3, 24, CURRENT_TIMESTAMP, 'TransUnion', CURRENT_TIMESTAMP), -- Poor credit score with multiple issues
(4, 700, 0.30, 0.36, 0, 0, 0, 0, 0, 0, CURRENT_TIMESTAMP, 'Experian', CURRENT_TIMESTAMP), -- Good credit score
(5, 620, 0.42, 0.36, 2, 12, 3, 18, 0, 0, CURRENT_TIMESTAMP, 'Equifax', CURRENT_TIMESTAMP); -- Credit score for manual review

INSERT INTO LOAN_APPLICATION (USER_ID, LOAN_AMOUNT_REQUESTED, TERM, PURPOSE, BANK_ACCOUNT_NUMBER, STATUS, APPLICATION_DATE, LAST_UPDATE_DATE, OUTSTANDING_BALANCE,TOTAL_INTEREST_PAID )
VALUES
(1, 25000.0, 36, 'Car Purchase', '1234567890', 'PENDING', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 25000.0 * 1.01, 0.0), -- Application for automatic approval
(2, 30000.0, 48, 'Home Improvement', '9876543210', 'PENDING', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 30000.0 * 1.01, 0.0), -- Application for manual approval
(3, 20000.0, 24, 'Debt Consolidation', '2468013579', 'PENDING', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 20000.0 * 1.01, 0.0), -- Application for rejection due to poor credit score
(4, 35000.0, 60, 'Business Expansion', '1357924680', 'PENDING', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 35000.0 * 1.01, 0.0), -- Application for decline due to alternative credit data
(5, 28000.0, 36, 'Vacation', '8642097531', 'PENDING', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 28000.0 * 1.01, 0.0), -- Application for auto structuring (increment down payment)
(6, 40000.0, 60, 'Education', '9753108642', 'PENDING', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 40000.0 * 1.01, 0.0), -- Application for auto structuring (decrease monthly payment)
(7, 30000.0, 48, 'Medical Expenses', '5310864297', 'PENDING', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 30000.0 * 1.01, 0.0), -- Application for auto structuring (reduce interest rate)
(8, 32000.0, 60, 'Wedding', '8642097531', 'PENDING', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 32000.0 * 1.01, 0.0); -- Application for high credit score and low DTI ratio