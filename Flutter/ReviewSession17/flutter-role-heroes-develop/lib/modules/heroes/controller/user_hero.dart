import 'package:meta/meta.dart';
import 'package:role_heroes/controllers/user_hero.dart';
import 'package:role_heroes/models/attribute.dart';
import 'package:role_heroes/models/characteristic/characteristic.dart';
import 'package:role_heroes/modules/games/models/game.dart';
import 'package:role_heroes/modules/heroes/models/user_hero.dart';
import 'package:role_heroes/repository/games.dart';
import 'package:role_heroes/repository/user_heroes.dart';

class UserHeroController implements IUserHeroController {
  final IGamesRepository gamesRepository;
  final IUserHeroesRepository userHeroRepository;
  final IUserHeroDataRepository userHeroDataRepository;

  UserHeroController({
    @required this.gamesRepository,
    @required this.userHeroRepository,
    @required this.userHeroDataRepository,
  });

  @override
  Future<List<UserHero>> getList(int gameId) {
    return userHeroRepository.getListByGame(gameId);
  }

  @override
  Future<UserHero> getDetail(int heroId) {
    return userHeroRepository.getDetail(heroId);
  }

  @override
  Future<List<Game>> gamesForCreateUserHero() {
    return gamesRepository.getList();
  }

  @override
  Future create(int gameId, String name) async {
    return await userHeroRepository.create({
      'game_id': gameId,
      'name': name,
    });
  }

  @override
  Future<dynamic> delete(int heroId) {
    return userHeroRepository.delete(heroId);
  }

  @override
  Future updateData(UserHero hero, Map<String, dynamic> data) {
    return userHeroRepository.update(hero, data);
  }

  @override
  Future updateCharacteristic(UserHero hero, Characteristic characteristic) {
    return userHeroDataRepository.updateCharacteristic(hero, characteristic);
  }

  @override
  Future updateAttribute(UserHero hero, Attribute attribute) {
    return userHeroDataRepository.updateAttribute(hero, attribute);
  }
}
