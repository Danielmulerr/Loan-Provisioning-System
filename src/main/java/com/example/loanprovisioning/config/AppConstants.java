package com.example.loanprovisioning.config;

public class AppConstants {
    public static final String TEXT_HTML = "text/html";
    public static final String NAME_PATTERN = "^[A-Za-z]+(?:\\s[A-Za-z]+)?$";
    public static final String PHONE_PATTERN = "^(\\+\\d{1,4})?[-.\\s]?\\(?\\d{1,6}\\)?[-.\\s]?\\d{1,10}$";
    public static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String URL_PATTERN = "^(https?|ftp):\\/\\/(www\\.)?([a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+)(:[0-9]+)?(\\/[^\\s]*)?$";
    public static final String URL_PATH_PATTERN = "^\\/[^\\s?#]+(?:\\?[^\\s#]*)?(?:#[^\\s]*)?$";
    public static final String LOG_PREFIX = "Core application {} {}";
    public static final String ILLEGAL_CHARACTERS_REGEX = "[<>:\"/\\|?*]";
    public static final int READ_TIMEOUT = 15;
    public static final int CONNECT_TIMEOUT = 10;
    public static final long ONE_YEAR_IN_SECONDS = 31_536_000;
    public static final long ONE_DAY_IN_SECONDS = 86_400;
    public static final long CACHE_EXPIRY_TIME = 600000;//Ten minutes
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String LAST_UPDATE_DATE_DESC = "lastUpdateDate.desc";
    public static final String EXCEPTION_ON_PREPARING_SMTP_PROPS = "Exception while preparing SMTP properties";
    public static final String EXCEPTION_ON_FORMATTING_EMAIL = "Exception while formatting email message";
    public static final String EXCEPTION_ON_SENDING_EMAIL = "Exception while sending email.";
    public static final int START_YEAR = 2023;
    public static final int END_YEAR = 4099;

}
