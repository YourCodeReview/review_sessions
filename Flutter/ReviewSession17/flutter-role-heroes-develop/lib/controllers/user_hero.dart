import 'package:role_heroes/models/attribute.dart';
import 'package:role_heroes/models/characteristic/characteristic.dart';
import 'package:role_heroes/modules/games/models/game.dart';
import 'package:role_heroes/modules/heroes/models/user_hero.dart';

abstract class IUserHeroController {
  Future<List<UserHero>> getList(int gameId);
  Future<UserHero> getDetail(int heroId);
  Future<List<Game>> gamesForCreateUserHero();
  Future create(int gameId, String name);
  Future delete(int heroId);

  Future updateData(UserHero hero, Map<String, dynamic> data);
  Future updateCharacteristic(UserHero hero, Characteristic characteristic);
  Future updateAttribute(UserHero hero, Attribute attribute);
}
