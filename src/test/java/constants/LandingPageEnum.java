package constants;

public enum LandingPageEnum {

    ENTER_SEARCH_PRODUCT ("//input[@id='twotabsearchtextbox']"),
    CLICK_ON_SEARCH_BTN ("//input[@id='nav-search-submit-button']"),


    ;

    public String desc;
    LandingPageEnum(String description) {
        this.desc = description;
    }

    public  String getLocator() {
        return desc;
    }
}
