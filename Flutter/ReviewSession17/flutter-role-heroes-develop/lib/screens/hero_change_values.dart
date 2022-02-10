import 'package:flutter/material.dart';
import 'package:role_heroes/controllers/structural_attribute.dart';
import 'package:role_heroes/modules/heroes/models/user_hero.dart';
import 'package:role_heroes/modules/structural_attribute/models/structural_attribute.dart';
import 'package:role_heroes/modules/structural_attribute/widgets/structural_attribute_values_select.dart';

class HeroChangeStructuralValues extends StatelessWidget {
  static String routeName = '/structural_attribute/select_values';

  final IStructuralAttributeController controller;

  const HeroChangeStructuralValues({
    Key key,
    @required this.controller,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final UserHero hero = (ModalRoute.of(context).settings.arguments as Map)['hero'] as UserHero;
    final StructuralAttribute attribute = (ModalRoute.of(context).settings.arguments as Map)['attribute'] as StructuralAttribute;
    return StructuralAttributeValuesSelect(
      hero: hero,
      attribute: attribute,
      controller: controller,
    );
  }
}
