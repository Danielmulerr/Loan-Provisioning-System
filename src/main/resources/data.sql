-- Insert data for USERS
INSERT INTO USERS (UUID, FIRST_NAME, LAST_NAME, EMAIL, PHONE, CREATION_DATE, LAST_UPDATE_DATE)
VALUES
('uuid-1234', 'John', 'Doe', 'john.doe@example.com', '1234567890', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('uuid-5678', 'Jane', 'Smith', 'jane.smith@example.com', '0987654321', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('uuid-9101', 'Jim', 'Beam', 'jim.beam@example.com', '1122334455', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('uuid-1121', 'Jill', 'Stark', 'jill.stark@example.com', '2233445566', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert data for CREDIT_SCORE
INSERT INTO CREDIT_SCORE (USER_ID, CREDIT_SCORE, DTI_RATIO, MAX_DTI_RATIO, CHANGE_OF_ADDRESS, CHANGE_OF_ADDRESS_PERIOD, LATE_RENTAL_PAYMENTS, LATE_RENTAL_PAYMENTS_PERIOD, LATE_CELL_PHONE_PAYMENTS, LATE_CELL_PHONE_PAYMENTS_PERIOD, SCORE_DATE, CREDIT_BUREAU_SOURCE, LAST_UPDATE_DATE)
VALUES
(1, 800, 0.25, 0.36, 0, 0, 0, 0, 0, 0, CURRENT_TIMESTAMP, 'Experian', CURRENT_TIMESTAMP), -- Excellent credit score
(2, 650, 0.35, 0.36, 1, 12, 2, 12, 1, 12, CURRENT_TIMESTAMP, 'Equifax', CURRENT_TIMESTAMP), -- Average credit score with late payments
(3, 500, 0.45, 0.36, 2, 24, 5, 24, 3, 24, CURRENT_TIMESTAMP, 'TransUnion', CURRENT_TIMESTAMP), -- Poor credit score with multiple issues
(4, 700, 0.30, 0.36, 0, 0, 0, 0, 0, 0, CURRENT_TIMESTAMP, 'Experian', CURRENT_TIMESTAMP); -- Good credit score
-- Insert data for LOAN_APPLICATION
INSERT INTO LOAN_APPLICATION (USER_ID, LOAN_AMOUNT_REQUESTED, TERM, PURPOSE, BANK_ACCOUNT_NUMBER, STATUS, APPLICATION_DATE, APPROVAL_DATE, DISBURSEMENT_DATE, LAST_UPDATE_DATE, OUTSTANDING_BALANCE, TOTAL_INTEREST_PAID)
VALUES
-- Auto-approval scenario (Excellent credit score)
(1, 10000, 24, 'Home Renovation', '12345678', 'PENDING', CURRENT_TIMESTAMP, NULL, NULL, CURRENT_TIMESTAMP, 0, 0),

-- Manual approval scenario (Average credit score with late payments)
(2, 5000, 36, 'Car Loan', '87654321', 'PENDING', CURRENT_TIMESTAMP, NULL, NULL, CURRENT_TIMESTAMP, 0, 0),

-- Auto-rejection scenario (Poor credit score with multiple issues)
(3, 2000, 12, 'Personal Loan', '12348765', 'PENDING', CURRENT_TIMESTAMP, NULL, NULL, CURRENT_TIMESTAMP, 0, 0),

-- Auto-restructuring scenario (Good credit score but high DTI ratio)
(4, 15000, 48, 'Business Expansion', '87651234', 'PENDING', CURRENT_TIMESTAMP, NULL, NULL, CURRENT_TIMESTAMP, 0, 0),

-- Scenario for outstanding balance update
(1, 2000, 12, 'Vacation', '12345678', 'PENDING', CURRENT_TIMESTAMP, NULL, NULL, CURRENT_TIMESTAMP, 0, 0),

-- Scenario for loan fully repaid
(2, 1000, 6, 'Emergency Fund', '87654321', 'PENDING', CURRENT_TIMESTAMP, NULL, NULL, CURRENT_TIMESTAMP, 0, 0),

-- Scenario for new loan application pending
(3, 3000, 24, 'Debt Consolidation', '12348765', 'PENDING', CURRENT_TIMESTAMP, NULL, NULL, CURRENT_TIMESTAMP, 0, 0);
