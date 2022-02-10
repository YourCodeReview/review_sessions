import 'package:role_heroes/models/attribute.dart';
import 'package:role_heroes/models/characteristic/characteristic.dart';
import 'package:role_heroes/modules/heroes/models/user_hero.dart';

abstract class IUserHeroesRepository {
  Future<List<UserHero>> getListByGame(int gameId);
  Future<UserHero> getDetail(int heroId);
  Future create(Map data);
  Future<bool> delete(int heroId);
  Future<bool> update(UserHero hero, Map data);
}

abstract class IUserHeroDataRepository {
  Future updateCharacteristic(UserHero hero, Characteristic characteristic);
  Future updateAttribute(UserHero hero, Attribute attribute);
}
