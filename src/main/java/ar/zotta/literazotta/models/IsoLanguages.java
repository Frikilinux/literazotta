package ar.zotta.literazotta.models;

public class IsoLanguages {
  Boolean success;
  Language data;

  public Language getData() {
    return data;
  }

  public Boolean getSuccess() {
    return success;
  }

  public class Language {
    private String language;

    private String code;

    public String getLanguage() {
      return language;
    }

    public String getCode() {
      return code;
    }
  }

}
