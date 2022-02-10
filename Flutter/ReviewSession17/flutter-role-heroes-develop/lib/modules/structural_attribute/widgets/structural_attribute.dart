import 'package:flutter/material.dart';
import 'package:role_heroes/constants.dart';
import 'package:role_heroes/modules/heroes/models/user_hero.dart';
import 'package:role_heroes/modules/structural_attribute/models/structural_attribute.dart';
import 'package:role_heroes/modules/structural_attribute/widgets/structural_attribute_content.dart';

class StructuralAttributeWidget extends StatefulWidget {
  final UserHero hero;
  final StructuralAttribute attribute;

  const StructuralAttributeWidget({
    Key key,
    @required this.hero,
    @required this.attribute,
  }) : super(key: key);

  @override
  _StructuralAttributeWidgetState createState() => _StructuralAttributeWidgetState();
}

class _StructuralAttributeWidgetState extends State<StructuralAttributeWidget> {
  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.symmetric(horizontal: gDefaultMargin, vertical: gDefaultMargin / 2),
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(12),
        color: Colors.white,
      ),
      child: Padding(
        padding: EdgeInsets.all(gDefaultPadding),
        child: StructuralAttributeContent(
          hero: widget.hero,
          attribute: widget.attribute,
        ),
      ),
    );
  }
}
