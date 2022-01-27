import 'package:json_annotation/json_annotation.dart';
import 'package:meta/meta.dart';

@JsonSerializable()
class Characteristic {
  int id;
  String name;
  String description;
  int value;

  Characteristic({
      @required this.id,
      @required this.name,
      @required this.description,
      @required this.value
  });

  factory Characteristic.createFromData(Map data) => Characteristic(
      id: data['id'],
      name: data['name'],
      description: data['description'],
      value: data['value'],
  );
}
