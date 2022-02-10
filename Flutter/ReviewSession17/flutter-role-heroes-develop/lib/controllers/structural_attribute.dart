import 'package:role_heroes/modules/heroes/models/user_hero.dart';
import 'package:role_heroes/modules/structural_attribute/models/structural_attribute.dart';
import 'package:role_heroes/modules/structural_attribute/models/structural_value.dart';

abstract class IStructuralAttributeController {
  Future<List<StructuralValue>> getValues(StructuralAttribute attribute);
  Future setUserHeroStructuralValues(UserHero userHero, StructuralAttribute attribute);
}
