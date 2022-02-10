import 'package:meta/meta.dart';
import 'package:role_heroes/clients/api/client.dart';
import 'package:role_heroes/modules/heroes/models/user_hero.dart';
import 'package:role_heroes/modules/structural_attribute/models/structural_attribute.dart';
import 'package:role_heroes/modules/structural_attribute/models/structural_value.dart';
import 'package:role_heroes/repository/structural_attribute.dart';
import 'package:role_heroes/utils/secure_storages.dart';

class StructuralAttributeRepository implements IStructuralAttributeRepository {
  final Client apiClient;
  final AccessTokenStorage accessTokenStorage;

  StructuralAttributeRepository({
    @required this.apiClient,
    @required this.accessTokenStorage
  });

  @override
  Future<List<StructuralValue>> getValues(StructuralAttribute attribute) async {
    final List data = await this.apiClient.getStructureAttributeValues(
      await this.accessTokenStorage.getValue(),
      attribute.id,
    );
    final List<StructuralValue> result = [];
    for (final Map datum in data)
      result.add(StructuralValue.fromData(datum));
    return result;
  }

  @override
  Future updateValues(UserHero userHero, StructuralAttribute attribute) async {
    await this.apiClient.setStructuralAttributeValues(
      await this.accessTokenStorage.getValue(),
      userHero.id,
      attribute.id,
      attribute.values.map<int>((StructuralValue value) => value.id).toList(),
    );
  }
}
