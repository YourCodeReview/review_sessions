import 'package:meta/meta.dart';
import 'package:role_heroes/modules/structural_attribute/models/structural_field_value.dart';

class StructuralValue {
  int id;
  String name;
  String description;
  List<StructuralFieldValue> fields;

  StructuralValue({
    @required this.id,
    @required this.name,
    @required this.description,
    @required this.fields,
  });

  factory StructuralValue.fromData(Map data) {
    List<StructuralFieldValue> fields = [];
    if (data.containsKey('fields')) {
      for (final Map fieldData in data['fields'])
        fields.add(StructuralFieldValue.fromData(fieldData));
    }

    return StructuralValue(
      id: data['id'] as int,
      name: data['name'] as String,
      description: data['description'] as String,
      fields: fields,
    );
  }

  @override
  bool operator ==(Object other) {
    if (other.runtimeType == StructuralValue)
      return id == (other as StructuralValue).id;
    return super == other;
  }

  @override
  int get hashCode => id;
}
