package en.builin.qna.security;

import en.builin.qna.utils.WebUtils;

public class SecurityUtils {

    public static final String REMEMBER_ME_KEY = "9FBB5793";
    public static final String REMEMBER_ME_PARAMETER = "rememberMe";
    public static final int TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 365;

    public static final String[] PERMITED_URLS = {
            WebUtils.URL_INDEX,
            WebUtils.URL_SIGN_IN,
            WebUtils.URL_SIGN_UP,
            WebUtils.URL_QUESTION_PAGE + "/**",
            WebUtils.URL_QUESTIONS,
            WebUtils.URL_QUESTIONS + "/**",
            WebUtils.URL_TOPICS + "/**"
    };

    public static final String[] AUTHENTICATED_URLS = {
            WebUtils.URL_SIGN_OUT,
            WebUtils.URL_ADD_QUESTION,
            WebUtils.URL_PROFILE
    };

    public static final String[] ADMIN_URLS = {
            WebUtils.URL_EDIT_TOPICS + "/**"
//            "/projects"
    };

    public static final String[] MODERATOR_URLS = {
            "/users"
//            "/projects"
    };

    private SecurityUtils() {}
}
