import 'package:meta/meta.dart';
import 'package:role_heroes/models/category/category.dart';
import 'package:role_heroes/modules/structural_attribute/models/structural_field.dart';
import 'package:role_heroes/modules/structural_attribute/models/structural_value.dart';

class StructuralAttribute {
  int id;
  String name;
  String description;
  bool multiply;
  Category category;
  List<StructuralField> fields;
  List<StructuralValue> values;

  StructuralAttribute({
    @required this.id,
    @required this.name,
    @required this.description,
    @required this.multiply,
    @required this.category,
    @required this.fields,
    @required this.values,
  });

  factory StructuralAttribute.fromData(Map data) {
    List<StructuralField> fields = [];
    if (data.containsKey('fields')) {
      for (final Map fieldData in data['fields'])
        fields.add(StructuralField.fromData(fieldData));
    }

    List<StructuralValue> values = [];
    if (data.containsKey('selected_values')) {
      for (final Map valueData in data['selected_values'])
        values.add(StructuralValue.fromData(valueData));
    }

    return StructuralAttribute(
      id: data['id'] as int,
      name: data['name'] as String,
      description: data['description'] as String,
      multiply: data['multiply'] as bool,
      category: Category.fromData(data['category']),
      fields: fields,
      values: values,
    );
  }

  selectedValue(StructuralValue value) {
    bool selected = this.values.contains(value);
    if (this.multiply) {
      if (selected)
        this.values.remove(value);
      else
        this.values.add(value);
    } else {
      this.values = selected ? [] : [value];
    }
  }
}
