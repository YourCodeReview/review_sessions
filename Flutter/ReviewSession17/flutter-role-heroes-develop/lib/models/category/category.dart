import 'package:json_annotation/json_annotation.dart';
import 'package:meta/meta.dart';

@JsonSerializable()
class Category {
  int id;
  String name;

  Category({
    this.id,
    @required this.name,
  });

  factory Category.fromData(Map data) => Category(
    id: data['id'],
    name: data['name'],
  );

  @override
  bool operator ==(Object other) {
    if (other.runtimeType == Category)
      return id == (other as Category).id;
    return super == other;
  }

  @override
  int get hashCode => id;
}
