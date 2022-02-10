import 'package:flutter/material.dart';

abstract class IValueType {
  convertValue(String value);
  String getNameType();
  TextInputType getInputType();
}

abstract class ValueTypeFactory {

  static final Map<String, IValueType> _cacheForFactory = <String, IValueType>{
    IntType.nameType: IntType(),
    StringType.nameType: StringType(),
    BooleanType.nameType: BooleanType(),
    DoubleType.nameType: DoubleType(),
  };

  static getTypeByName(String nameType) {
    return _cacheForFactory[nameType];
  }
}

class IntType implements IValueType {
  static String nameType = 'int';

  @override
  int convertValue(String value) {
    return int.parse(value, radix: 10, onError: (e) => null);
  }

  @override
  String getNameType() => IntType.nameType;

  @override
  TextInputType getInputType() {
    return TextInputType.number;
  }
}

class StringType implements IValueType {
  static String nameType = 'string';

  @override
  String convertValue(String value) {
    return value;
  }

  @override
  String getNameType() => StringType.nameType;

  @override
  TextInputType getInputType() {
    return TextInputType.text;
  }
}

class BooleanType implements IValueType {
  static String nameType = 'bool';

  @override
  bool convertValue(String value) {
    bool result = false;
    if (value != null)
      result = value.length > 0 ? true : false;
    return result;
  }

  @override
  String getNameType() => BooleanType.nameType;

  @override
  TextInputType getInputType() {
    return TextInputType.number;
  }
}

class DoubleType implements IValueType {
  static String nameType = 'double';

  @override
  double convertValue(String value) {
    return double.parse(value, (e) => null);
  }

  @override
  String getNameType() => DoubleType.nameType;


  @override
  TextInputType getInputType() {
    return TextInputType.numberWithOptions(decimal: true);
  }
}
