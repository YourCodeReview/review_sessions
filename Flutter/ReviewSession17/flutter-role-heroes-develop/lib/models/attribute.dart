import 'package:flutter/material.dart';
import 'package:role_heroes/models/category/category.dart';
import 'package:role_heroes/utils/value_types.dart';

class Attribute {
  int id;
  String name;

  IValueType type;
  dynamic _value;
  Category category;

  Attribute({
    @required this.id,
    @required this.name,
    @required this.type,
    @required dynamic value,
    @required this.category,
  }) {
    _value = value;
  }

  get value {
    var result;
    if (_value != null)
      result = type.convertValue(_value.toString());
    return result;
  }

  set value(dynamic value) {
    _value = value;
  }

  factory Attribute.fromData(Map data) => Attribute(
    id: data['id'] as int,
    name: data['name'] as String,
    type: ValueTypeFactory.getTypeByName(data['type']),
    value: data['value'],
    category: Category.fromData(data['category']),
  );
}
