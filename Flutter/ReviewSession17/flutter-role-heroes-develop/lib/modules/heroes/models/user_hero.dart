import 'package:role_heroes/models/attribute.dart';
import 'package:role_heroes/models/characteristic/characteristic.dart';
import 'package:role_heroes/modules/structural_attribute/models/structural_attribute.dart';

class UserHero {
  int id;
  String name;
  String note;
  List<Characteristic> characteristics;
  List<Attribute> attributes;
  List<StructuralAttribute> structuralAttributes;

  UserHero();

  factory UserHero.createFromData(Map data) {
    UserHero userHero = UserHero();
    userHero.id = data['id'];
    userHero.name = data['name'];
    userHero.note = data['note'];

    List<Characteristic> characteristics = [];
    if (data.containsKey('characteristics')) {
      for (final Map characteristicDatum in data['characteristics']) {
        characteristics.add(Characteristic.createFromData(characteristicDatum));
      }
    }
    userHero.characteristics = characteristics;

    List<Attribute> attributes = [];
    if (data.containsKey('attributes')) {
      for (final Map attributeDatum in data['attributes']) {
        attributes.add(Attribute.fromData(attributeDatum));
      }
    }
    userHero.attributes = attributes;

    List<StructuralAttribute> structuralAttributes = [];
    if (data.containsKey('structural_attributes')) {
      for (final Map attributeData in data['structural_attributes'])
        structuralAttributes.add(StructuralAttribute.fromData(attributeData));
    }
    userHero.structuralAttributes = structuralAttributes;

    return userHero;
  }
}
