package en.builin.qna.utils;

public class WebUtils {

    public static final String URL_INDEX = "/";
    public static final String URL_SIGN_IN = "/login";
    public static final String URL_SIGN_UP = "/registration";
    public static final String URL_SIGN_OUT = "/sign-out";
    public static final String URL_PROFILE = "/profile";
    public static final String URL_QUESTIONS = "/questions";
    public static final String URL_QUESTION_PAGE = "/q";
    public static final String URL_ADD_QUESTION = "/add-question";
    public static final String URL_TOPICS = "/t";
    public static final String URL_EDIT_TOPICS = "/edit-topics";

    private WebUtils() {}

    public static String getUrlTitleById(String name, Long id) {
        if (name == null || name.isBlank()) {
            return id.toString();
        } else {
            return TransliteratorUtils.transliterate(name)
                    .toLowerCase()
                    .trim()
                    .replaceAll("[^a-zA-ZА-Яа-я0-9 ]", "")
                    .replace(" ", "-")
                    + "-"
                    + id;
        }
    }

    public static Long getIdFromUrl(String topicUrlId) {
        String[] urlParts = topicUrlId.split("-");
        return Long.valueOf(urlParts[urlParts.length - 1]);
    }
}
