import 'package:meta/meta.dart';
import 'package:role_heroes/controllers/structural_attribute.dart';
import 'package:role_heroes/modules/heroes/models/user_hero.dart';
import 'package:role_heroes/modules/structural_attribute/models/structural_attribute.dart';
import 'package:role_heroes/modules/structural_attribute/models/structural_value.dart';
import 'package:role_heroes/repository/structural_attribute.dart';

class StructuralAttributeController implements IStructuralAttributeController {
  final IStructuralAttributeRepository repository;

  StructuralAttributeController({
    @required this.repository,
  });

  @override
  Future<List<StructuralValue>> getValues(StructuralAttribute attribute) {
    return repository.getValues(attribute);
  }

  @override
  Future setUserHeroStructuralValues(UserHero userHero, StructuralAttribute attribute) {
    return repository.updateValues(userHero, attribute);
  }
}
