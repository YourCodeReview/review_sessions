import 'package:meta/meta.dart';
import 'package:role_heroes/utils/value_types.dart';

class StructuralFieldValue {
  String name;
  dynamic _value;
  IValueType type;

  StructuralFieldValue({
    @required this.name,
    @required dynamic value,
    @required this.type
  }) {
    _value = value;
  }

  // TODO create mixin for value with IValueType
  get value {
    var result;
    if (_value != null)
      result = type.convertValue(_value.toString());
    return result;
  }

  set value(dynamic value) {
    _value = value;
  }

  factory StructuralFieldValue.fromData(Map data) => StructuralFieldValue(
    name: data['name'],
    value: data['value'],
    type: ValueTypeFactory.getTypeByName(data['type']),
  );
}
