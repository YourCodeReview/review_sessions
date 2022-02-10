class EnumHelper {
  static String getLabelEnum<T>(T enumValue) {
    return enumValue.toString().split('.').elementAt(1);
  }

  static String getSpecEnum<T>(T enumValue) {
    return EnumHelper.getLabelEnum(enumValue).toLowerCase();
  }
}