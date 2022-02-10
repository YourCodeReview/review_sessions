import 'package:flutter/material.dart';
import 'package:role_heroes/models/category/category.dart';

class CategoryTab extends Tab {
  final Category category;

  CategoryTab({@required this.category}) : super(child: Text(category.name));
}
