import 'package:meta/meta.dart';
import 'package:role_heroes/utils/value_types.dart';

class StructuralField {
  String name;
  IValueType type;

  StructuralField({
    @required this.name,
    @required this.type,
  });

  factory StructuralField.fromData(Map data) => StructuralField(
    name: data['name'] as String,
    type: ValueTypeFactory.getTypeByName(data['type']),
  );
}
